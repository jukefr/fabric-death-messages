package io.github.bymartrixx.player_events.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

/**
 * @deprecated Please use {@link io.github.bymartrixx.playerevents.api.event.PlayerDeathCallback} instead.
 */
@Deprecated
public interface PlayerDeathCallback {
    Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class, (listeners) -> (player, source) ->{
        for (PlayerDeathCallback listener : listeners) {
            ActionResult result = listener.interact(player, source);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult interact(ServerPlayerEntity player, DamageSource source);
}
