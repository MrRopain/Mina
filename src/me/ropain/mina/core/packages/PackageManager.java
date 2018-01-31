package me.ropain.mina.core.packages;

import me.ropain.mina.core.l10n.Localizable;
import me.ropain.mina.core.l10n.LocalizableValues;
import me.ropain.mina.core.logging.Logger;
import me.ropain.mina.core.logging.LoggingLevel;
import me.ropain.mina.protect.MinaProtect;

import java.util.HashMap;
import java.util.List;

/**
 * Contains functionality to load and unload packages.
 *
 * @author MrRopain
 */
public class PackageManager {

    private static final Localizable LOAD_PACKAGE = Localizable.get("load-package");
    private static final Localizable LOAD_PACKAGE_INVALID = Localizable.get("load-package-invalid");

    private static HashMap<String, Class<? extends IPackage>> packages = new HashMap<>();
    private static boolean loaded;

    static {
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
            Logger.log(LoggingLevel.WARN, LOAD_PACKAGE_INVALID, LocalizableValues.build("package", packageName));
            return;
        }

        try {
            Logger.log(LoggingLevel.INFO, LOAD_PACKAGE, LocalizableValues.build("package", packageName));


        }
        catch (Exception e) {}
    }
}
