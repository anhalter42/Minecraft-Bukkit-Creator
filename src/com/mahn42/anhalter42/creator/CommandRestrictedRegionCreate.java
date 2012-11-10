/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.RestrictedRegion;
import com.mahn42.framework.RestrictedRegions;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandRestrictedRegionCreate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 1) {
                BlockPosition lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[0]);
                BlockPosition lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[1]);
                RestrictedRegions lRegions = Framework.plugin.getRestrictedRegions(lWorld, true);
                ArrayList<Material> lMatList = new ArrayList<Material>();
                for(int lIndex = 2; lIndex < aStrings.length; lIndex++) {
                    Material lMat = Material.getMaterial(aStrings[lIndex]);
                    if (lMat == null) {
                        lMat = Material.getMaterial(Integer.parseInt(aStrings[lIndex]));
                    }
                    if (lMat != null) {
                        lMatList.add(lMat);
                    }
                }
                RestrictedRegion lRegion = new RestrictedRegion(lEdge2, lEdge2, lMatList);
                lRegions.add(lRegion);
            }
        }
        return true;
    }
}
