package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandTeleport extends AbstractCommand {

    public CommandTeleport() {
        super("teleport");

        toAliases("tp");
        command
                .permission("mina.teleport.player")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<Player>getOne("player").ifPresent(player -> Teleporter.teleport((Player) src, player));
        return CommandResult.success();
    }
}
