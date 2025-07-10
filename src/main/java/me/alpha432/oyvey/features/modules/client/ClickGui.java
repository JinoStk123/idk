package me.alpha432.gaps.features.modules.client; // Đã đổi package thành gaps

import com.google.common.eventbus.Subscribe;
import me.alpha432.gaps.Gaps; // Đã đổi import từ OyVey sang Gaps
import me.alpha432.gaps.event.impl.ClientEvent;
import me.alpha432.gaps.features.commands.Command;
import me.alpha432.gaps.features.gui.GapsGui; // Đã đổi OyVeyGui sang GapsGui
import me.alpha432.gaps.features.modules.Module;
import me.alpha432.gaps.features.settings.Setting;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class ClickGui
        extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = str("Prefix", ".");
    public Setting<Integer> red = num("Red", 0, 0, 255);
    public Setting<Integer> green = num("Green", 0, 0, 255);
    public Setting<Integer> blue = num("Blue", 255, 0, 255);
    public Setting<Integer> hoverAlpha = num("Alpha", 180, 0, 255);
    public Setting<Integer> topRed = num("SecondRed", 0, 0, 255);
    public Setting<Integer> topGreen = num("SecondGreen", 0, 0, 255);
    public Setting<Integer> topBlue = num("SecondBlue", 150, 0, 255);
    public Setting<Integer> alpha = num("HoverAlpha", 240, 0, 255);
    public Setting<Boolean> rainbow = bool("Rainbow", false);
    public Setting<Integer> rainbowHue = num("Delay", 240, 0, 600);
    public Setting<Float> rainbowBrightness = num("Brightness ", 150.0f, 1.0f, 255.0f);
    public Setting<Float> rainbowSaturation = num("Saturation", 150.0f, 1.0f, 255.0f);
    private GapsGui click; // Đã đổi OyVeyGui sang GapsGui

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        setBind(GLFW.GLFW_KEY_RIGHT_SHIFT);
        rainbowHue.setVisibility(v -> rainbow.getValue());
        rainbowBrightness.setVisibility(v -> rainbow.getValue());
        rainbowSaturation.setVisibility(v -> rainbow.getValue());
        this.setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                Gaps.commandManager.setPrefix(this.prefix.getPlannedValue()); // Đã đổi OyVey.commandManager sang Gaps.commandManager
                Command.sendMessage("Prefix set to " + Formatting.DARK_GRAY + Gaps.commandManager.getPrefix()); // Đã đổi OyVey.commandManager sang Gaps.commandManager
            }
            Gaps.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue()); // Đã đổi OyVey.colorManager sang Gaps.colorManager
        }
    }

    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        mc.setScreen(GapsGui.getClickGui()); // Đã đổi OyVeyGui.getClickGui() sang GapsGui.getClickGui()
    }

    @Override
    public void onLoad() {
        Gaps.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue()); // Đã đổi OyVey.colorManager sang Gaps.colorManager
        Gaps.commandManager.setPrefix(this.prefix.getValue()); // Đã đổi OyVey.commandManager sang Gaps.commandManager
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof GapsGui)) { // Đã đổi OyVeyGui sang GapsGui
            this.disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}
