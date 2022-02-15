package data.scripts.weapons.utility;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import java.awt.Color;

//import com.fs.starfarer.api.combat.ShipAPI;
//import java.util.Random;
//import java.awt.*;

// Provided by AxleMC131 on starsector discord
// Upgraded to smooth alpha transition by PureTilt Freelance Police Waifu on starsector discord

public class anexweapons_hide_on_lowAmmo implements EveryFrameWeaponEffectPlugin {

    float alpha = 1;
    final float timeToDisappear = 2.25f;        //time it takes to disappear
    final float remainingAmmoToDisappear = 2;  //the number of ammo remaining when disappear starts (ammo count cannot have a decimal value)

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if (engine.isPaused()) return;
        if (weapon.getAmmo() < remainingAmmoToDisappear) {
            weapon.getBarrelSpriteAPI().setColor(new Color(255, 255, 255, Math.round(255 * alpha)));
            if (alpha > 0){
                alpha -= 1/timeToDisappear * amount;
            }
            if (alpha < 0) alpha = 0;
        } else {
            weapon.getBarrelSpriteAPI().setColor(new Color(255, 255, 255, 255));
            alpha = 1;
        }
    }
}