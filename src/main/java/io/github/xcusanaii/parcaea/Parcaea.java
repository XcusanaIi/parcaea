package io.github.xcusanaii.parcaea;

import io.github.xcusanaii.parcaea.command.PCMCommand;
import io.github.xcusanaii.parcaea.event.ClientQuitHandler;
import io.github.xcusanaii.parcaea.event.RenderHandler;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.config.ConfigLoader;
import io.github.xcusanaii.parcaea.test.TestHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

@Mod(modid = Parcaea.MODID, version = Parcaea.VERSION)
public class Parcaea {

    public static final String MODID = "parcaea";
    public static final String VERSION = "0.0.1";
    public static final String CATEGORY = "category.parcaea";
    public static final String FILE_PATH = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/" + MODID;

    public static final int PX_PER_TICK = 20;

    public static final KeyBinding[] PK_KEY_BINDS = new KeyBinding[7];

    public static final int CLOSE_SAFE_PADDING = 10;

    public static final KeyBinding keyTest = new KeyBinding("key.test", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyRestartChart = new KeyBinding("key.play_chart", Keyboard.KEY_R, CATEGORY);
    public static final KeyBinding keyMenu = new KeyBinding("key.menu", Keyboard.KEY_Z, CATEGORY);
    public static final KeyBinding keyToggleInvertSprint = new KeyBinding("key.toggle_invert_sprint", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyInvertSprint = new KeyBinding("key.invert_sprint", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyIntenseSpaceLeft = new KeyBinding("key.intense_space_left", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyIntenseSpaceRight = new KeyBinding("key.intense_space_right", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapWA = new KeyBinding("key.double_tap_wa", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapWD = new KeyBinding("key.double_tap_wd", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapSA = new KeyBinding("key.double_tap_sa", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapSD = new KeyBinding("key.double_tap_sd", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyQuickCommandMacro = new KeyBinding("key.quick_command_macro", Keyboard.KEY_NONE, CATEGORY);

    public static final DecimalFormat DF_0 = new DecimalFormat("0");
    public static final DecimalFormat DF_1 = new DecimalFormat("0.0");
    public static final DecimalFormat DF_2 = new DecimalFormat("0.00");

    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        syncPKKeyBinds();
        JumpLoader.reloadJump();
        Chart.toChart();
        Chart.printChart();
        File configFile = new File(event.getModConfigurationDirectory(), "parcaea.cfg");
        config = new Configuration(configFile);
        ConfigLoader.loadConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(keyTest);
        ClientRegistry.registerKeyBinding(keyRestartChart);
        ClientRegistry.registerKeyBinding(keyMenu);
        ClientRegistry.registerKeyBinding(keyToggleInvertSprint);
        ClientRegistry.registerKeyBinding(keyInvertSprint);
        ClientRegistry.registerKeyBinding(keyIntenseSpaceLeft);
        ClientRegistry.registerKeyBinding(keyIntenseSpaceRight);
        ClientRegistry.registerKeyBinding(keyDoubleTapWA);
        ClientRegistry.registerKeyBinding(keyDoubleTapWD);
        ClientRegistry.registerKeyBinding(keyDoubleTapSA);
        ClientRegistry.registerKeyBinding(keyDoubleTapSD);
        ClientRegistry.registerKeyBinding(keyQuickCommandMacro);
        MinecraftForge.EVENT_BUS.register(new TestHandler());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new ClientQuitHandler());
        ClientCommandHandler.instance.registerCommand(new PCMCommand());
    }

    private static void syncPKKeyBinds() {
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        PK_KEY_BINDS[0] = settings.keyBindForward;
        PK_KEY_BINDS[1] = settings.keyBindLeft;
        PK_KEY_BINDS[2] = settings.keyBindBack;
        PK_KEY_BINDS[3] = settings.keyBindRight;
        PK_KEY_BINDS[4] = settings.keyBindJump;
        PK_KEY_BINDS[5] = settings.keyBindSneak;
        PK_KEY_BINDS[6] = settings.keyBindSprint;
    }
}
