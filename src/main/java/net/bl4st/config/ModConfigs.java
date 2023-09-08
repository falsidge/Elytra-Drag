package net.bl4st.config;

import com.mojang.datafixers.util.Pair;
import net.bl4st.elytradrag.ElytraDrag;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    /**
     * The drag coefficient to apply when sneaking while flying
     */
    public static float ELYTRA_DRAG;

    /**
     * The Maximum fallDistance for a player sneaking and flying
     */
    public static float MAXIMUM_FALLDISTANCE;

    /**
     * The minimum speed required to apply drag. This is to avoid near-stationary flight
     */
    public static float MINIMUM_SPEED;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(ElytraDrag.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("drag", 2.0d), "The drag coefficient to apply when sneaking while flying");
        configs.addKeyValuePair(new Pair<>("maximum-fall-distance-while-slowing-Down", 15.0d), "The Maximum fallDistance for a player sneaking and flying");
        configs.addKeyValuePair(new Pair<>("minimum-speed-required", 3.0d), "The minimum speed required to apply drag, in blocks/seconds");
    }

    private static void assignConfigs() {
        ELYTRA_DRAG = (float)CONFIG.getOrDefault("drag", 2.0d);
        MAXIMUM_FALLDISTANCE = (float)CONFIG.getOrDefault("maximum-fall-distance-while-slowing-Down", 15.0d);
        MINIMUM_SPEED = (float)CONFIG.getOrDefault("minimum-speed-required", 3.0d);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}