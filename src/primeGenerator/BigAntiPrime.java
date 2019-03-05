package primeGenerator;

import java.math.BigInteger;

/**
 * The AntiPrime class represents one node containing 
 * the value and increment value for one anti-prime.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.25
 */

public class BigAntiPrime implements Comparable<BigAntiPrime>{
    
    private BigInteger value;
    private BigInteger increment;
    
    /**
     * Creates a new AntiPrime object.
     * @param num the starting value of the anti-prime.
     * @param inc the amount to be added to 
     * the value when used.
     */
    public BigAntiPrime(BigInteger num, BigInteger inc)
    {
        value = num;
        increment = inc;
    }
    
    /**
     * Increases the value by the increment.
     * @return this increased object.
     */
    public BigAntiPrime increase()
    {
        value = value.add(increment);
        return this;
    }
    
    /**
     * Accessor method for value.
     * @return value.
     */
    public BigInteger getValue()
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
            BigAntiPrime other = (BigAntiPrime)obj;
            return (this.value.equals(other.getValue()));
        }
        else if (value.getClass().equals(obj.getClass()))
        {
            BigInteger other = (BigInteger)obj;
            return (value.equals(other));
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
    public int compareTo(BigAntiPrime other)
    {
        if (other == null)
        {
            throw new NullPointerException();
        }
        return this.value.compareTo(other.getValue());
    }
    
    /**
     * Returns the string representation of the object.
     * @return the string representation.
     */
    @Override
    public String toString()
    {
        return (this.value.toString());
    }
    
}
