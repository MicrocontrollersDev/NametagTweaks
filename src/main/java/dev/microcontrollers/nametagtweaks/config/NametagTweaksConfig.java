package dev.microcontrollers.nametagtweaks.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.ConfigInstance;
import dev.isxander.yacl3.config.GsonConfigInstance;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class NametagTweaksConfig {
    public static final ConfigInstance<NametagTweaksConfig> CONFIG = GsonConfigInstance.createBuilder(NametagTweaksConfig.class)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve("nametagtweaks.json"))
            .build();

    @ConfigEntry public boolean removeNametags = false;
    @ConfigEntry public boolean showOwnNametags = false;
    @ConfigEntry public boolean hideEntityNametagsInHiddenHud = true;
    @ConfigEntry public boolean hidePlayerNametagsInHiddenHud = false;
    @ConfigEntry public boolean hideArmorStandNametagsInHiddenHud = false;
    @ConfigEntry public int nametagOffset = 0;
    @ConfigEntry public Color nametagColor = new Color(0, 0, 0, 63);
    @ConfigEntry public boolean nametagTextShadow = false;
    @ConfigEntry public float nametagScale = 1F;

    @SuppressWarnings("deprecation")
    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.literal("Nametag Tweaks"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Nametag Tweaks"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Remove Nametags"))
                                .description(OptionDescription.of(Text.of("Removes all nametags.")))
                                .binding(defaults.removeNametags, () -> config.removeNametags, newVal -> config.removeNametags = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Show Own Nametag"))
                                .description(OptionDescription.of(Text.of("Show your own nametag when in third person perspective.")))
                                .binding(defaults.showOwnNametags, () -> config.showOwnNametags, newVal -> config.showOwnNametags = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Entity Nametags in F1 Mode"))
                                .description(OptionDescription.of(Text.of("Hide non-player and non-armor stand nametags in F1 mode when the HUD is hidden.")))
                                .binding(defaults.hideEntityNametagsInHiddenHud, () -> config.hideEntityNametagsInHiddenHud, newVal -> config.hideEntityNametagsInHiddenHud = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Player Nametags in F1 Mode"))
                                .description(OptionDescription.of(Text.of("Hide player nametags in F1 mode when the HUD is hidden.")))
                                .binding(defaults.hidePlayerNametagsInHiddenHud, () -> config.hidePlayerNametagsInHiddenHud, newVal -> config.hidePlayerNametagsInHiddenHud = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Armor Stand Nametags in F1 Mode"))
                                .description(OptionDescription.of(Text.of("Hide armor stand nametags in F1 mode when the HUD is hidden.")))
                                .binding(defaults.hideArmorStandNametagsInHiddenHud, () -> config.hideArmorStandNametagsInHiddenHud, newVal -> config.hideArmorStandNametagsInHiddenHud = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Nametag Height Offset"))
                                .description(OptionDescription.of(Text.of("Change the height offset of nametags.")))
                                .binding(0, () -> config.nametagOffset, newVal -> config.nametagOffset = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(-10, 10)
                                        .step(1))
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("Nametag Background Color"))
                                .description(OptionDescription.of(Text.of("Allows setting a custom color for the background of nametags.")))
                                .binding(defaults.nametagColor, () -> config.nametagColor, value -> config.nametagColor = value)
                                .customController(opt -> new ColorController(opt, true))
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Nametag Text Shadow"))
                                .description(OptionDescription.of(Text.of("Adds a textshadow to nametags. The shadow renders in front of the regular text, which seems to be a vanilla Minecraft bug.")))
                                .binding(defaults.nametagTextShadow, () -> config.nametagTextShadow, newVal -> config.nametagTextShadow = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(float.class)
                                .name(Text.literal("Nametag Scale"))
                                .description(OptionDescription.of(Text.of("Allow scaling for Minecraft nametags.")))
                                .binding(1F, () -> config.nametagScale, newVal -> config.nametagScale = newVal)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                        .range(0.1F, 1F)
                                        .step(0.1F))
                                .build())

                        .build())
        )).generateScreen(parent);
    }

}
