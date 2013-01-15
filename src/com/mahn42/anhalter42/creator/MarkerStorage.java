/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BlockRect;
import com.mahn42.framework.IMarker;
import com.mahn42.framework.IMarkerStorage;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;

/**
 *
 * @author andre
 */
class MarkerStorage implements IMarkerStorage {

    @Override
    public List<IMarker> findMarkers(World aWorld, String aName) {
        ArrayList<IMarker> lResult = new ArrayList<IMarker>();
        MarkerDB lDB = CreatorPlugin.plugin.MarkerDBs.getDB(aWorld);
        Marker lmark = lDB.getMarker(aName);
        if (lmark != null) {
            lResult.add(new MarkerStorageMarker(lmark));
        }
        return lResult;
    }

    @Override
    public List<IMarker> findMarkers(World aWorld, BlockRect aArea) {
        ArrayList<IMarker> lResult = new ArrayList<IMarker>();
        MarkerDB lDB = CreatorPlugin.plugin.MarkerDBs.getDB(aWorld);
        ArrayList<Marker> lmarks = lDB.getMarkers(aArea);
        for(Marker lmark : lmarks) {
            lResult.add(new MarkerStorageMarker(lmark));
        }
        return lResult;
    }

    private static class MarkerStorageMarker implements IMarker {

        Marker mark;
        public MarkerStorageMarker(Marker lmark) {
            mark = lmark;
        }

        @Override
        public String getName() {
            return mark.name;
        }

        @Override
        public BlockPosition getPosition() {
            return mark.pos;
        }
    }
    
}
