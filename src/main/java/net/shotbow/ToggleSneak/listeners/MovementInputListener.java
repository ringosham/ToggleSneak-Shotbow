package net.shotbow.ToggleSneak.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import net.shotbow.ToggleSneak.object.ToggleStatus;

import java.time.Duration;
import java.time.Instant;

public class MovementInputListener {

    private final KeyBinding shiftKey = Minecraft.getInstance().options.keyShift;
    private Instant shiftTime = null;
    private boolean isDismounting = false;
    private int dismountingTicks = 0;

    @SubscribeEvent
    public void moveEvent(InputUpdateEvent e) {
        ToggleConfig config = ToggleConfig.getInstance();
        final boolean isSneakingEnabled = config.getToggleSneak().get();
        final boolean isSprintingEnabled = config.getToggleSprint().get();
        if (!isSneakingEnabled && !isSprintingEnabled) {
            return;
        }
        ToggleStatus settings = ToggleSneak.getToggleSneak().getToggleStatus();
        PlayerEntity player = (PlayerEntity) e.getEntity();
        if (isSprintingEnabled && !player.isCrouching()) player.setSprinting(true);

        if (player.isPassenger() || isDismounting || player.abilities.flying || !isSneakingEnabled) {
            return;
        }
        if (settings.isSneakingToggled()) {
            e.getMovementInput().shiftKeyDown = true;
        }
        if (shiftKey.isDown()) {
            //Pressing Shift
            if (shiftTime == null) {
                shiftTime = Instant.now();
            }
        } else {
            //Not pressing shift
            if (shiftTime == null)
                return;
            long diff = Duration.between(shiftTime, Instant.now()).toMillis();
            shiftTime = null;
            if (diff <= config.getShiftReleaseTime().get() && shiftKey.consumeClick()) {
                if (settings.isSneakingToggled()) {
                    settings.setSneakingToggled(false);
                    return;
                }
                settings.setSneakingToggled(true);
                e.getMovementInput().shiftKeyDown = true;
            }
        }
    }

    @SubscribeEvent
    public void setDismounting(EntityMountEvent e){
        PlayerEntity player = ToggleSneak.getToggleSneak().getMinecraft().player;
        if(e.isMounting()
                || player == null
                || e.getEntityMounting() == null
                || !(e.getEntityMounting() instanceof PlayerEntity)
        ) {
            return;
        }
        PlayerEntity playerMounting = (PlayerEntity) e.getEntityMounting();
        if(!playerMounting.is(player))
            return;
        dismountingTicks = 0;
        isDismounting = true;
    }

    @SubscribeEvent
    public void unsetDismounting(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END)
            return;
        if(isDismounting){
            dismountingTicks += 1;
            if(dismountingTicks >= 15){
                dismountingTicks = 0;
                isDismounting = false;
            }
        }
    }

}
