/**
 * 
 */
package primeGenerator;

import java.util.LinkedList;
import java.util.List;
import java.math.BigInteger;

/**
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.03.25
 */
public class TryingCheap {

    private List<BigInteger> primes;
    BigInteger currNum;
    
    public TryingCheap()
    {
        primes = new LinkedList<BigInteger>();
        primes.add(BigInteger.valueOf(2));
        primes.add(BigInteger.valueOf(3));
        primes.add(BigInteger.valueOf(5));
        primes.add(BigInteger.valueOf(7));
        currNum = BigInteger.valueOf(8);
    }
    
    public void solve()
    {
        boolean found = false;
        for (BigInteger val: primes)
        {
            if (currNum.remainder(val).equals(BigInteger.ZERO))
            {
                found = true;
            }
        }
        if (!found)
        {
            System.out.println(currNum);
            primes.add(currNum);
        }
        currNum = currNum.add(BigInteger.ONE);
    }
    
}
