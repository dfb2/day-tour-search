package en.hi.dtsapp.model;

import java.util.Objects;

/**
 *
 * @author ellio
 */
public class CustomerPerson extends Person {

    public CustomerPerson(String name, String password, String email) {
        super(name, password, email);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true; 
        if (!(o instanceof CustomerPerson)) 
            return false;
        CustomerPerson cp = (CustomerPerson)o;
        return this.hashCode() == cp.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }
    


    
    public static void main(String[] args) {
        OperatorPerson op = new OperatorPerson("John", "johnspw", "john@john.com");
        CustomerPerson cp = new CustomerPerson("John", "johnspw", "john@john.com");
        System.out.println(cp.equals(op));
    }
}
