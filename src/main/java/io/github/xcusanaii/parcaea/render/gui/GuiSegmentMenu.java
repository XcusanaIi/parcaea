package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.io.SegmentLoader;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.util.BiConsumerWithArg;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class GuiSegmentMenu extends GuiScreen {

    private PGuiTextField txtSearch;
    private PGuiTextField txtSegmentViewDistance;
    private PGuiButton btnEnableSegment;
    private PGuiButton[] lstItems;
    private PGuiButton[] lstItemsRename;
    private PGuiButton[] lstItemsDelete;
    private List<String> idData;
    private List<String> displayData;
    private int scrollY = 0;

    private final int screenWidth = 395;
    private final int screenHeight = 270;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        idData = new ArrayList<String>();
        displayData = new ArrayList<String>();
        lstItems = new PGuiButton[10];
        lstItemsRename = new PGuiButton[10];
        lstItemsDelete = new PGuiButton[10];

        txtSearch = new PGuiTextField(100, fontRendererObj, x, y, 200, 20);

        txtSegmentViewDistance = new PGuiTextField(100, fontRendererObj, x + 353, y + 50, 30, 20);
        txtSegmentViewDistance.setText(String.valueOf(CfgGeneral.segmentViewDistance));

        buttonList.add(new PGuiButton(30, x + 205, y, 40, 20, I18n.format("txt.save")));

        buttonList.add(new PGuiButton(31, x + 250, y, 40, 20, I18n.format("txt.reload")));

        buttonList.add(new PGuiButton(32, x + 295, y, 100, 20, I18n.format("txt.new")));

        btnEnableSegment = new PGuiButton(33, x + 295, y + 25, 100, 20, I18n.format("txt.enable_segment"));
        btnEnableSegment.packedFGColour = CfgGeneral.enableSegment ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableSegment);

        buttonList.add(new PGuiButton(34, x + 387, y + 50, 8, 8, ""));
        buttonList.add(new PGuiButton(35, x + 387, y + 62, 8, 8, ""));

        buttonList.add(new PGuiButton(36, x + 295, y + 225, 100, 20, I18n.format("parcaea.render.gui.gui_segment_menu.new_coord_strategy")));

        buttonList.add(new PGuiButton(37, x + 295, y + 250, 100, 20, I18n.format("parcaea.render.gui.gui_segment_menu.remove_coord_strategy")));

        for (int i = 0; i < 10; i++) {
            lstItems[i] = new PGuiButton(i, x, y + 25 * (i + 1), 200, 20, I18n.format("txt.key_none"));
            buttonList.add(lstItems[i]);
        }

        for (int i = 0; i < 10; i++) {
            lstItemsRename[i] = new PGuiButton(i + 10, x + 205, y + 25 * (i + 1), 40, 20, I18n.format("txt.rename"));
            buttonList.add(lstItemsRename[i]);
        }

        for (int i = 0; i < 10; i++) {
            lstItemsDelete[i] = new PGuiButton(i + 20, x + 250, y + 25 * (i + 1), 40, 20, I18n.format("txt.delete"));
            buttonList.add(lstItemsDelete[i]);
        }

        reloadSegment();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id < 10) {
            Segment segment = Segment.searchSegment(button.displayString);
            if (segment != null) {
                Segment.setSelectedSegment(segment);
                mc.displayGuiScreen(null);
            }
        }else if (button.id < 20) {
            mc.displayGuiScreen(new GuiTextInput(I18n.format("txt.rename"), new BiConsumerWithArg(new String[]{String.valueOf(button.id)}) {
                @Override
                public void accept(String s, Boolean aBoolean) {
                    if (aBoolean && !s.equals("")) {
                        s = StringUtil.getUniqueId(s, idData.toArray(new String[0]));
                        Segment segment = Segment.searchSegment(lstItems[Integer.parseInt(args[0]) - 10].displayString);
                        if (segment != null) segment.id = s;
                    }
                    mc.displayGuiScreen(new GuiSegmentMenu());
                }
            }));
        }else if (button.id < 30) {
            Segment segment = Segment.searchSegment(lstItems[button.id - 20].displayString);
            if (segment != null) {
                if (segment == Segment.selectedSegment) {
                    Segment.selectedSegment.coords.clear();
                    Segment.reGenCoordMarker();
                }
                Segment.segments.remove(segment);
            }
            reloadSegment();
            txtSearch.setText("");
            matchSegments();
        }else if (button.id == 30) {
            SegmentLoader.saveSegment();
        }else if (button.id == 31) {
            SegmentLoader.reloadSegment();
            reloadSegment();
            txtSearch.setText("");
            matchSegments();
        }else if (button.id == 32) {
            mc.displayGuiScreen(new GuiTextInput(I18n.format("txt.new"), new BiConsumer<String, Boolean>() {
                @Override
                public void accept(String s, Boolean aBoolean) {
                    if (aBoolean && !s.equals("")) {
                        s = StringUtil.getUniqueId(s, idData.toArray(new String[0]));
                        Segment segment = new Segment(s, new ArrayList<CoordStrategy>());
                        Segment.segments.add(segment);
                        Segment.setSelectedSegment(segment);
                    }
                    mc.displayGuiScreen(new GuiSegmentMenu());
                }
            }));
        }else if (button.id == 33) {
            CfgGeneral.enableSegment = !CfgGeneral.enableSegment;
            btnEnableSegment.packedFGColour = CfgGeneral.enableSegment ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 34) {
            if (CfgGeneral.segmentViewDistance + 1 <= 512) {
                CfgGeneral.segmentViewDistance++;
            }
            txtSegmentViewDistance.setText(String.valueOf(CfgGeneral.segmentViewDistance));
        }else if (button.id == 35) {
            if (CfgGeneral.segmentViewDistance - 1 > 0) {
                CfgGeneral.segmentViewDistance--;
            }
            txtSegmentViewDistance.setText(String.valueOf(CfgGeneral.segmentViewDistance));
        }else if (button.id == 36) {
            if (Segment.selectedSegment != null) {
                Segment.addCoordStrategy();
                mc.displayGuiScreen(new GuiEditCoordStrategy());
            }else {
                InfoHud.infoDisplayList.add(new InfoHud.InfoDisplay(
                        new Vec2d(0.5, 0.75),
                        I18n.format("parcaea.render.gui.gui_segment_menu.select_segment_first"),
                        ColorGeneral.WARN,
                        20,
                        2.0F
                ));
            }
        }else if (button.id == 37) {
            Segment.removeNearestCoordMarker();
            mc.displayGuiScreen(null);
        }
    }

    private void reloadSegment() {
        idData.clear();
        displayData.clear();
        for (Segment segment : Segment.segments) {
            idData.add(segment.id);
        }
        displayData.addAll(idData);
        updateItem();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (displayData.size() > 10) {
            int wheel = Mouse.getDWheel();
            if (wheel != 0) {
                if (wheel > 0) {
                    if (scrollY > 0) scrollY--;
                } else {
                    if (scrollY < displayData.size() - 10) scrollY++;
                }
                updateItem();
            }
        }
    }

    private void updateItem() {
        for (int i = 0; i < 10; i++) {
            lstItems[i].displayString = I18n.format("txt.key_none");
        }
        if (displayData.size() <= 10) {
            for (int i = 0; i < displayData.size(); i++) {
                lstItems[i].displayString = displayData.get(i);
            }
        }else {
            for (int i = 0; i < 10; i++) {
                lstItems[i].displayString = displayData.get(scrollY + i);
            }
        }
    }

    private void matchSegments() {
        displayData.clear();
        for (String str: idData) {
            if (StringUtil.matchString(txtSearch.getText(), str)) {
                displayData.add(str);
            }
        }
        updateItem();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtSearch.drawTextBox();
        txtSegmentViewDistance.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.segment_view_distance"), x + 322, y + 55, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtSearch.updateCursorCounter();
        txtSegmentViewDistance.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (txtSearch.isFocused()) {
            txtSearch.textboxKeyTyped(typedChar, keyCode);
            matchSegments();
        }
        if (txtSegmentViewDistance.isFocused()) {
            txtSegmentViewDistance.textboxKeyTyped(typedChar, keyCode);
            String str = txtSegmentViewDistance.getText();
            if (StringUtil.isInteger(str)) {
                int distance = Math.abs(Integer.parseInt(str));
                if (distance <= 1024) CfgGeneral.segmentViewDistance = distance;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
        txtSearch.mouseClicked(mouseX, mouseY, mouseButton);
        txtSegmentViewDistance.mouseClicked(mouseX, mouseY, mouseButton);
        if (!txtSegmentViewDistance.isFocused()) {
            txtSegmentViewDistance.setText(String.valueOf(CfgGeneral.segmentViewDistance));
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
