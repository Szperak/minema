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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import info.ata4.minecraft.minema.client.config.MinemaConfig;
import info.ata4.minecraft.minema.util.reflection.PrivateFields;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

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
    public void onTick(ClientTickEvent evt) {
        if (evt.phase != Phase.END) {
            return;
        }
        ChunkRenderDispatcher d =  ReflectionHelper.getPrivateValue(RenderGlobal.class, MC.renderGlobal,"field_174995_M");
        ViewFrustum vf = ReflectionHelper.getPrivateValue(RenderGlobal.class, MC.renderGlobal,"field_175008_n");
        int j = vf.field_178164_f.length;
        for(int i = 0; i<j; i++){
        	RenderChunk c = vf.field_178164_f[i];
        	if(c != null && c.func_178569_m())
            {
                d.func_178505_b(c);
                c.func_178575_a(false);
            }
        }
        
        
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
