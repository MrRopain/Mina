package me.ropain.mina.packages.essentials.commands;

import me.ropain.mina.core.Mina;
import me.ropain.mina.core.l10n.L10n;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * Abstract command executor to simplify command registration.
 *
 * @author MrRopain
 */
public abstract class AbstractCommand implements CommandExecutor {

    protected String root;

    protected CommandSpec.Builder command = CommandSpec.builder();
    protected String[] aliases;

    protected AbstractCommand(String root) {
        this.root = root;

        command
                .description(Text.of(L10n.localize(root + "/description")))
                .executor(this);
    }

    /**
     * Displays the usage to the given command source.
     */
    protected void displayUsage(CommandSource source) {
        source.sendMessage(Text.of(L10n.localize(root + "/usage")));
    }

    /**
     * Assigns the alias array to the given aliases.
     */
    public void toAliases(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Registers the command.
     */
    public void register() {
        Sponge.getCommandManager().register(Mina.getContainer(), command.build(), aliases);
    }

    /**
     * Returns whether or not the given source is an entity.
     */
    protected boolean isPlayer(CommandSource source) {
        return source instanceof Player;
    }
}
