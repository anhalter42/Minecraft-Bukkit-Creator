/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import java.io.File;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandAreaSave implements CommandExecutor {
    // /c_area_save <filename> [<index>|add [<marker1> <marker2>]]
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 0) {
                String aName = aStrings[0];
                if (!aName.endsWith(".frm")) {
                    aName = aName + ".frm";
                }
                BlockPosition lEdge1 = null;
                BlockPosition lEdge2 = null;
                if (aStrings.length > 3) {
                    lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[2]);
                    lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[3]);
                } else {
                    lEdge1 = CreatorPlugin.plugin.getMarker(lWorld, "a");
                    lEdge2 = CreatorPlugin.plugin.getMarker(lWorld, "b");
                }
                if (lEdge1 != null && lEdge2 != null) {
                    BlockAreaList lAreaList = new BlockAreaList();
                    File lFile = new File(aName);
                    String lExtText = "";
                    if (aStrings.length > 1) {
                        if (lFile.exists()) {
                            lAreaList.load(lFile);
                        }
                        if (aStrings[1].equalsIgnoreCase("add")) {
                            lExtText = " added";
                            lAreaList.addFromWorld(lPlayer.getWorld(), lEdge1, lEdge2);
                        } else {
                            int lIndex = Integer.parseInt(aStrings[1]);
                            if (lIndex < lAreaList.size()) {
                                lExtText = " set at " + lIndex;
                                lAreaList.setFromWorld(lIndex, lPlayer.getWorld(), lEdge1, lEdge2);
                            } else {
                                lExtText = " added";
                                lAreaList.addFromWorld(lPlayer.getWorld(), lEdge1, lEdge2);
                            }
                        }
                    } else {
                        lAreaList.addFromWorld(lPlayer.getWorld(), lEdge1, lEdge2);
                    }
                    lAreaList.save(lFile);
                    lPlayer.sendMessage("area " + lEdge1 + " - " + lEdge2 + " saved to " + aName + lExtText + ".");
                }
            }
        }
        return true;
    }
}
