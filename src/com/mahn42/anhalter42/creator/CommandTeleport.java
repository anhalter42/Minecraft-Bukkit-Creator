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
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author andre
 */
public class CommandTeleport implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 0) {
                BlockPosition lPos;
                if (aStrings.length > 0) {
                    if (aStrings.length > 2) {
                        if (aStrings.length > 3) {
                            lWorld = CreatorPlugin.plugin.getServer().getWorld(aStrings[3]);
                        }
                        lPos = new BlockPosition(Integer.parseInt(aStrings[0]), Integer.parseInt(aStrings[1]), Integer.parseInt(aStrings[2]));
                    } else {
                        if (aStrings.length > 1) {
                            lWorld = CreatorPlugin.plugin.getServer().getWorld(aStrings[1]);
                        }
                        lPos = CreatorPlugin.plugin.getMarker(lWorld, aStrings[0]).clone();
                    }
                } else {
                    lPos = new BlockPosition();
                }
                lPlayer.teleport(lPos.getLocation(lWorld), PlayerTeleportEvent.TeleportCause.PLUGIN);
                lPlayer.sendMessage("teleport to" + lPos + " in world " + lWorld.getName());
            } else {

            }
        } else {
            
        }
        return true;
    }
    
}
