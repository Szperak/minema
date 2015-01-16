/*
 ** 2014 August 03
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.minema.client.modules;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import info.ata4.minecraft.minema.client.config.MinemaConfig;
import info.ata4.minecraft.minema.util.reflection.PrivateFields;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class ChunkPreloader extends CaptureModule {
    
    private static final Minecraft MC = Minecraft.getMinecraft();
    
    //private WorldRenderer[] worldRenderers;

    public ChunkPreloader(MinemaConfig cfg) {
        super(cfg);
    }
    
    @SubscribeEvent
    public void onTick(RenderTickEvent evt) {
        if (evt.phase != Phase.START) {
            return;
        }
        
        //Tessellator.getInstance().getWorldRenderer().startDrawing(p_startDrawing_1_);
    }

    @Override
    protected void doEnable() throws Exception {
        try {
            //worldRenderers = ReflectionHelper.getPrivateValue(RenderGlobal.class, MC.renderGlobal, PrivateFields.RENDERGLOBAL_WORLDRENDERERS);
        } catch (Exception ex) {
            throw new RuntimeException("Can't get worldRenderers field", ex);
        }
        
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    protected void doDisable() throws Exception {
        FMLCommonHandler.instance().bus().unregister(this);
    }
    
}
