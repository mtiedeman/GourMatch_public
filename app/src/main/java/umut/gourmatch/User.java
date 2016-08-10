package umut.gourmatch;

import java.lang.reflect.Array;

/**
 * Created by nabeel on 7/20/16.
 */
public class User {
    // will be used to put stuff into the database about the user
    private String lastLogin;
    private String birthYear;
    private String firstName;
    private String lastName;
    private String gender;
    // eggs, fish, milk, peanut, shellfish, soy, treenuts, wheats
    private boolean[] allergies = new boolean[8];
    // lacto, lacto_ovo, ovo, pesce, vegan
    private boolean[] dietary = new boolean[5];
    private String username;

    public User() {}

    public User(String birthYear, String firstName, String lastName, String gender, boolean[] allergies, boolean[] dietary, String username) {
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
    public boolean[] getAllergies(){
        return this.allergies;
    }
    public boolean[] getDietary(){
        return this.dietary;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getLastLogin() { return this.lastLogin; }

}