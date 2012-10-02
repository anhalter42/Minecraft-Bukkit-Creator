/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import java.util.List;
import org.bukkit.Location;
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
public class CommandExplode  implements CommandExecutor {
    
    // /c_explode [power]
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            List<Block> lastTwoTargetBlocks = lPlayer.getLastTwoTargetBlocks(null, 200);
            Location location = lastTwoTargetBlocks.get(lastTwoTargetBlocks.size()-1).getLocation();
            float lPower = 4;
            if (aStrings.length > 0) {
                lPower = Float.parseFloat(aStrings[0]);
            }
            lWorld.createExplosion(location, lPower, true);
        }
        return true;
    }
    
}
