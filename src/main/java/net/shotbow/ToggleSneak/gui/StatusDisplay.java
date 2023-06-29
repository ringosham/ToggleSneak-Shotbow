package net.shotbow.ToggleSneak.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import net.shotbow.ToggleSneak.object.ToggleStatus;

public class StatusDisplay {

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post e) {
        ToggleConfig config = ToggleConfig.getInstance();
        Minecraft minecraft = ToggleSneak.getToggleSneak().getMinecraft();
        if(minecraft.player == null
                || !config.getToggleDisplay().get()) {
            return;
        }
        ToggleStatus status = ToggleSneak.getToggleSneak().getToggleStatus();
        int midPoint = e.getWindow().getGuiScaledHeight() / 2;
        if(config.getToggleSneak().get()) {
            // Render sneaking display
            IFormattableTextComponent sneakingDisplayText = new TranslationTextComponent("displayGui.sneaking")
                    .withStyle(TextFormatting.WHITE);
            if(status.isSneakingToggled()) {
                sneakingDisplayText.withStyle(TextFormatting.GOLD);
            }
            Minecraft.getInstance().font.draw(
                    e.getMatrixStack(),
                    sneakingDisplayText,
                    1,
                    midPoint,
                    0
            );
        }
        if(config.getToggleSprint().get()) {
            // Render sprinting display
            IFormattableTextComponent sprintingDisplayText = new TranslationTextComponent("displayGui.sprinting")
                    .withStyle(TextFormatting.GOLD);
            int yHeight = midPoint;
            if(config.getToggleSneak().get()) {
                yHeight += 10;
            }
            Minecraft.getInstance().font.draw(
                    e.getMatrixStack(),
                    sprintingDisplayText,
                    1,
                    yHeight,
                    0
            );
        }
    }

}
