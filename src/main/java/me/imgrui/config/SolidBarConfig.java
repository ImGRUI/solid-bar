package me.imgrui.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import dev.isxander.yacl3.gui.controllers.string.StringController;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SolidBarConfig {
    public static final ConfigClassHandler<SolidBarConfig> CONFIG = ConfigClassHandler.createBuilder(SolidBarConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("solid-bar.json"))
                    .build())
            .build();

    @SerialEntry
    public boolean modEnabled = true;

    @SerialEntry
    public boolean stealthMode = false;

    @SerialEntry
    public String modBar = "&6XYZ:&r {player.pos.x} {player.pos.y} {player.pos.z} &6{player.direction} {world.time}&r";

    @SerialEntry
    public renderTypeEnum renderType = renderTypeEnum.DEFAULT;

    public enum renderTypeEnum {
        DEFAULT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    @SerialEntry
    public int defaultRenderIndentY = 85;

    @SerialEntry
    public int renderIndentX = 20;

    @SerialEntry
    public int renderIndentY = 20;

    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, (defaults, config, builder) -> builder
                .title(Component.translatable("solid-bar"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("solid-bar"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("solid-bar.modEnabled"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.modEnabled.description")))
                                .binding(defaults.modEnabled, () -> config.modEnabled, (value) -> config.modEnabled = value)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        ).option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("solid-bar.stealthMode"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.stealthMode.description")))
                                .binding(defaults.stealthMode, () -> config.stealthMode, (value) -> config.stealthMode = value)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        ).option(Option.<String>createBuilder()
                                .name(Component.translatable("solid-bar.modBar"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.modBar.description")))
                                .binding(defaults.modBar, () -> config.modBar, (value) -> config.modBar = value)
                                .customController(StringController::new)
                                .build()
                        ).option(Option.<SolidBarConfig.renderTypeEnum>createBuilder()
                                .name(Component.translatable("solid-bar.renderType"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.renderType.description")))
                                .binding(defaults.renderType, () -> config.renderType, (value) -> config.renderType = value)
                                .customController(opt -> new EnumController<>(opt, SolidBarConfig.renderTypeEnum.class))
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Component.translatable("solid-bar.defaultRenderIndentY"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.defaultRenderIndentY.description")))
                                .binding(defaults.defaultRenderIndentY, () -> config.defaultRenderIndentY, (value) -> config.defaultRenderIndentY = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0,100).step(1)
                                )
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Component.translatable("solid-bar.renderIndentX"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.renderIndentX.description")))
                                .binding(defaults.renderIndentX, () -> config.renderIndentX, (value) -> config.renderIndentX = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0,100).step(1)
                                )
                                .build()
                        ).option(Option.<Integer>createBuilder()
                                .name(Component.translatable("solid-bar.renderIndentY"))
                                .description(OptionDescription.of(Component.translatable("solid-bar.renderIndentY.description")))
                                .binding(defaults.renderIndentY, () -> config.renderIndentY, (value) -> config.renderIndentY = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0,100).step(1)
                                )
                                .build()
                        )
                        .build()
                )
        ).generateScreen(parent);
    }

    public static SolidBarConfig config() {
        return CONFIG.instance();
    }
}
