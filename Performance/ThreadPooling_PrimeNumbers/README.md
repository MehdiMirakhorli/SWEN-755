# thread-pooling

How to run:
```
javac ThreadPool.java
java ThreadPool
```

The program calculates the greatest prime number below a certain number (For example, the greatest prime number below 20 is 19). 50 random requests will be issued, but only 10 threads will work on them at any moment, other requests will wait for thread availability. The program takes a few seconds to terminate.
