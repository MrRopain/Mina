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
        return values.insertInto(localize(localizable));
    }

    /**
     * Returns the string corresponding to the @see Localizable.
     */
    public static String localize(Localizable localizable) {
        return localizable.string == null ? localizable.id : localizable.string;
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
}
