package me.ropain.mina.core.l10n;

/**
 * Contains localizable variables.
 *
 * @author MrRopain
 */
public enum Localizable {
    PACKAGING_LOAD("Loading %count packages"),
    PACKAGING_INVALID_PACKAGE("Package %package not found");

    private String defaultString;

    /**
     * @param defaultString fallback string in case no appropriate locale is found
     */
    Localizable(String defaultString) {
        this.defaultString = defaultString;
    }

    /**
     * @return defaultString as passed to the constructor
     */
    public String getDefaultString() {
        return defaultString;
    }
}
