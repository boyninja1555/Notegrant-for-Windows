package com.notegrant.util;

import java.io.*;
import java.util.*;

import com.notegrant.App;
import com.notegrant.control.Editor;

public class ConfigManager {
    public enum ConfigValueType {
        TEXT,
        INTEGER,
        FLOAT,
        BOOLEAN,
        DROPDOWN,
    }

    private static final File configFile = new File(System.getProperty("user.home"),
            "Notegrant Data/config.properties");
    private static final Properties configDefaults = new Properties();
    private static final Map<String, ConfigValueType> configDefaultsValueTypes = new HashMap<>();
    private static final Map<String, String[]> configDropdownOptions = new HashMap<>();
    private static final Properties config = new Properties();

    static {
        // Theme
        configDefaults.setProperty("theme", "light");
        configDefaultsValueTypes.put("theme", ConfigValueType.DROPDOWN);
        config.setProperty("theme", configDefaults.getProperty("theme"));

        String[] themeIDs = App.getAvailableThemeNames(App.class).toArray(new String[0]);
        configDropdownOptions.put("theme", themeIDs);

        // Opening Document
        configDefaults.setProperty("opening-document", new File(
                Editor.getDocumentsDir().getAbsolutePath()
                        + "/Example.txt")
                .getAbsolutePath());
        configDefaultsValueTypes.put("opening-document", ConfigValueType.TEXT);
        config.setProperty("opening-document", configDefaults.getProperty("opening-document"));
    }

    public static void initConfig() {
        for (String key : configDefaults.stringPropertyNames()) {
            config.setProperty(key, configDefaults.getProperty(key));
        }

        saveConfig();
    }

    public static void loadConfig() {
        if (configFile.exists()) {
            try (FileInputStream in = new FileInputStream(configFile)) {
                config.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveConfig() {
        try {
            configFile.getParentFile().mkdirs();
            try (FileOutputStream out = new FileOutputStream(configFile)) {
                config.store(out, "Notegrant Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getConfigDefaults() {
        return configDefaults;
    }

    public static Properties getConfig() {
        return config;
    }

    public static File getConfigFile() {
        return configFile;
    }

    public static boolean configFileExists() {
        return configFile.exists();
    }

    public static Set<String> getAllKeys() {
        return configDefaults.stringPropertyNames();
    }

    public static ConfigValueType getValueType(String key) {
        return configDefaultsValueTypes.getOrDefault(key, ConfigValueType.TEXT);
    }

    public static String[] getDropdownOptions(String key) {
        return configDropdownOptions.getOrDefault(key, new String[0]);
    }
}
