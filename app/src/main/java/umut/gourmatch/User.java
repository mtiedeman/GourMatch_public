package umut.gourmatch;

import java.lang.reflect.Array;

/**
 * Created by nabeel on 7/20/16.
 */
public class User {
    // will be used to put stuff into the database about the user
    private int birthYear;
    private String firstName;
    private String lastName;
    private String gender;
    // eggs, fish, milk, peanut, shellfish, soy, treenuts, wheats
    private Boolean[] allergies = new Boolean[8];
    // lacto, lacto_ovo, ovo, pesce, vegan
    private Boolean[] dietary = new Boolean[5];
    private String username;

    public User() {}

    public User(int birthYear, String firstName, String lastName, String gender, Boolean[] allergies, Boolean[] dietary) {
        this.birthYear = birthYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.allergies = allergies;
        this.dietary = dietary;
    }

}