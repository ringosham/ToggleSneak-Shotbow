package net.shotbow.ToggleSneak.gui;

import lombok.Getter;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

// 1.16 does not have CycleButtons. We'll have to make our own.
public class OnOffButton extends Button {

    @Getter private boolean on;
    private final IFormattableTextComponent text;
    public OnOffButton(int posX, int posY, int width, int height, IFormattableTextComponent text, IPressable handler, boolean defaultState) {
        super(posX, posY, width, height, text, handler);
        this.text = text;
        this.setMessage(text.append(": ").append(defaultState ? new TranslationTextComponent("options.on") : new TranslationTextComponent("options.off")));
        this.on = defaultState;
    }

    public void setOn(boolean on) {
        this.on = on;
        this.setMessage(this.text.append(": ").append(on ? new TranslationTextComponent("options.on") : new TranslationTextComponent("options.off")));
    }
}
