package data.scripts.weapons.OnHit;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import com.fs.starfarer.api.loading.DamagingExplosionSpec;
import org.magiclib.util.MagicLensFlare;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

import static com.fs.starfarer.api.util.Misc.ZERO;

public class anexweapons_mm_achuk_missile_OnHit implements OnHitEffectPlugin {

    private static final Color PARTICLE_COLOR = new Color(125, 255, 0, 100);
    private static final Color BLAST_COLOR = new Color(255, 125, 0, 150);
    private static final Color CORE_COLOR = new Color(255, 200, 0, 150);
    private static final Color FLASH_COLOR = new Color(0, 255, 175, 125);
    private static final int NUM_PARTICLES = 50;
    private static final String HIT_SOUND = "anexweapons_mm_sound_onhit_01";

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

        // Blast visuals
        float CoreExplosionRadius = 10f;       //CORE_COLOR explosion
        float CoreExplosionDuration = 2f;
        float ExplosionRadius = 25f;           //BLAST_COLOR explosion
        float ExplosionDuration = 1f;
        float CoreGlowRadius = 10f;            //CORE_COLOR glow
        float CoreGlowDuration = 1f;
        float GlowRadius = 50f;               //PARTICLE_COLOR spread and glow on explosion
        float GlowDuration = 1f;
        float FlashGlowRadius = 100f;          //FLASH_COLOR on explosion
        float FlashGlowDuration = 0.05f;


        engine.spawnExplosion(point, ZERO, CORE_COLOR, CoreExplosionRadius, CoreExplosionDuration);
        engine.spawnExplosion(point, ZERO, BLAST_COLOR, ExplosionRadius, ExplosionDuration);
        engine.addHitParticle(point, ZERO, CoreGlowRadius, 1f, CoreGlowDuration, CORE_COLOR);
        engine.addSmoothParticle(point, ZERO, GlowRadius, 1f, GlowDuration, PARTICLE_COLOR);
        engine.addHitParticle(point, ZERO, FlashGlowRadius, 1f, FlashGlowDuration, FLASH_COLOR);

        //increase decrease "MathUtils.getRandomNumberInRange(0.3f, 0.9f)" this is (min, max) size of Particles on explosion
        for (int x = 0; x < NUM_PARTICLES; x++) {
            engine.addHitParticle(point,
                    MathUtils.getPointOnCircumference(null, MathUtils.getRandomNumberInRange(50f, 400f), (float) Math.random() * 360f),
                    5f, 1f, MathUtils.getRandomNumberInRange(0.3f, 0.99f), PARTICLE_COLOR);
        }

        float damage = projectile.getDamageAmount() * 0.1f;

        DamagingExplosionSpec blast = new DamagingExplosionSpec(0.1f,
                200f,
                100f,
                damage,
                damage/2,
                CollisionClass.PROJECTILE_FF,
                CollisionClass.PROJECTILE_FIGHTER,
                10f,
                10f,
                0f,
                0,
                BLAST_COLOR,
                null);
        blast.setDamageType(DamageType.ENERGY);
        blast.setShowGraphic(false);
        engine.spawnDamagingExplosion(blast,projectile.getSource(),point,false);

        Global.getSoundPlayer().playSound(HIT_SOUND,1f,1f,point,ZERO);
    }
}
