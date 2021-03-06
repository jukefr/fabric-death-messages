package io.github.bymartrixx.player_events.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

/**
 * @deprecated Please use {@link io.github.bymartrixx.playerevents.api.event.PlayerLeaveCallback} instead.
 */
@Deprecated
public interface PlayerLeaveCallback {
    Event<PlayerLeaveCallback> EVENT = EventFactory.createArrayBacked(PlayerLeaveCallback.class, (listeners) -> (player, server) -> {
        for (PlayerLeaveCallback listener : listeners) {
            ActionResult result = listener.leave(player, server);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return  ActionResult.PASS;
    });

    ActionResult leave(ServerPlayerEntity player, MinecraftServer server);
}
