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
 * @version 2019.03.25
 */
public class EfficientHashedGenerator {

    private BigInteger[] currDecade;
    private int currIdx;
    private List<BigInteger> primes;
    private LinkedHashMap<BigInteger, BigInteger[]> antiPrimes;
    
    /**
     * Creates a new EfficientHashedGenerator object,
     * instantiates all fields,
     * adds starting primes, 
     */
    public EfficientHashedGenerator()
    {
        currDecade = new BigInteger[]{BigInteger.valueOf(11), BigInteger.valueOf(13),
                                        BigInteger.valueOf(17), BigInteger.valueOf(19)};
        currIdx = 0;
        primes = new LinkedList<BigInteger>();
        antiPrimes = new LinkedHashMap<BigInteger, BigInteger[]>(100000);
        addStartingNumbers();
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
        antiPrimes.put(BigInteger.valueOf(3), new BigInteger[] {BigInteger.valueOf(21), 
                                                                BigInteger.valueOf(3), 
                                                                BigInteger.valueOf(27), 
                                                                BigInteger.valueOf(9)});
        antiPrimes.put(BigInteger.valueOf(7), new BigInteger[] {BigInteger.valueOf(21), 
                                                                BigInteger.valueOf(63), 
                                                                BigInteger.valueOf(7), 
                                                                BigInteger.valueOf(49)});
    }
    
    /**
     * gets the index of the integer.
     * @param num being given
     * @return the index.
     */
    private int getIdx(BigInteger num)
    {
        int right = num.remainder(BigInteger.TEN).intValue();
        switch (right)
        {
            case 1:
                return 0;
            case 3:
                return 1;
            case 7:
                return 2;
            default:
                return 3;
        }
    }
    
    /**
     * Adds the anti-primes to the map.
     * @param num the anti to be added.
     */
    private void addPrime()
    {
        BigInteger[] list = new BigInteger[4];
        for (BigInteger num: currDecade)
        {
            BigInteger val = num.multiply(currDecade[currIdx]);
            int idx = getIdx(val);
            list[idx] = val;
        }
        antiPrimes.put(currDecade[currIdx], list);
        primes.add(currDecade[currIdx]);
    }
    
    /**
     * Checks to see if the current number is prime.
     * @return true if prime
     */
    private boolean checkPrime()
    {
        for (BigInteger key: antiPrimes.keySet())
        {
            BigInteger val = antiPrimes.get(key)[currIdx];
            if (val != null)
            {
                while (val.compareTo(currDecade[currIdx]) < 0)
                {
                    antiPrimes.get(key)[currIdx] = val.add(key.multiply(BigInteger.TEN));
                    val = antiPrimes.get(key)[currIdx];
                }
                if (val.equals(currDecade[currIdx]))
                {
                    antiPrimes.get(key)[currIdx] = val.add(key.multiply(BigInteger.TEN));
                    return false;
                }
            }
        }
        System.out.println(currDecade[currIdx]);
        return true;
    }
    
    /**
     * Completes a single iteration of the 
     * current decade being inspected.
     */
    private void singleIteration()
    {
        if (checkPrime())
        {
            addPrime();
        }
        currIdx++;
    }
    
    /**
     * Sets next decade to be inspected.
     */
    private void updateDecade()
    {
        currDecade[0] = currDecade[0].add(BigInteger.TEN);
        currDecade[1] = currDecade[1].add(BigInteger.TEN);
        currDecade[2] = currDecade[2].add(BigInteger.TEN);
        currDecade[3] = currDecade[3].add(BigInteger.TEN);
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
