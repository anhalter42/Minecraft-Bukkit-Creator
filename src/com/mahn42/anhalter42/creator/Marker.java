/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.creator;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.DBRecordWorld;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author andre
 */
public class Marker extends DBRecordWorld {
    public String name;
    public BlockPosition pos;
    
    @Override
    protected void toCSVInternal(ArrayList aCols) {
        super.toCSVInternal(aCols);
        aCols.add(name);
        aCols.add(pos.toCSV(","));
    }
    
    @Override
    protected void fromCSVInternal(DBRecordCSVArray aCols) {
        super.fromCSVInternal(aCols);
        name = aCols.pop();
        pos = new BlockPosition();
        pos.fromCSV(aCols.pop(), "\\,");
    }
}
