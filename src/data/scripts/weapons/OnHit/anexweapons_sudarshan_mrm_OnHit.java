package data.scripts.weapons.OnHit;

import com.fs.starfarer.api.Global;
//import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import org.lazywizard.lazylib.MathUtils;
//import org.lazywizard.lazylib.combat.CombatUtils;
//import org.lazywizard.lazylib.combat.entities.SimpleEntity;
import org.lwjgl.util.vector.Vector2f;
//import data.scripts.util.MagicRender;
import java.awt.Color;
//import java.util.ArrayList;
//import java.util.List;


// code in part by Tomatopaste and salvaged of random posts by various users on starsector discord.
// Some code is copied from other mod authors from modding section on forums
// copied from magellan, talahan, scy mods in perticular


public class anexweapons_sudarshan_mrm_OnHit implements OnHitEffectPlugin {

    private static final int MIN_ARCS = 1;
    private static final int MAX_ARCS = 3;
    private static final float ARC_DAMAGE = 0.1f;      //Normal Damage
    private static final float ARC_EMP = 1f;           //EMP multiplier from arcs only
    private static final float FLUXRAISE_MULT = 0.25f;    //Flux generated on target that is hit from main projectile (EmpDamageAmount * FLUXRAISE_MULT)
    private static final float EMP_RADIUS = 100f;      //size of emp affect on hit
    private static final String ARC_SOUND = "anexweapons_emp_2sec_01";
    //private static final float FLUXRAISE = 1000f; //depreciated
    // -- explosion graphics -------------------------------------------------
    private static final Color NEBULA_COLOR = new Color(200, 25, 255,255);
    private static final float NEBULA_SIZE = 10f * (0.75f + (float) Math.random() * 0.5f);
    private static final float NEBULA_SIZE_MULT = 50f;
    private static final float NEBULA_DUR = 3.5f;
    private static final float NEBULA_RAMPUP = 0.25f;
    // -- stuff for tweaking particle characteristics ------------------------
    private static final Color PARTICLE_COLOR = new Color(0, 150, 255,255);
    private static final Color GLOW_COLOR = new Color(0, 100, 255,150);
    private static final float PARTICLE_SIZE = 5f;
    private static final float PARTICLE_BRIGHTNESS = 200f;
    private static final float PARTICLE_DURATION = 2.5f;
    private static final int PARTICLE_COUNT = 10;
    // -- particle geometry --------------------------------------------------
    private static final float CONE_ANGLE = 150f;
    private static final float VEL_MIN = 0.06f;
    private static final float VEL_MAX = 0.1f;
    // one half of the angle. used internally, don't mess with this
    private static final float A_2 = CONE_ANGLE / 2;

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine)
    {
        // get the target's velocity to render the crit FX
        Vector2f v_target = new Vector2f(target.getVelocity());

        //Arc generate on hull hit
        //removed !shieldHit &&  in below (!shieldHit && !projectile.isFading() && target instanceof ShipAPI) so EMP damage apply on shield hit as well
        if (!projectile.isFading() && target instanceof ShipAPI) {
            float dam = projectile.getDamageAmount() * ARC_DAMAGE;
            float emp = projectile.getEmpAmount() * ARC_EMP;
            //float emp_radius = projectile.getEmpAmount() * ARC_EMP;
            int arcs = MathUtils.getRandomNumberInRange(MIN_ARCS, MAX_ARCS);
            for (int i = 0; i < arcs; i++) {
                engine.spawnEmpArcPierceShields(projectile.getSource(), point, target, target,
                        DamageType.ENERGY, // damage type
                        dam, // damage normal done on target
                        emp, // emp damage done on target
                        EMP_RADIUS, // max range of arcs (on target)
                        "tachyon_lance_emp_impact", // sound
                        25f, // thickness
                        GLOW_COLOR, // fringe color
                        PARTICLE_COLOR // core color
                );
            }
            //Play a sound
            Global.getSoundPlayer().playSound(ARC_SOUND, 0.75f, 0.25f, target.getLocation(), target.getVelocity());
            ShipAPI targetship = (ShipAPI) target;
            //calculate a number to raise target flux by
            float fluxmult = projectile.getEmpAmount() * FLUXRAISE_MULT;    //previously getDamageAmount() instead of getEmpAmount()
            // get target max flux level
            float maxflux = targetship.getMaxFlux();
            //Check that the target can handle the flux; if so, raise target ship flux on hull hit
            if (maxflux > (fluxmult * 1.5f)) {
                targetship.getFluxTracker().increaseFlux(fluxmult, true);   //true default
            }
        }

        /*
        //this applies flux damage on shield hit without arc
        engine.applyDamage(
                target, //enemy Entity
                target.getLocation(), //Our 2D vector to the exact world-position of the collision
                1, //DPS modified by the damage multiplier
                DamageType.KINETIC, //Using the damage type here, so that Kinetic / Explosive / Fragmentation AOE works.
                ARC_EMP, //EMP (if any)
                false, //Does not bypass shields.
                false, //Does not do Soft Flux damage (unless you want it to for some strange reason)
                projectile.getSource()  //Who owns this projectile?
        );
        */

        // do visual effects
        engine.addSwirlyNebulaParticle(point,
                v_target,
                NEBULA_SIZE,
                NEBULA_SIZE_MULT,
                NEBULA_RAMPUP,
                0.3f,
                NEBULA_DUR,
                NEBULA_COLOR,
                true
        );
        engine.spawnExplosion(point, v_target,
                NEBULA_COLOR, // color of the explosion
                NEBULA_SIZE * 2,
                NEBULA_DUR / 2
        );
        float speed = projectile.getVelocity().length();
        float facing = projectile.getFacing();
        for (int i = 0; i <= PARTICLE_COUNT; i++)
        {
            float angle = MathUtils.getRandomNumberInRange(facing - A_2,
                    facing + A_2);
            float vel = MathUtils.getRandomNumberInRange(speed * -VEL_MIN,
                    speed * -VEL_MAX);
            Vector2f vector = MathUtils.getPointOnCircumference(null,
                    vel,
                    angle);
            engine.addHitParticle(point,
                    vector,
                    PARTICLE_SIZE,
                    PARTICLE_BRIGHTNESS,
                    PARTICLE_DURATION,
                    PARTICLE_COLOR
            );
            engine.addHitParticle(point,
                    vector,
                    PARTICLE_SIZE * 4,
                    PARTICLE_BRIGHTNESS,
                    PARTICLE_DURATION * 0.75f,
                    GLOW_COLOR
            );
        }
    }
}