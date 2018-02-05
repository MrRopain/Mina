package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.playerdata.PlayerData;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

public class CommandAway extends AbstractCommand {

    public CommandAway() {
        super("away");

        toAliases("away", "afk");
        command.permission("mina.away");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        PlayerData.of((Player) src).toggleAway();
        return CommandResult.success();
    }
}
