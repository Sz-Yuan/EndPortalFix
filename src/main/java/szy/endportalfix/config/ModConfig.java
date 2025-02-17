package szy.endportalfix.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import szy.endportalfix.EndPortalFix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static ModConfig INSTANCE;
    private int renderMode = 1;

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModConfig();
            INSTANCE.load();
        }
        return INSTANCE;
    }

    private Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("endportalfix.json");
    }

    public void load() {
        try {
            JsonObject obj = new JsonObject();
            obj.addProperty("renderMode", renderMode);
            Files.writeString(getConfigPath(), new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(obj));
        } catch (IOException e) {
            EndPortalFix.LOGGER.error("Error while loading config", e);
        }
    }
    public void save() {
        try {
            JsonObject obj = new JsonObject();
            obj.addProperty("renderMode", renderMode);

            Files.writeString(getConfigPath(), new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(obj));
        } catch (IOException e) {
            EndPortalFix.LOGGER.error("Error while saving config", e);
        }
    }

    public int getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(int mode) {
        this.renderMode = Math.max(0, Math.min(2, mode));
    }
}
