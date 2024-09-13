package io.github.wylan.captcha.builder;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (short)((byte)data));
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack otherItem) {
        this.itemStack = otherItem;
        this.itemMeta = otherItem.getItemMeta();
    }

    public ItemBuilder itemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder itemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public ItemBuilder material(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder name(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(String... line) {
        List<String> lore = this.itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList();
        }

        ((List)lore).addAll(Arrays.asList(line));
        this.itemMeta.setLore((List)lore);
        return this;
    }

    public ItemBuilder addLoreLineIf(boolean b, String... line) {
        if (b) {
            this.addLoreLine(line);
        }

        return this;
    }

    public ItemBuilder durability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder data(MaterialData materialData) {
        this.itemStack.setData(materialData);
        return this;
    }

    public ItemBuilder acceptItemStack(Consumer<ItemStack> consumer) {
        consumer.accept(this.itemStack);
        return this;
    }

    public ItemBuilder acceptItemMeta(Consumer<ItemMeta> consumer) {
        consumer.accept(this.itemMeta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder hideEnchantments() {
        this.addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        SkullMeta skull = (SkullMeta)this.itemMeta;
        this.itemStack.setDurability((short)3);
        skull.setOwner(owner);
        return this;
    }
    public ItemBuilder armor(Color color) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta)this.itemMeta;
        armorMeta.setColor(color);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemMeta getItemMeta() {
        return this.itemMeta;
    }
}