package io.github.wylan.captcha;

import io.github.wylan.captcha.inventory.CaptchaInventory;
import io.github.wylan.captcha.listener.GenericListener;
import io.github.wylan.captcha.system.SpigotPlugin;
import me.saiintbrisson.minecraft.ViewFrame;

public class CaptchaPlugin extends SpigotPlugin {

    @Override
    public void onEnable() {
        ViewFrame viewFrame = ViewFrame.of(this);
        viewFrame.with(new CaptchaInventory(this));
        viewFrame.register();

        registerListener(
                new GenericListener(viewFrame, this)
        );


        getLogger().info("captcha iniciado.");
    }

    @Override
    public void onDisable() {

        getLogger().info("captcha desligado.");
    }

}
