/**
 * This is the Customer class. It serves to represent a customer within the domain.
 *
 * @author (Syed Hussaini)
 */
public class Customer
{    
    // Some attributes of the customer
    private String age;
    private String gender;
    private int life; // This is the number of domain timesteps (i.e. the time count in the Controller class) the customer will remain in the supermarket for after they have entered it.

    /**
     * Instantiate a customer object with the given attributes.
     * 
     * @param age The customer's age.
     * @param gender The customer's gender.
     * @param life The amount of timesteps the customer should remain in the supermarket after they have entered it.
     */
    public Customer(String age, String gender, int life)
    {
        this.age = age;
        this.gender = gender;
        this.life = life;
    }

    /**
     * Gets the customer's age group.
     * 
     * @return The customer's age group.
     */
    public String getAge()
    {
        return age;
    }
    
    /**
     * Gets the customer's current life value.
     * 
     * @return The customer's current life value.
     */
    public int getLife()
    {
        return life;
    }
    
    /**
     * Decrements the customer's life value.
     */
    public void decrementLife()
    {
        life = life - 1;
    }
}
