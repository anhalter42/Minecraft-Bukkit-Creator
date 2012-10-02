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
    
    // /c_area_load <filename> [<index>|play [mixed|reverse|full [<marker1> [<marker2>]]]]
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
                BlockAreaList aAreaList = new BlockAreaList();
                aAreaList.load(new File(aName));
                SyncBlockList lList = new SyncBlockList(lPlayer.getWorld());
                int lIndex = 0;
                boolean lWillPlay = false;
                BlockArea.BlockAreaPlaceMode lMode = BlockArea.BlockAreaPlaceMode.full;
                String lMarker1 = "a";
                String lMarker2 = "b";
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
                        } else {
                            lMode = BlockArea.BlockAreaPlaceMode.full;
                            if (aStrings.length > 3) {
                                lMarker1 = aStrings[3];
                                if (aStrings.length > 4) {
                                    lMarker2 = aStrings[4];
                                }
                            }
                        }
                    }
                }
                BlockPosition lPos = CreatorPlugin.plugin.getMarker(lWorld, lMarker1);
                if (lPos == null) {
                    lPos = new BlockPosition(lPlayer.getLocation());
                } else {
                    BlockPosition lPos2 = CreatorPlugin.plugin.getMarker(lWorld, lMarker2);
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
                    CreatorPlugin.plugin.setMarker(lWorld, lMarker1, lPlay.position);
                    CreatorPlugin.plugin.setMarker(lWorld, lMarker2, lPos);
                    lPlayer.sendMessage("File " + aName + " loaded and playing. Marker " + lMarker1 + " and " + lMarker2 + " are set.");
                    Framework.plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(Framework.plugin, lPlay, 20, 40);
                    
                } else {
                    aAreaList.toList(lIndex, lList, lPos, false, false, false, false, lMode);
                    lList.execute();
                    CreatorPlugin.plugin.setMarker(lWorld, lMarker1, lPos);
                    lPos.add(aAreaList.get(lIndex).width - 1, aAreaList.get(lIndex).height - 1, aAreaList.get(lIndex).depth - 1);
                    CreatorPlugin.plugin.setMarker(lWorld, lMarker2, lPos);
                    lPlayer.sendMessage("File " + aName + " loaded. Marker " + lMarker1 + " and " + lMarker2 + " are set.");
                }
            } else {

            }
        } else {
            
        }
        return true;
    }
}
