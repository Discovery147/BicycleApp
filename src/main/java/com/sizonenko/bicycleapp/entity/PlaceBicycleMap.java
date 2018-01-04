package com.sizonenko.bicycleapp.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlaceBicycleMap {
    private HashMap<String, String> map = new HashMap(){{
        this.put("Punkt #1", "Kross Level B4");
        this.put("Punkt #2", "Kross Level B7");
    }};
    private Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

    public int getSize() {
        return map.size();
    }

    public String getRevenue() {
        if (it.hasNext()) {
            Map.Entry<String, String> m = it.next();
            return m.getValue() + "  " + m.getKey();
        } else {
            return null;
        }
    }
}
