import java.util.*;
import java.io.*;

/**
 * This is the Controller class. 
 * It creates a domain instance and handles all the logic behind the simulation. 
 * It does this by manipulating the other classes and via specialised methods to execute the simulation.
 *
 * @author (Syed Hussaini)
 */
public class Controller
{
    private Supermarket supermarket;    
    
    // *NOTE:* though the word 'Queue' is used below, the data structure being used here is not a Queue but an ArrayList
    // Note to self: change the misleading variable name below.
    private ArrayList<Customer> customersInQueue; // A list storing the customers that are waiting to enter the supermarket.
    private ArrayList<Customer> customersInShop; // A list storing the customers that are currently inside the supermarket.
    
    private int time; // The time of day (time is in 24-hr format and goes from 0 to 23)
    
    /**
     * Below is a map and some lists for storing the entry regulations for the supermarket object of this controller. 
     * Time bounds are mapped to age groups.
     * All age groups specified for a timebound are allowed within the supermarket during that timebound. 
     * Timebounds are inclusive. 
     * 
     * E.g. an entry for ((9, 11), "Young") means that Young customers are allowed inside the store between the hours of 9 AM and 11 AM (inclusive). 
     * 
     * If an age group is not specified in the map for a specific time period, it is not allowed entry into the store.
     * E.g. In our Young example above, if there are no other entries which consider the hours of 9 AM to 11 AM, then no other age group will be allowed into the supermarket during this time period. 
     * 
     * Note: we have two separate lists (one for storing the time bounds and the other for age groups corresponding to those time bounds; **corresponding is done by INDEX**), 
     *       this is because of an issue we encountered with BlueJ where we could not enter the entry regulations for our supermarket after creating a Controller object because of the way BlueJ's GUI worked.
     *       To fix this, we created the two lists below and instead hardcoded our entry regulations into the program (for testing purposes).
     *       However, this is of course not what we want released to our client; we will clean this up and find a solution around the BlueJ issue in due time.
     */
    private HashMap<Tuple<Integer>, String> entryReg; 
    private ArrayList<Tuple<Integer>> timeBounds;
    private ArrayList<String> allowedAges;

    /**
     * Create a Controller object. 
     * This also instantiates all instance variables to default values and creates a supermarket with the given capacity.
     * Additionally, we hardcode our entry regulations in this constructor and parse them as entry regulations into our entry regulations map as well.
     * 
     * @param marketCap The maximum capacity for this controller's supermarket object.
     */
    public Controller(int marketCap)
    {
        entryReg = new HashMap<>();
        timeBounds = new ArrayList<>();
        allowedAges = new ArrayList<>();
        //timeBounds.add(this.new Tuple(3, 12)); // These two statements were used for testing purposes.
        //allowedAges.add("Young");
        
        // Parse the time bounds and allowed ages from the two lists storing them into the entry regulations map.
        if(timeBounds.size() == allowedAges.size())  // *NOTE:* Again, remember, time bounds correspond to age groups by INDEX (e.g. the time bound specified in the time bounds list at index 1 corresponds to the age group specified in the allowed ages list at index 1)
        {
            for(int i = 0; i < timeBounds.size(); i++)   {
                entryReg.put(timeBounds.get(i), allowedAges.get(i));
            }
        }  // If the two lists are not equal in size, then clearly we have a time bound without its corresponding age group or vice versa
        else    {
            throw new IllegalStateException("Please ensure that each time bound entry has exactly 1 age group associated with it for that entry");
        }
        
        supermarket = new Supermarket(marketCap);
        customersInQueue = new ArrayList<Customer>();
        customersInShop = new ArrayList<Customer>();
        time = 0;
    }

    /**
     * Simulates one timestep of the domain's simulation. 
     * It uses the other methods to do this in this order of execution:
     * 
     * 1. Exit any customers whose time in the supermarket is up (i.e. their life value has reached 0)
     * 2. Add more customers to the line of customers waiting outside the supermarket
     * 3. Retrieve which age groups are allowed entry into the supermarket for the current time
     * 4. Enter into the supermarket only the allowed age groups from the customers waiting in the line
     * 5. Move to the next time frame
     * 
     * *NOTE* The supermarket's state may be exported to a CSV file at any point during the simulation but is part of a separate method that the user can execute at will.
     */
    public void simulate()
    {
        updateCustomersInShop();
        addCustomersToQueue();
        ArrayList<String> whiteList = getReg();
        enterCustomersToShop(whiteList);     
        moveTimestep();
    }

    /**
     * Remove the customers whose time in the supermarket is up from the supermarket.
     * 
     * Note to self and to reader: there is one flaw with this method; if there are no customers within the supermarket 
     * and you still attempt to update the customers inside the supermarket by executing this method, nothing happens. 
     * This is not ideal as we would instead want some sort of an error message printing out informing the user of this behaviour (for usability purposes).
     * This can (and should) be fixed. Also, since this method is an internal method and is not mean to be called by the user, this issue is not a high-priority issue.
     */
    public void updateCustomersInShop() 
    {
        for(Iterator<Customer> it = customersInShop.iterator(); it.hasNext();)
        {
            Customer customer = it.next();
            if(customer.getLife() == 0)
            {
                it.remove();
                supermarket.removeCustomer(customer.getAge()); // request the supermarket to remove this customer's age group from their count for that age group.
            }
        }   
    }

    /**
     * Add customers to the line of customers that are waiting entry into the supermarket.
     * The number of customers added is pseudorandomised and can be any number between 0 - 7 (inclusive).
     */
    public void addCustomersToQueue()
    {
        Random rand = new Random();
        int noAddCust = rand.nextInt(8); // a pseudorandom number generated between 0 - 7 (inclusive)
        while(noAddCust > 0)
        {
            // create a customer with pseudorandomised attribute values and add this customer to the line of customers waiting entry into the supermarket
            String age = getRandomAge();
            String gender = getRandomGender();
            int life = rand.nextInt((3 - 1) + 1) + 1; // This creates a pseudorandomised number between 1 - 3 (inclusive). This formula was taken from a page by MKYong and can be found here: https://mkyong.com/java/java-generate-random-integers-in-a-range/
            Customer customer = new Customer(age, gender, life); 
            customersInQueue.add(customer);
            noAddCust = noAddCust - 1; // Remember to decrement the counter to avoid an infinite while loop
        }
    }

    /**
     * Generates and returns a pseudorandomised age group (from the allowed age groups) for a Customer object.
     * 
     * @return A pseudorandomised age group for the Customer object from the age groups that are considered valid for the Customer class.
     */
    public String getRandomAge()    
    {
        Random rand = new Random();
        int ageClass = rand.nextInt(3);
        switch (ageClass)   {
            case 0 : return "Young";
            case 1 : return "Middle";
            case 2 : return "Elder";
            default : throw new IllegalArgumentException("Incorrect age class generated!"); // Ensure we report erronous values.
        }
    }

    /**
     * Generates and returns a pseudorandomised gender (from the allowed genders of Male/Female) for a Customer object.
     * 
     * @return A pseudorandomised gender for the Customer object. This can either be "Male" or "Female".
     */
    public String getRandomGender()
    {
        Random rand = new Random();
        int genderClass = rand.nextInt(2);
        switch (genderClass)    {
            case 0 : return "Male";
            case 1 : return "Female";
            default : throw new IllegalArgumentException("Incorrect gender class generated!"); // Ensure we report erronous values.
        }
    }

    /**
     * Find the age groups that should be allowed entry into the supermarket for the current time.
     * 
     * @return A list containing the age groups that are allowed entry into the supermarket for the current time.
     */
    public ArrayList<String> getReg()
    {
        ArrayList<String> regList  = new ArrayList<String>();
        if(entryReg.size() > 0) // If we have entry regulations
        {
            // For every entry in the set of the map of entry regulations, add this entry into our to-be-returned list if the current time lies within the time bound specified by that entry
            for(Map.Entry<Tuple<Integer>, String> entry : entryReg.entrySet())  
            {
                Tuple<Integer> timeBound = entry.getKey();
                String ageGroup = entry.getValue();
                if ((timeBound.first() <= timeBound.second()) && (timeBound.first() <= time) && (time <= timeBound.second())) regList.add(ageGroup);                
            }
        }
        return regList;        
    }
    
    /**
     * Enter the given ages into the supermarket from the line of customers that are waiting entry into the supermarket.
     * 
     * @param A list containing the age groups that are allowed entry into the supermarket for the current time.
     * 
     * *NOTE* There are two problems with this method and one of them is major:
     * 
     * 1. The lesser of these issues is similar to the issue in the updatCustomersInShop method, namely, in the instance
     *    that we attempt to execute this method whilst the line of customers waiting entry into the supermarket is empty, then
     *    nothing happens. This is not correct as we should report this behaviour to the user with an appropriate error message.
     * 2. The more pertinent of these issues is when we attempt to enter customers into the supermarket whilst the supermarket's capacity
     *    is full. The supermarket object itself would, appropriately, report that it cannot do this as its capacity is full, however
     *    the customer is STILL moved to our list of customers who are assumed to be INSIDE the supermarket, which is absolutely erronous!
     *    Our tests did not pick this up, showing the importance of re-reading your source code and also of whitebox testing. 
     *    In any case, this needs to be addressed!
     */
    public void enterCustomersToShop(ArrayList<String> allowedAges)
    {
        for(Iterator<Customer> it = customersInQueue.iterator(); it.hasNext();)   
        {
            Customer customer = it.next();
            String age = customer.getAge();
            if(allowedAges.contains(age))   {
                supermarket.addCustomer(age); // Inform the supermarket that it needs to increment its internal count for the given age group
                customersInShop.add(customer); // Update the list of customers inside the supermarket
                it.remove(); // Remove the customer from the list of customers waiting entry into the supermarket
            }
        }
    }   

    /**
     * Export the current state of the supermarket to a local CSV file. Report an error message if we encounter any issues during this process.
     * The data that is exported includes: 
     * - the current time
     * - the number of Young customers in the supermarket
     * - the number of Middle customers in the supermarket
     * - the number of Elder customers in the supermarket
     * - whether or not the supermarket is full
     * 
     * *Note* Since we close the writer within this method, executing this method will OVERWRITE any data in the supermarket_simulation.csv file.
     *        Ideally, we would want to find a way to add another entry into the CSV instead of overwriting the previous entry. 
     *        This can be looked into for future improvement.
     */
    public void exportState()   
    {
        try {
            FileWriter writer = new FileWriter("supermarket_simulation.csv");
            writer.write("Time, YoungNo., MiddleNo., ElderNo., Full\n");
            writer.write(time + "," + supermarket.getYoung() + "," + supermarket.getMiddle() + "," + supermarket.getElder() + "," + supermarket.isFull() + "\n");            
            writer.close();
        }
        catch(IOException e)    {
            System.out.println("Cannot create CSV file!");
        }
    }

    /**
     * Increment the current time and decrement the life value of all customers currently within the supermarket.
     * The time is in a 24-hour format and goes from 0 - 23 (inclusive)
     */
    public void moveTimestep()
    {
        time = (time + 1)%24;
        for(Customer customer : customersInShop)    {
            customer.decrementLife();
        }
    }
   
    // *********************************************************************
    // *********************************************************************
    /**
     * THE FOLLOWING METHODS WERE USED DURING THE TESTING PHASE AND SHOULD BE REMOVED BEFORE RELEASE!
     */
    public void addToTimebounds(Tuple<Integer> timeBound)
    {
        System.out.println(timeBound.first() + ", " + timeBound.second());
        timeBounds.add(timeBound);
    }
    
    public void addToAllowedAges(String age)
    {
        allowedAges.add(age);
    }
    
    public void addToQueue(Customer customer)
    {
        customersInQueue.add(customer);
    }    
    
    public void addToShop(Customer customer)
    {
        customersInShop.add(customer);
    }    
    
    public ArrayList<Customer> getCustomersInQueue()
    {
        return customersInQueue;
    }
    
    public ArrayList<Customer> getCustomersInShop()
    {
        return customersInShop;
    }    
    
    public void forceEntryRegUpdate()
    {
        if(timeBounds.size() == allowedAges.size()) {
            for(int i = 0; i < timeBounds.size(); i++)   {
                entryReg.put(timeBounds.get(i), allowedAges.get(i));
            }
        }
        else    {
            throw new IllegalStateException("Please ensure that each time bound entry has exactly 1 age group associated with it for that entry");
        }
    }
    
    public void setTime(int t)
    {
        time = t;
    }
    
    public int getTime()
    {
        return time;
    }
    
    public Supermarket getSupermarket()
    {
        return supermarket;
    }    
    // *********************************************************************
    // *********************************************************************
    // *********************************************************************
    // *********************************************************************
    
    /**
     * This is the Tuple class. It is an inner class to the Controller (though its functionality is useful at a high-level so this should be refactored).
     * It serves to create tuples of a certain data type and provides the functionality to access data within them.
     */
    public class Tuple<T> {
        private T x;
        private T y;

        /**
         * Assign the given data values to this tuple.
         * 
         * @param x The first data value
         * @param y The second data value
         */
        public Tuple(T x, T y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Return the first element of this tuple.
         * 
         * @return This tuple's first element
         */
        public T first()  {
            return x;
        }

        /**
         * Return the second element of this tuple.
         * 
         * @return This tuple's second element
         */
        public T second() {
            return y;
        }
    }
}
