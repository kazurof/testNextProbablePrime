package testprime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestOneFile {
  private static final Logger LOGGER = LogManager.getLogger(TestOneFile.class);

  public static void main(String[] args) throws IOException {
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = new BigInteger("2");
    wrapper.count = BigInteger.ONE;
    final Path path = Paths.get("primelist.txt");
    try (BufferedReader primes = Files.newBufferedReader(path)) {
      primes.lines().forEach(s -> {
        BigInteger prime = new BigInteger(s);
        if (prime.divide(BigInteger.TEN).toString().endsWith("12345")) {
          LOGGER.info(prime + "  count -> " + wrapper.count);
        }
        if (!wrapper.value.equals(prime)) {
          LOGGER.info(String.format("BigInteger prime %s and true prime %s is not same", wrapper.value, prime));
          System.exit(0);
        }
        wrapper.value = wrapper.value.nextProbablePrime();
        wrapper.count = wrapper.count.add(BigInteger.ONE);
      });
    }
    LOGGER.info("Finished!! nextProbablePrime() passed all given primes!");
    LOGGER.info("last value is " + wrapper.value);
    LOGGER.info("last count is " + wrapper.count);
  }
}
