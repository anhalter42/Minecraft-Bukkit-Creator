/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BlockRect;
import com.mahn42.framework.Framework;
import com.mahn42.framework.ProjectionArea;
import com.mahn42.framework.ProjectionAreas;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandProjectionAreaCreate implements CommandExecutor {
    
    //pacreate <m1> <m2> <md> [<x,y,z> | [<x,y,z>]]
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 2) {
                BlockPosition lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[0]);
                BlockPosition lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[1]);
                BlockPosition lDest = CreatorPlugin.plugin.getMarker(lWorld, aStrings[2]);
                ProjectionAreas lAreas = Framework.plugin.getProjectionAreas(lWorld, true);
                ProjectionArea lArea = new ProjectionArea(new BlockRect(lEdge1, lEdge2), lDest);
                if (aStrings.length > 3) {
                    BlockPosition lv = new BlockPosition();
                    lv.fromCSV(aStrings[3], "\\,");
                    lArea.vector = lv.getVector();
                    if (aStrings.length > 4) {
                        BlockPosition ls = new BlockPosition();
                        ls.fromCSV(aStrings[4], "\\,");
                        lArea.scale = ls.getVector();
                    }                    
                }
                lAreas.add(lArea);
            }
        }
        return true;
    }
    
}
