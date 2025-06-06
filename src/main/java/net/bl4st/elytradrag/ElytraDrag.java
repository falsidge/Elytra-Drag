package net.bl4st.elytradrag;

import com.mojang.logging.LogUtils;
import net.bl4st.config.Config;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@Mod(ElytraDrag.MOD_ID)
public class ElytraDrag {

	public static final String MOD_ID = "elytradrag";
	public static final Logger LOGGER = LogUtils.getLogger();

	public ElytraDrag(IEventBus modEventBus, ModContainer modContainer) {
//		ServerTickEvents.END_SERVER_TICK.register(this::ElytraDragTick);
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
	@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
	public static class ServerGameEvents {
		@SubscribeEvent
		private static void ElytraDragTick(ServerTickEvent.Post event) {
			var playerList  = event.getServer().getPlayerList().getPlayers();
			if(playerList.isEmpty())
				return;

			for (ServerPlayer spe : playerList)
			{
				if(spe.isFallFlying() && spe.isShiftKeyDown())
				{
					spe.getJumpBoostPower();
					var playerVelocity = spe.getDeltaMovement();
					var playerSpeed = playerVelocity.length() * 20.0f;

					if(playerSpeed > Config.MINIMUM_SPEED)
					{
						var factor = 1.0f - 0.05f * Config.ELYTRA_DRAG;
						var newVelocity = playerVelocity.multiply(factor, factor, factor);
						spe.setDeltaMovement(newVelocity);
						spe.hurtMarked = true;
					}
					LimitFallDistance(spe);
				}
			}
		}

		/**
		 * Caps the FallDistance when drag is applied and
		 * makes the vertical speed requirement for
		 * fallDistance reset easier to reach
		 */
		private static void LimitFallDistance(ServerPlayer player) {
			if (player.getDeltaMovement().y() > -1.0f && player.fallDistance > 1.0f)
				player.fallDistance = 1.0f;
			else if (player.fallDistance > Config.MAXIMUM_FALLDISTANCE) {
				player.fallDistance = Config.MAXIMUM_FALLDISTANCE;
			}
		}
	}
}