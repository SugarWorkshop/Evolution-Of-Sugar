package io.github.sugarmgp.eos.handler;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    public static ForgeConfigSpec commonConfig;
    public static ForgeConfigSpec.BooleanValue torcherinoExploding;

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        commonBuilder.comment("General Settings").push("general");

        torcherinoExploding = commonBuilder
                .comment("Whether to explode when using the EOS Torcherino")
                .define("torcherinoExploding", true)
        ;

        commonBuilder.pop();
        commonConfig = commonBuilder.build();
    }
}
