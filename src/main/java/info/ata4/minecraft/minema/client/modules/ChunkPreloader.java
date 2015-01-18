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
        //MC.renderGlobal.
        //Tessellator.getInstance().getWorldRenderer().startDrawing(7);
        ChunkRenderDispatcher d =  ReflectionHelper.getPrivateValue(RenderGlobal.class, MC.renderGlobal,"field_174995_M");
        ViewFrustum vf = ReflectionHelper.getPrivateValue(RenderGlobal.class, MC.renderGlobal,"field_175008_n");
        //Method m = ReflectionHelper.findMethod(ViewFrustum.class, vf, new String[]{"func_178161_a"}, BlockPos.class);
        /*int j = 248;
        for(int k = -MC.gameSettings.renderDistanceChunks; k <= MC.gameSettings.renderDistanceChunks; k++)
        {
            for(int l = -MC.gameSettings.renderDistanceChunks; l <= MC.gameSettings.renderDistanceChunks; l++)
            {
            	
            	
            	RenderChunk renderchunk1;
				try {
					renderchunk1 = (RenderChunk) m.invoke(vf,new BlockPos((k << 4) + 8, j, (l << 4) + 8));
				} catch (IllegalAccessException e) {

					e.printStackTrace();
					return;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return;
				}
                //RenderChunk renderchunk1 = vf.func_178161_a(new BlockPos((k << 4) + 8, j, (l << 4) + 8));
                if(renderchunk1 != null)
                {
                	//renderchunk1.func_178575_a(true);
                    d.func_178505_b(renderchunk1);
                    renderchunk1.func_178575_a(false);
                }
            }

        }*/
        
        /*Frustum var8 = new Frustum(ClippingHelperImpl.getInstance());
        Entity var9 = MC.getRenderViewEntity();
        double var10 = var9.lastTickPosX + (var9.posX - var9.lastTickPosX) * (double)evt.renderTickTime;
        double var12 = var9.lastTickPosY + (var9.posY - var9.lastTickPosY) * (double)evt.renderTickTime;
        double var14 = var9.lastTickPosZ + (var9.posZ - var9.lastTickPosZ) * (double)evt.renderTickTime;
        var8.setPosition(var10, var12, var14);*/
        
        int j = vf.field_178164_f.length;
        for(int i = 0; i<j; i++){
        	RenderChunk c = vf.field_178164_f[i];
        	if(c != null && c.func_178569_m()) //var8.isBoundingBoxInFrustum(c.field_178591_c))
            {
            	//renderchunk1.func_178575_a(true);
                d.func_178505_b(c);
                c.func_178575_a(false);
            }
        }
        
        //MC.renderGlobal.func_174967_a(System.nanoTime()+900000000L);
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
