package me.ropain.mina.core.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Config allows simplified access to ConfigurationNodes.
 *
 * @author MrRopain
 */
public class Config {

    private static final String PATH_SEPARATOR = "/";

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

    /**
     * Returns a list of strings from the given path. Accepts a fallback value.
     */
    public static List<String> getList(String path, String[] def) {
        ConfigurationNode node = root.getNode(resolvePath(path));

        try {
            return node.getList(TypeToken.of(String.class), Arrays.asList(def));
        } catch (ObjectMappingException e) {}

        return null;
    }

    /**
     * Returns a string from the given path. Accepts a fallback value.
     */
    public static String getString(String path, String def) {
        return root.getNode(resolvePath(path)).getString(def);
    }

    /**
     * @see ConfigurationNode#getNode
     */
    public static ConfigurationNode getNode(Object... objects) {
        return root.getNode(objects);
    }

    /**
     * Returns a path made from the given paths.
     */
    public static String makePath(String... parts) {
        String path = "";

        for (int i = 0; i < parts.length; i++) {
            path += parts[i];

            if (i != parts.length - 1) {
                path += PATH_SEPARATOR;
            }
        }

        return path;
    }

    /**
     * Returns an array of objects representing the path to a node.
     */
    public static Object[] resolvePath(String path) {
        return path.split(PATH_SEPARATOR);
    }
}
