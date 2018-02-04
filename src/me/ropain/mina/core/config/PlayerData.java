package me.ropain.mina.core.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.nio.file.Path;

public class PlayerData {

    private static HoconConfigurationLoader loader;

    public static ConfigurationNode root;

    /**
     * Load root node from path.
     */
    public static void load(Path path) {
        loader = HoconConfigurationLoader.builder().setPath(path).build();

        try {
            root = loader.load();
        } catch (IOException e) {}
    }

    /**
     * Save root node to path.
     */
    public static void save() {

        try {
            loader.save(root);
        } catch (IOException e) {}
    }

    public static ConfigurationNode getData(Player player) {
        return root.getNode(player.getUniqueId());
    }
}
