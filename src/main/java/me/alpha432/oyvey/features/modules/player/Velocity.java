package me.alpha432.gaps.features.modules.player; // Đã đổi package thành gaps

import com.google.common.eventbus.Subscribe;
import me.alpha432.gaps.event.impl.PacketEvent; // Đã đổi import từ oyvey.event sang gaps.event
import me.alpha432.gaps.features.modules.Module; // Đã đổi import từ oyvey.features sang gaps.features
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Removes velocity from explosions and entities", Category.PLAYER, true, false, false);
    }

    @Subscribe
    private void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket || event.getPacket() instanceof ExplosionS2CPacket) event.cancel();
    }
}
