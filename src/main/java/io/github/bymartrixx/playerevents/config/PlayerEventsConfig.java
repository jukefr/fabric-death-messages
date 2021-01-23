package io.github.bymartrixx.playerevents.config;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.bymartrixx.playerevents.PlayerEvents;
import io.github.bymartrixx.playerevents.Utils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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

    private final Actions death;
    private final Actions join;
    private final Actions killEntity;
    private final Actions killPlayer;
    private final Actions leave;

    public PlayerEventsConfig() {
        this.death = new Actions();
        this.join = new Actions();
        this.killPlayer = new Actions();
        this.killEntity = new Actions();
        this.leave = new Actions();
    }

    public String[] getDeathActions() {
        return this.death.actions;
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
        // TODO: add ${source}?
        MinecraftServer server = player.getServer();
        if (server == null)
            return;

        if (source.getAttacker() == null) {
            String name = source.getName();
            switch (name) {
                case "fall":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} a pris un coup de yesfall", player), true);
                    break;
                case "lava":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} lava", player), true);
                    break;
                case "drown":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} drown", player), true);
                    break;
                case "inWall":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} suffocated", player), true);
                    break;
                case "hotFloor":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} magma block", player), true);
                    break;
                case "flyIntoWall":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} elytra", player), true);
                    break;
                case "wither":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} wither", player), true);
                    break;
                case "outOfWorld":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} void", player), true);
                    break;
                case "inFire":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} campfire", player), true);
                    break;
                case "onFire":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} fire tick", player), true);
                    break;
                case "cactus":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} cactus", player), true);
                    break;
                case "indirectMagic":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} potion", player), true);
                    break;
                case "lightningBolt":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} lightningBolt", player), true);
                    break;
                case "starve":
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} starvation", player), true);
                    break;
                default:
                    Utils.sendMessage(server, player, Utils.replaceGetText("${player} a mourru", player), true);
                    break;
            }

        } else {
            EntityType<?> type = source.getAttacker().getType();
            // u cant do a switch on custom types topkek java
            if (EntityType.ZOMBIE.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("imagine ${player} il se fait zlatan par un zombie mdr", player), true);
            } else if (EntityType.SLIME.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} s'est pris une grosse faciale de slime", player), true);
            } else if (EntityType.SKELETON.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} skelly", player), true);
            } else if (EntityType.HUSK.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} HUSK", player), true);
            } else if (EntityType.SPIDER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} SPIDER", player), true);
            } else if (EntityType.CAVE_SPIDER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} CAVE_SPIDER", player), true);
            } else if (EntityType.WITHER_SKELETON.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} WITHER_SKELETON", player), true);
            } else if (EntityType.WITHER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} WITHER", player), true);
            } else if (EntityType.ENDER_DRAGON.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ENDER_DRAGON", player), true);
            } else if (EntityType.ELDER_GUARDIAN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ELDER_GUARDIAN", player), true);
            } else if (EntityType.TNT.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} TNT", player), true);
            } else if (EntityType.CREEPER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} CREEPER", player), true);
            } else if (EntityType.GHAST.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} GHAST", player), true);
            } else if (EntityType.ENDERMAN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ENDERMAN", player), true);
            } else if (EntityType.SILVERFISH.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} SILVERFISH", player), true);
            } else if (EntityType.WITCH.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} WITCH", player), true);
            } else if (EntityType.SHULKER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} SHULKER", player), true);
            } else if (EntityType.GUARDIAN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} GUARDIAN", player), true);
            } else if (EntityType.IRON_GOLEM.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} IRON_GOLEM", player), true);
            } else if (EntityType.ZOMBIE_VILLAGER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ZOMBIE_VILLAGER", player), true);
            } else if (EntityType.ENDERMITE.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ENDERMITE", player), true);
            } else if (EntityType.PHANTOM.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} PHANTOM", player), true);
            } else if (EntityType.DROWNED.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} DROWNED", player), true);
            } else if (EntityType.PILLAGER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} PILLAGER", player), true);
            } else if (EntityType.RAVAGER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} RAVAGER", player), true);
            } else if (EntityType.ILLUSIONER.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ILLUSIONER", player), true);
            } else if (EntityType.BEE.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} BEE", player), true);
            } else if (EntityType.WOLF.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} WOLF", player), true);
            } else if (EntityType.LLAMA.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} LLAMA", player), true);
            } else if (EntityType.BLAZE.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} BLAZE", player), true);
            } else if (EntityType.STRAY.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} STRAY", player), true);
            } else if (EntityType.VEX.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} VEX", player), true);
            } else if (EntityType.VINDICATOR.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} VINDICATOR", player), true);
            } else if (EntityType.PUFFERFISH.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} PUFFERFISH", player), true);
            } else if (EntityType.POLAR_BEAR.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} POLAR_BEAR", player), true);
            } else if (EntityType.DOLPHIN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} DOLPHIN", player), true);
            } else if (EntityType.PANDA.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} PANDA", player), true);
            } else if (EntityType.HOGLIN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} HOGLIN", player), true);
            } else if (EntityType.PIGLIN.equals(type) || EntityType.PIGLIN_BRUTE.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} PIGLIN", player), true);
            } else if (EntityType.ZOGLIN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ZOGLIN", player), true);
            } else if (EntityType.ZOMBIFIED_PIGLIN.equals(type)) {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} ZOMBIFIED_PIGLIN", player), true);
            } else {
                Utils.sendMessage(server, player, Utils.replaceGetText("${player} s'est fait zlatan", player), true);
            }
        }
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

    public void testDeathActions(ServerCommandSource source) {
        String message = "Death actions (" + (death.broadcastToEveryone ? "Send to everyone" : "Send only to the player") + "):";
        source.sendFeedback(new LiteralText(message).formatted(Formatting.GRAY, Formatting.ITALIC), false);
        for (String action : death.actions) {
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
        testDeathActions(source);
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
}
