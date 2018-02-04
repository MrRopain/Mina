package me.ropain.mina.packages.essentials;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.packages.IPackage;
import me.ropain.mina.packages.essentials.commands.*;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;

public class MinaEssentials implements IPackage {

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public Object[] getListeners() {
        return new Object[] { this };
    }

    @Override
    public AbstractCommand[] getCommands() {
        return new AbstractCommand[] {
                new CommandBack(),
                new CommandHome(),
                new CommandSpawn(),
                new CommandTeleport(),
                new CommandTeleportAccept(),
                new CommandTeleportLocation(),
                new CommandTeleportRequest(),
                new CommandTeleportWorld(),
                new CommandVanish(),
        };
    }

    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Player player) {
        Teleporter.updateLastLocation(player);
    }
}
