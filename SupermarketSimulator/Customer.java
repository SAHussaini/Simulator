
/**
 * Write a description of class Customer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Customer
{
    // instance variables - replace the example below with your own
    private String age;
    private String gender;
    private int life;

    /**
     * Constructor for objects of class Customer
     */
    public Customer(String age, String gender, int life)
    {
        this.age = age;
        this.gender = gender;
        this.life = life;
    }

    public String getAge()
    {
        return age;
    }
    
    public int getLife()
    {
        return life;
    }
    
    public void decrementLife()
    {
        life = life - 1;
    }
}
