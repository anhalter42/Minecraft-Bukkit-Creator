/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.DBSetWorld;
import java.io.File;
import org.bukkit.World;

/**
 *
 * @author andre
 */
public class MarkerDB extends DBSetWorld<Marker> {
    public MarkerDB() {
        super(Marker.class);
    }

    public MarkerDB(World aWorld, File aFile) {
        super(Marker.class, aFile, aWorld);
    }
    
    public Marker getMarker(String aName) {
        for(Marker lMarker : this) {
            if (lMarker.name.equals(aName)) {
                return lMarker;
            }
        }
        return null;
    }
    
    public void setMarker(String aName, BlockPosition aPos) {
        Marker lMarker = getMarker(aName);
        if (lMarker == null) {
            lMarker = new Marker();
            lMarker.name = aName;
            addRecord(lMarker);
        }
        lMarker.pos = aPos.clone();
    }
}
