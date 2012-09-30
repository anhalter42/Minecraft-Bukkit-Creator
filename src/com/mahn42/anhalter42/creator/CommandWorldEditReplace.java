/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandWorldEditReplace implements CommandExecutor {

    // c_we_replace <frommat> <fromdata> <tomat> <todata> [<marker1> <marker2>]
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        World lWorld;
        if (aCommandSender instanceof Player) {
            lWorld = ((Player)aCommandSender).getWorld();
        } else {
            lWorld = CreatorPlugin.plugin.getServer().getWorld("world");
        }
        if (aStrings.length > 3) {
            Material lFromMat = CreatorPlugin.plugin.getMaterialForPlayer(aCommandSender, aStrings[0]);
            byte lFromData = Byte.parseByte(aStrings[1]);
            Material lToMat = CreatorPlugin.plugin.getMaterialForPlayer(aCommandSender, aStrings[2]);
            byte lToData = Byte.parseByte(aStrings[3]);
            BlockPosition l1 = CreatorPlugin.plugin.getMarker(lWorld, "1");
            BlockPosition l2 = CreatorPlugin.plugin.getMarker(lWorld, "2");
            if (aStrings.length > 5) {
                l1 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[4]);
                l2 = CreatorPlugin.plugin.getMarker(lWorld, aStrings[5]);
            }
            BlockPosition lFrom = l1.getMinPos(l2);
            BlockPosition lTo = l1.getMaxPos(l2);
            for(int x=lFrom.x;x<lTo.x;x++) {
                for(int y=lFrom.y;y<lTo.y;y++) {
                    for(int z=lFrom.z;z<lTo.z;z++) {
                        Block lBlock = lWorld.getBlockAt(x, y, z);
                        if (lBlock.getType().equals(lFromMat) && (lFromData == 0 || lBlock.getData() == lFromData)) {
                            Framework.plugin.setTypeAndData(new Location(lWorld, x, y, z), lToMat, lToData, true);
                        }
                    }
                }
            }
        }
        return true;
    }
}
