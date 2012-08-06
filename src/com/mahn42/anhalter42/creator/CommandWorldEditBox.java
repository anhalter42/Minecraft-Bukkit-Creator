/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import com.mahn42.framework.WoolColors;
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
public class CommandWorldEditBox  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player) aCommandSender;
            World lWorld = lPlayer.getWorld();
            SyncBlockList lList = new SyncBlockList(lWorld);
            BlockPosition lPos1, lPos2;
            Material lMat = Material.WOOL;
            if (aStrings.length > 1) {
                lPos1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[0]);
                lPos2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[1]);
                if (aStrings.length > 2) {
                    lMat = Material.getMaterial(aStrings[2]);
                    if (lMat == null) {
                        lMat = Material.getMaterial(Integer.parseInt(aStrings[2]));
                    }
                }
            } else {
                lPos1 = CreatorPlugin.plugin.getMarker(lWorld, "1");
                lPos2 = CreatorPlugin.plugin.getMarker(lWorld, "2");
            }
            BlockPosition lWHD = lPos1.getWHD(lPos2);
            lPos1 = lPos1.getMinPos(lPos2);
            lWHD.add(-1, -1, -1);
            for(int x=0;x<lWHD.x;x++) {
                BlockPosition lPos;
                lPos = new BlockPosition(lPos1.x + x, lPos1.y,          lPos1.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + x, lPos1.y + lWHD.y, lPos1.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + x, lPos1.y + lWHD.y, lPos1.z + lWHD.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + x, lPos1.y,          lPos1.z + lWHD.z);
                lList.add(lPos, lMat, WoolColors.purple);
            }
            for(int y=0;y<lWHD.y;y++) {
                BlockPosition lPos;
                lPos = new BlockPosition(lPos1.x,          lPos1.y + y, lPos1.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + lWHD.x, lPos1.y + y, lPos1.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + lWHD.x, lPos1.y + y, lPos1.z + lWHD.z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x,          lPos1.y + y, lPos1.z + lWHD.z);
                lList.add(lPos, lMat, WoolColors.purple);
            }
            for(int z=0;z<lWHD.z;z++) {
                BlockPosition lPos;
                lPos = new BlockPosition(lPos1.x,          lPos1.y,          lPos1.z + z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + lWHD.x, lPos1.y,          lPos1.z + z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x + lWHD.x, lPos1.y + lWHD.y, lPos1.z + z);
                lList.add(lPos, lMat, WoolColors.purple);
                lPos = new BlockPosition(lPos1.x,          lPos1.y + lWHD.y, lPos1.z + z);
                lList.add(lPos, lMat, WoolColors.purple);
            }
            lList.execute();
        }
        return true;
    }
    
}
