package com.notegrant.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final File configFile = new File(System.getProperty("user.home"),
            "Notegrant Data/config.properties");
    private static final Properties configDefaults = new Properties();
    private static final Properties config = new Properties();

    static {
        configDefaults.setProperty("theme", "light");
    }

    public static void initConfig() {
        for (Object key : configDefaults.keySet()) {
            config.setProperty((String) key, configDefaults.getProperty((String) key));
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

    public static Boolean configFileExists() {
        return configFile.exists();
    }
}
