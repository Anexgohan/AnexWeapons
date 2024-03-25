package data.scripts.weapons.OnHit;

import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lazywizard.lazylib.MathUtils;
// import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;
import com.fs.starfarer.api.combat.CombatAsteroidAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import java.awt.Color;

public class anexweapons_ltb_parashu_turret_OnHit implements OnHitEffectPlugin {

    //  --  explosion particles --------------------------------------------
    private static final int NUM_PARTICLES = 75;
    private static final Color EXPLOSION_COLOR = new Color(52, 255, 194, 200);
    private static final Color PARTICLE_COLOR = new Color(175, 146, 86, 200);
    private static final float CONE_ANGLE = 180f;
    private static final float minVelocity = 0.01f;     // lower value means less spread of particles that shoot off after impact
    private static final float maxVelocity = 0.08f;    // lower value means less spread of particles that shoot off after impact

    // -- smoke particles --------------------------------------------------
    private static final int NUM_SMOKE_PARTICLES = 150;
    //don't mess with alpha value of SMOKE_COLOR, it's randomized under "Opacity" & "randomize alpha value within range"
    private static final Color SMOKE_COLOR = new Color(126,124,121,255);

    //  --  one half of the angle. used internally, don't mess with this    -----------
    private static final float ANGLE_DEVISOR = CONE_ANGLE / 2;

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit,
                      ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
        if (target instanceof ShipAPI || target instanceof CombatAsteroidAPI) {

            float speed = projectile.getVelocity().length();
            float facing_V2f = projectile.getFacing();

            for (int x = 0; x < NUM_PARTICLES; x++) {

                if (!shieldHit) {
                    // below float values cannot be moved up before "for (int x = 0; x < NUM_PARTICLES; x++) {" particles wont spread and just be a point
                    float angle_V2f = MathUtils.getRandomNumberInRange(facing_V2f - ANGLE_DEVISOR, facing_V2f + ANGLE_DEVISOR);
                    float radius_V2f = MathUtils.getRandomNumberInRange(speed * -minVelocity, speed * -maxVelocity);
                    Vector2f velocityVector = MathUtils.getPointOnCircumference(null, radius_V2f, angle_V2f);

                    // creates hit particles in cone opposite to projectile hit
                    engine.addHitParticle(point,
                            velocityVector,
                            7f, 1f, MathUtils.getRandomNumberInRange(1.5f, 2f), PARTICLE_COLOR);
                }

            /*  original below, this creates particles in a circle, no vector2f is required
                engine.addHitParticle(point,
                    MathUtils.getPointOnCircumference(null,
                            MathUtils.getRandomNumberInRange(speed * 0.05f, speed * 0.075f),
                            MathUtils.getRandomNumberInRange(90f, 90f)),
                    7f, 1f, MathUtils.getRandomNumberInRange(1.5f, 2f), PARTICLE_COLOR);
            */
            }

            engine.spawnExplosion(point, new Vector2f(target.getVelocity().x * 0.45f, target.getVelocity().y * 0.45f),
                    EXPLOSION_COLOR, 50f, 0.5f);

            //generate smoke due to hit
            //Vector2f smokeVector = MathUtils.getRandomPointInCircle(null,150);
            for (int u = 0; u < NUM_SMOKE_PARTICLES; u++) {

                // to randomize color
                Color randomColorAlpha = new Color(SMOKE_COLOR.getRed(), SMOKE_COLOR.getGreen(), SMOKE_COLOR.getBlue(),
                        // randomize alpha value within range
                        // this will take alpha (255-155 = 100, 255-75 = 180) so final alpha will be in range of (100,180)
                        (SMOKE_COLOR.getAlpha()-MathUtils.getRandomNumberInRange(75,155)));

                engine.addSmokeParticle(
                        MathUtils.getRandomPointInCircle(point, 45),
                        MathUtils.getRandomPointInCone(
                                new Vector2f(),
                                30,
                                projectile.getFacing()+90,
                                projectile.getFacing()+270),
                        //  --  size    --
                        MathUtils.getRandomNumberInRange(5, 15),
                        //  --  Opacity --
                        MathUtils.getRandomNumberInRange(0.1f, 1f),
                        //  --  duration    --
                        MathUtils.getRandomNumberInRange(0.5f, 2.5f),
                        //  --  color   --
                        //SMOKE_COLOR
                        randomColorAlpha

                        /*
                        new Color(255, 255, 255,
                                Math.round(255 * MathUtils.getRandomNumberInRange(75, 180))
                        )
                        */
                );
            }
        }
    }
}