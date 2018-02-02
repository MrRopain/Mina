package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandVanish extends AbstractCommand {

    public CommandVanish() {
        super("vanish");

        toAliases("vanish", "v");
        command.permission("mina.teleport.vanish");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        toggleVanish((Player) src);
        return CommandResult.success();
    }

    private void toggleVanish(Player player) {
        boolean vanished = !player.get(Keys.VANISH).get();
        player.offer(Keys.VANISH, vanished);

        if (vanished) {
            displayResponse(player, ChatTypes.ACTION_BAR, "invisible");
        }
        else {
            displayResponse(player, ChatTypes.ACTION_BAR, "visible");
        }
    }
}
