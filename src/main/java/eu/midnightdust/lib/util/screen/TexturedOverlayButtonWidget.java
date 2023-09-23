package eu.midnightdust.lib.util.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TexturedOverlayButtonWidget extends TexturedButtonWidget {
    protected int xOverlayOffset;
    protected int yOverlayOffset;
    protected TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction, Text text, int xOffset, int yOffset) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, text);
        xOverlayOffset = xOffset;
        yOverlayOffset = yOffset;
    }

    protected TexturedOverlayButtonWidget(Builder buttonBuilder) {
        this(buttonBuilder.x, buttonBuilder.y, buttonBuilder.width, buttonBuilder.height, buttonBuilder.u, buttonBuilder.v, buttonBuilder.hoveredVOffset, buttonBuilder.texture, buttonBuilder.textureWidth, buttonBuilder.textureHeight, buttonBuilder.onPress, buttonBuilder.message, buttonBuilder.xOverlayOffset, buttonBuilder.yOverlayOffset);
        setTooltip(buttonBuilder.tooltip);
    }

    public static TexturedOverlayButtonWidget.Builder texturedBuilder(Text message, ButtonWidget.PressAction onPress) {
        return new Builder(message, onPress);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int i = 66;
        if (!this.isNarratable()) {
            i += hoveredVOffset * 2;
        } else if (this.isSelected()) {
            i += hoveredVOffset;
        }
        context.drawNineSlicedTexture(WIDGETS_TEXTURE, this.getX(), this.getY(), this.width, this.height, 4, 200, 20, 0, i);
        this.drawTexture(context, this.texture, this.getX() + xOverlayOffset, this.getY() + yOverlayOffset, this.u, this.v, this.hoveredVOffset, this.width - xOverlayOffset * 2, this.height - yOverlayOffset * 2, this.textureWidth, this.textureHeight);
    }
    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        public final Text message;
        public final PressAction onPress;
        @Nullable
        public Tooltip tooltip;
        public int x;
        public int y;
        public int width = 150;
        public int height = 20;
        public Identifier texture;
        public int textureWidth = 16;
        public int textureHeight = 16;
        public int u = 0;
        public int v = 0;
        public int hoveredVOffset = 0;
        public int xOverlayOffset = 0;
        public int yOverlayOffset = 0;
        public NarrationSupplier narrationSupplier;

        public Builder(Text message, PressAction onPress) {
            this.narrationSupplier = ButtonWidget.DEFAULT_NARRATION_SUPPLIER;
            this.message = message;
            this.onPress = onPress;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        public Builder texture(Identifier texture, int width, int height) {
            this.texture = texture;
            this.textureWidth = width;
            this.textureHeight = height;
            return this;
        }

        public Builder uv(int u, int v) {
            this.u = u;
            this.v = v;
            return this;
        }

        public Builder overlayOffset(int x, int y) {
            this.xOverlayOffset = x;
            this.yOverlayOffset = y;
            return this;
        }

        public Builder vOffset(int hoveredVOffset) {
            this.hoveredVOffset = hoveredVOffset;
            return this;
        }

        public TexturedOverlayButtonWidget build() {
            return this.build(TexturedOverlayButtonWidget::new);
        }

        public TexturedOverlayButtonWidget build(Function<Builder, TexturedOverlayButtonWidget> builder) {
            return builder.apply(this);
        }
    }
}