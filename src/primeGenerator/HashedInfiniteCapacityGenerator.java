/**
 * 
 */
package primeGenerator;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.03.03
 */
public class HashedInfiniteCapacityGenerator {
    
    private BigInteger[] currDecade;
    private BigInteger numDecade;
    private int currIdx;
    private List<BigInteger> primes;
    private LinkedHashMap<BigInteger, BigAntiPrime[]> antiPrimes;
    
    /**
     * Creates a new InfiniteGenerator object,
     * instantiates all fields,
     * adds starting primes, 
     * and sorts the anti-primes.
     */
    public HashedInfiniteCapacityGenerator()
    {
        currDecade = new BigInteger[]{BigInteger.valueOf(11), BigInteger.valueOf(13),
                                        BigInteger.valueOf(17), BigInteger.valueOf(19)};
        numDecade = BigInteger.ONE;
        currIdx = 0;
        primes = new LinkedList<BigInteger>();
        antiPrimes = new LinkedHashMap<BigInteger, BigAntiPrime[]>(25000000);
        addStartingNumbers();
    }
    
    /**
     * Gives back the decade of the number.
     * @param num the given number.
     * @return its decade.
     */
    private BigInteger convertToDecade(BigInteger num)
    {
        return num.divide(BigInteger.TEN);
    }
    
    /**
     * gets the index of the integer.
     * @param num being given
     * @return the index.
     */
    private int getIdx(BigInteger num)
    {
        int right = num.remainder(BigInteger.TEN).intValue();
        if (right == 1)
        {
            return 0;
        }
        else if (right == 3)
        {
            return 1;
        }
        else if (right == 7)
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }
    
    /**
     * Adds the anti-prime to the map.
     * @param num the anti to be added.
     */
    private void addAnti(BigAntiPrime num)
    {
        int idx = getIdx(num.getValue());
        BigInteger dec = convertToDecade(num.getValue());
        while (antiPrimes.containsKey(dec) && antiPrimes.get(dec)[idx] != null)
        {
            num.increase();
            dec = convertToDecade(num.getValue());
        }
        if (!antiPrimes.containsKey(dec))
        {
            antiPrimes.put(dec, new BigAntiPrime[4]);
        }
        antiPrimes.get(dec)[idx] = num;
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
        addAnti(new BigAntiPrime(BigInteger.valueOf(3), BigInteger.valueOf(30)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(9), BigInteger.valueOf(30)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(21), BigInteger.valueOf(30)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(27), BigInteger.valueOf(30)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(7), BigInteger.valueOf(70)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(21), BigInteger.valueOf(70)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(49), BigInteger.valueOf(70)));
        addAnti(new BigAntiPrime(BigInteger.valueOf(63), BigInteger.valueOf(70)));
    }
    
    /**
     * Completes a single iteration of the 
     * current decade being inspected.
     */
    private void singleIteration()
    {
        if (antiPrimes.containsKey(numDecade))
        {
            if (antiPrimes.get(numDecade)[currIdx] != null)
            {
                currDecade[currIdx] = BigInteger.ZERO;
            }
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
        addAnti(new BigAntiPrime(value, value1.multiply(BigInteger.TEN)));
        if (!same)
        {
            addAnti(new BigAntiPrime(value, value2.multiply(BigInteger.TEN)));
        }
    }
    
    /**
     * Adds the leftover numbers in decade to the list of primes.
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
     * updates all the current decade anti-primes.
     */
    private void updateAntiPrimes()
    {
        if (antiPrimes.containsKey(numDecade))
        {
            for (int i = 0; i < 4; i++)
            {
                if (antiPrimes.get(numDecade)[i] != null)
                {
                    addAnti(antiPrimes.get(numDecade)[i].increase());
                }
            }
            antiPrimes.remove(numDecade);
        }
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
        updateAntiPrimes();
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
