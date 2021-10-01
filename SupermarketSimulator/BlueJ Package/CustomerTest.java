import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CustomerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CustomerTest
{
    /**
     * Default constructor for test class CustomerTest
     */
    public CustomerTest()
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
        Customer customer1 = new Customer("Young", "Male", 3);
        assertEquals("Young", customer1.getAge());
        assertEquals(3, customer1.getLife());
    }
    
    @Test // note: nothing to make life go negative
    public void testDecrementLife()
    {
        Customer customer1 = new Customer("Young", "Male", 3);
        customer1.decrementLife();
        assertEquals(2, customer1.getLife());
    }
}
