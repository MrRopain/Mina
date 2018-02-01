package me.ropain.mina.core.l10n;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a set of key/value pairs used to replace identifiers in localizable strings with values.
 * todo create builder
 *
 * @author MrRopain
 */
public class LocalizableValues {

    private static final char VALUE_IDENTIFIER = '%';

    private Map<String, String> values;

    /**
     * Prevent instantiation from outside.
     */
    private LocalizableValues(Map<String, String> values) {
        this.values = values;
    }

    /**
     * Inserts values into the given string by replacing identifiers.
     */
    String insertInto(String string) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            string = string.replace(VALUE_IDENTIFIER + entry.getKey(), entry.getValue());
        }

        return string;
    }

    /**
     * Returns a new Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Map<String, String> values = new HashMap<>();

        /**
         * Adds a key/value pair to the collection.
         */
        public Builder add(String key, Object value) {
            values.put(key, value.toString());
            return this;
        }

        /**
         * Returns the built object.
         */
        public LocalizableValues build() {
            return new LocalizableValues(values);
        }
    }
}
