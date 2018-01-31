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

    private static final String PATH_SEPARATOR = ".";

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
     * Returns a list of strings from the given path. Accepts a fallback string.
     */
    public static List<String> getList(String path, String[] def) {
        ConfigurationNode node = root.getNode(resolvePath(path));

        try {
            return node.getList(TypeToken.of(String.class), Arrays.asList(def));
        } catch (ObjectMappingException e) {}

        return null;
    }

    /**
     * Returns a list of strings from the given path.
     */
    public static List<String> getList(String path) {
        return getList(path, null);
    }

    /**
     * Returns a string from the given path. Accepts a fallback string.
     */
    public static String getString(String path, String def) {
        return root.getNode(resolvePath(path)).getString(def);
    }

    /**
     * Returns a string from the given path.
     */
    public static String getString(String path) {
        return getString(path, null);
    }

    /**
     * Returns an array of objects representing the path to a node.
     */
    private static Object[] resolvePath(String path) {
        return path.contains(PATH_SEPARATOR) ? path.split(PATH_SEPARATOR) : new Object[] { path };
    }
}
