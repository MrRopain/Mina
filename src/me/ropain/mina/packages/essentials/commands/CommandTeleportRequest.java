package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.l10n.LocalizableValues;
import me.ropain.mina.packages.essentials.teleport.TeleportRequest;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandTeleportRequest extends AbstractCommand {

    public CommandTeleportRequest() {
        super("teleport_request");

        toAliases("tpr");
        command
                .permission("mina.teleport.request")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(ARGUMENT_PLAYER)));
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if (!isPlayer(src)) {
            return CommandResult.empty();
        }

        args.<Player>getOne(ARGUMENT_PLAYER).ifPresent(to -> sendRequest((Player) src, to));
        return CommandResult.success();
    }

    private void sendRequest(Player player, Player to) {

        if (TeleportRequest.exists(player, to)) {
            // todo request_exists
            return;
        }

        TeleportRequest.send(player, to);

        displayResponse(player, ChatTypes.CHAT, "player_to", LocalizableValues.builder()
                .add("player", to.getName())
                .build());

        displayResponse(to, ChatTypes.CHAT, "player_from", LocalizableValues.builder()
                .add("player", player.getName())
                .build());

        displayResponse(to, ChatTypes.CHAT, "hint");
    }
}
