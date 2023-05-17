package io.github.xcusanaii.parcaea.render.hud;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.NoteHandler;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.event.note.BasicNoteHandler;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgBasic;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.AbsHud;
import io.github.xcusanaii.parcaea.util.math.Vec2i;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BasicHud extends AbsHud {

    public static Vec2i leftTop = new Vec2i();
    public static Vec2i rightBottom = new Vec2i();
    public static Vec2i jLineCenter = new Vec2i();
    public static Vec2i[] tracks = new Vec2i[5];
    public static List<KeyNoteDisplay> keyNoteDisplays = new ArrayList<KeyNoteDisplay>();
    public static List<MouseNoteDisplay> mouseNoteDisplays = new ArrayList<MouseNoteDisplay>();
    public static List<LastInputDisplay> lastInputDisplays = new ArrayList<LastInputDisplay>();
    public static MouseNoteDisplay lastUnstableMouseNote = null;
    public static int keyNoteWidth = 0;
    public int keyNoteHeight = 0;
    public int stripWidth = 0;
    public int indicatorSize = 0;
    public int mouseNoteSize = 0;
    public int mouseTrackLeft = 0;
    public int mouseTrackWidth = 0;

    public BasicHud() {
        super();
    }

    @Override
    public void calculateFramePos() {
        jLineCenter.x = screenCenter.x;
        jLineCenter.y = screenCenter.y + CfgBasic.basicHudHeight / 2 - CfgBasic.basicJLineOffsetY;
        leftTop.x = screenCenter.x - CfgBasic.basicHudWidth / 2;
        leftTop.y = screenCenter.y - CfgBasic.basicHudHeight / 2;
        rightBottom.x = screenCenter.x + CfgBasic.basicHudWidth / 2;
        rightBottom.y = screenCenter.y + CfgBasic.basicHudHeight / 2;
        tracks[0].x = (int) (jLineCenter.x - CfgBasic.basicHudWidth * 0.4);
        tracks[0].y = jLineCenter.y;
        for (int i = 1; i < 5; i++) {
            tracks[i].x = tracks[i - 1].x + CfgBasic.basicHudWidth / 5;
            tracks[i].y = tracks[0].y;
        }
        keyNoteWidth = (int) (CfgBasic.basicHudWidth / 5 * CfgBasic.basicKeyNoteSize);
        keyNoteHeight = (int) (keyNoteWidth / CfgBasic.basicKeyNoteAspectRatio);
        stripWidth = (int) (keyNoteHeight * CfgBasic.basicStripWidthRatio);

        indicatorSize = (int) (CfgBasic.basicHudWidth / 5 * CfgBasic.basicMouseIndicatorSize);
        mouseNoteSize = (int) (indicatorSize * CfgBasic.basicMouseNoteSizeRatio);
        mouseTrackLeft = (int) (leftTop.x + CfgBasic.basicHudWidth * CfgBasic.basicMouseTrackPaddingRatio);
        mouseTrackWidth = (int) (CfgBasic.basicHudWidth * (1 - 2 * CfgBasic.basicMouseTrackPaddingRatio));
    }

    @Override
    public void draw45S() {
        drawRectWithBorder(jLineCenter, CfgBasic.basicHudWidth / 2, 1, ColorGeneral.ALL_PERFECT, 1, ColorGeneral.ALL_PERFECT_BORDER);
        drawStyleNoteWithBorder(jLineCenter, mouseNoteSize / 2, mouseNoteSize / 2, ColorGeneral.BLUE, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
        drawStyleNoteWithBorder(new Vec2i(jLineCenter.x - CfgBasic.basicHudWidth / 4, jLineCenter.y), mouseNoteSize / 2, mouseNoteSize / 2, ColorGeneral.YELLOW, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
        drawStyleNoteWithBorder(new Vec2i(jLineCenter.x - CfgBasic.basicHudWidth / 2, jLineCenter.y), mouseNoteSize / 2, mouseNoteSize / 2, ColorGeneral.PINK, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
        drawStyleNoteWithBorder(new Vec2i(jLineCenter.x + CfgBasic.basicHudWidth / 4, jLineCenter.y), mouseNoteSize / 2, mouseNoteSize / 2, ColorGeneral.YELLOW, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
        drawStyleNoteWithBorder(new Vec2i(jLineCenter.x + CfgBasic.basicHudWidth / 2, jLineCenter.y), mouseNoteSize / 2, mouseNoteSize / 2, ColorGeneral.PINK, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
        double mousePosPercent = InputStat.mousePosPercent;
        if (mousePosPercent < 0) mousePosPercent = 0.0;
        if (mousePosPercent > 1) mousePosPercent = 1.0;
        int indicatorX = (int) (leftTop.x + CfgBasic.basicHudWidth * mousePosPercent);
        drawStyleNoteWithBorder(new Vec2i(indicatorX, jLineCenter.y + indicatorSize / 2), indicatorSize / 2, indicatorSize / 2, ColorGeneral.ORANGE, CfgBasic.basicNoteBorderSize, ColorGeneral.BORDER_BLACK);
    }

    @Override
    public void drawFrame() {
        String hex = Integer.toHexString((int) Math.round((CfgBasic.basicHudBGOpacity / 100.0) * 255.0));
        int padding = CfgBasic.basicHudBGPadding;
        drawRect(
                leftTop.x - padding,
                leftTop.y - padding,
                rightBottom.x + padding,
                rightBottom.y + padding,
                new BigInteger(hex + "000000", 16).intValue()
        );
        int jLineColor;
        int jLineBorderColor;
        switch (NoteHandler.rating) {
            case ALL_PERFECT:
                jLineColor = ColorGeneral.ALL_PERFECT;
                jLineBorderColor = ColorGeneral.ALL_PERFECT_BORDER;
                break;
            case FULL_COMBO:
                jLineColor = ColorGeneral.FULL_COMBO;
                jLineBorderColor = ColorGeneral.FULL_COMBO_BORDER;
                break;
            default:
                jLineColor = ColorGeneral.A_MISS;
                jLineBorderColor = ColorGeneral.A_MISS_BORDER;
                break;
        }
        drawRectWithBorder(jLineCenter, CfgBasic.basicHudWidth / 2, 1, jLineColor, 1, jLineBorderColor);
        int keyPressWidth = (int) (CfgBasic.basicHudWidth * CfgBasic.basicKeyNoteSize * 0.2);
        int keyPressHeight = (int) (keyPressWidth / CfgBasic.basicKeyNoteAspectRatio);
        List<Vec2i> pressedTracks = new ArrayList<Vec2i>();
        for (int i = 0; i < 7; i++) {
            if (InputStat.isKeyDown.keyList[i]) {
                pressedTracks.add(tracks[BasicNoteHandler.mapTrackSlot(i)]);
            }
        }
        for (Vec2i track : tracks) {
            drawRectWithBorder(
                    new Vec2i(track.x, track.y + keyPressHeight / 2 + 2),
                    keyPressWidth / 2,
                    keyPressHeight / 2,
                    ColorGeneral.UNPRESSED_KEY,
                    1,
                    ColorGeneral.UNPRESSED_KEY
            );
        }
        for (Vec2i pressedTrack : pressedTracks) {
            drawRectWithBorder(
                    new Vec2i(pressedTrack.x, pressedTrack.y + keyPressHeight / 2 + 2),
                    keyPressWidth / 2,
                    keyPressHeight / 2,
                    ColorGeneral.PRESSED_KEY,
                    1,
                    ColorGeneral.PRESSED_KEY_BORDER
            );
        }
    }

    @Override
    public void drawKeyNote(float partialTicks) {
        if (CfgGeneral.enableLastInput) {
            drawLastInputKeyNote(partialTicks);
        }
        for (KeyNoteDisplay keyNote : keyNoteDisplays) {
            int y1 = (int) (keyNote.y1 + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed);
            int y2 = (int) (keyNote.y2 + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed);
            if ((y1 < leftTop.y && y2 < leftTop.y) || (y1 > jLineCenter.y && y2 > jLineCenter.y)) continue;
            y1 = Math.max(y1, leftTop.y);
            y2 = Math.min(y2, jLineCenter.y);
            drawRect(
                    tracks[keyNote.trackSlot].x - stripWidth / 2,
                    y1,
                    tracks[keyNote.trackSlot].x + stripWidth / 2,
                    y2,
                    keyNote.color
            );
            drawStyleNoteWithBorder(
                    new Vec2i(tracks[keyNote.trackSlot].x, y2),
                    keyNoteWidth / 2,
                    keyNoteHeight / 2,
                    keyNote.color,
                    CfgBasic.basicNoteBorderSize - 1,
                    ColorGeneral.BORDER_BLACK
            );
            drawStyleNoteWithBorder(
                    new Vec2i(tracks[keyNote.trackSlot].x, y2),
                    (int) (keyNoteHeight * 0.75),
                    (int) (keyNoteHeight * 0.75),
                    keyNote.color,
                    CfgBasic.basicNoteBorderSize,
                    ColorGeneral.BORDER_BLACK
            );
            drawStyleNoteWithBorder(
                    new Vec2i(tracks[keyNote.trackSlot].x, y1),
                    keyNoteHeight / 2,
                    keyNoteHeight / 2,
                    keyNote.color,
                    CfgBasic.basicNoteBorderSize,
                    ColorGeneral.BORDER_BLACK
            );
        }
    }

    private void drawLastInputKeyNote(float partialTicks) {
        for (LastInputDisplay lastInput : lastInputDisplays) {
            int y2 = (int) (lastInput.y + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed);
            int y1 = (int) (y2 - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed);
            if ((y1 < leftTop.y && y2 < leftTop.y) || (y1 > jLineCenter.y && y2 > jLineCenter.y)) continue;
            y1 = Math.max(y1, leftTop.y);
            y2 = Math.min(y2, jLineCenter.y);
            drawRect(
                    (int) (tracks[lastInput.trackSlot].x - CfgBasic.basicHudWidth * 0.08D),
                    y1,
                    (int) (tracks[lastInput.trackSlot].x + CfgBasic.basicHudWidth * 0.08D),
                    y2,
                    ColorGeneral.UNPRESSED_KEY
            );
            drawRect(
                    (int) (tracks[lastInput.trackSlot].x - CfgBasic.basicHudWidth * 0.08D + 1),
                    (int) (y1 + Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed / 3),
                    (int) (tracks[lastInput.trackSlot].x - CfgBasic.basicHudWidth * 0.08D + 3),
                    (int) (y2 - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed / 3),
                    lastInput.color
            );
            drawRect(
                    (int) (tracks[lastInput.trackSlot].x + CfgBasic.basicHudWidth * 0.08D - 3),
                    (int) (y1 + Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed / 3),
                    (int) (tracks[lastInput.trackSlot].x + CfgBasic.basicHudWidth * 0.08D - 1),
                    (int) (y2 - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed / 3),
                    lastInput.color
            );
        }
    }

    @Override
    public void drawMouseNote(float partialTicks) {
        drawLastUnstableMouseNote(partialTicks);
        for (int i = 0; i < mouseNoteDisplays.size(); i++) {
            MouseNoteDisplay mouseNote = mouseNoteDisplays.get(i);
            double y = mouseNote.y + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed;
            if (y < leftTop.y || y > jLineCenter.y + Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed) continue;
            Vec2i notePos = new Vec2i((int) (mouseTrackLeft + mouseTrackWidth * mouseNote.posPercent), (int) y);
            if (i + 1 < mouseNoteDisplays.size() && mouseNoteDisplays.get(i + 1).posPercent != mouseNote.posPercent) {
                MouseNoteDisplay mouseNoteNext = mouseNoteDisplays.get(i + 1);
                if (y > jLineCenter.y) {
                    y = jLineCenter.y;
                    double posPercent = mouseNote.posPercent + (mouseNoteNext.posPercent - mouseNote.posPercent) * partialTicks;
                    notePos = new Vec2i((int) (mouseTrackLeft + mouseTrackWidth * posPercent), (int) y);
                }
                double yNext = mouseNoteNext.y + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed;
                Vec2i notePosNext = new Vec2i((int) (mouseTrackLeft + mouseTrackWidth * mouseNoteNext.posPercent), (int) yNext);
                drawStyledLine(notePos, notePosNext, (int) (mouseNoteSize * 0.8), ColorGeneral.YELLOW_ALPHA);
                drawStyledLine(notePos, notePosNext, CfgBasic.basicNoteBorderSize, ColorGeneral.ALL_PERFECT_BORDER);
            }
            if (y > jLineCenter.y) {
                notePos.y = jLineCenter.y;
            }
            drawStyleNoteWithBorder(
                    notePos,
                    (mouseNote.color == ColorGeneral.BLUE ? indicatorSize : mouseNoteSize) / 2,
                    (mouseNote.color == ColorGeneral.BLUE ? indicatorSize : mouseNoteSize) / 2,
                    mouseNote.color,
                    CfgBasic.basicNoteBorderSize,
                    ColorGeneral.BORDER_BLACK
            );
        }
        int indicatorX = (int) (mouseTrackLeft + mouseTrackWidth * InputStat.mousePosPercent);
        indicatorX = Math.max(indicatorX, leftTop.x);
        indicatorX = Math.min(indicatorX, rightBottom.x);
        drawStyleNoteWithBorder(
                new Vec2i(indicatorX, jLineCenter.y),
                indicatorSize / 2,
                indicatorSize / 2,
                ColorGeneral.MAGENTA,
                CfgBasic.basicNoteBorderSize,
                ColorGeneral.BORDER_BLACK
        );
    }

    private void drawLastUnstableMouseNote(float partialTicks) {
        if (lastUnstableMouseNote == null || !CfgGeneral.enableLastInput) return;
        int y = (int) (lastUnstableMouseNote.y + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed);
        if (y < leftTop.y || y > jLineCenter.y) return;
        Vec2i center = new Vec2i((int) (mouseTrackLeft + mouseTrackWidth * lastUnstableMouseNote.posPercent), y);
        drawRect(
                (int) (center.x - indicatorSize / 1.5),
                (int) (center.y - indicatorSize / 1.5),
                (int) (center.x + indicatorSize / 1.5),
                (int) (center.y + indicatorSize / 1.5),
                ColorGeneral.BG_UNSTABLE
        );
    }

    @Override
    public void setNoteHandler() {
        TickHandler.setNoteHandler(new BasicNoteHandler());
    }

    public static class LastInputDisplay {
        public final int trackSlot;
        public final int color;
        public double y;

        public LastInputDisplay(int trackSlot, int color, double y) {
            this.trackSlot = trackSlot;
            this.color = color;
            this.y = y;
        }
    }

    public static class KeyNoteDisplay {
        public final int trackSlot;
        public final int color;
        public double y1;
        public double y2;

        public KeyNoteDisplay(int trackSlot, int color, double y1, double y2) {
            this.trackSlot = trackSlot;
            this.color = color;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    public static class MouseNoteDisplay {
        public final double posPercent;
        public final int color;
        public double y;

        public MouseNoteDisplay(double posPercent, int color, double y) {
            this.posPercent = posPercent;
            this.color = color;
            this.y = y;
        }
    }

    static {
        for (int i = 0; i < 5; i++) {
            tracks[i] = new Vec2i();
        }
    }
}
