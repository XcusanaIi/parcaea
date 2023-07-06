package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.event.handler.tick.CommandMacroHandler;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.render.entity.BarrierMarker;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.util.io.KeyMouse;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.util.Arrays;

public class GuiIndex extends GuiScreen {

    private PGuiButton[] pGuiButtons;

    private final int screenWidth = 220;
    private final int screenHeight = 220;
    private int x;
    private int y;

    private boolean closed = false;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        pGuiButtons = new PGuiButton[9];

        pGuiButtons[0] = new PGuiButton(0, x, y, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_1"));
        pGuiButtons[1] = new PGuiButton(1, x + 75, y, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_2"));
        pGuiButtons[2] = new PGuiButton(2, x + 150, y, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_3"));
        pGuiButtons[3] = new PGuiButton(3, x, y + 75, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_4"));
        pGuiButtons[4] = new PGuiButton(4, x + 75, y + 75, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_5"));
        pGuiButtons[5] = new PGuiButton(5, x + 150, y + 75, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_6"));
        pGuiButtons[6] = new PGuiButton(6, x, y + 150, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_7"));
        pGuiButtons[7] = new PGuiButton(7, x + 75, y + 150, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_8"));
        pGuiButtons[8] = new PGuiButton(8, x + 150, y + 150, 70, 70, I18n.format("parcaea.render.gui.gui_index.slot_9"));

        pGuiButtons[0].setTexture("textures/gui/gui_index/keybinds.png", "textures/gui/gui_index/keybinds_hovered.png", 50, 50, true);
        pGuiButtons[1].setTexture("textures/gui/gui_index/segment.png", "textures/gui/gui_index/segment_hovered.png", 50, 50, true);
        pGuiButtons[2].setTexture("textures/gui/gui_index/bongo_capoo.png", "textures/gui/gui_index/bongo_capoo_hovered.png", 50, 50, true);
        pGuiButtons[3].setTexture("textures/gui/gui_index/style.png", "textures/gui/gui_index/style_hovered.png", 50, 50, true);
        pGuiButtons[4].setTexture("textures/gui/gui_index/chart.png", "textures/gui/gui_index/chart_hovered.png", 50, 50, true);
        pGuiButtons[5].setTexture("textures/gui/gui_index/config.png", "textures/gui/gui_index/config_hovered.png", 50, 50, true);
        pGuiButtons[6].setTexture("textures/gui/gui_index/coord.png", "textures/gui/gui_index/coord_hovered.png", 50, 50, true);
        pGuiButtons[7].setTexture("textures/gui/gui_index/practice.png", "textures/gui/gui_index/practice_hovered.png", 50, 50, true);
        pGuiButtons[8].setTexture("textures/gui/gui_index/barrier.png", "textures/gui/gui_index/barrier_hovered.png", 50, 50, true);

        buttonList.addAll(Arrays.asList(pGuiButtons));
    }

    @Override
    public void updateScreen() {
        if (!closed && !KeyMouse.isKeyOrMouseDown(KeyBinds.keyIndex.getKeyCode())) {
            mc.displayGuiScreen(null);
            for (PGuiButton pGuiButton : pGuiButtons) {
                if (pGuiButton.isMouseOver()) {
                    actionPerformed(pGuiButton);
                    break;
                }
            }
            closed = true;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiPKControls());
        }else if (button.id == 1) {
            mc.displayGuiScreen(new GuiSegmentMenu());
        }else if (button.id == 2) {
            mc.displayGuiScreen(new GuiBongoCapooConfig());
        }else if (button.id == 3) {
            mc.displayGuiScreen(new GuiHudMenu());
        }else if (button.id == 4) {
            mc.displayGuiScreen(new GuiChart());
        }else if (button.id == 5) {
            mc.displayGuiScreen(new GuiConfig());
        }else if (button.id == 6) {
            if (Segment.findNearestCoordMarker() != null) {
                mc.displayGuiScreen(new GuiEditCoordStrategy());
            }else {
                InfoHud.infoDisplayList.add(new InfoHud.InfoDisplay(
                        new Vec2d(0.5, 0.75),
                        I18n.format("parcaea.render.gui.gui_segment_menu.no_coord_strategy_found"),
                        ColorGeneral.WARN,
                        20,
                        2.0F
                ));
            }
        }else if (button.id == 7) {
            CommandMacroHandler.runCommandMacro();
            mc.displayGuiScreen(null);
        }else if (button.id == 8) {
            BarrierMarker.toggleBarrier();
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
