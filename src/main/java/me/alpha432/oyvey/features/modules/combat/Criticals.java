package me.alpha432.oyvey.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.models.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Criticals extends Module {
    private final Timer timer = new Timer();
    private int packetMode = 0;

    public Criticals() {
        super("Criticals", "Makes you do critical hits subtly for ghost play", Category.COMBAT, true, false, false);
    }

    @Subscribe
    private void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet && packet.type.getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK) {
            Entity entity = mc.world.getEntityById(packet.entityId);

            if (entity == null
                    || entity instanceof EndCrystalEntity
                    || !mc.player.isOnGround()
                    || mc.player.isFallFlying()
                    || mc.player.isInSwimmingPose()
                    || mc.player.hasVehicle()
                    || !timer.passedMs(mc.player.getAttackCooldownProgress(0.5F) >= 1.0f ? 250 : 500)) {
                return;
            }

            double yOffset = 0.000000000000001;

            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + yOffset, mc.player.getZ(), false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
            
            mc.player.addCritParticles(entity);
            timer.reset();
        }
    }

    @Override
    public String getDisplayInfo() {
        return "Ghost";
    }
}
