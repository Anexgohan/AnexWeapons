package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
//import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.weapons.ai.anexweapons_achuk_mrm_AI;
import data.scripts.weapons.ai.anexweapons_vajra_mrm_AI;
import data.scripts.weapons.ai.anexweapons_sudarshan_mrm_AI;
import data.scripts.weapons.ai.anexweapons_bhairav_mrm_AI;
import data.scripts.weapons.ai.anexweapons_sm_lakshay_AI;

public class anexweapons_ModPlugin extends BaseModPlugin {
    public static final String achuk_projectile_ID = "anexweapons_achuk_mrm_projectile";
    public static final String vajra_projectile_ID = "anexweapons_vajra_mrm_projectile";
    public static final String sudarshan_projectile_ID = "anexweapons_sudarshan_mrm_projectile";
    public static final String bhairav_projectile_ID = "anexweapons_bhairav_mrm_projectile";
    public static final String sm_lakshay_projectile_ID = "anexweapons_sm_lakshay_projectile";

    ////////////////////////////////////////
    //                                    //
    //      MISSILES AI OVERRIDES         //
    //                                    //
    ////////////////////////////////////////

    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case achuk_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_achuk_mrm_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            //case vajra_projectile_ID:
                //return new PluginPick<MissileAIPlugin>(new anexweapons_vajra_mrm_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case sudarshan_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_sudarshan_mrm_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case bhairav_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_bhairav_mrm_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case sm_lakshay_projectile_ID:
                return new PluginPick<MissileAIPlugin>(new anexweapons_sm_lakshay_AI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            default:
        }
        return null;
    }
}