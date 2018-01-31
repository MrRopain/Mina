package me.ropain.mina.packages.essentials.teleport;


import me.ropain.mina.core.config.Config;
import me.ropain.mina.core.l10n.L10n;
import me.ropain.mina.core.l10n.LocalizableValues;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Contains functions to easily teleport players.
 *
 * @author MrRopain
 */
public class Teleporter {

    private static Teleporter instance;
    private static HashMap<Player, Location<World>> lastLocations = new HashMap<>();

    /**
     * Private to prevent instantiation from outside.
     */
    private Teleporter() {}

    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Player player) {
        updateLastLocation(player);
    }

    /**
     * Teleports an player to another player.
     */
    public static void teleport(Player player, Player target) {
        String message = L10n.localize("teleport/message", LocalizableValues.build("player", target.getName()));

        updateLastLocation(player);
        player.setLocation(target.getLocation());
        player.sendMessage(ChatTypes.ACTION_BAR, Text.of(message));
    }

    /**
     * Teleports the player to the given location.
     */
    public static void teleport(Player player, Location<World> location) {
        String message = L10n.localize("teleport_location/message", LocalizableValues.build()
                .add("x", location.getBlockX())
                .add("y", location.getBlockY())
                .add("z", location.getBlockZ())
        );

        updateLastLocation(player);
        player.setLocation(location);
        player.sendMessage(ChatTypes.ACTION_BAR, Text.of(message));
    }

    /**
     * Teleports the player back to the last location.
     */
    public static void teleportBack(Player player) {

        if (!lastLocations.containsKey(player)) {
            return;
        }

        player.setLocation(lastLocations.get(player));
        player.sendMessage(ChatTypes.ACTION_BAR, Text.of(L10n.localize("back/message")));
    }

    /**
     * Sets or replaces the last location for the given player with the current location.
     */
    private static void updateLastLocation(Player player) {

        if (lastLocations.containsKey(player)) {
            lastLocations.remove(player);
        }

        lastLocations.put(player, player.getLocation());
    }

    public static Teleporter getInstance() {
        if (instance == null) {
            instance = new Teleporter();
        }

        return instance;
    }
}
