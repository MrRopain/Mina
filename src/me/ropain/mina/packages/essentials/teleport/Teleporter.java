package me.ropain.mina.packages.essentials.teleport;


import me.ropain.mina.core.playerdata.PlayerData;
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

    /**
     * Private to prevent instantiation from outside.
     */
    private Teleporter() {}

    /**
     * Teleports the player to the given location.
     */
    public static void teleport(Player player, Location<World> location) {
        PlayerData.of(player).updateLastLocation();
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

        Location<World> lastLocation = PlayerData.of(player).getLastLocation();
        if (lastLocation == null) {
            return;
        }

        teleport(player, lastLocation);
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
