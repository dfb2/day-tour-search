package en.hi.dtsapp.model;

import en.hi.dtsapp.model.DAOs.DTSMethods;

/**
 *
 * @author Erling Oskar Kristjansson, eok4@hi.is
 */
public abstract class Person {
    private final String name, password, email;
    
    public Person(String name, String email, String password){
        if(DTSMethods.isBadInput(name)) { 
            System.err.println("Creating a person with a name that contains characters that the Database will refuse");
        }
        if(DTSMethods.isBadInput(password)) { 
            System.err.println("Creating a person with a password that contains characters that the Database will refuse");
        }
        if(DTSMethods.isBadInput(email)) { 
            System.err.println("Creating a person with an email that contains characters that the Database will refuse");
        }
        this.name = name;
        this.password  = password;
        this.email = email;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    private String getPassword(){
        return this.password;
    }
}
