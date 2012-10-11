/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandMarkerList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        World lWorld = CreatorPlugin.plugin.getServer().getWorld("world");
        if (aCommandSender instanceof Player) {
            lWorld = ((Player)aCommandSender).getWorld();
        }
        if (aStrings.length > 0) {
            lWorld = CreatorPlugin.plugin.getServer().getWorld(aStrings[0]);
            if (lWorld == null) {
                aCommandSender.sendMessage("Unkown world " + aStrings[0]);
                return true;
            }
        }
        MarkerDB lDB = CreatorPlugin.plugin.MarkerDBs.getDB(lWorld);
        for(Marker lMarker : lDB) {
            aCommandSender.sendMessage(lMarker.name + " " + lMarker.pos);
        }
        return true;
    }    
}
