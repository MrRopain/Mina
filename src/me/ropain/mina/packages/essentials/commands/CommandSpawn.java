package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandSpawn extends AbstractCommand {

    public CommandSpawn() {
        super("spawn");

        toAliases("spawn", "s");
        command.permission("mina.spawn");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        spawn((Player) src);
        return CommandResult.success();
    }

    private void spawn(Player player) {
        Teleporter.teleport(player, player.getWorld());
        displayResponse(player, ChatTypes.ACTION_BAR, "success");
    }
}
