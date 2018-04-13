package com.example.camer.client.activities;

//import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.camer.client.R;
import com.example.camer.client.background.DataSync;
import com.example.camer.client.background.syncEvents;
import com.example.camer.client.background.syncPeople;
import com.example.camer.client.fragments.LoginFragment;
import com.example.camer.client.fragments.MapFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;

import model.Person;
import model.event;
import model.events;
import model.people;
import model.Data;

public class MainActivity extends AppCompatActivity {

    private Data data;

    private MapFragment mapFragment;
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Data.getInstance();
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment)fm.findFragmentById(R.id.fragmentLayout);
        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance("LOGIN");
            fm.beginTransaction()
                    .add(R.id.fragmentLayout,loginFragment).commit();
        }
        Iconify.with(new FontAwesomeModule());
    }

    public void onCallBackSyncEvents(Object returned, syncEvents syncEvents){
        data = Data.getInstance();
        if(returned.getClass() == events.class){
            events events = (events) returned;
            ArrayList<event> allEvents = events.getEvents();
            if(allEvents.size() <= 0 || events.getMessage() != null){
                Toast.makeText(this,"Sync Events Failed", Toast.LENGTH_SHORT).show();
            }
            else{
                data.setModelEvents(events);
            }
        }
    }

    public void onCallBackSyncPeople(Object returned, syncPeople syncPeople){
        data = Data.getInstance();
        people people = (people) returned;
        ArrayList<Person> allPeople = people.getPeople();
        if(allPeople.size() <= 0 || people.getMessage() != null){
            Toast.makeText(this,"Sync People Failed", Toast.LENGTH_LONG).show();
        }
        String userPersonID = allPeople.get(0).getDescendant();
        String first;
        String last;
        data.setModelPeople(people);
        for(Person person : allPeople){
            if(person.getId().equals(userPersonID)){
                first = person.getFirstName();
                last = person.getLastName();
                Toast.makeText(this,first + " " + last, Toast.LENGTH_SHORT).show();
                switchToMap();
                break;
            }
        }
    }

    public void switchToMap(){
        FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = new MapFragment();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.fragmentLayout, mapFragment);
        ft.commit();
    }

    public void switchToSettings(){
        //TODO: add button to action bar to switch to settings
        Intent myIntent = new Intent(this,SettingsActivity.class);
        this.startActivity(myIntent);
    }

}
