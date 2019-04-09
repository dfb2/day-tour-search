package en.hi.dtsapp.model;

import java.util.Objects;

/**
 *
 * @author ellio
 */
public class OperatorPerson extends Person {
    
    public OperatorPerson(String name, String password, String email) {
        super(name, password, email);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true; 
        if (!(o instanceof OperatorPerson)) 
            return false;
        OperatorPerson op = (OperatorPerson)o;
        return this.hashCode() == op.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 37;
        hash = 7 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }
    
    
    public static void main(String[] args) {
        OperatorPerson op = new OperatorPerson("John", "johnspw", "john@john.com");
    }
}
