/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.SyncBlockList;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandAreaLoad implements CommandExecutor {

    public class Play implements Runnable {
        public BlockAreaList Area;
        public World world;
        public BlockPosition position;
        public int index = 0;
        
        @Override
        public void run() {
            if (index < Area.size()) {
                SyncBlockList lList = new SyncBlockList(world);
                Area.toList(index, lList, position);
                lList.execute();
                index++;
            }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            if (aStrings.length > 0) {
                String aName = aStrings[0];
                BlockAreaList aAreaList = new BlockAreaList();
                aAreaList.load(new File(aName));
                SyncBlockList lList = new SyncBlockList(lPlayer.getWorld());
                int lIndex = 0;
                boolean lWillPlay = false;
                if (aStrings.length > 1) {
                    if (aStrings[1].equalsIgnoreCase("play")) {
                        lWillPlay = true;
                    } else {
                        lIndex = Integer.parseInt(aStrings[1]);
                    }
                }
                BlockPosition lPos = Framework.plugin.getPositionMarker("1");
                if (lPos == null) {
                    lPos = new BlockPosition(lPlayer.getLocation());
                } else {
                    BlockPosition lPos2 = Framework.plugin.getPositionMarker("2");
                    if (lPos2 != null) {
                        lPos = lPos.getMinPos(lPos2);
                    }
                }
                if (lWillPlay) {
                    Play lPlay = new Play();
                    lPlay.Area = aAreaList;
                    lPlay.index = lIndex;
                    lPlay.world = lPlayer.getWorld();
                    lPlay.position = lPos;
                    lPos = lPlay.position.clone();
                    lPos.add(aAreaList.get(lIndex).width, aAreaList.get(lIndex).height, aAreaList.get(lIndex).depth);
                    Framework.plugin.setPositionMarker("1", lPlay.position);
                    Framework.plugin.setPositionMarker("2", lPos);
                    lPlayer.sendMessage("File " + aName + " loaded and playing.");
                    Framework.plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(Framework.plugin, lPlay, 20, 40);
                    
                } else {
                    aAreaList.toList(lIndex, lList, lPos);
                    lList.execute();
                    Framework.plugin.setPositionMarker("1", lPos);
                    lPos.add(aAreaList.get(lIndex).width, aAreaList.get(lIndex).height, aAreaList.get(lIndex).depth);
                    Framework.plugin.setPositionMarker("2", lPos);
                    lPlayer.sendMessage("File " + aName + " loaded.");
                }
            } else {

            }
        } else {
            
        }
        return true;
    }
}
