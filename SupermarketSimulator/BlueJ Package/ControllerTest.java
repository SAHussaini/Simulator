import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class ControllerTest.
 *
 * @author  (Syed Hussaini)
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
    // UNIT TESTS
    @Test
    public void testAddCustomersToQueue()  
    {
        // Blackbox
        Controller controller = new Controller(10);
        
        assertEquals(true, controller.getCustomersInQueue().isEmpty());
        
        controller.addCustomersToQueue();
        
        ArrayList<Customer> t1Queue = controller.getCustomersInQueue();
        assertTrue(t1Queue.size() >= 0);
        
        controller.addCustomersToQueue();
        
        ArrayList<Customer> t2Queue = controller.getCustomersInQueue();
        
        assertTrue(t2Queue.size() >= t1Queue.size());   
        
        System.out.println(t1Queue.size() + ", " + t2Queue.size());
    }
    
    @Test
    public void testEnterAllCustomersToShop()  
    {
        // Blackbox
        Controller controller = new Controller(10);        
        ArrayList<String> allowedAges = new ArrayList<>();
        allowedAges.add("Young"); allowedAges.add("Middle"); allowedAges.add("Elder");                      
        
        controller.addCustomersToQueue();   
        
        int queueSize = controller.getCustomersInQueue().size();
        
        controller.enterCustomersToShop(allowedAges);
        
        int shopSize = controller.getCustomersInShop().size();        
        
        assertTrue(queueSize == shopSize);
        System.out.println(queueSize + ", " + shopSize);
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
        
        assertEquals(true, controller.getCustomersInShop().isEmpty());        
        controller.updateCustomersInShop();
        
        controller.addToQueue(new Customer("Young", "Male", 1));        
        controller.enterCustomersToShop(allowedAges);
        controller.getCustomersInShop().get(0).decrementLife();
        controller.updateCustomersInShop();
        
        assertEquals(0, controller.getCustomersInShop().size());       
        
        controller.addToQueue(new Customer("Middle", "Female", 2));       
        controller.addToQueue(new Customer("Elder", "Female", 3)); 
        controller.enterCustomersToShop(allowedAges);
        controller.getCustomersInShop().get(0).decrementLife();
        controller.getCustomersInShop().get(0).decrementLife();
        controller.getCustomersInShop().get(1).decrementLife();
        controller.getCustomersInShop().get(1).decrementLife();
        controller.updateCustomersInShop();
        
        assertEquals(1, controller.getCustomersInShop().size());
        assertEquals("Elder", controller.getCustomersInShop().get(0).getAge());
        
        controller.getCustomersInShop().get(0).decrementLife(); // life can be decremented to below 0 but that is for integration/system test
        controller.updateCustomersInShop();
        
        assertEquals(true, controller.getCustomersInShop().isEmpty());
    }
    
    @Test
    public void testGetReg()
    {
        // Blackbox
        // 1 value and with Middle age group
        Controller controller = new Controller(10);
        Controller.Tuple<Integer> timeboundMiddle = controller.new Tuple(9, 11);
        controller.addToTimebounds(timeboundMiddle);
        controller.addToAllowedAges("Middle");
        controller.forceEntryRegUpdate();

        controller.setTime(8);        
        assertEquals(true, controller.getReg().isEmpty());
        
        controller.setTime(9);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(10);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(11);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(12);
        assertEquals(true, controller.getReg().isEmpty());
        
        // 2 values with Middle and Elder age groups and distinct timebounds
        Controller.Tuple<Integer> timeboundElder1 = controller.new Tuple(15, 17);
        controller.addToTimebounds(timeboundElder1);
        controller.addToAllowedAges("Elder");
        controller.forceEntryRegUpdate();
        
        controller.setTime(8);        
        assertEquals(true, controller.getReg().isEmpty());
        
        controller.setTime(9);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(10);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(11);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(12);
        assertEquals(true, controller.getReg().isEmpty());
        
        controller.setTime(14);        
        assertEquals(true, controller.getReg().isEmpty());
        
        controller.setTime(15);
        assertEquals(1, controller.getReg().size());
        assertEquals("Elder", controller.getReg().get(0));
        
        controller.setTime(16);
        assertEquals(1, controller.getReg().size());
        assertEquals("Elder", controller.getReg().get(0));
        
        controller.setTime(17);
        assertEquals(1, controller.getReg().size());
        assertEquals("Elder", controller.getReg().get(0));
        
        controller.setTime(18);
        assertEquals(true, controller.getReg().isEmpty());
        
        // 3 values with Young, Middle and Elder age groups and partially overlapping timebounds
        Controller.Tuple<Integer> timeboundYoung = controller.new Tuple(10, 12);
        Controller.Tuple<Integer> timeboundElder2 = controller.new Tuple(11, 13);
        controller.addToTimebounds(timeboundYoung);
        controller.addToAllowedAges("Young");
        controller.addToTimebounds(timeboundElder2);
        controller.addToAllowedAges("Elder");
        controller.forceEntryRegUpdate();
        
        controller.setTime(8);        
        assertEquals(true, controller.getReg().isEmpty());
        
        controller.setTime(9);
        assertEquals(1, controller.getReg().size());
        assertEquals("Middle", controller.getReg().get(0));
        
        controller.setTime(10);
        assertEquals(2, controller.getReg().size());
        assertTrue(controller.getReg().contains("Middle"));
        assertTrue(controller.getReg().contains("Young"));
        
        controller.setTime(11);
        assertEquals(3, controller.getReg().size());
        assertTrue(controller.getReg().contains("Middle"));
        assertTrue(controller.getReg().contains("Young"));
        assertTrue(controller.getReg().contains("Elder"));
        
        controller.setTime(12);
        assertEquals(2, controller.getReg().size());
        assertTrue(controller.getReg().contains("Young"));
        assertTrue(controller.getReg().contains("Elder"));
        
        controller.setTime(13);        
        assertEquals(1, controller.getReg().size());
        assertTrue(controller.getReg().contains("Elder"));
        
        controller.setTime(14);
        assertEquals(true, controller.getReg().isEmpty());   
        
        //Whitebox
        // Controller.Tuple<Integer> timebound = controller.new Tuple(3, 5);
        // controller.addToTimebounds(timebound);
        // controller.forceEntryRegUpdate(); PASS
    }        
    
    @Test
    public void testMoveTimestep()
    {
        Controller controller = new Controller(5);
        controller.setTime(3);
        controller.addToQueue(new Customer("Young", "Female", 2));
        controller.addToShop(new Customer("Middle", "Female", 1));
        
        controller.moveTimestep();
        assertEquals(4, controller.getTime());
        assertEquals(2, controller.getCustomersInQueue().get(0).getLife());
        assertEquals(0, controller.getCustomersInShop().get(0).getLife());
        
        controller.setTime(23);
        controller.moveTimestep();
        assertEquals(0, controller.getTime());
        
        controller.moveTimestep();
        assertEquals(1, controller.getTime());
    }
    //SYSTEM/INTEGRATION TESTS
    @Test
    public void testSimulation()
    {
        Controller controller = new Controller(10);
        Controller.Tuple<Integer> timeboundElder = controller.new Tuple(9, 11);
        controller.addToTimebounds(timeboundElder);
        controller.addToAllowedAges("Elder");
        controller.forceEntryRegUpdate();
        controller.setTime(8);
        controller.addToQueue(new Customer("Elder", "Male", 2)); // to ensure our tests are carried out
        controller.addToQueue(new Customer("Young", "Female", 3));
        controller.addCustomersToQueue();          
        controller.enterCustomersToShop(controller.getReg());
        
        assertEquals(true, controller.getCustomersInShop().isEmpty());
        assertEquals(0, controller.getSupermarket().getYoung());
        assertEquals(0, controller.getSupermarket().getMiddle());
        assertEquals(0, controller.getSupermarket().getElder());
        
        controller.setTime(9); 
        controller.addCustomersToQueue(); 
        int noTotal = controller.getCustomersInQueue().size();
        int noElders = 0;
        for(Customer customer : controller.getCustomersInQueue())
        {
            if(customer.getAge() == "Elder") { noElders = noElders + 1; }
        }
        controller.enterCustomersToShop(controller.getReg());
        
        assertEquals(noElders, controller.getCustomersInShop().size());
        assertEquals(noTotal - noElders, controller.getCustomersInQueue().size());        
        
        controller.moveTimestep(); 
        assertEquals(1, controller.getCustomersInShop().get(0).getLife());
        assertEquals(3, controller.getCustomersInQueue().get(0).getLife()); 
        
        controller.moveTimestep();
        assertEquals(0, controller.getCustomersInShop().get(0).getLife());
        assertEquals(3, controller.getCustomersInQueue().get(0).getLife()); 
        
        int noEldersInShop = 0;
        for(Customer customer : controller.getCustomersInShop())
        {
            if(customer.getAge() == "Elder") { noEldersInShop = noEldersInShop + 1; }
        }
        
        System.out.println(noEldersInShop);
        controller.exportState();
    }
}
