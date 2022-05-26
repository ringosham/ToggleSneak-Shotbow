package net.shotbow.ToggleSneak.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.shotbow.ToggleSneak.ToggleSneak;

public class KeyBinding {

    private final String toggleSneakCategory = ToggleSneak.MOD_TITLE;
    private @Getter KeyMapping toggleSneakKey;
    private @Getter KeyMapping toggleSprintKey;

    public KeyBinding(){
        KeyMapping.createNameSupplier(toggleSneakCategory);
        registerKeys();
    }

    private void registerKeys() {
        this.toggleSneakKey = registerMapping("toggle.sneak", InputConstants.KEY_G);
        this.toggleSprintKey = registerMapping("toggle.sprint", InputConstants.KEY_H);
    }

    private KeyMapping registerMapping(String key, int keycode){
        final KeyMapping keyMapping = new KeyMapping("keybinding." + key, keycode, toggleSneakCategory);
        ClientRegistry.registerKeyBinding(keyMapping);
        return keyMapping;
    }

}
