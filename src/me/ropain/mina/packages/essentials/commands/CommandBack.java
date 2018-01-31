package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

public class CommandBack extends AbstractCommand {

    public CommandBack() {
        super("back");

        toAliases("back", "b");
        command.permission("mina.teleport.back");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        Teleporter.teleportBack((Player) src);
        return CommandResult.success();
    }
}
