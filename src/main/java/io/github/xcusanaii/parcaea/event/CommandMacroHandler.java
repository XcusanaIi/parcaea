package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.Parcaea;
import net.minecraft.client.Minecraft;

public class CommandMacroHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static String msg1 = "/prac";
    public static String msg2 = "/unprac";

    public static boolean toggleMsg = false;

    public void onClientTickPre() {
        if (Parcaea.keyQuickCommandMacro.isPressed() && mc.thePlayer != null) {
            toggleMsg = !toggleMsg;
            String msg = toggleMsg ? msg1 : msg2;
            mc.thePlayer.sendChatMessage(msg);
        }
    }
}
