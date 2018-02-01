package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class CommandTeleportWorld extends AbstractCommand {

    public CommandTeleportWorld() {
        super("teleport_world");

        toAliases("tpw");
        command
                .permission("mina.teleport.world")
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("world"))));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<String>getOne("world").ifPresent(worldName -> teleportToWorld((Player) src, worldName));
        return CommandResult.success();
    }

    private void teleportToWorld(Player player, String worldName) {
        Optional<World> world = Sponge.getServer().getWorld(worldName);
        if (world.isPresent()) {
            displayResponse(player, ChatTypes.ACTION_BAR, "world_not_present");
        }
        else {
            Teleporter.teleport(player, world.get());
            displayResponse(player, ChatTypes.ACTION_BAR, "success");
        }
    }
}
