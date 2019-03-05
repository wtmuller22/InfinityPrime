package primeGenerator;

/**
 * The AntiPrime class represents one node containing 
 * the value and increment value for one anti-prime.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.23
 */

public class AntiPrime implements Comparable<AntiPrime>{
    
    private int value;
    private int increment;
    
    /**
     * Creates a new AntiPrime object.
     * @param num the starting value of the anti-prime.
     * @param inc the amount to be added to 
     * the value when used.
     */
    public AntiPrime(int num, int inc)
    {
        value = num;
        increment = inc;
    }
    
    /**
     * Increases the value by the increment.
     */
    public void increase()
    {
        value += increment;
    }
    
    /**
     * Accessor method for value.
     * @return value.
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * Returns whether the two objects are 
     * equal based on their value fields.
     * @param obj being compared.
     * @return true if they are equal.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass().equals(obj.getClass()))
        {
            AntiPrime other = (AntiPrime)obj;
            return (this.value == other.getValue());
        }
        return false;
    }
    
    /**
     * Compares the two objects' values to see 
     * if they are equal or if one is greater.
     * @param other being compared.
     * @return 0 if equal; 1 if greater; -1 if less than.
     */
    @Override
    public int compareTo(AntiPrime other)
    {
        if (other == null)
        {
            throw new NullPointerException();
        }
        if (this.equals(other))
        {
            return 0;
        }
        if (this.value > other.getValue())
        {
            return 1;
        }
        else
        {
            return -1;
        }
        
    }
    
    /**
     * Returns the string representation of the object.
     * @return the string representation.
     */
    @Override
    public String toString()
    {
        return (Integer.toString(value));
    }
    
}
