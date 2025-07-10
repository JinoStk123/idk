// Giả định đây là nội dung cơ bản của lớp Component của bạn
// Tách ra một file Component.java riêng cho dễ quản lý và chỉnh sửa nhé
// package me.alpha432.gaps.features.gui; // Package của Component

// import me.alpha432.gaps.features.Feature; // Import nếu cần
// import me.alpha432.gaps.features.gui.items.Item; // Import nếu cần
// import me.alpha432.gaps.features.gui.items.buttons.ModuleButton; // Import nếu cần
// import net.minecraft.client.gui.DrawContext; // Import nếu cần

import java.util.ArrayList; // Đảm bảo có
import java.util.Comparator; // Đảm bảo có

public abstract class Component { // Đổi thành public abstract class Component

    public String name;
    public int x, y, width, height;
    public boolean open; // Trạng thái mở/đóng của panel
    private boolean dragging; // Trạng thái đang kéo
    private int dragX, dragY; // Tọa độ offset khi kéo
    
    // counter1 của bạn có vẻ là biến tạm trong inner class, nếu tách ra
    // có thể cần quản lý lại, hoặc là bỏ nó đi nếu không dùng cho logic core của Component
    // protected int[] counter1; 

    protected ArrayList<Item> items = new ArrayList<>(); // Danh sách các items (ModuleButton, SettingButton)

    // Constructor của Component
    public Component(String name, int x, int y, boolean open) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = 88; // Hoặc một giá trị width cố định nào đó
        this.height = 18; // Chiều cao của thanh tiêu đề panel
        this.open = open;
        this.dragging = false; // Mặc định không kéo
        setupItems(); // Gọi phương thức setupItems để thêm các module
    }

    // --- Các phương thức Getter/Setter cơ bản ---
    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addButton(ModuleButton button) {
        this.items.add(button);
    }
    
    // --- Phương thức để thiết lập các Item (module/settings) ---
    // Cái này sẽ được override trong từng Category Component cụ thể
    public abstract void setupItems();

    // --- Các phương thức xử lý sự kiện Mouse/Key ---

    public void drawScreen(DrawContext context, int mouseX, int mouseY, float delta) {
        // Nếu đang kéo, cập nhật vị trí của panel
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        // --- Logic vẽ Panel (Header và Background) ---
        // Ví dụ: Vẽ header màu theo client.colorManager
        // Vẽ background nếu panel đang mở
        // context.drawRect(x, y, x + width, y + height, colorManager.getPrimaryColor().getRGB()); // Example
        // context.drawText(name, x + 2, y + 4, 0xFFFFFF); // Example

        // Nếu panel đang mở, vẽ các item (module) bên trong
        if (open) {
            // Cập nhật vị trí Y của các item dựa trên vị trí Y của panel
            int currentY = y + height; // Bắt đầu từ dưới header
            for (Item item : items) {
                item.setX(x);
                item.setY(currentY);
                item.setWidth(width);
                item.drawScreen(context, mouseX, mouseY, delta); // Vẽ từng item
                currentY += item.getHeight(); // Tăng Y cho item tiếp theo
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        // Kiểm tra xem click có vào header của panel không
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (clickedButton == 0) { // Chuột trái (để kéo)
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            } else if (clickedButton == 1) { // Chuột phải (để mở/đóng panel)
                open = !open;
            }
        }
        // Nếu panel đang mở, chuyển sự kiện click tới các item bên trong
        if (open) {
            for (Item item : items) {
                item.mouseClicked(mouseX, mouseY, clickedButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        dragging = false; // Ngừng kéo khi nhả chuột
        if (open) {
            for (Item item : items) {
                item.mouseReleased(mouseX, mouseY, releaseButton);
            }
        }
    }
    
    // Thêm các phương thức khác như mouseScrolled, keyPressed, charTyped nếu bạn muốn Panel tự xử lý scroll/input
    // Hoặc bạn có thể để GapsGui xử lý và chuyển tiếp
    public void onKeyPressed(int keyCode) {
        if (open) {
            for (Item item : items) {
                item.onKeyPressed(keyCode);
            }
        }
    }

    public void onKeyTyped(char chr, int modifiers) {
        if (open) {
            for (Item item : items) {
                item.onKeyTyped(chr, modifiers);
            }
        }
    }

    // --- Phương thức để tự động tính toán chiều cao của panel khi mở ---
    public int getTotalHeight() {
        if (!open) {
            return height; // Chỉ chiều cao header nếu đóng
        }
        int total = height;
        for (Item item : items) {
            total += item.getHeight(); // Cộng dồn chiều cao của tất cả các item
        }
        return total;
    }
}
