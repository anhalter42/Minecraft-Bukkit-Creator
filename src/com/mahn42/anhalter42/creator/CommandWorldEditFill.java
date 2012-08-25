/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandWorldEditFill implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player && aStrings.length > 0) {
            Player lPlayer = (Player) aCommandSender;
            World lWorld = lPlayer.getWorld();
            SyncBlockList lList = new SyncBlockList(lWorld);
            boolean lEmpty = false;
            byte lData = 0;
            if (aStrings.length > 1) {
                lData = Byte.parseByte(aStrings[1]);
            }
            BlockPosition lEdge1;
            BlockPosition lEdge2;
            if (aStrings.length > 3) {
                lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[2]);
                lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[3]);
            } else {
                lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, "1");
                lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, "2");
            }
            BlockArea lArea = new BlockArea(lWorld, lEdge1, lEdge2);
            Material lMat;
            if (aStrings[0].equalsIgnoreCase("empty")) {
                lMat = Material.AIR;
                lEmpty = true; 
            } else {
                lMat = Material.getMaterial(aStrings[0]);
                if (lMat == null) {
                    lMat = Material.getMaterial(Integer.parseInt(aStrings[0]));
                }
            }
            lArea.clear(lMat, lData);
            lArea.toList(lList, lEdge1.getMinPos(lEdge2), BlockArea.BlockAreaPlaceMode.full);
            int lECount = 0;
            if (lEmpty) {
                List<Entity> LEntities = lWorld.getEntities();
                for(Entity lEntity : LEntities) {
                    BlockPosition lPos = new BlockPosition(lEntity.getLocation());
                    if (lPos.isBetween(lEdge1, lEdge2)) {
                        lEntity.remove();
                        lECount++;
                    }
                }
            }
            lPlayer.sendMessage("region " + lEdge1 + " - " + lEdge2 + " is filled with " + lMat + " data " + lData + (lEmpty?" entities removed " + lECount: ""));
            lList.execute();
        }
        return true;
    }
}
