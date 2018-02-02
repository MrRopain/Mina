package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.l10n.LocalizableValues;
import me.ropain.mina.packages.essentials.teleport.Teleporter;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandTeleport extends AbstractCommand {

    public CommandTeleport() {
        super("teleport");

        toAliases("tp");
        command
                .permission("mina.teleport.player")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(ARGUMENT_PLAYER)));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<Player>getOne(ARGUMENT_PLAYER).ifPresent(player -> teleportToPlayer((Player) src, player));
        return CommandResult.success();
    }

    private void teleportToPlayer(Player player, Player target) {
        Teleporter.teleport(player, target);
        displayResponse(player, ChatTypes.ACTION_BAR, "success", LocalizableValues.builder()
                .add("player", target.getName())
                .build());
    }
}
