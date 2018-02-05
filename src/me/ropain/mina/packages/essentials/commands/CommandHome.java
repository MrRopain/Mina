package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.playerdata.PlayerData;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

// TODO this is very bad and only temporary
public class CommandHome extends AbstractCommand {

    private static Text ARGUMENT_SUBCOMMAND = Text.of("subcommand");

    public CommandHome() {
        super("home");

        toAliases("home", "h");
        command
                .permission("mina.home")
                .arguments(GenericArguments.optional(GenericArguments.string(ARGUMENT_SUBCOMMAND)));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }


        Player player = (Player) src;
        Optional<String> optionalSubcommand = args.getOne(ARGUMENT_SUBCOMMAND);
        if (optionalSubcommand.isPresent() && optionalSubcommand.get().equalsIgnoreCase("set")) {
            setHome(player);
        }
        else {
            teleportHome(player);
        }

        return CommandResult.success();
    }

    private void setHome(Player player) {
        PlayerData.of(player).getHome().setLocation(player.getLocation());
        displayResponse(player, ChatTypes.ACTION_BAR, "home_set");
    }

    private void teleportHome(Player player) {
        Location<World> homeLocation = PlayerData.of(player).getHome().getLocation();

        if (homeLocation == null) {
            displayResponse(player, ChatTypes.ACTION_BAR, "home_not_set");
            return;
        }

        Teleporter.teleport(player, homeLocation);
        displayResponse(player, ChatTypes.ACTION_BAR, "success");
    }
}
