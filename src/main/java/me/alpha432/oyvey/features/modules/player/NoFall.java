package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "Removes fall damage", Category.PLAYER, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;
        
        if (!mc.player.isOnGround() && mc.player.fallDistance > 3.0f && !mc.player.hasVehicle() && !mc.player.isFallFlying()) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                mc.player.getX(), 
                mc.player.getY(), 
                mc.player.getZ(), 
                true 
            ));
        }
    }
}
