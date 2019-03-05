package primeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * The InfiniteGenerator class has the capabilities 
 * to generate all prime numbers to infinity.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.23
 */

public class InfiniteGenerator {
    
    private int[] currDecade;
    private int numDecade;
    private int currIdx;
    private List<Integer> primes;
    private List<AntiPrime> antiPrimes;
    private List<AntiPrime> overflow;
    private boolean changed;
    
    /**
     * Creates a new InfiniteGenerator object,
     * instantiates all fields,
     * adds starting primes, 
     * and sorts the anti-primes.
     */
    public InfiniteGenerator()
    {
        currDecade = new int[]{11, 13, 17, 19};
        numDecade = 1;
        currIdx = 0;
        primes = new ArrayList<Integer>();
        antiPrimes = new ArrayList<AntiPrime>();
        overflow = new ArrayList<AntiPrime>();
        changed = false;
        addStartingNumbers();
        sortAntis();
    }
    
    /**
     * Adds the starting primes and anti-primes.
     */
    private void addStartingNumbers()
    {
        primes.add(2);
        primes.add(3);
        primes.add(5);
        primes.add(7);
        antiPrimes.add(new AntiPrime(3, 30));
        antiPrimes.add(new AntiPrime(9, 30));
        antiPrimes.add(new AntiPrime(21, 30));
        antiPrimes.add(new AntiPrime(27, 30));
        antiPrimes.add(new AntiPrime(7, 70));
        antiPrimes.add(new AntiPrime(21, 70));
        antiPrimes.add(new AntiPrime(49, 70));
        antiPrimes.add(new AntiPrime(63, 70));
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
        int lowest = antiPrimes.get(0).getValue();
        int idx = 0;
        while (overflow.get(0).getValue() <= lowest)
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
        while(antiPrimes.get(0).getValue() < currDecade[currIdx])
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
    private AntiPrime getMinValue()
    {
        if (overflow.size() > 0 && (overflow.get(0).getValue() < currDecade[currIdx] 
            || overflow.get(0).getValue() < antiPrimes.get(0).getValue()))
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
        AntiPrime lowest = getMinValue();
        int val = lowest.getValue();
        if (currDecade[currIdx] == val)
        {
            currDecade[currIdx] = 0;
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
            int num1 = currDecade[i];
            if (num1 != 0)
            {
                System.out.println(num1);
                for (int k = 0; k < primes.size(); k++)
                {
                    int value = num1 * primes.get(k);
                    if (overflow.size() > 0 && value <= overflow.get(0).getValue())
                    {
                        overflow.add(0, new AntiPrime(value, num1 * 10));
                        overflow.add(0, new AntiPrime(value, primes.get(k) * 10));
                    }
                    else
                    {
                        overflow.add(new AntiPrime(value, num1 * 10));
                        overflow.add(new AntiPrime(value, primes.get(k) * 10));
                    }
                }
                primes.add(num1);
                //System.out.println(primes.size());
                for (int j = 0; j < 4; j++)
                {
                    int num2 = currDecade[j];
                    if (num2 != 0)
                    {
                        int val = num1 * num2;
                        int inc = num1 * 10;
                        if (overflow.size() > 0 && val <= overflow.get(0).getValue())
                        {
                            overflow.add(0, new AntiPrime(val, inc));
                        }
                        else
                        {
                            overflow.add(new AntiPrime(val, inc));
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
        currDecade[0] = (numDecade * 10) + 1;
        currDecade[1] = (numDecade * 10) + 3;
        currDecade[2] = (numDecade * 10) + 7;
        currDecade[3] = (numDecade * 10) + 9;
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
        numDecade++;
        currIdx = 0;
        updateDecade();
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
