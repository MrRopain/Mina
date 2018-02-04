package me.ropain.mina.packages.essentials.teleport;


import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;

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

    /**
     * Teleports the player to the given location.
     */
    public static void teleport(Player player, Location<World> location) {
        updateLastLocation(player);
        player.setLocation(location);
    }

    /**
     * Teleports an player to another player.
     */
    public static void teleport(Player player, Player target) {
        teleport(player, target.getLocation());
    }

    /**
     * Transfers the player to the given world.
     */
    public static void teleport(Player player, World world) {
        teleport(player, world.getSpawnLocation());
    }

    /**
     * Teleports the player back to the last location.
     */
    public static void teleportBack(Player player) {

        if (!lastLocations.containsKey(player)) {
            return;
        }

        teleport(player, lastLocations.get(player));
    }

    /**
     * Sets or replaces the last location for the given player with the current location.
     */
    public static void updateLastLocation(Player player) {

        if (lastLocations.containsKey(player)) {
            lastLocations.remove(player);
        }

        lastLocations.put(player, player.getLocation());
    }

    /**
     * Returns the instance.
     */
    public static Teleporter getInstance() {
        if (instance == null) {
            instance = new Teleporter();
        }

        return instance;
    }
}
