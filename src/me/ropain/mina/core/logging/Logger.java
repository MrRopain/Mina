package me.ropain.mina.core.logging;

import me.ropain.mina.core.Mina;
import me.ropain.mina.core.l10n.L10n;
import me.ropain.mina.core.l10n.Localizable;
import me.ropain.mina.core.l10n.LocalizableValues;

/**
 * Allows logging on different levels with automated localization.
 *
 * @author MrRopain
 */
public class Logger {

    /**
     * Logs a localized string with inserted values on the given logging level.
     */
    public static void log(LoggingLevel level, Localizable localizable, LocalizableValues values) {
        org.slf4j.Logger logger = Mina.getLogger();
        String localizedString = L10n.localize(localizable, values);

        switch (level) {
            case INFO:
                logger.info(localizedString);
                break;
            case WARN:
                logger.warn(localizedString);
                break;
            case ERROR:
                logger.error(localizedString);
                break;
        }
    }

    /**
     * Logs a localized string on the given logging level.
     */
    public static void log(LoggingLevel level, Localizable localizable) {
        log(level, localizable);
    }
}
