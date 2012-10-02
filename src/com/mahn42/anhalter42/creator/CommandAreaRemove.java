/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.Framework;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author andre
 */
public class CommandAreaRemove implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aStrings.length > 1) {
            String aName = aStrings[0];
            if (!aName.endsWith(".frm")) {
                aName = aName + ".frm";
            }
            File lFile = new File(aName);
            if (lFile.exists()) {
                BlockAreaList lAreaList = new BlockAreaList();
                lAreaList.load(lFile);
                int lIndex = Integer.parseInt(aStrings[1]);
                Framework.plugin.getLogger().info("remove part " + lIndex + " of area file " + aStrings[0] + "!");
                lAreaList.remove(lIndex);
                lAreaList.save(lFile);
            }
        }
        return true;
    }
    
}
