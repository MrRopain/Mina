package me.ropain.mina.core.l10n;

import me.ropain.mina.core.commands.AbstractCommand;
import me.ropain.mina.core.config.Config;
import org.spongepowered.api.text.Text;

/**
 * Contains functionality to localize strings.
 *
 * @author MrRopain
 */
public class L10n {

    /**
     * Returns the string corresponding to the @see Localizable,
     * filled with the given values.
     */
    public static String localize(Localizable localizable, LocalizableValues values) {
        return values.insertInto(localize(localizable));
    }

    /**
     * Returns the string corresponding to the @see Localizable.
     */
    public static String localize(Localizable localizable) {
        return localizable.string == null ? localizable.path : localizable.string;
    }

    /**
     * Returns the string corresponding to the @see Localizable specified by localizableId.
     * Values are filled in.
     */
    public static String localize(String localizableId, LocalizableValues values) {
        return localize(Localizable.get(localizableId), values);
    }

    /**
     * Returns the string corresponding to the @see Localizable specified by localizableId.
     */
    public static String localize(String localizableId) {
        return localize(Localizable.get(localizableId));
    }

    /**
     * Returns a localized command response, filled with the given values.
     */
    public static Text localizeResponse(AbstractCommand command, String response, LocalizableValues values) {
        String responsePath = Config.makePath(command.getLocalePath("responses", response));
        return Text.of(localize(Localizable.get(responsePath), values));
    }

    /**
     * Returns a localized command response.
     */
    public static Text localizeResponse(AbstractCommand command, String response) {
        String responsePath = Config.makePath(command.getLocalePath("responses", response));
        return Text.of(localize(Localizable.get(responsePath)));
    }
}
