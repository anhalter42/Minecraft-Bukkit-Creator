/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.WorldDBList;
import org.bukkit.World;
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
    public WorldDBList<MarkerDB> DBs; 

    @Override
    public void onEnable() { 
        plugin = this;
        DBs = new WorldDBList<MarkerDB>(MarkerDB.class, this);
        Framework.plugin.registerSaver(DBs);
        getCommand("c_markpos").setExecutor(new CommandMarkPos());
        getCommand("c_markbox").setExecutor(new CommandMarkBox());
        getCommand("c_area_load").setExecutor(new CommandAreaLoad());
        getCommand("c_area_save").setExecutor(new CommandAreaSave());
        getCommand("c_area_delete").setExecutor(new CommandAreaDelete());
        getCommand("c_area_remove").setExecutor(new CommandAreaRemove());
        getCommand("c_we_fill").setExecutor(new CommandWorldEditFill());
        getCommand("c_we_markbox").setExecutor(new CommandWorldEditMarkBox());
        for(MarkerDB lDB : DBs) {
            for(Marker lMarker : lDB) {
                lDB.setMarker(lMarker.name, lMarker.pos);
            }
        }
    }

    public void setMarker(World aWorld, String name, BlockPosition pos) {
        MarkerDB lDB = DBs.getDB(aWorld);
        lDB.setMarker(name, pos);
    }
    
    public BlockPosition getMarker(World aWorld, String name) {
        MarkerDB lDB = DBs.getDB(aWorld);
        Marker lMarker = lDB.getMarker(name);
        return lMarker != null ? lMarker.pos : null;
    } 

}
