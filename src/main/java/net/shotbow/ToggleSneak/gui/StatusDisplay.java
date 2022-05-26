package net.shotbow.ToggleSneak.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
        if (e.getType() != RenderGameOverlayEvent.ElementType.ALL
                || minecraft.player == null
                || !config.getToggleDisplay().get()) {
            return;
        }
        ToggleStatus status = ToggleSneak.getToggleSneak().getToggleStatus();
        int midPoint = e.getWindow().getGuiScaledHeight() / 2;
        if(config.getToggleSneak().get()) {
            // Render sneaking display
            MutableComponent sneakingDisplayText = new TranslatableComponent("displayGui.sneaking")
                    .withStyle(ChatFormatting.WHITE);
            if (status.isSneakingToggled()) {
                sneakingDisplayText.withStyle(ChatFormatting.GOLD);
            }
            GuiComponent.drawString(
                    e.getMatrixStack(),
                    minecraft.font,
                    sneakingDisplayText,
                    1, //X
                    midPoint, //Y
                    0 //Z
            );
        }
        if(config.getToggleSprint().get()) {
            // Render sprinting display
            MutableComponent sprintingDisplayText = new TranslatableComponent("displayGui.sprinting")
                    .withStyle(ChatFormatting.GOLD);
            int yHeight = midPoint;
            if(config.getToggleSneak().get()){
                yHeight += 10;
            }
            GuiComponent.drawString(
                    e.getMatrixStack(),
                    minecraft.font,
                    sprintingDisplayText,
                    1, //X
                    yHeight, //Y
                    0 //Z
            );
        }
    }

}
