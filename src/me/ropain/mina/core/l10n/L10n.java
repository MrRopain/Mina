package me.ropain.mina.core.l10n;

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
        return values.fillIn(localize(localizable));
    }

    /**
     * Returns the string corresponding to the @see Localizable.
     */
    public static String localize(Localizable localizable) {

        // todo implement
        return localizable.getDefaultString();
    }
}
