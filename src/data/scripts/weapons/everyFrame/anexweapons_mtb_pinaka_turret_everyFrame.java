package data.scripts.weapons.everyFrame;

import com.fs.starfarer.api.combat.*;
import data.scripts.weapons.ai.anexweapons_mtb_pinaka_turret_projectile_AI;
import org.lazywizard.lazylib.combat.CombatUtils;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

// Contains (switch fired projectile AI) and (hide barrel on low ammo) scripts
public class anexweapons_mtb_pinaka_turret_everyFrame implements EveryFrameWeaponEffectPlugin {

	private List<DamagingProjectileAPI> alreadyRegisteredProjectiles = new ArrayList<DamagingProjectileAPI>();

	@Override
	public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {

		// switch fired projectile AI
		ShipAPI source = weapon.getShip();
		ShipAPI target = null;

		if(source.getWeaponGroupFor(weapon)!=null ){
			//WEAPON IN AUTOFIRE
			if(source.getWeaponGroupFor(weapon).isAutofiring()  //weapon group is autofiring
					&& source.getSelectedGroupAPI()!=source.getWeaponGroupFor(weapon)){ //weapon group is not the selected group
				target = source.getWeaponGroupFor(weapon).getAutofirePlugin(weapon).getTargetShip();
			}
			else {
				target = source.getShipTarget();
			}
		}

		for (DamagingProjectileAPI proj : CombatUtils.getProjectilesWithinRange(weapon.getLocation(), 200f)) {
			if (proj.getWeapon() == weapon && !alreadyRegisteredProjectiles.contains(proj) && engine.isEntityInPlay(proj) && !proj.didDamage()) {
				engine.addPlugin(new anexweapons_mtb_pinaka_turret_projectile_AI(proj, target));
				alreadyRegisteredProjectiles.add(proj);
			}
		}

		//And clean up our registered projectile list
		List<DamagingProjectileAPI> cloneList = new ArrayList<>(alreadyRegisteredProjectiles);
		for (DamagingProjectileAPI proj : cloneList) {
			if (!engine.isEntityInPlay(proj) || proj.didDamage()) {
				alreadyRegisteredProjectiles.remove(proj);
			}
		}
		// end of switch fired projectile AI


		// hide barrel on low ammo
		// Provided by AxleMC131 on starsector discord
		// Upgraded to smooth alpha transition by PureTilt Freelance Police Waifu on starsector discord

		float alpha = 1;
		final float timeToDisappear = 2.25f;        //time it takes to disappear
		final float remainingAmmoToDisappear = 7;  //the number of ammo remaining when disappear starts (ammo count cannot have a decimal value)

		if (engine.isPaused()) return;
		if (weapon.getBarrelSpriteAPI() != null) {
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
		// end of hide barrel on low ammo
	}
}
