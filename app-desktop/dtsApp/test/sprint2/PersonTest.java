/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint2;

import en.hi.dtsapp.model.OperatorPerson;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ellio
 */
public class PersonTest {
    
    private OperatorPerson operatorPerson;
    private final String name = "john";
    private final String password = "johnpw";
    private final String email = "john@jo.do";
            
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
     public void initiate() {
        operatorPerson = new OperatorPerson(name, password, email);
     }
}
