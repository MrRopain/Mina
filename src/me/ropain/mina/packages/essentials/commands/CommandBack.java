package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandBack extends AbstractCommand {

    public CommandBack() {
        super("back");

        toAliases("back", "b");
        command.permission("mina.teleport.back");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        teleportBack((Player) src);
        return CommandResult.success();
    }

    private void teleportBack(Player player) {
        Teleporter.teleportBack(player);
        displayResponse(player, ChatTypes.ACTION_BAR, "success");
    }
}
