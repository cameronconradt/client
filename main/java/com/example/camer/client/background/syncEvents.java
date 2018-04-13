package com.example.camer.client.background;

import android.os.AsyncTask;

import com.example.camer.client.activities.MainActivity;

import model.auth_token;

/**
 * Created by camer on 3/27/2018.
 */

public class syncEvents extends AsyncTask {


    private ServerInterface serverInterface;
    private MainActivity context;

    public syncEvents(String ip, String port, MainActivity mycontext){
        serverInterface = new ServerInterface(ip,port);
        context = mycontext;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return serverInterface.syncEvents((auth_token) objects[0]);
    }

    protected void onPostExecute(Object toReturn){
        context.onCallBackSyncEvents(toReturn,this);
    }
}
