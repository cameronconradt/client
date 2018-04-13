package com.example.camer.client.background;

import android.os.AsyncTask;

import com.example.camer.client.fragments.LoginFragment;

import model.loginRequest;

/**
 * Created by camer on 3/27/2018.
 */

public class aSyncLogin extends AsyncTask {

    private ServerInterface serverInterface;
    private LoginFragment context;

    public aSyncLogin(String ip, String port, LoginFragment mycontext){
        serverInterface = new ServerInterface(ip,port);
        context = mycontext;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        return serverInterface.login((loginRequest) objects[0]);
    }

    protected void onPostExecute(Object toReturn){
        context.onCallBackLogin(toReturn,this);
    }

}
