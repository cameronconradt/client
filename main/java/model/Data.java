package model;

/**
 * Created by camer on 2/16/2018.
 */

public class Data {
    protected String message;
    private static Data instance;
    private people people;
    private events events;
    private auth_token auth_token;

    public static Data getInstance(){
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    public Data(){
        message = null;
    }
    public Data(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public model.people getModelPeople() {
        return people;
    }

    public void setModelPeople(model.people people) {
        this.people = people;
    }

    public model.events getModelEvents() {
        return events;
    }

    public void setModelEvents(model.events events) {
        this.events = events;
    }

    public auth_token getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(auth_token auth_token) {
        this.auth_token = auth_token;
    }
}
