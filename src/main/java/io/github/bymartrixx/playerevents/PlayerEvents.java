package io.github.bymartrixx.playerevents;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.bymartrixx.playerevents.api.event.*;
import io.github.bymartrixx.playerevents.command.PlayerEventsCommand;
import io.github.bymartrixx.playerevents.config.PlayerEventsConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerEvents implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    public static final String MOD_ID = "player_events";
    public static final String MOD_NAME = "Player Events";
    public static final String VERSION = "2.0.0";

    public static PlayerEventsConfig CONFIG;

    @Override
    public void onInitializeServer() {
        log(Level.INFO, "Initializing...");

        PlayerEventsConfig.Manager.loadConfig();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> PlayerEventsCommand.register(dispatcher));

        PlayerDeathCallback.EVENT.register((player, source) -> {
            CONFIG.runDeathActions(player, source);
            return ActionResult.PASS;
        });

        PlayerJoinCallback.EVENT.register((player, server) -> {
            CONFIG.runJoinActions(server, player);
            return ActionResult.PASS;
        });

        PlayerLeaveCallback.EVENT.register((player, server) -> {
            CONFIG.runLeaveActions(server, player);
            return ActionResult.PASS;
        });

        PlayerKillPlayerCallback.EVENT.register((player, killedPlayer) -> {
            CONFIG.runKillPlayerActions(player.getServer(), player, killedPlayer);
            return ActionResult.PASS;
        });

        PlayerKillEntityCallback.EVENT.register((player, killedEntity) -> {
            CONFIG.runKillEntityActions(player.getServer(), player, killedEntity);
            return ActionResult.PASS;
        });

        log(Level.INFO, "Initialized {} version {}", MOD_NAME, VERSION);
    }

    public static void log(Level level, String message) {
        log(level, message, (Object) null);
    }

    public static void log(Level level, String message, Object ... fields){
        LOGGER.log(level, "["+MOD_NAME+"] " + message, fields);
    }
}