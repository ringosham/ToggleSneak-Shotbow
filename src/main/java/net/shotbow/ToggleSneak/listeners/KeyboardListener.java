package net.shotbow.ToggleSneak.listeners;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.keyboard.KeyMapping;
import net.shotbow.ToggleSneak.object.ToggleConfig;

public class KeyboardListener {

    @SubscribeEvent
    public void keyPress(TickEvent.ClientTickEvent e){
        if(e.phase != TickEvent.Phase.END)
            return;
        //Handle toggling of options
        KeyMapping keyBinding = ToggleSneak.getToggleSneak().getKeyBinding();
        ToggleConfig config = ToggleConfig.getInstance();
        if(keyBinding.getToggleSneakKey() != null && isPressed(keyBinding.getToggleSneakKey())){
            //Toggle Sneak pressed
            final boolean setTo = !config.getToggleSneak().get();
            ToggleConfig.getInstance().setToggleSneak(setTo);
        }
        if(keyBinding.getToggleSprintKey() != null && isPressed(keyBinding.getToggleSprintKey())){
            //Toggle Sprint pressed
            final boolean setTo = !config.getToggleSprint().get();
            ToggleConfig.getInstance().getToggleSprint().set(setTo);
        }
    }

    private boolean isPressed(KeyBinding mapping){
        return mapping.isDown()
                && mapping.getKeyConflictContext().isActive()
                && mapping.consumeClick();
    }

}
