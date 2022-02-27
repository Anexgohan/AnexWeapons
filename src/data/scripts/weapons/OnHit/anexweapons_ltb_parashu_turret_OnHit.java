package data.scripts.weapons.OnHit;

import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import com.fs.starfarer.api.combat.CombatAsteroidAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import java.awt.Color;

public class anexweapons_ltb_parashu_turret_OnHit implements OnHitEffectPlugin {

    private static final int NUM_PARTICLES = 75;
    private static final Color EXPLOSION_COLOR = new Color(52, 255, 194, 200);
    private static final Color PARTICLE_COLOR = new Color(175, 146, 86, 200);

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit,
                      ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
        if (target instanceof ShipAPI || target instanceof CombatAsteroidAPI) {
            float speed = projectile.getVelocity().length();
            for (int x = 0; x < NUM_PARTICLES; x++) {
                engine.addHitParticle(point,
                        MathUtils.getPointOnCircumference(null,
                                MathUtils.getRandomNumberInRange(speed * 0.05f, speed * 0.075f),
                                MathUtils.getRandomNumberInRange(0f, 90f)),
                        7f, 1f, MathUtils.getRandomNumberInRange(1.5f, 2f), PARTICLE_COLOR);
            }
            engine.spawnExplosion(point, new Vector2f(target.getVelocity().x * 0.45f, target.getVelocity().y * 0.45f), EXPLOSION_COLOR, 150f, 0.5f);
        }
    }
}

