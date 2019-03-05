package primeGenerator;

/**
 * The main class for the primeGenerator package.
 * NOTE: PROGRAM BROKE AT 46,350.
 * REASON: 46,350^2 is larger than the maximum integer value.
 * NOTE: Will construct a new program utilizing 
 * LinkedLists and BigIntegers.
 * @author Wyatt Muller (wtmuller11@gmail.com)
 * @version 2019.02.23
 */

public class ProjectRunner {

    /**
     * The main method.
     * @param args should be none.
     */
    public static void main(String[] args) {
        
        //InfiniteGenerator alpha = new InfiniteGenerator();
        /*
        InfiniteCapacityGenerator alpha = new InfiniteCapacityGenerator();
        while (true)
        {
            alpha.decadeIteration();
        }
        */
        //System.out.println(alpha);
        /*
        EfficientInfiniteCapacityGenerator beta = new EfficientInfiniteCapacityGenerator();
        
        for (int i = 0; i < 10; i++)
        {
            beta.decadeIteration();
        }
        
        while (true)
        {
            beta.decadeIteration();
        }
        */        
        HashedInfiniteCapacityGenerator omega = new HashedInfiniteCapacityGenerator();
        /*
        for (int i = 0; i < 10; i++)
        {
            omega.decadeIteration();
        }
        */
        
        while (true)
        {
            omega.decadeIteration();
        }
        
    }

}
