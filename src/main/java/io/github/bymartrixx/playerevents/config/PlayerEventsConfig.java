package io.github.bymartrixx.playerevents.config;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.bymartrixx.playerevents.PlayerEvents;
import io.github.bymartrixx.playerevents.Utils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.io.*;

public class PlayerEventsConfig {
    public static class Manager {
        private static File configFile;

        public static void prepareConfigFile() {
            if (configFile != null) {
                return;
            }
            configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), PlayerEvents.MOD_ID + ".json");
        }

        public static void createConfigFile() {
            PlayerEvents.CONFIG = new PlayerEventsConfig();

            saveConfig();
        }

        public static void saveConfig() {
            prepareConfigFile();

            String jsonString = PlayerEvents.GSON.toJson(PlayerEvents.CONFIG);
            try (FileWriter fileWriter = new FileWriter(configFile)) {
                fileWriter.write(jsonString);
            } catch (IOException e) {
                System.err.println("Couldn't save Player Events config.");
                e.printStackTrace();
            }
        }

        public static void loadConfig() {
            prepareConfigFile();

            try {
                if (!configFile.exists()) {
                    createConfigFile();
                }
                if (configFile.exists()) {
                    BufferedReader bReader = new BufferedReader(new FileReader(configFile));

                    PlayerEventsConfig savedConfig = PlayerEvents.GSON.fromJson(bReader, PlayerEventsConfig.class);
                    if (savedConfig != null) {
                        PlayerEvents.CONFIG = savedConfig;
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't load configuration for Player Events. Reverting to default.");
                e.printStackTrace();
                createConfigFile();
            }
        }
    }

    private final DeathActions death;
    private final Actions join;
    private final Actions killEntity;
    private final Actions killPlayer;
    private final Actions leave;


    public PlayerEventsConfig() {
        this.death = new DeathActions();
        this.join = new Actions();
        this.killPlayer = new Actions();
        this.killEntity = new Actions();
        this.leave = new Actions();
    }

    public String[][] getDeathNoEntity() {
        return this.death.noEntity;
    }
    public String[][] getDeathYesEntity() {
        return this.death.yesEntity;
    }

    public String[] getJoinActions() {
        return this.join.actions;
    }

    public String[] getKillEntityActions() {
        return this.killEntity.actions;
    }

    public String[] getKillPlayerActions() {
        return this.killPlayer.actions;
    }

    public String[] getLeaveActions() {
        return this.leave.actions;
    }

    public void runDeathActions(ServerPlayerEntity player, DamageSource source) {
        MinecraftServer server = player.getServer();
        if (server == null)
            return;

        if (source.getAttacker() == null) {
            for (String[] noEntity : death.noEntity) {
                if (noEntity[0].equals(source.getName()) && noEntity.length > 1) {
                        Utils.sendMessage(server, player, Utils.replaceGetText(noEntity[1], player), death.broadcastToEveryone);
                        return;
                }
            }
        } else {
            String type = source.getAttacker().getType().toString();
            for (String[] yesEntity : death.yesEntity) {
                if (yesEntity[0].equals(type) && yesEntity.length > 1) {
                    Utils.sendMessage(server, player, Utils.replaceGetText(yesEntity[1], player), death.broadcastToEveryone);
                    return;
                }
            }
        }
        server.getPlayerManager().broadcastChatMessage(player.getDamageTracker().getDeathMessage(), MessageType.SYSTEM, Util.NIL_UUID);
    }

    public void runJoinActions(MinecraftServer server, ServerPlayerEntity player) {
        for (String action : join.actions) {
            if (action.charAt(0) == '/') {
                server.getCommandManager().execute(server.getCommandSource(), Utils.replace(action, player));
            } else {
                Utils.sendMessage(server, player, Utils.replaceGetText(action, player), join.broadcastToEveryone);
            }
        }
    }

    public void runLeaveActions(MinecraftServer server, ServerPlayerEntity player) {
        for (String action : leave.actions) {
            if (action.charAt(0) == '/') {
                server.getCommandManager().execute(server.getCommandSource(), Utils.replace(action, player));
            } else {
                Utils.sendMessage(server, Utils.replaceGetText(action, player));
            }
        }
    }

    public void runKillEntityActions(MinecraftServer server, ServerPlayerEntity player, Entity killedEntity) {
        for (String action : killPlayer.actions) {
            if (action.charAt(0) == '/') {
                String string = Utils.replace(action, player);
                string = Utils.replace(string, "${killedEntity}", killedEntity.getName().asString());
                server.getCommandManager().execute(server.getCommandSource(), string);
            } else {
                MutableText text = Utils.replaceGetText(action, new String[]{"${player}", "${killedEntity}"}, new Text[]{player.getDisplayName(), killedEntity.getDisplayName()});
                Utils.sendMessage(server, player, text, killEntity.broadcastToEveryone);
            }
        }
    }

    public void runKillPlayerActions(MinecraftServer server, ServerPlayerEntity player, ServerPlayerEntity killedPlayer) {
        for (String action : killPlayer.actions) {
            if (action.charAt(0) == '/') {
                String string = Utils.replace(action, player);
                string = Utils.replace(string, "${killedPlayer}", killedPlayer.getName().asString());
                server.getCommandManager().execute(server.getCommandSource(), string);
            } else {
                MutableText text = Utils.replaceGetText(action, new String[]{"${player}", "${killedPlayer}"}, new Text[]{player.getDisplayName(), killedPlayer.getDisplayName()});
                Utils.sendMessage(server, player, text, killPlayer.broadcastToEveryone);
            }
        }
    }

//    public void testDeathActions(ServerCommandSource source) {
//        String message = "Death actions (" + (death.broadcastToEveryone ? "Send to everyone" : "Send only to the player") + "):";
//        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
//        for (String action : death.actions) {
//            try {
//                ServerPlayerEntity player = source.getPlayer();
//                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, player)) : Utils.replaceGetText(action, player);
//                Utils.sendMessage(source, text);
//            } catch (CommandSyntaxException e) {
//                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, "${player}", source.getName())) : Utils.replaceGetText(action, "${player}", source.getDisplayName());
//                Utils.sendMessage(source, text);
//            }
//        }
//    }

    public void testJoinActions(ServerCommandSource source) {
        String message = "Join actions (" + (join.broadcastToEveryone ? "Send to everyone" : "Send only to the player") + "):";
        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
        for (String action : join.actions) {
            try {
                ServerPlayerEntity player = source.getPlayer();
                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, player)) : Utils.replaceGetText(action, player);
                Utils.sendMessage(source, text);
            } catch (CommandSyntaxException e) {
                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, "${player}", source.getName())) : Utils.replaceGetText(action, "${player}", source.getDisplayName());
                Utils.sendMessage(source, text);
            }
        }
    }

    public void testKillEntityActions(ServerCommandSource source) {
        String message = "Kill entity actions (" + (killEntity.broadcastToEveryone ? "Send to everyone" : "Send only to the player") + "):";
        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
        for (String action : killEntity.actions) {
            try {
                ServerPlayerEntity player = source.getPlayer();
                MutableText text;
                if (action.charAt(0) == '/') {
                    String string = Utils.replace(action, player);
                    string = Utils.replace(string, "${killedPlayer}", "dummyEntity");
                    text = new LiteralText(string);
                } else {
                    text = Utils.replaceGetText(action, new String[]{"${player}", "${killedPlayer}"}, new Text[]{player.getDisplayName(), new LiteralText("dummyEntity")});
                }
                Utils.sendMessage(source, text);
            } catch (CommandSyntaxException e) {
                MutableText text;
                if (action.charAt(0) == '/') {
                    String string = Utils.replace(action, "${player}", source.getName());
                    string = Utils.replace(string, "${killedPlayer}", "dummyEntity");
                    text = new LiteralText(string);
                } else {
                    text = Utils.replaceGetText(action, new String[]{"${player}", "${killedPlayer}"}, new Text[]{source.getDisplayName(), new LiteralText("dummyEntity")});
                }
                Utils.sendMessage(source, text);
            }
        }
    }

    public void testKillPlayerActions(ServerCommandSource source) {
        String message = "Kill player actions (" + (killPlayer.broadcastToEveryone ? "Send to everyone" : "Send only to the player") + "):";
        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
        for (String action : killPlayer.actions) {
            try {
                ServerPlayerEntity player = source.getPlayer();
                MutableText text;
                if (action.charAt(0) == '/') {
                    String string = Utils.replace(action, player);
                    string = Utils.replace(string, "${killedPlayer}", player.getName().asString());
                    text = new LiteralText(string);
                } else {
                    text = Utils.replaceGetText(action, new String[]{"${player}", "${killedPlayer}"}, new Text[]{player.getDisplayName(), player.getDisplayName()});
                }
                Utils.sendMessage(source, text);
            } catch (CommandSyntaxException e) {
                MutableText text;
                if (action.charAt(0) == '/') {
                    String string = Utils.replace(action, "${player}", source.getName());
                    string = Utils.replace(string, "${killedPlayer}", source.getName());
                    text = new LiteralText(string);
                } else {
                    text = Utils.replaceGetText(action, new String[]{"${player}", "${killedPlayer}"}, new Text[]{source.getDisplayName(), source.getDisplayName()});
                }
                Utils.sendMessage(source, text);
            }
        }
    }

    public void testLeaveActions(ServerCommandSource source) {
        String message = "Leave actions:";
        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
        for (String action : leave.actions) {
            try {
                ServerPlayerEntity player = source.getPlayer();
                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, player)) : Utils.replaceGetText(action, player);
                Utils.sendMessage(source, text);
            } catch (CommandSyntaxException e) {
                Text text = action.charAt(0) == '/' ? new LiteralText(Utils.replace(action, "${player}", source.getName())) : Utils.replaceGetText(action, "${player}", source.getDisplayName());
                Utils.sendMessage(source, text);
            }
        }
    }

    public void testEveryActionGroup(ServerCommandSource source) {
//        testDeathActions(source);
        testJoinActions(source);
        testKillEntityActions(source);
        testKillPlayerActions(source);
        testLeaveActions(source);
    }

    public static class Actions {
        private final String[] actions;
        private final boolean broadcastToEveryone;

        public Actions() {
            this.actions = new String[]{};
            this.broadcastToEveryone = true;
        }
    }

    public static class DeathActions {
        private final String[][] noEntity;
        private final String[][] yesEntity;
        private final boolean broadcastToEveryone;

        public DeathActions() {
            this.noEntity = new String[][]{
                    {"cactus", "${player} was pricked to death"},
                    {"drown"},
                    {"fall"},
                    {"flyIntoWall"},
                    {"hotFloor"},
                    {"inFire"},
                    {"inWall"},
                    {"indirectMagic"},
                    {"lava"},
                    {"lightningBolt"},
                    {"onFire"},
                    {"outOfWorld"},
                    {"starve"},
                    {"wither"},
            };
            this.yesEntity = new String[][]{
                    {"entity.minecraft.bee", "${player} was stung to death"},
                    {"entity.minecraft.blaze"},
                    {"entity.minecraft.cave_spider"},
                    {"entity.minecraft.creeper"},
                    {"entity.minecraft.dolphin"},
                    {"entity.minecraft.drowned"},
                    {"entity.minecraft.elder_guardian"},
                    {"entity.minecraft.ender_dragon"},
                    {"entity.minecraft.enderman"},
                    {"entity.minecraft.endermite"},
                    {"entity.minecraft.ghast"},
                    {"entity.minecraft.guardian"},
                    {"entity.minecraft.hoglin"},
                    {"entity.minecraft.husk"},
                    {"entity.minecraft.illusioner"},
                    {"entity.minecraft.iron_golem"},
                    {"entity.minecraft.llama"},
                    {"entity.minecraft.panda"},
                    {"entity.minecraft.phantom"},
                    {"entity.minecraft.piglin"},
                    {"entity.minecraft.piglin_brute"},
                    {"entity.minecraft.pillager"},
                    {"entity.minecraft.polar_bear"},
                    {"entity.minecraft.pufferfish"},
                    {"entity.minecraft.ravager"},
                    {"entity.minecraft.shulker"},
                    {"entity.minecraft.silverfish"},
                    {"entity.minecraft.skeleton"},
                    {"entity.minecraft.slime"},
                    {"entity.minecraft.spider"},
                    {"entity.minecraft.stray"},
                    {"entity.minecraft.tnt"},
                    {"entity.minecraft.vex"},
                    {"entity.minecraft.vindicator"},
                    {"entity.minecraft.witch"},
                    {"entity.minecraft.wither"},
                    {"entity.minecraft.wither_skeleton"},
                    {"entity.minecraft.wolf"},
                    {"entity.minecraft.zoglin"},
                    {"entity.minecraft.zombie"},
                    {"entity.minecraft.zombie_villager"},
                    {"entity.minecraft.zombified_piglin"}
            };
            this.broadcastToEveryone = true;
        }
    }
}
