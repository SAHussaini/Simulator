import java.util.*;
import java.io.*;

/**
 * Write a description of class Controller here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Controller
{
    // instance variables - replace the example below with your own
    private Supermarket supermarket;
    private ArrayList<Customer> customersInQueue;
    private ArrayList<Customer> customersInShop;
    private int time;
    private HashMap<Tuple<Integer>, String> entryReg; //Java does not have an in-built way to make a map with the keys as tuples, so we will require the input to be in a certain format (can consider adding tuple class later)
    private FileWriter writer;

    /**
     * Constructor for objects of class Controller
     */
    public Controller(int marketCap)
    {
        entryReg = new HashMap<>();
        ArrayList<Tuple<Integer>> timeBounds = new ArrayList<>();
        ArrayList<String> allowedAges = new ArrayList<>();

        if(timeBounds.size() == allowedAges.size()) {
            for(int i = 0; i < timeBounds.size(); i++)   {
                entryReg.put(timeBounds.get(i), allowedAges.get(i));
            }
        }
        else    {
            throw new IllegalStateException("Please ensure that each time bound entry has exactly 1 age group associated with it for that entry");
        }
        supermarket = new Supermarket(marketCap);

        customersInQueue = new ArrayList<Customer>();
        customersInShop = new ArrayList<Customer>();
        time = 0;
        try {
            writer = new FileWriter("supermarket_simulation.csv");
            writer.write("Time, YoungNo., MiddleNo., ElderNo., Total\n");
        }
        catch(IOException e)    {
            System.out.println("Cannot create CSV file!");
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void simulate()
    {
        updateCustomersInShop();
        addCustomersToQueue();
        ArrayList<String> whiteList = getReg();

        for(Iterator<Customer> it = customersInQueue.iterator(); it.hasNext();)   {
            Customer customer = it.next();
            String age = customer.getAge();
            if(whiteList.contains(age))   {
                supermarket.addCustomer(age);
                customersInShop.add(customer);
                it.remove();
            }
        }

        exportState();
        moveTimestep();
    }

    public void updateCustomersInShop() 
    {
        for(Iterator<Customer> it = customersInShop.iterator(); it.hasNext();)
        {
            Customer customer = it.next();
            if(customer.getLife() == 0)
            {
                it.remove();
                supermarket.removeCustomer(customer.getAge());
            }
        }   
    }

    public void addCustomersToQueue()
    {
        Random rand = new Random();
        int noAddCust = rand.nextInt(8); //create 0 - 7 customers
        while(noAddCust > 0)
        {
            String age = getRandomAge();
            String gender = getRandomGender();
            int life = rand.nextInt((3 - 1) + 1) + 1; //reference formula https://mkyong.com/java/java-generate-random-integers-in-a-range/
            Customer customer = new Customer(age, gender, life);
            customersInQueue.add(customer);
            noAddCust = noAddCust - 1;
        }
    }

    public String getRandomAge()    
    {
        Random rand = new Random();
        int ageClass = rand.nextInt(3);
        switch (ageClass)   {
            case 0 : return "Young";
            case 1 : return "Middle";
            case 2 : return "Elder";
            default : throw new IllegalArgumentException("Incorrect age class generated!");
        }
    }

    public String getRandomGender()
    {
        Random rand = new Random();
        int genderClass = rand.nextInt(2);
        switch (genderClass)    {
            case 0 : return "Male";
            case 1 : return "Female";
            default : throw new IllegalArgumentException("Incorrect gender class generated!");
        }
    }

    public ArrayList<String> getReg()
    {
        ArrayList<String> regList  = new ArrayList<String>();
        if(entryReg.size() > 0) {
            for(Map.Entry<Tuple<Integer>, String> entry : entryReg.entrySet())
            {
                Tuple<Integer> timeBound = entry.getKey();
                String ageGroup = entry.getValue();
                if ((timeBound.first() <= timeBound.second()) && (timeBound.first() <= time) && (time <= timeBound.second())) regList.add(ageGroup);
                else if ((timeBound.second() <= timeBound.first()) && (timeBound.second() <= time) && (time <= timeBound.first())) regList.add(ageGroup);            
            }
        }
        return regList;        
    }

    public void exportState()   
    {
        try {
            writer.write(time + "," + supermarket.getYoung() + "," + supermarket.getMiddle() + "," + supermarket.getElder() + "," + supermarket.isFull() + "\n");
        }
        catch (IOException e)   {
            System.out.println("Cannot write to CSV file!");
        }
    }

    public void moveTimestep()
    {
        time = (time + 1)%24;
        for(Customer customer : customersInShop)    {
            customer.decrementLife();
        }
    }

    public class Tuple<T> {
        private T x;
        private T y;

        public Tuple(T x, T y) {
            this.x = x;
            this.y = y;
        }

        public T first()  {
            return x;
        }

        public T second() {
            return y;
        }
    }
}
