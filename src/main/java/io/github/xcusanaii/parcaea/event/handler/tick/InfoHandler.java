package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.render.InfoHud.InfoDisplay;
import java.util.function.Predicate;

public class InfoHandler {

    public void onClientTickPost() {
        for (InfoDisplay infoDisplay : InfoHud.infoDisplayList) {
            if (infoDisplay.life > 0) {
                infoDisplay.life--;
            }
        }
        InfoHud.infoDisplayList.removeIf(new Predicate<InfoDisplay>() {
            @Override
            public boolean test(InfoDisplay infoDisplay) {
                return infoDisplay.life == 0;
            }
        });
    }
}
