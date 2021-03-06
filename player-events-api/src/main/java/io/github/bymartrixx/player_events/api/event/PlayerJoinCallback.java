package io.github.bymartrixx.player_events.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

/**
 * @deprecated Please use {@link io.github.bymartrixx.playerevents.api.event.PlayerJoinCallback} instead.
 */
@Deprecated
public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class, (listeners) -> (player, server) -> {
        for (PlayerJoinCallback listener : listeners) {
            ActionResult result = listener.join(player, server);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult join(ServerPlayerEntity player, MinecraftServer server);
}
