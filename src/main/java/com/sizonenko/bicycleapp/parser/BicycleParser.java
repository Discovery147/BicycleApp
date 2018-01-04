package com.sizonenko.bicycleapp.parser;

import com.sizonenko.bicycleapp.entity.Bicycle;
import com.sizonenko.bicycleapp.entity.Place;

import java.util.*;

public class BicycleParser {
    public Map<Place,Set<Bicycle>> transformListToMap(List<Bicycle> list) {
        Map<Place,Set<Bicycle>> map = new HashMap();
        list.forEach(element -> map.put(element.getPlace(),new HashSet()));
        for ( Place key : map.keySet() ) {
            Set set = map.get(key);
            for(int j = 0; j < list.size(); j++){
                Place place = list.get(j).getPlace();
                if(place.equals(key)){
                    set.add(list.get(j));
                }
            }
        }
        return map;
    }

}
