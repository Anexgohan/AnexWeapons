package data.scripts.weapons.beamEffect;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import java.awt.Color;
import org.lwjgl.util.vector.Vector2f;

public class anexweapons_test_beamEffect implements BeamEffectPlugin
{

    private static final Vector2f ZERO = new Vector2f();
    private static final String SOUND_ID = "anexweapons_mm_sound_onhit_01";
    private static final Color BEAMER_COLOR_EXPLOSION = new Color(255, 200, 150, 255);
    private static final float BEAMER_EXPLOSION_RADIUS = 5f;
    private static final float BEAMER_EXPLOSION_DURATION = 0.5f;
    private static final Color BEAMER_COLOR_HIT = new Color(50, 100, 255, 255);
    private static final float BEAMER_HIT_RADIUS = 75f;
    private static final float BEAMER_HIT_DURATION = 0.3f;
    private static final float BEAMER_HIT_BRIGHTNESS = 1f;
    private float level = 0.99f;
    private final IntervalUtil interval = new IntervalUtil(0.15f, 0.15f);

    @Override
    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam)
    {
        if (Global.getCombatEngine().isPaused())
        {
            return;
        }

        {
            interval.advance(amount);
        }
        if (interval.intervalElapsed())
            if (beam.getBrightness() > level)
            {
                if (beam.getDamageTarget() != null)
                {
                    Global.getCombatEngine().addHitParticle(beam.getTo(), ZERO, BEAMER_HIT_RADIUS,  BEAMER_HIT_BRIGHTNESS,  BEAMER_HIT_DURATION, BEAMER_COLOR_HIT);

                    Global.getCombatEngine().spawnExplosion(beam.getTo(), ZERO, BEAMER_COLOR_EXPLOSION, BEAMER_EXPLOSION_RADIUS, BEAMER_EXPLOSION_DURATION);

                    Global.getSoundPlayer().playSound(SOUND_ID, 1f, 1f, beam.getTo(), ZERO);
                }
            }
    }
}
//addHitParticle(Vector2f loc, Vector2f vel, float size, float brightness, float duration, Color color);