package net.shotbow.ToggleSneak.keyboard;

import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.shotbow.ToggleSneak.ToggleSneak;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyMapping {

    private @Getter KeyBinding toggleSneakKey;
    private @Getter KeyBinding toggleSprintKey;

    public KeyMapping(){
        this.toggleSneakKey = getKeyBinding("toggle.sneak", GLFW.GLFW_KEY_G);
        this.toggleSprintKey = getKeyBinding("toggle.sprint", GLFW.GLFW_KEY_H);
        ClientRegistry.registerKeyBinding(this.toggleSneakKey);
        ClientRegistry.registerKeyBinding(this.toggleSprintKey);
    }

    private KeyBinding getKeyBinding(String key, int keycode){
        return new KeyBinding(
                "keybinding." + key,
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputMappings.Type.KEYSYM,
                keycode,
                ToggleSneak.MOD_TITLE
        );
    }

}
