package szy.endportalfix;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import szy.endportalfix.config.ModConfig;

import java.util.concurrent.CompletableFuture;

public class EndPortalFix implements ClientModInitializer {
    public static final String MOD_ID = "endportalfix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModConfig.getInstance();
        registerClientCommands();
    }

    private void registerClientCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("endportalfix")
                    .then(ClientCommandManager.argument("mode", IntegerArgumentType.integer(0, 2))
                            .suggests((context, builder) -> {
                                return CompletableFuture.supplyAsync(() -> {
                                    for (int i = 0; i <= 2; i++) {
                                        Component modeName = getModeName(i);
                                        builder.suggest(i, Component.literal(i + " - ").append(modeName));
                                    }
                                    return builder.build();
                                });
                            })
                            .executes(context -> {
                                int mode = IntegerArgumentType.getInteger(context, "mode");
                                ModConfig.getInstance().setRenderMode(mode);
                                ModConfig.getInstance().save();
                                Player player = Minecraft.getInstance().player;
                                if (player != null) {
                                    Component Tongzhi = Component.translatable("endportalfix.message");
                                    Component message = Component.literal("§6EPF: ")
                                            .append(Tongzhi)
                                            .append(" ")
                                            .append(getModeName(mode));
                                    player.sendSystemMessage(message);
                                }
                                return 1;
                            })
                    )
            );
        });
    }

    // 如果需要模式名称显示可以保留
    private Component getModeName(int mode) {
        Component zero = Component.translatable("endportalfix.zero");
        Component one = Component.translatable("endportalfix.one");
        Component two = Component.translatable("endportalfix.two");
        Component mr = Component.translatable("endportalfix.mr");
        return switch (mode) {
            case 0 -> zero;
            case 1 -> one;
            case 2 -> two;
            default -> mr;
        };
    }
}