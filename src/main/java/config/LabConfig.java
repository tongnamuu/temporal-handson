package config;

import java.io.IOException;
import java.util.Properties;

public abstract class LabConfig {
    private static final Properties DEFAULTS = loadDefaults();
    public static final String ADDRESS = setting("TEMPORAL_ADDRESS");
    public static final String NAMESPACE = setting("TEMPORAL_NAMESPACE");
    public static final String TASK_QUEUE = setting("TEMPORAL_TASK_QUEUE");

    private static Properties loadDefaults() {
        Properties properties = new Properties();
        try (var input = LabConfig.class.getResourceAsStream("/temporal.properties")) {
            if (input == null) throw new IllegalStateException("temporal.properties가 없습니다.");
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Temporal 설정을 읽지 못했습니다.", e);
        }
    }

    private static String setting(String name) {
        String value = System.getenv(name);
        if (value == null) value = DEFAULTS.getProperty(name);
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " 설정이 비어 있습니다.");
        return value;
    }

    private LabConfig() {}
}
