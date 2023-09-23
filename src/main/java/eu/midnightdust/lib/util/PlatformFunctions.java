package eu.midnightdust.lib.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformFunctions {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
    public static boolean isClientEnv() {
        return FMLEnvironment.dist.isClient();
    }
    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        // Ignored here, see MidnightLibServerEvents#registerCommands
    }
}
