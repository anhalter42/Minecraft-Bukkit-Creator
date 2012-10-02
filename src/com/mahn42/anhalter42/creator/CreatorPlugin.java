/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.WorldDBList;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class CreatorPlugin extends JavaPlugin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }
    
    public static CreatorPlugin plugin = null;
    public WorldDBList<MarkerDB> MarkerDBs; 

    @Override
    public void onEnable() { 
        plugin = this;
        MarkerDBs = new WorldDBList<MarkerDB>(MarkerDB.class, "CreatorMarker", this);
        Framework.plugin.registerSaver(MarkerDBs);
        getCommand("c_markpos").setExecutor(new CommandMarkPos());
        getCommand("c_markbox").setExecutor(new CommandMarkBox());
        
        getCommand("c_area_load").setExecutor(new CommandAreaLoad());
        getCommand("c_area_save").setExecutor(new CommandAreaSave());
        getCommand("c_area_delete").setExecutor(new CommandAreaDelete());
        getCommand("c_area_remove").setExecutor(new CommandAreaRemove());
        
        getCommand("c_we_fill").setExecutor(new CommandWorldEditFill());
        getCommand("c_we_box").setExecutor(new CommandWorldEditBox());
        getCommand("c_we_markbox").setExecutor(new CommandWorldEditMarkBox());
        getCommand("c_we_flood").setExecutor(new CommandWorldEditFlood());
        getCommand("c_we_replace").setExecutor(new CommandWorldEditReplace());
        
        getCommand("c_teleport").setExecutor(new CommandTeleport());

        getCommand("c_explode").setExecutor(new CommandExplode());
        
        for(MarkerDB lDB : MarkerDBs) {
            for(Marker lMarker : lDB) {
                lDB.setMarker(lMarker.name, lMarker.pos);
            }
        }
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void setMarker(World aWorld, String name, BlockPosition pos) {
        MarkerDB lDB = MarkerDBs.getDB(aWorld);
        lDB.setMarker(name, pos);
    }
    
    public BlockPosition getMarker(World aWorld, String name) {
        MarkerDB lDB = MarkerDBs.getDB(aWorld);
        Marker lMarker = lDB.getMarker(name);
        return lMarker != null ? lMarker.pos : null;
    } 

    public Material getMaterialForPlayer(CommandSender aSender, String aName) {
        if (aSender instanceof Player && aName.length() == 2 && aName.startsWith("m")) {
            int lI = Integer.parseInt(aName.substring(1, 2));
            if (lI == 0) {
                return ((Player)aSender).getItemInHand().getType();
            } else {
                lI--;
                PlayerInventory lInv = ((Player)aSender).getInventory();
                return lInv.getItem(lI).getType();
            }
        }
        Material lFromMat = Material.getMaterial(aName.toUpperCase());
        if (lFromMat == null) {
            lFromMat = Material.getMaterial(Integer.parseInt(aName));
        }
        return lFromMat;
    }
}
