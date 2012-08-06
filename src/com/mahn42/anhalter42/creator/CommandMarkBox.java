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
public class CommandMarkBox  implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 4) {
                BlockPosition lPos1 = new BlockPosition(lPlayer.getLocation());
                BlockPosition lPos2 = lPos1.clone();
                lPos2.add(Integer.parseInt(aStrings[2]), Integer.parseInt(aStrings[3]), Integer.parseInt(aStrings[4]));
                CreatorPlugin.plugin.setMarker(lWorld, aStrings[0], lPos1);
                CreatorPlugin.plugin.setMarker(lWorld, aStrings[1], lPos2);
                lPlayer.sendMessage("mark " + aStrings[0] + " at " + lPos1 + " and " + aStrings[1] + " at " + lPos2);
            } else {

            }
        } else {
            
        }
        return true;
    }
}
