/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

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

    @Override
    public void onEnable() { 
        getCommand("c_markpos").setExecutor(new CommandSetPosMarker());
        getCommand("c_area_load").setExecutor(new CommandAreaLoad());
        getCommand("c_area_save").setExecutor(new CommandAreaSave());
        getCommand("c_area_delete").setExecutor(new CommandAreaDelete());
        getCommand("c_area_remove").setExecutor(new CommandAreaRemove());
        getCommand("c_we_fill").setExecutor(new CommandWorldEditFill());
    }

}
