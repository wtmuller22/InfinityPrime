package primeGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.math.BigInteger;
import java.util.LinkedHashMap;

/**
 * The InfiniteGenerator class has the capabilities 
 * to generate all prime numbers to infinity.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.28
 */

public class EfficientInfiniteCapacityGenerator {
    
    private BigInteger[] currDecade;
    private BigInteger numDecade;
    private int currIdx;
    private List<BigInteger> primes;
    private List<BigAntiPrime> antiPrimes;
    private boolean changed;
    private LinkedHashMap<BigInteger, List<BigAntiPrime>> storage;
    
    /**
     * Creates a new InfiniteGenerator object,
     * instantiates all fields,
     * adds starting primes, 
     * and sorts the anti-primes.
     */
    public EfficientInfiniteCapacityGenerator()
    {
        currDecade = new BigInteger[]{BigInteger.valueOf(11), BigInteger.valueOf(13),
                                        BigInteger.valueOf(17), BigInteger.valueOf(19)};
        numDecade = BigInteger.ONE;
        currIdx = 0;
        primes = new LinkedList<BigInteger>();
        antiPrimes = new LinkedList<BigAntiPrime>();
        changed = false;
        storage = new LinkedHashMap<BigInteger, List<BigAntiPrime>>(25000000);
        addStartingNumbers();
        sortAntis();
    }
    
    /**
     * Gives back the number of digits in the given number.
     * @param num the given number.
     * @return its number of digits.
     */
    private BigInteger convertToDigits(BigInteger num)
    {
        BigInteger count = BigInteger.ZERO;
        BigInteger copy = num;
        while (copy.compareTo(BigInteger.TEN) >= 0)
        {
            copy = copy.divide(BigInteger.TEN);
            count = count.add(BigInteger.ONE);
        }
        count = count.add(BigInteger.ONE);
        return count;
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
     * Sorts the selected storage list.
     * @param select to be sorted.
     */
    private void sortStorage(List<BigAntiPrime> select)
    {
        Collections.sort(select);
    }
    
    /**
     * Transfers over the smallest 
     * overflows to the anti-primes.
     */
    private void transfer()
    {
        sortStorage(storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))));
        int idx = 0;
        while (storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))).size() > 0 
                && storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))).get(0).getValue().compareTo(currDecade[currIdx]) <= 0)
        {
            antiPrimes.add(idx, storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))).remove(0));
            idx++;
        }
    }
    
    /**
     * Updates anti-primes by increasing all values 
     * lower than current number being inspected.
     */
    private void cycle()
    {
        sortAntis();
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
        if (storage.containsKey(convertToDigits(numDecade.multiply(BigInteger.TEN)))
            && storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))).size() > 0 
            && (storage.get(convertToDigits(numDecade.multiply(BigInteger.TEN))).get(0).getValue().compareTo(currDecade[currIdx]) <= 0))
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
     * Adds a value to antiPrimes.
     * @param value1 first value.
     * @param value2 second value.
     */
    private void addValue(BigInteger value1, BigInteger value2)
    {
        boolean same = value1.equals(value2);
        BigInteger value = value1.multiply(value2);
        BigInteger digits = convertToDigits(value);
        BigInteger currDigits = convertToDigits(numDecade.multiply(BigInteger.TEN));
        if (digits.compareTo(currDigits) <= 0)
        {
            if (value.compareTo(antiPrimes.get(0).getValue()) <= 0)
            {
                antiPrimes.add(0, new BigAntiPrime(value, value1.multiply(BigInteger.TEN)));
                if (!same)
                {
                    antiPrimes.add(0, new BigAntiPrime(value, value2.multiply(BigInteger.TEN)));
                }
            }
            else
            {
                antiPrimes.add(new BigAntiPrime(value, value1.multiply(BigInteger.TEN)));
                if (!same)
                {
                    antiPrimes.add(new BigAntiPrime(value, value2.multiply(BigInteger.TEN)));
                }
            }
            changed = true;
        }
        else
        {
            if (!storage.containsKey(digits))
            {
                storage.put(digits, new LinkedList<BigAntiPrime>());
            }
            if (storage.get(digits).size() > 0 && value.compareTo(storage.get(digits).get(0).getValue()) <= 0)
            {
                storage.get(digits).add(0, new BigAntiPrime(value, value1.multiply(BigInteger.TEN)));
                if (!same)
                {
                    storage.get(digits).add(0, new BigAntiPrime(value, value2.multiply(BigInteger.TEN)));
                }
            }
            else
            {
                storage.get(digits).add(new BigAntiPrime(value, value1.multiply(BigInteger.TEN)));
                if (!same)
                {
                    storage.get(digits).add(new BigAntiPrime(value, value2.multiply(BigInteger.TEN)));
                }
            }
        }
    }
    
    /**
     * Adds the numbers left in the decade to primes.
     */
    private void addPrimes()
    {
        for (int i = 0; i < 4; i++)
        {
            BigInteger num1 = currDecade[i];
            if (!num1.equals(BigInteger.ZERO))
            {
                primes.add(num1);
            }
        }
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
                        addValue(num1, primes.get(k));
                    }
                }
                //System.out.println(primes.size());
                for (int j = i; j < 4; j++)
                {
                    BigInteger num2 = currDecade[j];
                    if (!num2.equals(BigInteger.ZERO))
                    {
                        addValue(num1, num2);
                    }
                }
            }
        }
        addPrimes();
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
