package io.github.xcusanaii.parcaea.render.hud;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.handler.tick.NoteHandler;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.note.BasicNoteHandler;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgBasic;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.UnstableMouseNote;
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
    public static List<UnstableMouseNoteDisplay> unstableMouseNoteDisplays = new ArrayList<UnstableMouseNoteDisplay>();
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
        drawRectWithBorder(jLineCenter, CfgBasic.basicHudWidth / 2, 5, jLineColor, 1, jLineBorderColor);
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

        drawLastUnstableMouseNote(partialTicks);

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
        for (UnstableMouseNoteDisplay note : unstableMouseNoteDisplays) {
            double y = note.y + Parcaea.PX_PER_TICK * partialTicks * CfgGeneral.noteSpeed;
            if (y < leftTop.y || y > jLineCenter.y + Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed) continue;
            y = y > jLineCenter.y ? jLineCenter.y : y;
            int x = (int) (mouseTrackLeft + mouseTrackWidth * note.posPercent);
            if (note.type == UnstableMouseNote.Type.BOTH || note.type == UnstableMouseNote.Type.LEFT) {
                drawPolygon(new Vec2i[]{
                        new Vec2i((int) (x), (int) (y + mouseNoteSize / 3.0D) + 1),
                        new Vec2i((int) (x), (int) (y - mouseNoteSize / 3.0D)),
                        new Vec2i((int) (x - mouseNoteSize / 3.0D), (int) y)
                }, ColorGeneral.BG_UNSTABLE);
            }
            if (note.type == UnstableMouseNote.Type.BOTH || note.type == UnstableMouseNote.Type.RIGHT) {
                drawPolygon(new Vec2i[]{
                        new Vec2i((int) (x), (int) (y + mouseNoteSize / 3.0D) + 1),
                        new Vec2i((int) (x + mouseNoteSize / 3.0D) + 1, (int) y),
                        new Vec2i((int) (x), (int) (y - mouseNoteSize / 3.0D))
                }, ColorGeneral.BG_UNSTABLE);
            }
        }
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

    public static class UnstableMouseNoteDisplay {
        public final double posPercent;
        public double y;
        public UnstableMouseNote.Type type;

        public UnstableMouseNoteDisplay(double posPercent, double y, UnstableMouseNote.Type type) {
            this.posPercent = posPercent;
            this.y = y;
            this.type = type;
        }
    }

    static {
        for (int i = 0; i < 5; i++) {
            tracks[i] = new Vec2i();
        }
    }
}
