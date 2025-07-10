package me.alpha432.gaps.features.gui;

import me.alpha432.gaps.Gaps;
import me.alpha432.gaps.features.Feature;
import me.alpha432.gaps.features.gui.items.Item;
import me.alpha432.gaps.features.gui.items.buttons.ModuleButton;
import me.alpha432.gaps.features.modules.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GapsGui extends Screen {
    private static GapsGui INSTANCE; // Bỏ oyveyGui đi
    static {
        INSTANCE = new GapsGui();
    }

    private final ArrayList<Component> components = new ArrayList();

    public GapsGui() {
        super(Text.literal("Gaps"));
        setInstance();
        load();
    }

    public static GapsGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GapsGui();
        }
        return INSTANCE;
    }

    public static GapsGui getClickGui() {
        return GapsGui.getInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    private void load() {
        int x = 5; // Bắt đầu từ vị trí X hợp lý hơn
        for (final Module.Category category : Gaps.moduleManager.getCategories()) {
            // Thay vì inner class, tạo một instance của Component mới
            this.components.add(new Component(category.getName(), x, 5, true) { // x, y, open. y có thể là 5 cho đẹp
                @Override
                public void setupItems() {
                    // counter1 = new int[]{1}; // Cái này có thể bỏ nếu không dùng
                    Gaps.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
            x += 95; // Tăng X để các panel không bị chồng lên nhau
        }
        // Sắp xếp các item trong từng component
        this.components.forEach(comp -> comp.getItems().sort(Comparator.comparing(Feature::getName)));
    }

    // ... (Giữ nguyên các phương thức updateModule, render, mouseClicked, mouseReleased, mouseScrolled, keyPressed, charTyped, shouldPause, getComponents, getTextOffset, getComponentByName)

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Vẽ background mờ
        context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), new Color(0, 0, 0, 120).hashCode());
        
        // Vẽ và xử lý các component (panel)
        this.components.forEach(component -> component.drawScreen(context, mouseX, mouseY, delta));
        
        super.render(context, mouseX, mouseY, delta); // Quan trọng để các input event vẫn hoạt động
    }
}
