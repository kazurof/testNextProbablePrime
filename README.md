# testNextProbablePrime

This repository containts some source code to study [BigInteger#nextProbablePrime()](https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html#nextProbablePrime--)

Presentation slide is http://www.slideshare.net/kazurof/nextprobableprime


testprime.Main will download prime list from "http://www.utm.edu/~caldwell/primes/millions/primes1.zip" and
compare to result of nextProbablePrime(). This will success and it will take almost one hours.


testprime.TestOneFile will compare result in primelist.txt and nextProbablePrime(). You can put any prime number to it,
and you can test. if you put primes number up to 10 billion , This code will success and takes around 17 hours and more.

And you will see some small methods under /src/test/java/testprime to check behavior of nextProbablePrime().


