package io.github.wylan.captcha.listener;

import com.google.common.collect.ImmutableMap;
import io.github.wylan.captcha.CaptchaPlugin;
import io.github.wylan.captcha.inventory.CaptchaInventory;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GenericListener implements Listener {
    private final ViewFrame viewFrame;
    private final CaptchaPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent event) {
        val player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {


    Runnable failCallback = () -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.closeInventory();
                player.kickPlayer("§cVocê errou o captcha!");
            }, 3L);
        };

        Runnable successCallback = () -> {
            player.sendMessage("§a§lYAY! §aVocê passou do captcha.");
        };

        viewFrame.open(CaptchaInventory.class, player, ImmutableMap.of(
                "fail-runnable", failCallback,
                "success-runnable", successCallback
        ));
        }, 3L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void kick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        val player = event.getPlayer();
        player.removeMetadata("captcha-success", plugin);
    }
}
