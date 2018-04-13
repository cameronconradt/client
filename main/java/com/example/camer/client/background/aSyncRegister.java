package com.example.camer.client.background;

import android.os.AsyncTask;

import com.example.camer.client.fragments.LoginFragment;

import model.User;

/**
 * Created by camer on 3/27/2018.
 */

public class aSyncRegister extends AsyncTask {
    private ServerInterface serverInterface;
    private LoginFragment context;

    public aSyncRegister(String ip, String port, LoginFragment mycontext){
        serverInterface = new ServerInterface(ip,port);
        context = mycontext;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return serverInterface.register((User) objects[0]);
    }

    protected void onPostExecute(Object toReturn){
        context.onCallBackRegister(toReturn,this);
    }
}
