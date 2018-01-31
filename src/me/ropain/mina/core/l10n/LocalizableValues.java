package me.ropain.mina.core.l10n;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a set of key/value pairs used to replace identifiers in localizable strings with values.
 *
 * @author MrRopain
 */
public class LocalizableValues {

    private static final char VALUE_IDENTIFIER = '%';

    private Map<String, Object> values = new HashMap<>();

    /**
     * Returns a new instance.
     */
    public static LocalizableValues build() {
        return new LocalizableValues();
    }

    /**
     * Returns a new instance with exactly one key/value pair.
     *
     * @param key the key used as identifier for replacement
     * @param value the value to replace the identifier when filled in
     */
    public static LocalizableValues build(String key, Object value) {
        return build().add(key, value);
    }

    /**
     * Appends a key/value pair to the map.
     * Returns the same instance it was called on for quick reusability.
     *
     * @param key the key used as identifier for replacement
     * @param value the value to replace the identifier when filled in
     */
    public LocalizableValues add(String key, Object value) {
        values.put(key, value);
        return this;
    }

    /**
     * Fills values into the given string by replacing identifiers.
     */
    String fillIn(String string) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            string = string.replace(VALUE_IDENTIFIER + entry.getKey(), entry.getValue().toString());
        }

        return string;
    }
}
