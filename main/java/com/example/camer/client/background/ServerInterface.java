package com.example.camer.client.background;

import com.google.gson.Gson;

import model.User;
import model.auth_token;
import model.events;
import model.loginRequest;
import model.loginResponse;
import model.people;

/**
 * Created by camer on 3/27/2018.
 */

public class ServerInterface {

    private HttpClient client;
    private Gson gson = new Gson();

    public ServerInterface(String ip, String port) {
        client = new HttpClient(port,ip);
    }

    public loginResponse login(loginRequest request){
        String toConvert = client.getUrl("/user/login", gson.toJson(request),null);
        return gson.fromJson(toConvert,loginResponse.class);
    }

    public loginResponse register(User user){
        String toConvert = client.getUrl("/user/register", gson.toJson(user), null);
        return gson.fromJson(toConvert,loginResponse.class);
    }

    public events syncEvents(auth_token token){
        String toConvert = client.getUrl("/event",null,token.getId());
        return gson.fromJson(toConvert,events.class);
    }

    public people syncPeople(auth_token token){
        String toConvert = client.getUrl("/person",null,token.getId());
        return gson.fromJson(toConvert,people.class);
    }

}
