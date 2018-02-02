package me.ropain.mina.packages.essentials;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.packages.IPackage;
import me.ropain.mina.packages.essentials.commands.*;
import me.ropain.mina.packages.essentials.teleport.Teleporter;

public class MinaEssentials implements IPackage {

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public Object[] getListeners() {
        return new Object[] { Teleporter.getInstance() };
    }

    @Override
    public AbstractCommand[] getCommands() {
        return new AbstractCommand[] {
                new CommandBack(),
                new CommandTeleport(),
                new CommandTeleportAccept(),
                new CommandTeleportLocation(),
                new CommandTeleportRequest(),
                new CommandTeleportWorld(),
                new CommandVanish(),
        };
    }
}
