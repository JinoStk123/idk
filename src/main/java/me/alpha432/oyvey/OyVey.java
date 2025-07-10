package me.alpha432.gaps; // Đổi package thành gaps

import me.alpha432.gaps.manager.*; // Đổi import manager thành gaps.manager
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gaps implements ModInitializer, ClientModInitializer { // Đổi tên class từ OyVey thành Gaps
    public static final String NAME = "Gaps"; // Đổi tên hiển thị
    public static final String VERSION = "0.0.3 - " + SharedConstants.getGameVersion().getName(); // Có thể cập nhật version nếu muốn

    public static float TIMER = 1f;

    public static final Logger LOGGER = LogManager.getLogger("Gaps"); // Đổi tên Logger
    public static ServerManager serverManager;
    public static ColorManager colorManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static HoleManager holeManager;
    public static EventManager eventManager;
    public static SpeedManager speedManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;

    @Override public void onInitialize() {
        eventManager = new EventManager();
        serverManager = new ServerManager();
        rotationManager = new RotationManager();
        positionManager = new PositionManager();
        friendManager = new FriendManager();
        colorManager = new ColorManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        speedManager = new SpeedManager();
        holeManager = new HoleManager();
    }

    @Override public void onInitializeClient() {
        eventManager.init();
        moduleManager.init();

        configManager = new ConfigManager();
        configManager.load();
        colorManager.init();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.save()));
    }
}
