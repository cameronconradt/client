package com.example.camer.client.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camer.client.R;
import com.example.camer.client.background.DataSync;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.widget.IconTextView;

import model.Data;
import model.event;
import model.people;
import model.Person;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private GoogleMap myMap;
    private ImageView icon;
    private TextView eventText;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    public GoogleMap getMap(){ return myMap;}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        addMarkers();
        addLines();
        setMarkerListener();
        //TODO: Initialize map and load markers
        initMapType(myMap);
    }

    private void addMarkers(){
        Data data = Data.getInstance();
        ArrayList<Marker> markers = new ArrayList<>();
        for(event event : data.getModelEvents().getEvents()){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(event.getLatitude(),event.getLongitude()));
            markerOptions.title(event.getType());
            //bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            BitmapDescriptor bitmap;
            if(event.getType().equals("baptism"))
                bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            else if(event.getType().equals("birth"))
                bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            else if(event.getType().equals("death"))
                bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            else{
                bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            }
            markerOptions.icon(bitmap);
            Marker tempMarker = myMap.addMarker(markerOptions);
            markers.add(tempMarker);
            tempMarker.setTag(event);
            people people = data.getModelPeople();
            for(model.Person person : people.getPeople()){
                if(person.getId().equals(event.getPerson_id())){
                    person.addEvent(event);
                    break;
                }
            }
        }
    }

    private void addLines(){
        Data data = Data.getInstance();
        ArrayList<Person> people = data.getModelPeople().getPeople();

        for(Person person : people){
            ArrayList<event> events = person.getEvents();
            if(person.getDescendant_id().equals(person.getId())){
                addLinesAncestors(person,people);
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            for(event event : events){
                polylineOptions.add(new LatLng(event.getLatitude(),event.getLongitude())).color(Color.GREEN);
            }
            myMap.addPolyline(polylineOptions);
        }
    }

    private void addLinesAncestors(Person root, ArrayList<Person> people){
        //TODO: recursively make family tree
        if(root != null) {
            Person mother = null, father = null;
            for (int i = 0; i < people.size(); i++) {
                if (people.get(i).getId().equals(root.getMother()))
                    mother = people.get(i);
                if (people.get(i).getId().equals(root.getFather()))
                    father = people.get(i);
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            if (root.getFirstEvent() != null)
                polylineOptions.add(new LatLng(root.getFirstEvent().getLatitude(), root.getFirstEvent().getLongitude())).color(Color.RED);
            if (father != null && father.getFirstEvent() != null) {
                polylineOptions.add(new LatLng(father.getFirstEvent().getLatitude(), father.getFirstEvent().getLongitude())).color(Color.RED);
            }
            if (mother != null && mother.getFirstEvent() != null) {
                polylineOptions.add(new LatLng(father.getFirstEvent().getLatitude(), father.getFirstEvent().getLongitude())).color(Color.RED);
            }
            myMap.addPolyline(polylineOptions);
            addLinesAncestors(father,people);
            addLinesAncestors(mother,people);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_map,container,false);
        //Bundle bundle = this.getArguments();
        FragmentManager cfm = getChildFragmentManager();
        SupportMapFragment smf = (SupportMapFragment) cfm.findFragmentByTag("mapFragment");
        if (smf == null){
            smf = new SupportMapFragment();
            FragmentTransaction ft = cfm.beginTransaction();
            ft.add(R.id.map,smf,"mapFragment");
            ft.commit();
        }
        smf.getMapAsync(this);

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }
    @Override
    public void onStart(){
        super.onStart();
        icon = (ImageView) getView().findViewById(R.id.icon);
        eventText = (TextView) getView().findViewById(R.id.eventtext);
        ColorDrawable cd = new ColorDrawable(0xFFFF6666);
        Drawable Icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).
                colorRes(R.color.android).sizeDp(40);
        icon.setImageDrawable(Icon);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.eventData){

                }
            }
        });

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void setMarkerListener(){
        myMap   .setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                event event = (event) marker.getTag();
                people people = Data.getInstance().getModelPeople();
                for(model.Person person : people.getPeople()){
                    if(person.getId().equals(event.getPerson_id())){
                        Drawable genderIcon = null;
                        if(person.getGender().equals("m")) {
                            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                                    colorRes(R.color.male).sizeDp(40);
                            eventText.setText(person.getFirstName() + " " + person.getLastName() + "\n"
                                                + event.getType() + ": " + event.getCity() + ", " + event.getCountry()
                                                + "(" + event.getDate() + ")" );

                        }
                        else{
                            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                                    colorRes(R.color.female).sizeDp(40);
                            eventText.setText(person.getFirstName() + " " + person.getLastName() + "\n"
                                    + event.getType() + ": " + event.getCity() + ", " + event.getCountry()
                                    + "(" + event.getDate() + ")" );
                        }
                        icon.setImageDrawable(genderIcon);
                        break;
                    }
                }

                return true;
            }
        });
    }

    public void initMapType(GoogleMap googleMap){
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
