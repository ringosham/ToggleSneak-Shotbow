package net.shotbow.ToggleSneak.object;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import lombok.Data;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;

public @Data class ToggleConfig {

    @Getter
    private static final ToggleConfig instance;
    private static final ForgeConfigSpec clientConfig;
    private final ForgeConfigSpec.BooleanValue toggleSneak;
    private final ForgeConfigSpec.BooleanValue toggleSprint;
    private final ForgeConfigSpec.BooleanValue toggleDisplay;
    private final ForgeConfigSpec.ConfigValue<Double> shiftReleaseTime;

    static {
        Pair<ToggleConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ToggleConfig::new);
        clientConfig = specPair.getRight();
        instance = specPair.getLeft();
    }

    private ToggleConfig(ForgeConfigSpec.Builder configSpecBuilder){
        toggleSneak = configSpecBuilder
                .comment("Toggle Sneaking ON (true) or OFF (false)")
                .define("toggleSneak", true);
        toggleSprint = configSpecBuilder
                .comment("Toggle Sprinting ON (true) or OFF (false)")
                .define("toggleSprint", true);
        toggleDisplay = configSpecBuilder
                .comment("Display the status UI in-game")
                .define("toggleDisplay", true);
        shiftReleaseTime = configSpecBuilder
                .comment("This is the time in milliseconds that determines if shift should toggle or hold sneaking.",
                  "If you're holding LONGER than this time, sneaking will be held.",
                  "If you're holding LESS than (or equal to) this time, sneaking will toggle.")
                .define("shiftReleaseTime", 800D);
    }

    public static void loadConfigFile(Path path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        clientConfig.setConfig(file);
    }

    public static void save(){
        clientConfig.save();
    }

    public void setToggleSneak(boolean toggle){
        if(toggle)
            Minecraft.getInstance().options.toggleCrouch = false;
        toggleSneak.set(toggle);
    }

}
