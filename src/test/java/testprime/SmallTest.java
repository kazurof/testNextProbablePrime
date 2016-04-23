package testprime;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.math.BigInteger;
import java.util.stream.IntStream;


public class SmallTest {
  static final Logger LOGGER = LogManager.getLogger(SmallTest.class);



  @Test
  public void test341IsPseudoPrime() {
    BigInteger bi1 = new BigInteger("2");
    BigInteger bi2 = new BigInteger("340");
    BigInteger bi3 = new BigInteger("341");
    System.out.println(bi1.modPow(bi2, bi3));
  }


  @Test
  public void testLargeComposite() {
    // this is index of Mersenne number.
    int[] exponents = new int[] {2, 3, 5, 7, 13, 17, 19, 31, 61, 89, 107, 127,
      521, 607, 1279, 2203, 2281, 3217, 4253, 4423, 9689, 9941, 11213, 19937,
      21701, 23209, 44497, 86243, 110503, 132049, 216091, 756839, 859433,
      1257787, 1398269, 2976221, 3021377, 6972593, 13466917, 20996011,
      24036583, 25964951, 30402457, 32582657, 37156667, 42643801, 43112609,
      57885161, 74207281};

    // you can change i to upto exponents.length - 1 but it will take very long time.
    for (int i = 0; i < 10; i++) {
      long start = System.currentTimeMillis();
      LOGGER.info(String.format("round %d / %d  " , i , exponents.length - 1));
      // generate 2^exponents[i] - 1
      BigInteger bi1 = BigInteger.ONE;
      bi1 = bi1.shiftLeft(exponents[i]);
      bi1 = bi1.subtract(BigInteger.ONE);
      LOGGER.info("first  " + bi1.bitLength());

      // generate 2^exponents[i+1] - 1
      BigInteger bi2 = BigInteger.ONE;
      bi2 = bi2.shiftLeft(exponents[i + 1]);
      bi2 = bi2.subtract(BigInteger.ONE);

      BigInteger target = bi1.multiply(bi2);

      LOGGER.info("target.bitLength()" + target.bitLength());
      boolean result = target.isProbablePrime(100);

      LOGGER.info(result);
      if(result){
        LOGGER.info(exponents[i] + " and " + exponents[i] +"  , isProbablePrime failed!!!");
      }
      long end = System.currentTimeMillis();
      long time = end - start ;
      LOGGER.info("time " + time +" milli sec");
    }
  }
}
