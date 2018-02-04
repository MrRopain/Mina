package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.config.PlayerData;
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
        Location<World> location = player.getLocation();

        ConfigurationNode home = PlayerData.getData(player).getNode("home");
        home.getNode("world").setValue(location.getExtent().getName());
        home.getNode("x").setValue(location.getBlockX());
        home.getNode("y").setValue(location.getBlockY());
        home.getNode("z").setValue(location.getBlockZ());

        displayResponse(player, ChatTypes.ACTION_BAR, "home_set");
    }

    private void teleportHome(Player player) {
        ConfigurationNode home = PlayerData.getData(player).getNode("home");
        String worldName = home.getNode("world").getString();

        if (worldName == null) {
            displayResponse(player, ChatTypes.ACTION_BAR, "home_not_set");
            return;
        }

        int x = home.getNode("x").getInt();
        int y = home.getNode("y").getInt();
        int z = home.getNode("z").getInt();

        Teleporter.teleport(player, new Location(Sponge.getServer().getWorld(worldName).get(), x, y, z));
        displayResponse(player, ChatTypes.ACTION_BAR, "success");
    }
}
