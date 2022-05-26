package net.shotbow.ToggleSneak.listeners;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.object.ToggleConfig;

public class KeyboardListener {

    @SubscribeEvent
    public void keyPress(TickEvent.ClientTickEvent e){
        if(e.phase != TickEvent.Phase.END)
            return;
        //Handle toggling of options
        ToggleConfig config = ToggleConfig.getInstance();
        ToggleSneak toggleSneak = ToggleSneak.getToggleSneak();
        if(toggleSneak.getKeyBinding().getToggleSneakKey().isDown()
                && toggleSneak.getKeyBinding().getToggleSneakKey().consumeClick()){
            //Toggle Sneak pressed
            final boolean setTo = !config.getToggleSneak().get();
            ToggleConfig.getInstance().setToggleSneak(setTo);
        }
        if(toggleSneak.getKeyBinding().getToggleSprintKey().isDown()
                && toggleSneak.getKeyBinding().getToggleSprintKey().consumeClick()){
            //Toggle Sprint pressed
            final boolean setTo = !config.getToggleSprint().get();
            ToggleConfig.getInstance().getToggleSprint().set(setTo);
        }
    }


}
