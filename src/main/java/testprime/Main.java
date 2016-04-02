package testprime;

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
  static final String WORK_DIR = "work";

  public static void main(String[] args) throws IOException {
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = new BigInteger("2");

    Stream.iterate(1, i -> i + 1).limit(1).forEach(i -> {
        try {
          wrapper.value = doTest(i, wrapper.value);
        } catch (IOException e) {
          e.printStackTrace();
          System.exit(0);
        }
      }
    );
  }


  static BigInteger doTest(int index, BigInteger initialNumber) throws IOException {
    downloadPrimeZip(index);
    extractPrimeFile(index);

    final Path path = Paths.get(String.format("primes%d.txt", index));
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = initialNumber;
    System.out.printf("start for %d%n", index);
    try (BufferedReader csv = Files.newBufferedReader(path)) {
      csv.readLine();
      csv.lines()
        .flatMap(s -> Arrays.stream(s.split("\\s++")))
        .map(s -> s.trim())
        .filter(s -> !s.isEmpty())
        .map(BigInteger::new)
        .peek(bi -> {
          if (bi.divide(BigInteger.TEN).toString().endsWith("1234")) {
            System.out.println(bi);
          }
        }).forEach(bi -> {
          if (!bi.equals(wrapper.value)) {
            System.out.printf("BigInteger prime %s and true prime %s is not same%n", wrapper.value, bi);
            System.exit(0);
          }
          wrapper.value = wrapper.value.nextProbablePrime();
        }
      );
    }
    System.out.printf("end for %d%n", index);
    return wrapper.value;
  }

  /*
  * https://primes.utm.edu/lists/small/millions/primes1.zip
  * http://www.utm.edu/~caldwell/primes/millions/primes1.zip
  * */
  static void downloadPrimeZip(int index) throws IOException {
    Path dest = Paths.get(String.format("primes%d.zip", index));
    if (dest.toFile().exists()) {
      System.out.println(dest + " is already exists.");
      return;
    }
    System.out.println(dest + " not found. try to download");
    URL url = new URL(String.format("http://www.utm.edu/~caldwell/primes/millions/primes%d.zip", index));
    try (InputStream is = url.openStream()) {
      Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
    }
    System.out.println("download finished");
  }

  static void extractPrimeFile(int index) throws IOException {
    Path dest = Paths.get(String.format("primes%d.txt", index));
    if (dest.toFile().exists()) {
      System.out.println(dest + " is already exists.");
      return;
    }
    System.out.println(dest + " not found. try to extract");

    Path zipPath = Paths.get(String.format("primes%d.zip", index));

    try (FileSystem fs = FileSystems.newFileSystem(zipPath, ClassLoader.getSystemClassLoader())) {
      Path path = fs.getPath(String.format("/primes%d.txt", index));
      try (InputStream is = Files.newInputStream(path)) {
        Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
      }
    }
    System.out.println("extract finished");

  }

}

class BigIntegerWrapper {
  BigInteger value;

}
