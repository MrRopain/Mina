package me.ropain.mina.core.packages;

import me.ropain.mina.core.commands.AbstractCommand;

/**
 * Contains functions to load and unload packages and automated event listener registration.
 *
 * @author MrRopain
 */
public interface IPackage {

    /**
     * Loads the package.
     */
    void load();

    /**
     * Unloads the package.
     */
    void unload();

    /**
     * Returns an array of event listeners to be registered by Mina.
     */
    Object[] getListeners();

    /**
     * Returns an array of command executors to be registered by Mina.
     */
    AbstractCommand[] getCommands();
}
