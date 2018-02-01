package me.ropain.mina.core.commands;

import me.ropain.mina.core.Mina;
import me.ropain.mina.core.config.Config;
import me.ropain.mina.core.l10n.L10n;
import me.ropain.mina.core.l10n.LocalizableValues;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;

/**
 * Abstract command executor to simplify command registration.
 *
 * @author MrRopain
 */
public abstract class AbstractCommand implements CommandExecutor {

    protected String root;

    protected CommandSpec.Builder command = CommandSpec.builder();
    protected String[] aliases;

    protected AbstractCommand(String name) {
        root = "commands/" + name;

        command
                .description(Text.of(L10n.localize(Config.makePath(name, "description"))))
                .executor(this);
    }

    /**
     * Registers the command.
     */
    public void register() {
        Sponge.getCommandManager().register(Mina.getContainer(), command.build(), aliases);
    }

    /**
     * Displays a response to the player.
     *
     * @param chatType the chat type to display the response in
     * @param response the response identifier
     */
    public void displayResponse(Player player, ChatType chatType, String response) {
        player.sendMessage(chatType, L10n.localizeResponse(this, response));
    }

    /**
     * Displays a response to the player.
     *
     * @param chatType the chat type to display the response in
     * @param response the response identifier
     * @param values the values inserted into the response
     */
    public void displayResponse(Player player, ChatType chatType, String response, LocalizableValues values) {
        player.sendMessage(chatType, L10n.localizeResponse(this, response, values));
    }

    /**
     * Returns the path for localized values for this command.
     */
    public String getLocalePath(String... parts) {
        ArrayUtils.add(parts, 0, root);
        return Config.makePath(parts);
    }

    /**
     * Assigns the alias array to the given aliases.
     */
    protected void toAliases(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Returns whether or not the given source is an entity.
     */
    protected boolean isPlayer(CommandSource source) {
        return source instanceof Player;
    }
}
