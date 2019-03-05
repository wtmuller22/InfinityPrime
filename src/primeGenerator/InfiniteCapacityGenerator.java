package primeGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.math.BigInteger;

/**
 * The InfiniteGenerator class has the capabilities 
 * to generate all prime numbers to infinity.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.25
 */

public class InfiniteCapacityGenerator {
    
    private BigInteger[] currDecade;
    private BigInteger numDecade;
    private int currIdx;
    private List<BigInteger> primes;
    private List<BigAntiPrime> antiPrimes;
    private List<BigAntiPrime> overflow;
    private boolean changed;
    
    /**
     * Creates a new InfiniteGenerator object,
     * instantiates all fields,
     * adds starting primes, 
     * and sorts the anti-primes.
     */
    public InfiniteCapacityGenerator()
    {
        currDecade = new BigInteger[]{BigInteger.valueOf(11), BigInteger.valueOf(13),
                                        BigInteger.valueOf(17), BigInteger.valueOf(19)};
        numDecade = BigInteger.ONE;
        currIdx = 0;
        primes = new LinkedList<BigInteger>();
        antiPrimes = new LinkedList<BigAntiPrime>();
        overflow = new LinkedList<BigAntiPrime>();
        changed = false;
        addStartingNumbers();
        sortAntis();
    }
    
    /**
     * Adds the starting primes and anti-primes.
     */
    private void addStartingNumbers()
    {
        primes.add(BigInteger.valueOf(2));
        primes.add(BigInteger.valueOf(3));
        primes.add(BigInteger.valueOf(5));
        primes.add(BigInteger.valueOf(7));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(3), BigInteger.valueOf(30)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(9), BigInteger.valueOf(30)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(21), BigInteger.valueOf(30)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(27), BigInteger.valueOf(30)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(7), BigInteger.valueOf(70)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(21), BigInteger.valueOf(70)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(49), BigInteger.valueOf(70)));
        antiPrimes.add(new BigAntiPrime(BigInteger.valueOf(63), BigInteger.valueOf(70)));
    }
    
    /**
     * Sorts the anti-primes list.
     */
    private void sortAntis()
    {
        Collections.sort(antiPrimes);
    }
    
    /**
     * Sorts the overflow list.
     */
    private void sortOverflow()
    {
        Collections.sort(overflow);
    }
    
    /**
     * Transfers over the smallest 
     * overflows to the anti-primes.
     */
    private void transfer()
    {
        sortOverflow();
        BigInteger lowest = antiPrimes.get(0).getValue();
        int idx = 0;
        while (overflow.get(0).getValue().compareTo(lowest) <= 0)
        {
            antiPrimes.add(idx, overflow.remove(0));
            idx++;
        }
    }
    
    /**
     * Updates anti-primes by increasing all values 
     * lower than current number being inspected.
     */
    private void cycle()
    {
        while(antiPrimes.get(0).getValue().compareTo(currDecade[currIdx]) < 0)
        {
            antiPrimes.get(0).increase();
            antiPrimes.add(antiPrimes.remove(0));
            changed = true;
        }
    }
    
    /**
     * Finds the anti-prime with the lowest value,
     * greater than or equal to the current number 
     * being inspected.
     * @return the lowest AntiPrime value.
     */
    private BigAntiPrime getMinValue()
    {
        if (overflow.size() > 0 && ((overflow.get(0).getValue().compareTo(currDecade[currIdx]) < 0) 
            || overflow.get(0).getValue().compareTo(antiPrimes.get(0).getValue()) < 0))
        {
            transfer();
        }
        cycle();
        if (changed)
        {
            sortAntis();
            changed = false;
        }
        return antiPrimes.get(0);
    }
    
    /**
     * Completes a single iteration of the 
     * current decade being inspected.
     */
    private void singleIteration()
    {
        BigAntiPrime lowest = getMinValue();
        BigInteger val = lowest.getValue();
        if (currDecade[currIdx].equals(val))
        {
            currDecade[currIdx] = BigInteger.ZERO;
        }
        currIdx++;
    }
    
    /**
     * Adds the inspected current decade to 
     * the primes and overflow lists.
     */
    private void addDecade()
    {
        for (int i = 0; i < 4; i++)
        {
            BigInteger num1 = currDecade[i];
            if (!num1.equals(BigInteger.ZERO))
            {
                System.out.println(num1);
                for (int k = 1; k < primes.size(); k++) //Starting at one avoids 2
                {
                    if (k != 2) //Skips 5
                    {
                        BigInteger value = num1.multiply(primes.get(k));
                        if (overflow.size() > 0 && value.compareTo(overflow.get(0).getValue()) <= 0)
                        {
                            overflow.add(0, new BigAntiPrime(value, num1.multiply(BigInteger.TEN)));
                            overflow.add(0, new BigAntiPrime(value, primes.get(k).multiply(BigInteger.TEN)));
                        }
                        else
                        {
                            overflow.add(new BigAntiPrime(value, num1.multiply(BigInteger.TEN)));
                            overflow.add(new BigAntiPrime(value, primes.get(k).multiply(BigInteger.TEN)));
                        }
                    }
                }
                primes.add(num1);
                //System.out.println(primes.size());
                for (int j = 0; j < 4; j++)
                {
                    BigInteger num2 = currDecade[j];
                    if (!num2.equals(BigInteger.ZERO))
                    {
                        BigInteger val = num1.multiply(num2);
                        BigInteger inc = num1.multiply(BigInteger.TEN);
                        if (overflow.size() > 0 && val.compareTo(overflow.get(0).getValue()) <= 0)
                        {
                            overflow.add(0, new BigAntiPrime(val, inc));
                        }
                        else
                        {
                            overflow.add(new BigAntiPrime(val, inc));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Sets next decade to be inspected.
     */
    private void updateDecade()
    {
        currDecade[0] = numDecade.multiply(BigInteger.TEN).add(BigInteger.ONE);
        currDecade[1] = numDecade.multiply(BigInteger.TEN).add(BigInteger.valueOf(3));
        currDecade[2] = numDecade.multiply(BigInteger.TEN).add(BigInteger.valueOf(7));
        currDecade[3] = numDecade.multiply(BigInteger.TEN).add(BigInteger.valueOf(9));
    }
    
    /**
     * Runs through an entire decade iteration.
     */
    public void decadeIteration()
    {
        while (currIdx < 4)
        {
            singleIteration();
        }
        addDecade();
        //System.out.println("[" + currDecade[0].toString() + ", " + currDecade[1].toString() + ", " + currDecade[2].toString() + ", " + currDecade[3].toString() + "]");
        numDecade = numDecade.add(BigInteger.ONE);
        currIdx = 0;
        updateDecade();
        //System.out.println(antiPrimes);
    }
    
    /**
     * Returns the string representation of this object.
     * @return the string representation.
     */
    @Override
    public String toString()
    {
        //return antiPrimes.toString();
        return primes.toString();
    }
}
