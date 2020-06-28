package net.coordinate.keeper.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

/**
 * A Config helper class for creating and reading from json configs
 */
public class NameConfig {
    private static Logger LOGGER = LogManager.getLogger("[Coordinate Keeper] ");

    private Gson gson;
    private File configFile;
    private HashMap<String, String> config;

    /**
     * Constructor for defining a new config using a name
     * Append properties using the add methods from this class
     * Save config once properties are added using saveConfig
     */
    public NameConfig() {
        this.config = new HashMap<>();
        this.gson = new Gson();
        loadConfig();
    }

    public void addName(String username, String name) {
        config.put(name, username);
        saveConfig();
    }

    /**
     * Get an {@link Integer} from the config
     *
     * @param name - The name of the entry
     * @return - The return value
     */
    public String getUsername(String name) {
        return config.getOrDefault(name, null);
    }

    public Collection<String> getNames() {
        return config.keySet();
    }

    public Collection<String> getUserNames() {
        return config.values();
    }

    public boolean isUsernameStored(String username) {
        return config.containsValue(username);
    }

    /**
     * Load an existing config file or create an empty one
     */
    private void loadConfig() {
        String baseConfigFolder = "coordinateKeeper";
        File configDir = new File(FabricLoader.getInstance().getConfigDirectory() + "/" + baseConfigFolder);
        if (!configDir.exists()) {
            if (configDir.mkdir()) {
                LOGGER.info("Successfully Created Directory");
            } else {
                LOGGER.fatal("Error Creating Dir");
            }
        }
        this.configFile = new File(configDir + "/names.json");
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    LOGGER.info("Successfully Created File names");
                } else {
                    LOGGER.fatal("Error Creating File name");
                }
            } catch (IOException e) {
                LOGGER.fatal("Error Creating File name");
            }
        }

        Type empMapType = new TypeToken<HashMap<String, String>>() {}.getType();
        try {
            config = gson.fromJson(new FileReader(configFile), empMapType);
            if (config == null) {
                LOGGER.fatal("Config was null");
                config = new HashMap<>();
            }
            LOGGER.info("Successfully Parsed Config File name");
        } catch (FileNotFoundException e) {
            LOGGER.fatal("Error Parsing Config File name");
        }
    }

    /**
     * Save a config file
     **/
    private void saveConfig() {
        try {
            FileWriter fileWriter = new FileWriter(configFile, false);
            fileWriter.write(gson.toJson(config));
            fileWriter.close();
            LOGGER.info("Successfully Saved Config File name");
        } catch (IOException e) {
            LOGGER.fatal("Error saving Config");
        }
    }
}
