package com.example.camer.client.background;

import java.util.ArrayList;

/**
 * Created by conradt2 on 4/9/18.
 */

public class filter {

    ArrayList<Boolean> filterArray = new ArrayList<>();

    ArrayList<String> eventFilterTypes = new ArrayList<>();
    //TODO: incorporate filter class into map and settings
    public boolean maleEvents;
    public boolean femaleEvents;
    public boolean motherEvents;
    public boolean fatherEvents;

    public filter(){
        setDefaults();
    }

    public void setDefaults(){
        maleEvents = true;
        femaleEvents = true;
        motherEvents = true;
        fatherEvents = true;
    }

    public void clear(){
        eventFilterTypes.clear();
        filterArray.clear();
        setDefaults();
    }

    public boolean getBoolByType(String eventType){
        int index = -1;
        for(int i = 0; i < filterArray.size(); i++){
            if(eventFilterTypes.get(i).equalsIgnoreCase(eventType)) {
                index = i;
            }
        }
        return filterArray.get(index);
    }
    
    public int getIndexByType(String eventType){
        int index = -1;
        for(int i = 0; i < filterArray.size(); i++){
            if(eventFilterTypes.get(i).equalsIgnoreCase(eventType)) {
                index = i;
            }
        }
        return index;
    }

    public ArrayList<Boolean> getFilterArray() {
        return filterArray;
    }

    public void setFilterArray(ArrayList<Boolean> filterArray) {
        this.filterArray = filterArray;
    }

    public ArrayList<String> getEventFilterTypes() {
        return eventFilterTypes;
    }

    public void setEventFilterTypes(ArrayList<String> eventFilterTypes) {
        this.eventFilterTypes = eventFilterTypes;
        for(int i = 0; i < eventFilterTypes.size(); i++){
            filterArray.add(true);
        }
    }

    public boolean isMaleEvents() {
        return maleEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        this.maleEvents = maleEvents;
    }

    public boolean isFemaleEvents() {
        return femaleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    public boolean isMotherEvents() {
        return motherEvents;
    }

    public void setMotherEvents(boolean motherEvents) {
        this.motherEvents = motherEvents;
    }

    public boolean isFatherEvents() {
        return fatherEvents;
    }

    public void setFatherEvents(boolean fatherEvents) {
        this.fatherEvents = fatherEvents;
    }
}
