package net.shotbow.ToggleSneak.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Credit to <a href="https://leo3418.github.io/2021/03/31/forge-mod-config-screen-1-16.html">leo3418</a>
 * for their great tutorial.
 */
public class ConfigScreen extends Screen {
    private static final int TITLE_HEIGHT = 8;
    private static final int OPTIONS_LIST_TOP_HEIGHT = 24;
    private static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
    private static final int OPTIONS_LIST_ITEM_HEIGHT = 25;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int DONE_BUTTON_TOP_OFFSET = 26;
    private OptionsList optionsRowList;

    public ConfigScreen() {
        super(new TranslatableComponent("configGui.title"));
    }

    @Override
    protected void init() {
        this.optionsRowList = new OptionsList(
                Minecraft.getInstance(), this.width, this.height,
                OPTIONS_LIST_TOP_HEIGHT,
                this.height - OPTIONS_LIST_BOTTOM_OFFSET,
                OPTIONS_LIST_ITEM_HEIGHT
        );
        ToggleConfig toggleConfig = ToggleConfig.getInstance();
        this.optionsRowList.addBig(CycleOption.createOnOff(
                "configGui.option.toggleSneak",
                (options) -> toggleConfig.getToggleSneak().get(),
                (options, option, bool) -> toggleConfig.setToggleSneak(bool)
        ));
        this.optionsRowList.addBig(CycleOption.createOnOff(
                "configGui.option.toggleSprint",
                (options) -> toggleConfig.getToggleSprint().get(),
                (options, option, bool) -> toggleConfig.getToggleSprint().set(bool)
        ));
        this.optionsRowList.addBig(CycleOption.createOnOff(
                "configGui.option.toggleDisplay",
                (options) -> toggleConfig.getToggleDisplay().get(),
                (options, option, bool) -> toggleConfig.getToggleDisplay().set(bool)
        ));
        this.optionsRowList.addBig(
            new ProgressOption("configGui.option.shiftReleaseTime", 500D, 1000D, 1F,
                (options) -> toggleConfig.getShiftReleaseTime().get(),
                (options, value) -> toggleConfig.getShiftReleaseTime().set((double) (Math.round(value * 100) / 100)),
                (options, progressOption) -> {
                    double value = progressOption.toPct(toggleConfig.getShiftReleaseTime().get());
                    return new TextComponent(
                            new TranslatableComponent("configGui.option.shiftReleaseTime").getString()
                           + String.format(": %.0f", progressOption.toValue(value))
                    );
                }
            )
        );
        this.addWidget(optionsRowList);
        this.addRenderableWidget(new Button(
                (this.width - BUTTON_WIDTH) / 2,
                this.height - DONE_BUTTON_TOP_OFFSET,
                BUTTON_WIDTH,
                BUTTON_HEIGHT,
                new TranslatableComponent("configGui.done"),
                button -> this.onClose()
        ));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float ticks) {
        this.renderBackground(poseStack);
        this.optionsRowList.render(poseStack, mouseX, mouseY, ticks);
        drawCenteredString(poseStack, this.font, this.title.getString(),
                this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
        super.render(poseStack, mouseX, mouseY, ticks);
    }

    @Override
    public void onClose() {
        super.onClose();
        ToggleConfig.save();
    }
}
