import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SupermarketTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SupermarketTest
{
    /**
     * Default constructor for test class SupermarketTest
     */
    public SupermarketTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testGetters()
    {
       Supermarket supermarket1 = new Supermarket(10);
        
       // Blackbox
        assertEquals(0, supermarket1.getYoung());
        assertEquals(0, supermarket1.getMiddle());
        assertEquals(0, supermarket1.getElder()); 
    }
    
    @Test
    public void testAddCustomer()
    {
        Supermarket supermarket1 = new Supermarket(10);
        
        // Blackbox
        supermarket1.addCustomer("Young");
        assertEquals(1, supermarket1.getYoung());
        
        supermarket1.addCustomer("Middle");
        supermarket1.addCustomer("Middle");
        assertEquals(2, supermarket1.getMiddle());
        
        supermarket1.addCustomer("Elder");
        supermarket1.addCustomer("Elder");
        supermarket1.addCustomer("Elder");
        assertEquals(3, supermarket1.getElder());
        
        supermarket1.addCustomer("Young");
        supermarket1.addCustomer("Middle");
        supermarket1.addCustomer("Elder");
        assertEquals(2, supermarket1.getYoung());
        assertEquals(3, supermarket1.getMiddle());
        assertEquals(4, supermarket1.getElder());
        
        //Whitebox:
        //supermarket1.addCustomer("Orange"); PASS
        supermarket1.addCustomer("Young");
        supermarket1.addCustomer("Young"); // Trigger        
    }
    
    @Test
    public void testRemoveCustomer()
    {
        Supermarket supermarket1 = new Supermarket(10);
        
        // Blackbox
        supermarket1.addCustomer("Young");
        supermarket1.addCustomer("Middle");
        supermarket1.addCustomer("Elder");
        assertEquals(1, supermarket1.getYoung());
        assertEquals(1, supermarket1.getMiddle());
        assertEquals(1, supermarket1.getElder());        
        
        supermarket1.removeCustomer("Young");
        supermarket1.removeCustomer("Middle");
        supermarket1.removeCustomer("Elder");
        assertEquals(0, supermarket1.getYoung());
        assertEquals(0, supermarket1.getMiddle());
        assertEquals(0, supermarket1.getElder());
        
        // Whitebox:
        // supermarket1.addCustomer("Young"); 
        // supermarket1.removeCustomer("Orange"); PASSED
        // supermarket1.removeCustomer("Young");
        
        supermarket1.removeCustomer("Young");
        supermarket1.removeCustomer("Middle");
        supermarket1.removeCustomer("Elder");
    }
    
    @Test
    public void testIsFull()
    {
        Supermarket supermarket1 = new Supermarket(2);
        
        // Blackbox
        assertEquals(false, supermarket1.isFull());
        
        supermarket1.addCustomer("Young");
        
        assertEquals(false, supermarket1.isFull());
        
        supermarket1.addCustomer("Young");
        
        assertEquals(true, supermarket1.isFull());
    }
}
