package com.example.camer.client.background;

import com.example.camer.client.activities.MainActivity;
import com.example.camer.client.fragments.MapFragment;
import com.google.android.gms.maps.GoogleMap;

import model.Data;


/**
 * Created by conradt2 on 4/12/18.
 */

public class DataSync {


    public DataSync(){

    }



    public static void execute(String ip, String port, MapFragment mycontext) {
        syncEvents syncEvents = new syncEvents(ip,port, (MainActivity)mycontext.getActivity());
        syncEvents.execute(Data.getInstance().getAuth_token());
        syncPeople syncPeople = new syncPeople(ip,port, (MainActivity)mycontext.getActivity());
        syncPeople.execute(Data.getInstance().getAuth_token());
        GoogleMap myMap = mycontext.getMap();
        myMap.clear();
        mycontext.onMapReady(myMap);
    }

}
