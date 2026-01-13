package com.hytale.zoom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ZoomSettings {
    public static final String DEFAULT_KEY = "Z";
    private static final String FILE_NAME = "zoom-settings.json";
    private static final Pattern KEY_PATTERN = Pattern.compile("\"hotkey\"\\s*:\\s*\"([^\"]+)\"");

    private final String hotkey;

    private ZoomSettings(String hotkey) {
        this.hotkey = normalize(hotkey);
    }

    public static ZoomSettings load(Path dataDirectory) {
        Path settingsPath = dataDirectory.resolve(FILE_NAME);
        if (Files.exists(settingsPath)) {
            try {
                String content = Files.readString(settingsPath, StandardCharsets.UTF_8);
                Matcher matcher = KEY_PATTERN.matcher(content);
                if (matcher.find()) {
                    return new ZoomSettings(matcher.group(1));
                }
            } catch (IOException ignored) {
                // Fall back to defaults.
            }
        }

        ZoomSettings defaults = new ZoomSettings(DEFAULT_KEY);
        defaults.save(settingsPath);
        return defaults;
    }

    public String getHotkey() {
        return hotkey;
    }

    private void save(Path settingsPath) {
        try {
            Files.createDirectories(settingsPath.getParent());
            String json = "{\n  \"hotkey\": \"" + hotkey + "\"\n}\n";
            Files.writeString(settingsPath, json, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            // Best-effort; config can be created manually.
        }
    }

    private static String normalize(String key) {
        if (key == null || key.isBlank()) {
            return DEFAULT_KEY;
        }
        return key.trim().toUpperCase(Locale.ROOT);
    }
}
