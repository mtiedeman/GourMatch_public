package umut.gourmatch;

import java.lang.reflect.Array;

/**
 * Created by nabeel on 7/20/16.
 */
public class User {
    // will be used to put stuff into the database about the user
    private String birthYear;
    private String firstName;
    private String lastName;
    private String gender;
    // eggs, fish, milk, peanut, shellfish, soy, treenuts, wheats
    private Boolean[] allergies = new Boolean[8];
    // lacto, lacto_ovo, ovo, pesce, vegan
    private Boolean[] dietary = new Boolean[5];
    private String username;

    public User() {}

    public User(String birthYear, String firstName, String lastName, String gender, Boolean[] allergies, Boolean[] dietary, String username) {
        this.birthYear = birthYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.allergies = allergies;
        this.dietary = dietary;
        this.username = username;
    }
    //calls

    public String getUsername(){
        return this.username;
    }
    public String getBirthYear(){
        return this.birthYear;
    }
    public String getGender(){
        return this.gender;
    }
    public Boolean[] getAllergies(){
        return this.allergies;
    }
    public Boolean[] getDietary(){
        return this.dietary;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }

}