package model;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by camer on 2/16/2018.
 */

public class Person extends Model  {
    private String personID;
    private String descendant; //userid
    private String descendant_id;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;
    private ArrayList<event> events = new ArrayList<>();

    /*      "firstName": "Sheila",
      "lastName": "Parker",
      "gender": "f",
      "personID": "Sheila_Parker",
      "father": "Patrick_Spencer",
      "mother": "Im_really_good_at_names",
      "descendant": "sheila"*/
    /**
     *
     * @param data [id,descendant_id,user_id,firstName,lastName,gender,father,mother,spouse]
     */
    public Person(Object[] data){
        if(data.length == 5){
            descendant_id = (String) data[0];
            descendant = (String) data[1];
            firstName = (String) data[2];
            lastName = (String) data[3];
            gender = (String) data[4];

        }
        else if(data.length == 6) {
            personID= (String) data[0];
            descendant_id = (String) data[1];
            descendant = (String) data[2];
            firstName = (String) data[3];
            lastName = (String) data[4];
            gender = (String) data[5];
        }
        else if(data.length == 8){
            descendant_id = (String) data[0];
            descendant = (String) data[1];
            firstName = (String) data[2];
            lastName = (String) data[3];
            gender = (String) data[4];
            father = (String) data[5];
            mother = (String) data[6];
            spouse = (String) data[7];
        }
        else if(data.length == 9){
            personID= (String) data[0];
            descendant_id = (String) data[1];
            descendant = (String) data[2];
            firstName = (String) data[3];
            lastName = (String) data[4];
            gender = (String) data[5];
            father = (String) data[6];
            mother = (String) data[7];
            spouse = (String) data[8];
        }
        else throw new IllegalArgumentException();
    }

    public Person(User descendant, String gender){}

    public Person(User user){
        descendant=user.getId();
        firstName= user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
    }

    public String getData(){
        return new String("user_id = " + descendant + ", firstName = " + firstName + ", lastName = " + lastName + ", gender = " + gender + ", father = " + father + ", mother = " + mother + ", spouse = " + spouse);
    }

    public boolean isValid(){
        for (Field f: getClass().getDeclaredFields())
        {
            try {
                if (!f.equals( getClass().getDeclaredField("personID")) && !f.equals( getClass().getDeclaredField("father"))
                        && !f.equals( getClass().getDeclaredField("mother"))
                        && !f.equals( getClass().getDeclaredField("spouse"))) {
                    if(f.get(this)== null || f.get(this).equals(""))
                        return false;
                }
            }
            catch(NoSuchFieldException e){
                System.err.println("person.isvalid should never happen");
            }
            catch (IllegalAccessException e){
                System.err.println("person.isvalid should never happen");
            }
        }
        return true;
    }

    public String getId() {
        return personID;
    }

    public String getUser_id() {
        return descendant;
    }

    public void setUser_id(String user_id) {
        this.descendant = user_id;
    }

    public String getDescendant() {
        return descendant_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setId(String id) {  this.personID = id;  }

    public String getDescendant_id() {
        return descendant_id;
    }

    public void setDescendant_id(String descendant_id) {
        this.descendant_id = descendant_id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public void addEvent(event event){
        if(events == null){
            events = new ArrayList<>();
        }
        if(event != null)
            events.add(event);
    }

    public ArrayList<event> getEvents(){
        return events;
    }

    public event getFirstEvent(){
        int year = 100000000;
        event toReturn = null;
        for(int i = 0; i < events.size(); i++){
            if(Integer.parseInt(events.get(i).getDate()) < year)
                toReturn = events.get(i);
        }
        return toReturn;
    }
}
