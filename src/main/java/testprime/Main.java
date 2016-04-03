package testprime;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
  static final String PRIME_FILES = "primeFiles";
  private static final Logger LOGGER = LogManager.getLogger(Main.class);


  public static void main(String[] args) throws IOException {
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = new BigInteger("2");

    Stream.iterate(1, i -> i + 1).limit(2).forEach(i -> {
        try {
          ThreadContext.push("index " + i);
          wrapper.value = doTest(i, wrapper.value);
        } catch (IOException e) {
          e.printStackTrace();
          System.exit(0);
        } finally {
          ThreadContext.pop();
        }
      }
    );
  }


  static BigInteger doTest(int index, BigInteger initialNumber) throws IOException {
    downloadPrimeZip(index);
    extractPrimeFile(index);

    final Path path = Paths.get(String.format("%s/primes%d.txt", PRIME_FILES, index));
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = initialNumber;
    LOGGER.info(String.format("start for %d", index));
    try (BufferedReader primes = Files.newBufferedReader(path)) {
      primes.readLine();
      primes.lines()
        .flatMap(s -> Arrays.stream(s.split("\\s++")))
        .filter(s -> !s.isEmpty())
        .map(BigInteger::new)
        .peek(bi -> {
          if (bi.divide(BigInteger.TEN).toString().endsWith("12345")) {
            LOGGER.info(bi);
          }
        }).forEach(bi -> {
          if (!bi.equals(wrapper.value)) {
            LOGGER.info(String.format("BigInteger prime %s and true prime %s is not same", wrapper.value, bi));
            System.exit(0);
          }
          wrapper.value = wrapper.value.nextProbablePrime();
        }
      );
    }
    LOGGER.info(String.format("end for %d. the last prime is %s", index, wrapper.value));
    return wrapper.value;
  }

  /*
  * http://www.utm.edu/~caldwell/primes/millions/primes1.zip
  * */
  static void downloadPrimeZip(int index) throws IOException {
    Path dest = Paths.get(String.format("%s/primes%d.zip", PRIME_FILES, index));
    if (dest.toFile().exists()) {
      LOGGER.info(dest + " is already exists.");
      return;
    }
    LOGGER.info(dest + " not found. try to download");
    URL url = new URL(String.format("http://www.utm.edu/~caldwell/primes/millions/primes%d.zip", index));
    try (InputStream is = url.openStream()) {
      Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
    }
    LOGGER.info("download finished");
  }

  static void extractPrimeFile(int index) throws IOException {
    Path dest = Paths.get(String.format("%s/primes%d.txt", PRIME_FILES, index));
    if (dest.toFile().exists()) {
      LOGGER.info(dest + " is already exists.");
      return;
    }
    LOGGER.info(dest + " not found. try to extract");

    Path zipPath = Paths.get(String.format("%s/primes%d.zip", PRIME_FILES, index));

    try (FileSystem fs = FileSystems.newFileSystem(zipPath, ClassLoader.getSystemClassLoader())) {
      Path path = fs.getPath(String.format("primes%d.txt", index));
      try (InputStream is = Files.newInputStream(path)) {
        Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
      }
    }
    LOGGER.info("extract finished");

  }

}

class BigIntegerWrapper {
  BigInteger value;

}


