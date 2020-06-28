package net.coordinate.keeper.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.coordinate.keeper.helpers.CoordinatesHelper. Category;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * A Config helper class for creating and reading from json configs
 */
public class Config {
    private static Logger LOGGER = LogManager.getLogger("[Coordinate Keeper] ");

    private Gson gson;
    private File configFile;
    private HashMap<String, Coordinates> config;
    private Category category;

    /**
     * Constructor for defining a new config using a name
     * Append properties using the add methods from this class
     * Save config once properties are added using saveConfig
     *
     * @param category - The category of the config file
     */
    public Config(Category category) {
        this.config = new HashMap<>();
        this.category = category;
        this.gson = new Gson();
        loadConfig();
    }

    public void addCoordinates(String key, Coordinates coordinates) {
        config.put(key, coordinates);
        saveConfig();
    }

    /**
     * Get an {@link Integer} from the config
     *
     * @param key - The name of the entry
     * @return - The return value
     */
    public Coordinates getCoordinates(String key) {
        return config.getOrDefault(key, null);
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
        this.configFile = new File(configDir + "/" + category.getFileName() + ".json");
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    LOGGER.info("Successfully Created File " + category.getFileName());
                } else {
                    LOGGER.fatal("Error Creating File " + category.getFileName());
                }
            } catch (IOException e) {
                LOGGER.fatal("Error Creating File " + category.getFileName());
            }
        }

        Type empMapType = new TypeToken<HashMap<String, Coordinates>>() {}.getType();
        try {
            config = gson.fromJson(new FileReader(configFile), empMapType);
            if (config == null) {
                LOGGER.fatal("Config was null");
                config = new HashMap<>();
            }
            LOGGER.info("Successfully Parsed Config File " + category.getFileName());
        } catch (FileNotFoundException e) {
            LOGGER.fatal("Error Parsing Config File " + category.getFileName());
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
            LOGGER.info("Successfully Saved Config File " + category.getFileName());
        } catch (IOException e) {
            LOGGER.fatal("Error saving Config");
        }
    }
}
