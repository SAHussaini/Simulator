import java.lang.Exception;
/**
 * This is the Supermarket class. It serves to represent the domain's supermarket.
 *
 * @author (Syed Hussaini)
 */
public class Supermarket
{
    private int maxCap;
    private int currentCap; // this is equivalent to the (current) total number of customers from each age group
    
    // The supermarket holds the number of each age group presently within the supermarket
    private int noYoung;
    private int noMiddle;
    private int noElder;

    /**
     * Instantiate a supermarket object with the given maximum capacity; all other attributes are set to 0.
     * 
     * @param capacity The supermarket's maximum capacity.
     */
    public Supermarket(int capacity)
    {
        maxCap = capacity;
        currentCap = noYoung = noMiddle = noElder = 0;
    }

    /**
     * Gets the number of customers currently within the supermarket who are of the "Young" age group.
     * 
     * @return The number of customers currently within the supermarket who are of the "Young" age group.
     */
    public int getYoung()
    {
        return noYoung;
    }
    
    /**
     * Gets the number of customers currently within the supermarket who are of the "Elder" age group.
     * 
     * @return The number of customers currently within the supermarket who are of the "Elder" age group.
     */
    public int getMiddle()
    {
        return noMiddle;
    }
    
    /**
     * Gets the number of customers currently within the supermarket who are of the "Elder" age group.
     * 
     * @return The number of customers currently within the supermarket who are of the "Elder" age group.
     */
    public int getElder()
    {
        return noElder;
    }
    
    /**
     * Checks whether or not the supermarket has reached its full capacity of customers.
     * 
     * @return A Boolean; true if the supermarket is full and false otherwise.
     */
    public boolean isFull()
    {
        return currentCap == maxCap;
    }
    
    /**
     * Requests the supermarket to add a customer for the given age group to its internal customer count.
     * 
     * @param age The age group for the customer that is requested to be added.
     */    
    public void addCustomer(String age)
    {        
        if (isFull()) // We cannot add a customer if the supermarket is full!
        {
            System.out.println("There is no more space in the supermarket!");
        }        
        else   // Increment the count for the age given; also increment the supermarket's current capacity.
        {
            switch(age) 
            {
                case "Young" : // #Note to self: Consider modularising the incrementing below
                    noYoung = noYoung + 1;
                    currentCap = currentCap + 1;
                    break;
                case "Middle" :
                    noMiddle = noMiddle + 1;
                    currentCap = currentCap + 1;
                    break;
                case "Elder" :
                    noElder = noElder + 1;
                    currentCap = currentCap + 1;
                    break;
                default:   // All other age groups are considered invalid                  
                    throw new IllegalArgumentException("This customer's age is not correctly defined");
            }
        }
    }    
    
    /**
     * Requests the supermarket to remove a customer for the given age group from its internal customer count.
     * 
     * @param age The age group for the customer that is requested to be removed.
     */    
    public void removeCustomer(String age)
    {        
        if (currentCap == 0)   // We cannot remove customers if there are none within the supermarket!
        {   
            System.out.println("There are no customers in the supermarket!");
        }
        else   // Decrement the count for the age given; also decrement the supermarket's current capacity.
        {            
            switch(age)  // Note to reader: below we do not consider the age groups being 0; this may cause problems and should be addressed (e.g. when the capacity is not 0 but the count for the age group being requested removal is 0)
            {
                case "Young" : // #Note to self: consider modularising the increment function
                    noYoung = noYoung - 1;
                    currentCap = currentCap - 1;
                    break;
                case "Middle" :
                    noMiddle = noMiddle - 1;
                    currentCap = currentCap - 1;
                    break;
                case "Elder" :
                    noElder = noElder - 1;
                    currentCap = currentCap - 1;
                    break;
                default:   // All other age groups are considered invalid 
                    throw new IllegalArgumentException("This customer's age is not correctly defined");
            }
        }
    }
}
