package io.github.xcusanaii.parcaea;

import io.github.xcusanaii.parcaea.command.PPKCommand;
import io.github.xcusanaii.parcaea.event.RenderHandler;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.ClientQuitHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.io.SegmentLoader;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.config.ConfigLoader;
import io.github.xcusanaii.parcaea.render.entity.BarrierMarker;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.render.entity.renderer.BarrierMarkerRender;
import io.github.xcusanaii.parcaea.render.entity.renderer.CoordMarkerRender;
import io.github.xcusanaii.parcaea.test.TestHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

@Mod(modid = Parcaea.MODID, version = Parcaea.VERSION)
public class Parcaea {

    public static final String MODID = "parcaea";
    public static final String VERSION = "1.3.5";
    public static final Logger LOGGER = FMLLog.getLogger();
    public static final String FILE_PATH = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/" + MODID;

    public static final int PX_PER_TICK = 20;

    public static final KeyBinding[] PK_KEY_BINDS = new KeyBinding[7];

    public static final int CLOSE_SAFE_PADDING = 10;

    public static final DecimalFormat DF_0 = new DecimalFormat("0");
    public static final DecimalFormat DF_1 = new DecimalFormat("0.0");
    public static final DecimalFormat DF_2 = new DecimalFormat("0.00");
    public static final DecimalFormat DF_5 = new DecimalFormat("0.00000");

    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        syncPKKeyBinds();
        JumpLoader.reloadJump();
        SegmentLoader.reloadSegment();
        Chart.toChart();
        Chart.printChart();
        File configFile = new File(event.getModConfigurationDirectory(), "parcaea.cfg");
        config = new Configuration(configFile);
        ConfigLoader.loadConfig();

        RenderingRegistry.registerEntityRenderingHandler(CoordMarker.class, new IRenderFactory<CoordMarker>() {
            @Override
            public Render<? super CoordMarker> createRenderFor(RenderManager manager) {
                return new CoordMarkerRender(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(BarrierMarker.class, new IRenderFactory<BarrierMarker>() {
            @Override
            public Render<? super BarrierMarker> createRenderFor(RenderManager manager) {
                return new BarrierMarkerRender(manager);
            }
        });
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        KeyBinds.registerKeyBinds();
        MinecraftForge.EVENT_BUS.register(new TestHandler());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new ClientQuitHandler());
        ClientCommandHandler.instance.registerCommand(new PPKCommand());
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
