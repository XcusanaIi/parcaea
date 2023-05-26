package io.github.xcusanaii.parcaea.model.config;

import io.github.xcusanaii.parcaea.Parcaea;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader {
    public static void loadConfig() {
        Configuration config = Parcaea.config;

        CfgGeneral.enableMod = config.get("general", "enableMod", true).getBoolean();
        CfgGeneral.enableParcaea = config.get("general", "enableParcaea", true).getBoolean();
        CfgGeneral.enableSnake = config.get("general", "enableSnake", true).getBoolean();
        CfgGeneral.enable45S = config.get("general", "enable45S", true).getBoolean();
        CfgGeneral.noteSpeed = config.get("general", "noteSpeed", 1.0).getDouble();
        CfgGeneral.enableAutoPos = config.get("general", "enableAutoPos", false).getBoolean();
        CfgGeneral.toleranceFactor = config.get("general", "toleranceFactor", 0.1).getDouble();
        CfgGeneral.enableLastInput = config.get("general", "enableLastInput", true).getBoolean();
        CfgGeneral.hudOffsetX = config.get("general", "hudOffsetX", 0).getInt();
        CfgGeneral.hudOffsetY = config.get("general", "hudOffsetY", 0).getInt();
        CfgGeneral.enableSoundEffect = config.get("general", "enableSoundEffect", true).getBoolean();
        CfgGeneral.enableSegment = config.get("general", "enableSegment", false).getBoolean();
        CfgGeneral.segmentViewDistance = config.get("general", "segmentViewDistance", 16).getInt();
        CfgGeneral.barrierDistance = config.get("general", "barrierDistance", 16).getInt();

        CfgBasic.basicHudWidth = config.get("hud.basic", "basicHudWidth", 400).getInt();
        CfgBasic.basicHudHeight = config.get("hud.basic", "basicHudHeight", 300).getInt();
        CfgBasic.basicJLineOffsetY = config.get("hud.basic", "basicJLineOffsetY", 40).getInt();
        CfgBasic.basicMouseTrackPaddingRatio = config.get("hud.basic", "basicMouseTrackPaddingRatio", 0.1).getDouble();
        CfgBasic.basicHudBGOpacity = config.get("hud.basic", "basicHudBGOpacity", 40).getInt();
        CfgBasic.basicHudBGPadding = config.get("hud.basic", "basicHudBGPadding", 5).getInt();
        CfgBasic.basicKeyNoteSize = config.get("hud.basic", "basicKeyNoteSize", 0.8).getDouble();
        CfgBasic.basicMouseIndicatorSize = config.get("hud.basic", "basicMouseIndicatorSize", 0.25).getDouble();
        CfgBasic.basicMouseNoteSizeRatio = config.get("hud.basic", "basicMouseNoteSizeRatio", 0.8).getDouble();
        CfgBasic.basicKeyNoteAspectRatio = config.get("hud.basic", "basicKeyNoteAspectRatio", 5.0).getDouble();
        CfgBasic.basicStripWidthRatio = config.get("hud.basic", "basicStripWidthRatio", 0.5).getDouble();
        CfgBasic.basicNoteBorderSize = config.get("hud.basic", "basicNoteBorderSize", 2).getInt();
    }

    public static void saveConfig() {
        Configuration config = Parcaea.config;

        config.get("general", "enableMod", true).set(CfgGeneral.enableMod);
        config.get("general", "enableParcaea", true).set(CfgGeneral.enableParcaea);
        config.get("general", "enableSnake", true).set(CfgGeneral.enableSnake);
        config.get("general", "enable45S", true).set(CfgGeneral.enable45S);
        config.get("general", "noteSpeed", 1.0).set(CfgGeneral.noteSpeed);
        config.get("general", "enableAutoPos", false).set(CfgGeneral.enableAutoPos);
        config.get("general", "toleranceFactor", 0.1).set(CfgGeneral.toleranceFactor);
        config.get("general", "enableLastInput", true).set(CfgGeneral.enableLastInput);
        config.get("general", "hudOffsetX", 0).set(CfgGeneral.hudOffsetX);
        config.get("general", "hudOffsetY", 0).set(CfgGeneral.hudOffsetY);
        config.get("general", "enableSoundEffect", true).set(CfgGeneral.enableSoundEffect);
        config.get("general", "enableSegment", false).set(CfgGeneral.enableSegment);
        config.get("general", "segmentViewDistance", 16).set(CfgGeneral.segmentViewDistance);
        config.get("general", "barrierDistance", 16).set(CfgGeneral.barrierDistance);

        config.get("hud.basic", "basicHudWidth", 400).set(CfgBasic.basicHudWidth);
        config.get("hud.basic", "basicHudHeight", 300).set(CfgBasic.basicHudHeight);
        config.get("hud.basic", "basicJLineOffsetY", 40).set(CfgBasic.basicJLineOffsetY);
        config.get("hud.basic", "basicMouseTrackPaddingRatio", 0.1).set(CfgBasic.basicMouseTrackPaddingRatio);
        config.get("hud.basic", "basicHudBGOpacity", 40).set(CfgBasic.basicHudBGOpacity);
        config.get("hud.basic", "basicHudBGPadding", 5).set(CfgBasic.basicHudBGPadding);
        config.get("hud.basic", "basicKeyNoteSize", 0.8).set(CfgBasic.basicKeyNoteSize);
        config.get("hud.basic", "basicMouseIndicatorSize", 0.25).set(CfgBasic.basicMouseIndicatorSize);
        config.get("hud.basic", "basicMouseNoteSizeRatio", 0.8).set(CfgBasic.basicMouseNoteSizeRatio);
        config.get("hud.basic", "basicKeyNoteAspectRatio", 5.0).set(CfgBasic.basicKeyNoteAspectRatio);
        config.get("hud.basic", "basicStripWidthRatio", 0.5).set(CfgBasic.basicStripWidthRatio);
        config.get("hud.basic", "basicNoteBorderSize", 2).set(CfgBasic.basicNoteBorderSize);

        config.save();
    }
}
