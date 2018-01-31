package me.ropain.mina.packages.essentials;

import me.ropain.mina.core.packages.IPackage;
import me.ropain.mina.packages.essentials.commands.CommandBack;
import me.ropain.mina.packages.essentials.commands.CommandTeleport;
import me.ropain.mina.packages.essentials.commands.CommandTeleportLocation;
import me.ropain.mina.packages.essentials.teleport.Teleporter;

public class MinaEssentials implements IPackage {

    @Override
    public void load() {
        new CommandTeleport().register();
        new CommandTeleportLocation().register();
        new CommandBack().register();
    }

    @Override
    public void unload() {
    }

    @Override
    public Object[] getListeners() {
        return new Object[] { Teleporter.getInstance() };
    }
}
