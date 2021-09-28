import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class ControllerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ControllerTest
{
    /**
     * Default constructor for test class ControllerTest
     */
    public ControllerTest()
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
    
    @Test //simulating a simulation!
    public void testAddCustomersToQueue()  
    {
        // Blackbox
        Controller controller = new Controller(10);
        
        assertEquals(null, controller.getCustomersInQueue());
        
        controller.addCustomersToQueue();
        
        ArrayList<Customer> t1Queue = controller.getCustomersInQueue();
        assertTrue(t1Queue.size() >= 0);
        
        controller.addCustomersToQueue();
        
        ArrayList<Customer> t2Queue = controller.getCustomersInQueue();
        
        assertTrue(t2Queue.size() >= t1Queue.size());               
    }
    
    @Test
    public void testEnterAllCustomersToShop()  
    {
        // Blackbox
        Controller controller = new Controller(10);        
        ArrayList<String> allowedAges = new ArrayList<>();
        allowedAges.add("Young"); allowedAges.add("Middle"); allowedAges.add("Elder");                      
        
        controller.addCustomersToQueue();   
        
        ArrayList<Customer> t1Queue = controller.getCustomersInQueue();
        
        controller.enterCustomersToShop(allowedAges);
        
        ArrayList<Customer> t1Shop = controller.getCustomersInShop();        
        
        assertTrue(t1Queue.equals(t1Shop));
    }
    
    @Test
    public void testEnterSpecifiedCustomersToShop()  
    {
        // Blackbox
        Controller controller = new Controller(10);        
        ArrayList<String> allowedAges = new ArrayList<>();
        allowedAges.add("Young"); allowedAges.add("Elder");                      
        
        controller.addToQueue(new Customer("Young", "Male", 3)); 
        controller.addToQueue(new Customer("Middle", "Male", 3)); 
        controller.addToQueue(new Customer("Elder", "Male", 3)); 
        
        assertEquals(3, controller.getCustomersInQueue().size());
        
        controller.enterCustomersToShop(allowedAges);   
        
        ArrayList<Customer> t1Queue = controller.getCustomersInQueue();
        ArrayList<Customer> t1Shop = controller.getCustomersInShop();
        
        assertEquals(1, t1Queue.size());
        assertEquals(2, t1Shop.size());        
        assertEquals("Young", t1Shop.get(0).getAge());
        assertEquals("Middle", t1Queue.get(0).getAge());
        assertEquals("Elder", t1Shop.get(1).getAge());
    }
    
    @Test
    public void testUpdateCustomersInShop()  
    {
        // Blackbox
        Controller controller = new Controller(10);        
        ArrayList<String> allowedAges = new ArrayList<>();
        allowedAges.add("Young"); allowedAges.add("Middle"); allowedAges.add("Elder");               
        
        assertEquals(null, controller.getCustomersInShop());        
        controller.updateCustomersInShop();
        
        controller.addToQueue(new Customer("Young", "Male", 1));        
        controller.enterCustomersToShop(allowedAges);
        controller.getCustomersInShop().get(0).decrementLife();
        controller.updateCustomersInShop();
        
        assertEquals(0, controller.getCustomersInShop().size());       
        
        controller.addToQueue(new Customer("Middle", "Female", 2));       
        controller.addToQueue(new Customer("Elder", "Female", 3)); 
        
        controller.getCustomersInShop().get(0).decrementLife();
        controller.getCustomersInShop().get(0).decrementLife();
        controller.getCustomersInShop().get(1).decrementLife();
        controller.getCustomersInShop().get(1).decrementLife();
        controller.updateCustomersInShop();
        
        assertEquals(1, controller.getCustomersInShop().size());
        assertEquals("Elder", controller.getCustomersInShop().get(0).getAge());
        
        controller.getCustomersInShop().get(0).decrementLife(); // life can be decremented to below 0 but that is for integration/system test
        controller.updateCustomersInShop();
        
        assertEquals(0, controller.getCustomersInShop().size());
    }
}
