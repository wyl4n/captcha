package io.github.wylan.captcha.inventory;

import com.google.common.collect.Lists;
import io.github.wylan.captcha.CaptchaPlugin;
import io.github.wylan.captcha.builder.ItemBuilder;
import io.github.wylan.captcha.item.CaptchaItem;
import lombok.val;
import me.saiintbrisson.minecraft.OpenViewContext;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class CaptchaInventory extends View {
    private static final int[] SLOTS = new int[]{10, 12, 14, 16};
    private final CaptchaPlugin plugin;


    public CaptchaInventory(CaptchaPlugin plugin) {
        super(3);
        this.plugin = plugin;

        setCancelOnClick(true);
        setCancelOnDrag(true);
    }

    @Override
    protected void onOpen(@NotNull OpenViewContext context) {
        val player = context.getPlayer();
        CaptchaItem correctCaptchaItem = CaptchaItem.values()[new Random().nextInt(CaptchaItem.values().length)];

        Map<MaterialData, Boolean> items = new LinkedHashMap<>();

        {
            List<MaterialData> mustClickItems = Lists.newArrayList(correctCaptchaItem.getItems());

            mustClickItems.forEach(materialData -> items.put(materialData, false));

            while (items.size() < SLOTS.length) {
                CaptchaItem randomEnumCaptchaItem = CaptchaItem.values()[new Random().nextInt(CaptchaItem.values().length)];
                MaterialData materialData = randomEnumCaptchaItem.getRandomItem();

                if (mustClickItems.stream().anyMatch(item -> item.getItemType() == materialData.getItemType()) || correctCaptchaItem.getConflicts().contains(materialData)) {
                    continue;
                }

                items.put(materialData, false);
            }
        }

        {
            List<Map.Entry<MaterialData, Boolean>> list = Lists.newArrayList(items.entrySet());

            Collections.shuffle(list);

            items.clear();
            list.forEach(entry -> items.put(entry.getKey(), entry.getValue()));
        }

        player.removeMetadata("captcha-success", plugin);

        context.setContainerTitle("Clique " + correctCaptchaItem.getPhrase());

        context.set("captcha-correct", correctCaptchaItem);
        context.set("captcha-items", items);
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        context.update();
    }

    @Override
    protected void onUpdate(@NotNull ViewContext context) {
        val player = context.getPlayer();

        Runnable failRunnable = context.get("fail-runnable");
        Runnable successRunnable = context.get("success-runnable");

        CaptchaItem correctCaptchaItem = context.get("captcha-correct");
        Map<MaterialData, Boolean> items = context.get("captcha-items");

        int slotIndex = 0;

        for (Map.Entry<MaterialData, Boolean> entry : items.entrySet()) {
            boolean selected = entry.getValue();
            MaterialData materialData = entry.getKey();
            ItemBuilder builder = new ItemBuilder(materialData.getItemType());

            builder.name("§a§lCAPTCHA!");
            builder.durability(materialData.getData());
            builder.addFlags(ItemFlag.values());

            if (selected) {
                builder.enchantment(Enchantment.DAMAGE_ALL, 1);
                builder.hideEnchantments();
                builder.lore("§aSelecionado.");
            } else {
                builder.lore("§aClique para selecionar.");
            }

            context.slot(SLOTS[slotIndex++]).withItem(builder.build()).onClick(handler -> {
                if (selected) {
                    items.put(materialData, false);
                } else {
                    if(!correctCaptchaItem.getItems().contains(materialData)) {
                        player.closeInventory();
                        failRunnable.run();
                        return;
                    }

                    items.put(materialData, true);

                    List<MaterialData> selectedItems = items.entrySet().stream()
                            .filter(Map.Entry::getValue)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

                    if (selectedItems.size() == correctCaptchaItem.getItems().size()) {


                        if (equals(selectedItems, correctCaptchaItem.getItems())) {
                            player.setMetadata("captcha-success", new FixedMetadataValue(plugin, true));
                            successRunnable.run();
                        } else {
                            failRunnable.run();
                        }

                        context.close();

                        return;
                    }

                }

                handler.update();
            });
        }
    }

    @Override
    protected void onClose(@NotNull ViewContext context) {
        val player = context.getPlayer();
        context.setCancelled(!player.hasMetadata("captcha-success"));
    }

    private static boolean equals(@NotNull List<MaterialData> list1, @NotNull List<MaterialData> list2) {
        if (list1.size() != list2.size()) return false;

        return new HashSet<>(list2).containsAll(list1);
    }

}