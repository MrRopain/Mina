package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.l10n.LocalizableValues;
import me.ropain.mina.packages.essentials.teleport.TeleportRequest;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandTeleportAccept extends AbstractCommand {

    public CommandTeleportAccept() {
        super("teleport_accept");

        toAliases("tpa");
        command
                .permission("mina.teleport.request")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(ARGUMENT_PLAYER)));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<Player>getOne(ARGUMENT_PLAYER).ifPresent(from -> acceptRequest((Player) src, from));
        return CommandResult.success();
    }

    private void acceptRequest(Player player, Player from) {
        TeleportRequest request = TeleportRequest.getByPlayerFrom(from);

        if (request == TeleportRequest.NONE) {
            // todo request_not_found
            return;
        }

        request.accept();

        displayResponse(player, ChatTypes.CHAT, "player_to", LocalizableValues.builder()
                .add("player", from.getName())
                .build());

        displayResponse(from, ChatTypes.CHAT, "player_from", LocalizableValues.builder()
                .add("player", player.getName())
                .build());
    }
}
