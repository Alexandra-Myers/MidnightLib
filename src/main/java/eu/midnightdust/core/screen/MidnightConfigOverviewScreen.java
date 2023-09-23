package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class MidnightConfigOverviewScreen extends Screen {

    public MidnightConfigOverviewScreen(Screen parent) {
        super(Text.translatable( "midnightlib.overview.title"));
        this.parent = parent;
    }
    private final Screen parent;
    private MidnightOverviewListWidget list;

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> Objects.requireNonNull(client).setScreen(parent)).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());

        this.list = new MidnightOverviewListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        if (this.client != null && this.client.world != null) this.list.setRenderBackground(false);
        this.addSelectableChild(this.list);
        List<String> sortedMods = new ArrayList<>(MidnightConfig.configClass.keySet());
        Collections.sort(sortedMods);
        sortedMods.forEach((modid) -> {
            if (!MidnightLibClient.hiddenMods.contains(modid)) {
                list.addButton(ButtonWidget.builder(Text.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(client).setScreen(MidnightConfig.getScreen(this,modid))).dimensions(this.width / 2 - 125, this.height - 28, 250, 20).build());
            }
        });
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
    @OnlyIn(Dist.CLIENT)
    public static class MidnightOverviewListWidget extends ElementListWidget<OverviewButtonEntry> {
        public MidnightOverviewListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
            super(minecraftClient, i, j, k, l, m);
            this.centerListVertically = false;
        }
        @Override
        public int getScrollbarPositionX() {return this.width-7;}

        public void addButton(ClickableWidget button) {
            this.addEntry(OverviewButtonEntry.create(button));
        }
        @Override
        public int getRowWidth() { return 400; }
    }
    public static class OverviewButtonEntry extends ElementListWidget.Entry<OverviewButtonEntry> {
        private final ClickableWidget button;

        private OverviewButtonEntry(ClickableWidget button) {
            this.button = button;
        }
        public static OverviewButtonEntry create(ClickableWidget button) {return new OverviewButtonEntry(button);}
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            button.setY(y);
            button.render(context, mouseX, mouseY, tickDelta);
        }
        public List<? extends Element> children() {return List.of(button);}
        public List<? extends Selectable> selectableChildren() {return List.of(button);}
    }
}