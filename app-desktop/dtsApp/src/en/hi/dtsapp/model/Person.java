package en.hi.dtsapp.model;

import java.util.Objects;

/**
 *
 * @author ellio
 */
public abstract class Person {
    private final String name, password, email;
    
    public Person(String name, String password, String email){
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
