package me.ropain.mina.core;

import com.google.inject.Inject;
import me.ropain.mina.core.config.Config;
import me.ropain.mina.core.l10n.Localizable;
import me.ropain.mina.core.packages.PackageManager;
import me.ropain.mina.core.playerdata.PlayerData;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;
import java.nio.file.Path;

@Plugin(id="mina", name = "Mina", version = "1.0")
public class Mina {

    private static final String LOCALE_DIR = "locales";
    private static final String LOCALE_DEFAULT = "english";

    private static final String PATH_PLAYERDATA = "playerdata";

    private static final String[] PACKAGES_DEFAULT = new String[] {
            "mina-essentials",
            "mina-protect"
    };

    private static Mina instance;

    @Inject
    private PluginContainer container;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path path;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configPath;

    @Listener
    public void onStart(GameStartedServerEvent event) {
        init();
    }

    @Listener
    public void onStop(GameStoppedServerEvent event) {
        Config.save();
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        PlayerData.create(event.getTargetEntity());
    }

    @Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event) {
        PlayerData.destroy(event.getTargetEntity());
    }

    @Listener
    public void onPlayerMove(MoveEntityEvent event, @Getter("getTargetEntity") Player player) {
        PlayerData playerData = PlayerData.of(player);

        if (playerData.isAway()) {
            playerData.setAway(false);
        }
    }

    private void init() {
        instance = this;

        Config.load(configPath);
        Localizable.setLocalesPath(getMinaPath(LOCALE_DIR));
        Localizable.setLocale(Config.getString("locale", LOCALE_DEFAULT));
        PlayerData.PersistentData.setRootPath(getMinaPath(PATH_PLAYERDATA));
        PackageManager.loadPackages(Config.getList("packages", PACKAGES_DEFAULT));
    }

    private Path getMinaPath(String subpath) {
        Path path = this.path.resolve(subpath);

        File file = path.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    public static Mina getInstance() {
        return instance;
    }

    public static PluginContainer getContainer() {
        return instance.container;
    }

    public static Logger getLogger() {
        return instance.logger;
    }
}
