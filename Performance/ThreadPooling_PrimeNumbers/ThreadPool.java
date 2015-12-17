import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    public static final int POOL_SIZE = 10;

    /**
     * Get random number between min and max
     */
    static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        // get prime numbers between min and max
        int min = 10000;
        int max = 100000;
        // execute 50 workers, only 10 threads will be running at a time
        for (int i = 0; i < 50; i++) {
            int limit = getRandom(min, max);
            Runnable worker = new GreatestPrimeWorker(limit);
            executor.execute(worker);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("Done");
    }
}

class GreatestPrimeWorker implements Runnable {

    private int limit;

    /**
     * Worker calculates the greatest prime number below limit
     */
    public GreatestPrimeWorker(int limit) {
        this.limit = limit;
    }

    /**
     * Returns true if n is a prime number
     */
    boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int d = 2; d < n; d++) {
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns greatest prime number below limit
     */
    int greatestPrimeBelow(int limit) {
        int max = 0;
        for (int i = 2; i < limit; i++) {
            if (isPrime(i)) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().getId() % ThreadPool.POOL_SIZE + 1;
        System.out.println("[Thread #" + threadId + "] Running task for limit " + this.limit);
        int max = greatestPrimeBelow(this.limit);
        System.out.println("[Thread #" + threadId + "] Returned greatest prime number below " + this.limit + ": " + max);
    }
}