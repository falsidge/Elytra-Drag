package net.bl4st.config;

import net.bl4st.elytradrag.ElytraDrag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ElytraDrag.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue ELYTRA_DRAG_CONFIGSPEC = BUILDER
            .defineInRange("drag", 2.0, 0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue MAXIMUM_FALLDISTANCE_CONFIGSPEC = BUILDER
            .defineInRange("maximum-fall-distance-while-slowing-Down", 1.0, 0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue MINIMUM_SPEED_CONFIGSPEC = BUILDER
            .defineInRange("minimum-speed-required", 0.1, 0, Double.MAX_VALUE);
    public static final ModConfigSpec SPEC = BUILDER.build();

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

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        ELYTRA_DRAG = ELYTRA_DRAG_CONFIGSPEC.get().floatValue();
        MAXIMUM_FALLDISTANCE = MAXIMUM_FALLDISTANCE_CONFIGSPEC.get().floatValue();
        MINIMUM_SPEED = MINIMUM_SPEED_CONFIGSPEC.get().floatValue();
    }
//    public static void LoadConfig() {
//        var configPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), ElytraDrag.MOD_ID + ".properties").toString();
//        Properties properties = new Properties();
//        try (InputStream input = new FileInputStream(configPath)) {
//            properties.load(input);
//
//            // Read values from the properties file
//            ELYTRA_DRAG = Float.parseFloat(properties.getProperty("drag", "2.0"));
//            MAXIMUM_FALLDISTANCE = Float.parseFloat(properties.getProperty("maximum-fall-distance-while-slowing-Down", "15.0"));
//            MINIMUM_SPEED = Float.parseFloat(properties.getProperty("minimum-speed-required", "0.1"));
//        } catch (FileNotFoundException e) {
//            // File does not exist, create it with default values
//            try (OutputStream output = new FileOutputStream(configPath)) {
//                properties.setProperty("drag", "2.0");
//                properties.setProperty("maximum-fall-distance-while-slowing-Down", "15.0");
//                properties.setProperty("minimum-speed-required", "0.1");
//                properties.store(output, "Elytra Drag Configuration");
//            } catch (IOException ex) {
//                System.err.println("Error creating default config file: " + ex.getMessage());
//            }
//        } catch (IOException e) {
//            System.err.println("Error loading config file: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            System.err.println("Error parsing config values: " + e.getMessage());
//        }
//    }
}
