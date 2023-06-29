package net.shotbow.ToggleSneak.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.shotbow.ToggleSneak.object.ToggleConfig;

public class ConfigScreen extends Screen {
    private static final int TITLE_HEIGHT = 8;
    private static final int BUTTON_WIDTH = 150;
    private static final int LONG_BUTTON_WIDTH = BUTTON_WIDTH * 2;
    private static final int BUTTON_HEIGHT = 20;
    private Slider releaseTimeSlider;

    public ConfigScreen() {
        super(new TranslationTextComponent("configGui.title"));
    }

    @Override
    protected void init() {
        ToggleConfig toggleConfig = ToggleConfig.getInstance();
        int screenWidth = this.width;
        int screenHeight = this.height;
        int buttonGap = 5;
        int buttonX = (screenWidth - BUTTON_WIDTH * 2 - buttonGap) / 2; // center the two buttons horizontally
        int buttonY = TITLE_HEIGHT + 30; // position the two buttons below the title
        int sliderX = (screenWidth - BUTTON_WIDTH * 2) / 2; // center the slider horizontally
        int sliderY = buttonY + BUTTON_HEIGHT + 10; // position the slider below the buttons
        int doneButtonX = (screenWidth - LONG_BUTTON_WIDTH) / 2; // center the done button horizontally
        int doneButtonY = screenHeight - 30; // position the done button near the bottom of the screen

        this.addButton(new OnOffButton(
           buttonX,
           buttonY,
           BUTTON_WIDTH,
           BUTTON_HEIGHT,
           new TranslationTextComponent("configGui.option.toggleSneak"),
           (button) -> {
               OnOffButton onOffButton = (OnOffButton) button;
               onOffButton.setOn(!onOffButton.isOn());
           },
           toggleConfig.getToggleSneak().get()
        ));

        this.addButton(new OnOffButton(
           buttonX + BUTTON_WIDTH + buttonGap,
           buttonY,
           BUTTON_WIDTH,
           BUTTON_HEIGHT,
           new TranslationTextComponent("configGui.option.toggleSprint"),
           (button) -> {
               OnOffButton onOffButton = (OnOffButton) button;
               onOffButton.setOn(!onOffButton.isOn());
           },
           toggleConfig.getToggleSprint().get()
        ));

        this.addWidget(this.releaseTimeSlider = new Slider(
                sliderX,
                sliderY,
                LONG_BUTTON_WIDTH,
                BUTTON_HEIGHT,
                new TranslationTextComponent("configGui.option.shiftReleaseTime").append(": "),
                new StringTextComponent(""),
                toggleConfig.getShiftReleaseMinimum(),
                toggleConfig.getShiftReleaseMaximum(),
                toggleConfig.getShiftReleaseTime().get(),
                false,
                true,
                (slider) -> {}
        ));

        this.addButton(new Button(
                doneButtonX,
                doneButtonY,
                LONG_BUTTON_WIDTH,
                BUTTON_HEIGHT,
                new TranslationTextComponent("gui.done"),
                (button) -> onClose()
        ));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float ticks) {
        // renderDirBackground parameter is always 0
        this.renderDirtBackground(0);
        this.font.draw(matrixStack, this.title.getVisualOrderText(), (this.width / 2) - (font.width(this.title.getVisualOrderText()) / 2), TITLE_HEIGHT, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, ticks);
    }

    @Override
    public void onClose() {
        ToggleConfig.getInstance().getShiftReleaseTime().set(this.releaseTimeSlider.getValue());
        ToggleConfig.save();
        super.onClose();
    }
}
