package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandTeleportLocation extends AbstractCommand {

    public CommandTeleportLocation() {
        super("teleport_location");

        toAliases("tpl");
        command
                .permission("mina.teleport.location")
                .arguments(GenericArguments.onlyOne(GenericArguments.location(Text.of("location"))));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<Location<World>>getOne("location").ifPresent(location -> Teleporter.teleport((Player) src, location));
        return CommandResult.success();
    }
}
