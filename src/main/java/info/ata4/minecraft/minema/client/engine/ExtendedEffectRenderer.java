/*
 ** 2012 April 30
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.minema.client.engine;

import net.minecraftforge.fml.relauncher.ReflectionHelper;
import info.ata4.minecraft.minema.util.reflection.PrivateFields;
import java.util.List;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EffectRenderer with configurable particle limit.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class ExtendedEffectRenderer extends EffectRenderer {
    
    private static final Logger L = LogManager.getLogger();
    
    private List<EntityFX>[][] fxLayers;
    private int particleLimit = 4000;

    public ExtendedEffectRenderer(World world, TextureManager textureManager) {
        super(world, textureManager);

        try {
            fxLayers = ReflectionHelper.getPrivateValue(EffectRenderer.class, this, PrivateFields.EFFECTRENDERER_FXLAYERS);
        } catch (Exception ex) {
            throw new RuntimeException("Can't get FX layers array", ex);
        }
    }

    @Override
    public void addEffect(EntityFX fx) {
        if (particleLimit == 0) {
            return;
        }
        
        int i = fx.getFXLayer();
        int j = fx.func_174838_j() == 1.0F ? 1 : 0;
        if(fxLayers[i][j].size() >= particleLimit)
            fxLayers[i][j].remove(0);
        fxLayers[i][j].add(fx);
    }

    public int getParticleLimit() {
        return particleLimit;
    }

    public void setParticleLimit(int particleLimit) {
        this.particleLimit = particleLimit;
    }
}
