/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandMarkPos implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 0) {
                String aName = aStrings[0];
                BlockPosition lPos;
                if (aStrings.length > 1) {
                    if (aStrings.length > 3) {
                        lPos = new BlockPosition(Integer.parseInt(aStrings[1]), Integer.parseInt(aStrings[2]), Integer.parseInt(aStrings[3]));
                    } else {
                        lPos = CreatorPlugin.plugin.getMarker(lWorld, aStrings[1]).clone();
                    }
                } else {
                    lPos = new BlockPosition(lPlayer.getLocation());
                }
                CreatorPlugin.plugin.setMarker(lWorld, aName, lPos);
                lPlayer.sendMessage("mark " + aName + " at " + lPos);
            } else {

            }
        } else {
            
        }
        return true;
    }
}
