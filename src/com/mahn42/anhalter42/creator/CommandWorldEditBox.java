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
            lPos1 = CreatorPlugin.plugin.getMarker(lWorld, "1");
            lPos2 = CreatorPlugin.plugin.getMarker(lWorld, "2");
            byte lData = 0;
            boolean lwN = true;
            boolean lwS = true;
            boolean lwE = true;
            boolean lwW = true;
            boolean lwT = true;
            boolean lwB = true;
            if (aStrings.length > 0) {
                lwN = aStrings[0].contains("n");
                lwS = aStrings[0].contains("s");
                lwE = aStrings[0].contains("e");
                lwW = aStrings[0].contains("w");
                lwT = aStrings[0].contains("t");
                lwB = aStrings[0].contains("b");
                if (aStrings.length > 1) {
                    lMat = CreatorPlugin.plugin.getMaterialForPlayer(aCommandSender, aStrings[1]);
                    if (aStrings.length > 2) {
                        lData = Byte.parseByte(aStrings[2]);
                        if (aStrings.length > 4) {
                            lPos1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[3]);
                            lPos2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[4]);
                        }
                    }
                }
            }
            BlockPosition lWHD = lPos1.getWHD(lPos2);
            lPos1 = lPos1.getMinPos(lPos2);
            for(int x=0;x<lWHD.x;x++) {
                BlockPosition lPos;
                for(int y=0;y<lWHD.y;y++) {
                    if (lwS) {
                        lPos = new BlockPosition(lPos1.x + x, lPos1.y + y, lPos1.z);
                        lList.add(lPos, lMat, lData);
                    }
                    if (lwN) {
                        lPos = new BlockPosition(lPos1.x + x, lPos1.y + y, lPos1.z + lWHD.z - 1);
                        lList.add(lPos, lMat, lData);
                    }
                }
                for(int z=0;z<lWHD.z;z++) {
                    if (lwB) {
                        lPos = new BlockPosition(lPos1.x + x, lPos1.y,              lPos1.z + z);
                        lList.add(lPos, lMat, lData);
                    }
                    if (lwT) {
                        lPos = new BlockPosition(lPos1.x + x, lPos1.y + lWHD.y - 1, lPos1.z + z);
                        lList.add(lPos, lMat, lData);
                    }
                }
            }
            for(int z=0;z<lWHD.z;z++) {
                BlockPosition lPos;
                for(int y=0;y<lWHD.y;y++) {
                    if (lwW) {
                        lPos = new BlockPosition(lPos1.x, lPos1.y + y, lPos1.z + z);
                        lList.add(lPos, lMat, lData);
                    }
                    if (lwE) {
                        lPos = new BlockPosition(lPos1.x + lWHD.x - 1, lPos1.y + y, lPos1.z + z);
                        lList.add(lPos, lMat, lData);
                    }
                }
            }
            lList.execute();
        }
        return true;
    }
    
}
