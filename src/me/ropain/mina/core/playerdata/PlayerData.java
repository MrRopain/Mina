package me.ropain.mina.core.playerdata;

import me.ropain.mina.core.l10n.L10n;
import me.ropain.mina.core.l10n.LocalizableValues;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Contains Mina-related data about players.
 */
public class PlayerData {

    private static final PlayerData EMPTY = new PlayerData();

    private static HashMap<Player, PlayerData> dataMap = new HashMap<>();

    public final Player player;
    public final PersistentData persistentData;

    private boolean away;
    private Location<World> lastLocation;

    private Home home = new Home();

    /**
     * Used for empty player data.
     */
    private PlayerData() {
        player = null;
        persistentData = null;
    }

    /**
     * Prevent instantiation from outside.
     */
    private PlayerData(Player player) {
        this.player = player;
        persistentData = PersistentData.create(this);
    }

    /**
     * Sets whether or not the player is away and is thus sleepingIgnored and invulnerable.
     * Also displays the respective message.
     */
    public void setAway(boolean away) {
        this.away = away;

        player.setSleepingIgnored(away);
        player.offer(Keys.INVULNERABLE, away);

        MessageChannel serverChannel = Sponge.getServer().getBroadcastChannel();

        if (away) {
            serverChannel.send(Text.of(L10n.localize("other/away/away", LocalizableValues.builder()
                    .add("player", player.getName())
                    .build())));
        }
        else {
            serverChannel.send(Text.of(L10n.localize("other/away/back", LocalizableValues.builder()
                    .add("player", player.getName())
                    .build())));
        }
    }

    /**
     * Toggles away, calling setAway
     */
    public void toggleAway() {
        setAway(!away);
    }

    /**
     * Updates the player's last location.
     */
    public void updateLastLocation() {
        lastLocation = player.getLocation();
    }

    /**
     * Returns whether or not the player is away.
     */
    public boolean isAway() {
        return away;
    }

    /**
     * Returns the player's last location.
     */
    public Location<World> getLastLocation() {
        return lastLocation;
    }

    /**
     * Returns the player's home.
     */
    public Home getHome() {
        return home;
    }

    /**
     * Returns the storable fields.
     */
    private IStorable[] getStorables() {
        return new IStorable[] { home };
    }

    /**
     * Creates the playerdata object for this player and loads persistent data.
     */
    public static void create(Player player) {

        if (dataMap.containsKey(player)) {
            return;
        }

        PlayerData playerData = new PlayerData(player);
        playerData.persistentData.load(playerData.getStorables());

        dataMap.put(player, playerData);
    }

    /**
     * Destroys the playerdata object for this player and saves persistent data.
     */
    public static void destroy(Player player) {

        if (!dataMap.containsKey(player)) {
            return;
        }

        PlayerData playerData = dataMap.get(player);
        playerData.persistentData.save(playerData.getStorables());

        dataMap.remove(player);
    }

    /**
     * Returns the playerdata object for this player.
     */
    public static PlayerData of(Player player) {

        if (!dataMap.containsKey(player)) {
            return EMPTY;
        }

        return dataMap.get(player);
    }

    /**
     * Contains persistent player data.
     */
    public static class PersistentData {

        private static Path rootPath;

        private HoconConfigurationLoader loader;
        private ConfigurationNode rootNode;

        /**
         * Prevent instantiation from outside.
         */
        private PersistentData(Path path) {
            loader = HoconConfigurationLoader.builder().setPath(path).build();
        }

        /**
         * Load all data.
         */
        private void load(IStorable... storables) {

            try {
                rootNode = loader.load();
            }
            catch (IOException e) {}

            for (IStorable storable : storables) {
                storable.load(rootNode.getNode(storable.getKey()));
            }
        }

        /**
         * Save all data.
         */
        private void save(IStorable... storables) {
            for (IStorable storable : storables) {
                storable.store(rootNode.getNode(storable.getKey()));
            }

            try {
                loader.save(rootNode);
            } catch (IOException e) {}
        }

        /**
         * Sets the root path.
         */
        public static void setRootPath(Path rootPath) {
            PersistentData.rootPath = rootPath;
        }

        /**
         * Load the root node.
         */
        private static PersistentData create(PlayerData playerData) {
            Path path = rootPath.resolve(playerData.player.getUniqueId().toString());
            return new PersistentData(path);
        }
    }
}
