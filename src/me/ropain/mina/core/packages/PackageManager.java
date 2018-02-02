package me.ropain.mina.core.packages;

import me.ropain.mina.core.Mina;
import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.l10n.Localizable;
import me.ropain.mina.core.l10n.LocalizableValues;
import me.ropain.mina.core.logging.Logger;
import me.ropain.mina.core.logging.LoggingLevel;
import me.ropain.mina.packages.essentials.MinaEssentials;
import me.ropain.mina.packages.protect.MinaProtect;
import org.spongepowered.api.Sponge;

import java.util.HashMap;
import java.util.List;

/**
 * Contains functionality to load and unload packages.
 *
 * @author MrRopain
 */
public class PackageManager {

    private static final Localizable LOADING = Localizable.get("packaging/loading");
    private static final Localizable INVALID = Localizable.get("packaging/invalid");

    private static HashMap<String, Class<? extends IPackage>> packages = new HashMap<>();
    private static boolean loaded;

    static {
        packages.put("mina-essentials", MinaEssentials.class);
        packages.put("mina-protect", MinaProtect.class);
    }

    /**
     * Private to prevent instantiation.
     */
    private PackageManager() {}

    /**
     * Loads all packages enabled in the mina config.
     */
    public static void loadPackages(List<String> enabledPackages) {

        if (loaded) {
            return;
        }

        loaded = true;

        for (String packageName : enabledPackages) {
            loadPackage(packageName);
        }
    }

    /**
     * Loads a package by name.
     */
    private static void loadPackage(String packageName) {

        if (!packages.containsKey(packageName)) {
            Logger.log(LoggingLevel.WARN, INVALID, LocalizableValues.builder()
                    .add("package", packageName)
                    .build());
            return;
        }

        try {
            Logger.log(LoggingLevel.INFO, LOADING, LocalizableValues.builder()
                    .add("package", packageName)
                    .build());
            IPackage pack = packages.get(packageName).newInstance();

            for (Object obj : pack.getListeners()) {
                Sponge.getEventManager().registerListeners(Mina.getInstance(), obj);
            }

            for (AbstractCommand command : pack.getCommands()) {
                command.register();
            }

            pack.load();
        }
        catch (Exception e) {}
    }
}
