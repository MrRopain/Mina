package me.ropain.mina.packages.protect;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.packages.IPackage;

public class MinaProtect implements IPackage {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public Object[] getListeners() {
        return new Object[0];
    }

    @Override
    public AbstractCommand[] getCommands() {
        return new AbstractCommand[0];
    }
}
