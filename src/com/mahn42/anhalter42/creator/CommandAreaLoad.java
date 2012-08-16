/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.SyncBlockList;
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
public class CommandAreaLoad implements CommandExecutor {

    public class Play implements Runnable {
        public BlockAreaList Area;
        public World world;
        public BlockPosition position;
        public int index = 0;
        public BlockArea.BlockAreaPlaceMode mode = BlockArea.BlockAreaPlaceMode.full;
        
        @Override
        public void run() {
            if (index < Area.size()) {
                SyncBlockList lList = new SyncBlockList(world);
                Area.toList(index, lList, position, false, false, false, false, mode);
                lList.execute();
                index++;
            }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player)aCommandSender;
            World lWorld = lPlayer.getWorld();
            if (aStrings.length > 0) {
                String aName = aStrings[0];
                BlockAreaList aAreaList = new BlockAreaList();
                aAreaList.load(new File(aName));
                SyncBlockList lList = new SyncBlockList(lPlayer.getWorld());
                int lIndex = 0;
                boolean lWillPlay = false;
                BlockArea.BlockAreaPlaceMode lMode = BlockArea.BlockAreaPlaceMode.full;
                if (aStrings.length > 1) {
                    if (aStrings[1].equalsIgnoreCase("play")) {
                        lWillPlay = true;
                    } else {
                        lIndex = Integer.parseInt(aStrings[1]);
                    }
                    if (aStrings.length > 2) {
                        if (aStrings[2].equalsIgnoreCase("mixed")) {
                            lMode = BlockArea.BlockAreaPlaceMode.mixed;
                        } else if (aStrings[2].equalsIgnoreCase("reverse")) {
                            lMode = BlockArea.BlockAreaPlaceMode.reverse;
                        }
                    }
                }
                BlockPosition lPos = CreatorPlugin.plugin.getMarker(lWorld, "a");
                if (lPos == null) {
                    lPos = new BlockPosition(lPlayer.getLocation());
                } else {
                    BlockPosition lPos2 = CreatorPlugin.plugin.getMarker(lWorld, "b");
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
                    lPlay.mode = lMode;
                    lPos = lPlay.position.clone();
                    lPos.add(aAreaList.get(lIndex).width - 1, aAreaList.get(lIndex).height - 1, aAreaList.get(lIndex).depth - 1);
                    CreatorPlugin.plugin.setMarker(lWorld, "a", lPlay.position);
                    CreatorPlugin.plugin.setMarker(lWorld, "b", lPos);
                    lPlayer.sendMessage("File " + aName + " loaded and playing. Marker a + b set.");
                    Framework.plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(Framework.plugin, lPlay, 20, 40);
                    
                } else {
                    aAreaList.toList(lIndex, lList, lPos, false, false, false, false, lMode);
                    lList.execute();
                    CreatorPlugin.plugin.setMarker(lWorld, "a", lPos);
                    lPos.add(aAreaList.get(lIndex).width - 1, aAreaList.get(lIndex).height - 1, aAreaList.get(lIndex).depth - 1);
                    CreatorPlugin.plugin.setMarker(lWorld, "b", lPos);
                    lPlayer.sendMessage("File " + aName + " loaded. Marker a + b set.");
                }
            } else {

            }
        } else {
            
        }
        return true;
    }
}
