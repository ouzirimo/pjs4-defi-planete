package model;

import java.util.HashMap;

public class MapWrapper {
    private HashMap<Integer, String> myMap;

    public MapWrapper(){
        myMap = new HashMap<>();
    }

    public HashMap getMap(){
        return myMap;
    }

    public void setMap(HashMap map){
        myMap = map;
    }
}

