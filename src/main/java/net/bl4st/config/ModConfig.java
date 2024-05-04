package net.bl4st.config;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

import net.bl4st.elytradrag.ElytraDrag;
import net.fabricmc.loader.api.FabricLoader;

public class ModConfig {
    /**
     * The drag coefficient to apply when sneaking while flying
     */
    public static float ELYTRA_DRAG = 2.0f;

    /**
     * The Maximum fallDistance for a player sneaking and flying
     */
    public static float MAXIMUM_FALLDISTANCE = 15.0f;

    /**
     * The minimum speed required to apply drag. This is to avoid near-stationary flight
     */
    public static float MINIMUM_SPEED = 0.1f;

    public static void LoadConfig() {
        var configPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), ElytraDrag.MOD_ID + ".properties").toString();
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);

            // Read values from the properties file
            ELYTRA_DRAG = Float.parseFloat(properties.getProperty("drag", "2.0"));
            MAXIMUM_FALLDISTANCE = Float.parseFloat(properties.getProperty("maximum-fall-distance-while-slowing-Down", "15.0"));
            MINIMUM_SPEED = Float.parseFloat(properties.getProperty("minimum-speed-required", "0.1"));
        } catch (FileNotFoundException e) {
            // File does not exist, create it with default values
            try (OutputStream output = new FileOutputStream(configPath)) {
                properties.setProperty("drag", "2.0");
                properties.setProperty("maximum-fall-distance-while-slowing-Down", "15.0");
                properties.setProperty("minimum-speed-required", "0.1");
                properties.store(output, "Elytra Drag Configuration");
            } catch (IOException ex) {
                System.err.println("Error creating default config file: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing config values: " + e.getMessage());
        }
    }
}
