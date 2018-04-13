package model;

import java.lang.reflect.Field;

//import generator.generator;

/**
 * Created by camer on 2/16/2018.
 */

public class event extends Model  {
    private String year;
    private String eventType;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventID;
    private String personID;
    private String descendant; //user_id
/*      "eventType": "started family map",
      "personID": "Sheila_Parker",
      "city": "Salt Lake City",
      "country": "United States",
      "latitude": 40.7500,
      "longitude": -110.1167,
      "year": 2016,
      "eventID": "Sheila_Family_Map",
      "descendant":"sheila"*/


    public String getData(){
        return new String("date = " + year + ", type = " + eventType + ", latitude = " + Double.toString(latitude) + ", longitude = " + Double.toString(longitude) + ", country = " + country + ", city = " + city + ", person_id = " + personID);
    }
    /**
     *
     * @param data string array [id(do not set),user_id,date,type,country,city,latitude,longitude,person_id]
     */
    public event(Object[] data){
        if(data.length == 8){
            descendant = (String) data[0];
            year = (String) data[1];
            eventType = (String) data[2];
            country = (String) data[3];
            city = (String) data[4];
            if(data[6].getClass().equals(Double.class)){
                latitude = (Double) data[5];
                longitude = (Double) data[6];
            }
            else{
                latitude = Double.parseDouble((String)data[5]);
                longitude = Double.parseDouble((String)data[6]);
            }
            personID = (String) data[7];
        }
        else if(data.length == 9){
            eventID = (String) data[0];
            descendant = (String) data[1];
            year = (String) data[2];
            eventType = (String) data[3];
            country = (String) data[4];
            city = (String) data[5];
            personID = (String) data[8];
            if(data[6].getClass().equals(Double.class)){
                latitude = (Double) data[6];
                longitude = (Double) data[7];
            }
            else{
                latitude = Double.parseDouble((String)data[6]);
                longitude = Double.parseDouble((String)data[7]);
            }
        }
        else
            throw new IllegalArgumentException("This is the place");
    }



    public boolean isValid(){
        for (Field f: getClass().getDeclaredFields())
        {
            try {
                if (!f.equals(getClass().getDeclaredField("eventID"))) {
                    if(f.get(this)== null || f.get(this).equals(""))
                        return false;
                }
            }
            catch(NoSuchFieldException e){
                System.err.println("event.isvalid should never happen");
            }
            catch (IllegalAccessException e){
                System.err.println("event.isvalid should never happen");
            }
        }
        return true;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return eventID;
    }

    public String getDate() {
        return year;
    }

    public String getType() {
        return eventType;
    }

    public String getCity(){ return city;}

    public void setDate(String date) {
        this.year = date;
    }

    public void setType(String type) {
        this.eventType = type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(String id) {
        this.eventID = id;
    }
    public String getUser_id() {return descendant;}
    public void setUser_id(String id){descendant = id;}
    public String getPerson_id() {
        return personID;
    }
    public void setPerson_id(String id){personID = id;}


}
