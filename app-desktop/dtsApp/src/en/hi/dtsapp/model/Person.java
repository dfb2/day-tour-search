package en.hi.dtsapp.model;

import static en.hi.dtsapp.model.DBO.validateDatabaseInput;
import static en.hi.dtsapp.model.DBO.validateEmail;

/**
 * 
 * @author Erling Oskar Kristjansson, eok4@hi.is
 */
public abstract class Person implements DBO {
    private final String name, password, email;
    private final String SECRET_WORD = "erling";
    
    public Person(String name, String password, String email) throws IllegalArgumentException{
        if(validatePersonName(name)) { 
            this.name = name;
        } else {
            throw new IllegalArgumentException("Person name must be shorter than 50 characters and "
                    + INVALID_DATABASE_INPUT_STRING);
        }
        if(validatePersonPassword(password)) { 
            this.password  = password;
        } else {
            throw new IllegalArgumentException("Person password must be shorter than 40 characters and "
                    + INVALID_DATABASE_INPUT_STRING);
        }
        if(validateEmail(email)) { 
            this.email = email;
        } else {
            throw new IllegalArgumentException("Person email must be sensible email format, "
                    + "shorter than 40 characters and "
                    + INVALID_DATABASE_INPUT_STRING);
        }
    }

    /**
     * @param name
     * @return true if length of name is less than 50 and name contains no signs from the set { ; ' " null }
     */
    private boolean validatePersonName(String name){
        return (validateDatabaseInput(name) && name.length() <= 50 );
    }
    /**
     * @param password
     * @return true if length of password is less than 40 and password contains no signs from the set { ; ' " null }
     */
    private boolean validatePersonPassword(String password){
        return (validateDatabaseInput(password) && password.length() <= 40);
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
    
    public String getPassword(String secretWord){
        if (secretWord.equals(SECRET_WORD)) return this.getPassword();
        else return "";
    }
}
