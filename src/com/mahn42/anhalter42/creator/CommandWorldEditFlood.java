/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BlockPositionDelta;
import com.mahn42.framework.BlockPositionWalkAround;
import com.mahn42.framework.Framework;
import java.util.ArrayList;
import java.util.List;
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
public class CommandWorldEditFlood implements CommandExecutor {

    // c_we_flood <mat> <data> [<transmat1> [<transmat2>...]]
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            if (aStrings.length > 1) {
                BlockPositionDelta[] mode = BlockPositionDelta.HorizontalAndDown;
                int lSIndex = 0;
                if (aStrings[lSIndex].startsWith("mode=")) {
                    String aMode = aStrings[lSIndex].substring(5).toLowerCase();
                    if (aMode.equals("hd")) { // HorizontalAndDown
                        mode = BlockPositionDelta.HorizontalAndDown;
                    } else if (aMode.equals("h")) { // Horizontal
                        mode = BlockPositionDelta.Horizontal;
                    } else if (aMode.equals("hu")) { // HorizontalAndUp
                        mode = BlockPositionDelta.HorizontalAndUp;
                    } else if (aMode.equals("hv")) { // HorizontalAndVertical
                        mode = BlockPositionDelta.HorizontalAndVertical;
                    } else if (aMode.equals("hvd")) { // HorizontalAndVerticalAndDiagonal
                        mode = BlockPositionDelta.HorizontalAndVerticalAndDiagonal;
                    } else if (aMode.equals("dh")) { // DiagonalHorizontal
                        mode = BlockPositionDelta.DiagonalHorizontal;
                    }
                    lSIndex++;
                }
                ArrayList<Material> lTrans = new ArrayList<Material>();
                if (aStrings.length > (lSIndex + 2)) {
                    int lIndex = lSIndex + 2;
                    while (lIndex < aStrings.length) {
                        lTrans.add(CreatorPlugin.plugin.getMaterialForPlayer(aCommandSender, aStrings[lIndex]));
                        lIndex++;
                    }
                } else {
                    lTrans.add(Material.AIR);
                }
                World lWorld;
                Player lPlayer = ((Player)aCommandSender); 
                lWorld = lPlayer.getWorld();
                List<Block> lastTwoTargetBlocks = lPlayer.getLastTwoTargetBlocks(null, 20);
                BlockPosition lStart;
                if (lTrans.contains(lastTwoTargetBlocks.get(1).getType())) {
                    lStart = new BlockPosition(lastTwoTargetBlocks.get(1).getLocation());
                } else {
                    lStart = new BlockPosition(lastTwoTargetBlocks.get(0).getLocation());
                }
                Material lMat = CreatorPlugin.plugin.getMaterialForPlayer(aCommandSender, aStrings[lSIndex]);
                byte lData = Byte.parseByte(aStrings[lSIndex + 1]);
                int lMaxBlocks = 20000;
                ArrayList<BlockPosition> lStack = new ArrayList<BlockPosition>();
                ArrayList<BlockPosition> lAll = new ArrayList<BlockPosition>();
                lStack.add(lStart);
                while (!lStack.isEmpty() && lAll.size() < lMaxBlocks) {
                    ArrayList<BlockPosition> lNext = new ArrayList<BlockPosition>();
                    for(BlockPosition lpos : lStack) {
                        if (lTrans.contains(lpos.getBlockType(lWorld))) {
                            lAll.add(lpos);
                            for(BlockPosition lnp : new BlockPositionWalkAround(lpos, mode)) {
                                if (!lNext.contains(lnp) && !lAll.contains(lnp)) {
                                    Material lnpm = lnp.getBlockType(lWorld);
                                    if (lTrans.contains(lnpm)) {
                                        lNext.add(lnp);
                                    }
                                }
                            }
                        }
                    }
                    lStack.clear();
                    lStack = lNext;
                }
                for(BlockPosition lPos : lAll) {
                    Framework.plugin.setTypeAndData(lPos.getLocation(lWorld), lMat, lData, true);
                }
            }
        }
        return true;
    }
}
