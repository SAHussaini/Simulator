import java.lang.Exception;
/**
 * Write a description of class Supermarket here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Supermarket
{
    // instance variables - replace the example below with your own
    private int maxCap;
    private int currentCap;
    private int noYoung;
    private int noMiddle;
    private int noElder;

    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket(int capacity)
    {
        maxCap = capacity;
        currentCap = noYoung = noMiddle = noElder = 0;
    }

    public int getYoung()
    {
        return noYoung;
    }
    
    public int getMiddle()
    {
        return noMiddle;
    }
    
    public int getElder()
    {
        return noElder;
    }
    
    public boolean isFull()
    {
        return currentCap == maxCap;
    }
    
    public void addCustomer(String age)
    {
        if (isFull())  {
            System.out.println("There is no more space in the supermarket!");
        }
        else    {
            switch(age) {
                case "Young" : // Consider modularising the increment function
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
                default:                   
                    throw new IllegalArgumentException("This customer's age is not correctly defined");
            }
        }
    }
    
    public void removeCustomer(String age)
    {        
        if (currentCap == 0)    
        {   
            System.out.println("There are no customers in the supermarket!");
        }
        else // Below we dont consider the individual age groups being 0; this may cause problems
        {            
            switch(age) {
                case "Young" : // Consider modularising the increment function
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
                default:
                    throw new IllegalArgumentException("This customer's age is not correctly defined");
            }
        }
    }
}
