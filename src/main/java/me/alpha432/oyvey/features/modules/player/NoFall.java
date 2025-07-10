package me.alpha432.gaps.features.modules.player; // Đã đổi package thành gaps

import me.alpha432.gaps.Gaps; // Đã đổi import từ OyVey sang Gaps
import me.alpha432.gaps.features.modules.Module; // Đã đổi import từ oyvey.features sang gaps.features
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Removes fall damage", Category.PLAYER, true, false, false);
    }

    @Override
    public void onUpdate() {
        if( !mc.player.isOnGround() && Gaps.positionManager.getFallDistance() > 3 ) // Đã đổi OyVey.positionManager sang Gaps.positionManager
        {
            boolean bl = mc.player.horizontalCollision;
            PlayerMoveC2SPacket.Full pakcet = new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.000000001, mc.player.getZ(),
                    mc.player.getYaw(), mc.player.getPitch(), false, bl );
            mc.player.networkHandler.sendPacket(pakcet);
        }
    }
}
