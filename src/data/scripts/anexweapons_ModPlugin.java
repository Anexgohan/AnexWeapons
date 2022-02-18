package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
//import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.weapons.ai.anexweapons_mm_achuk_missile_AI;
import data.scripts.weapons.ai.anexweapons_mm_vajra_missile_AI;
import data.scripts.weapons.ai.anexweapons_mm_sudarshan_missile_AI;
import data.scripts.weapons.ai.anexweapons_mm_bhairav_missile_AI;
import data.scripts.weapons.ai.anexweapons_mm_lakshya_missile_AI;
import data.scripts.weapons.ai.anexweapons_mm_trishul_missile_AI;

public class anexweapons_ModPlugin extends BaseModPlugin {
    public static final String mm_achuk_missile_projectile_ID = "anexweapons_mm_achuk_missile_projectile";
    public static final String mm_vajra_missile_projectile_ID = "anexweapons_mm_vajra_missile_projectile";
    public static final String mm_sudarshan_missile_projectile_ID = "anexweapons_mm_sudarshan_missile_projectile";
    public static final String mm_bhairav_missile_projectile_ID = "anexweapons_mm_bhairav_missile_projectile";
    public static final String mm_lakshya_missile_projectile_ID = "anexweapons_mm_lakshya_missile_projectile";
    public static final String mm_trishul_missile_projectile_ID = "anexweapons_mm_trishul_missile_projectile";

    ////////////////////////////////////////
    //                                    //
    //      MISSILES AI OVERRIDES         //
    //                                    //
    ////////////////////////////////////////

    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case mm_achuk_missile_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_mm_achuk_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            //case mm_vajra_missile_projectile_ID:
                //return new PluginPick<MissileAIPlugin>(new anexweapons_mm_vajra_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case mm_sudarshan_missile_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_mm_sudarshan_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case mm_bhairav_missile_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_mm_bhairav_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case mm_lakshya_missile_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_mm_lakshya_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case mm_trishul_missile_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_mm_trishul_missile_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            default:
        }
        return null;
    }
}