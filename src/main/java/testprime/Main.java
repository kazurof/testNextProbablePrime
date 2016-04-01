package testprime;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
  static final String WORK_DIR = "work";

  public static void main(String[] args) {

    String start = "15485867";
    int index = 2;

    final Path path = Paths.get(String.format("primes%d/primes%d.txt", index, index));
    BigIntegerWrapper wrapper = new BigIntegerWrapper();
    wrapper.value = new BigInteger(start);
    System.out.println("aaa");
    try (BufferedReader csv = Files.newBufferedReader(path)) { // UTF-8
      csv.readLine();
      csv.lines().flatMap(s -> Arrays.stream(s.split("\\s++"))).map(s -> s.trim()).filter(s -> !s.isEmpty())
        .forEach(s -> {
            BigInteger target = new BigInteger(s);

            System.out.println(s);
            if (!target.equals(wrapper.value)) {
              System.out.printf("BigInteger prime %s and true prime %s is not same", wrapper.value, target);
              System.exit(0);
            }
            wrapper.value = wrapper.value.nextProbablePrime();
          }
        );

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("bbb");

//    Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> {
//        downloadPrimeZip(i);
//        extractPrimeFile(i);
//      }
//    );
//    BigInteger bi = BigInteger.ONE.add(BigInteger.ONE);
//    Stream.iterate(bi, i -> i.nextProbablePrime()).limit(100).forEach(System.out::println);
  }

  /*
  * https://primes.utm.edu/lists/small/millions/primes1.zip
  * http://www.utm.edu/~caldwell/primes/millions/primes1.zip
  * */
  static void downloadPrimeZip(int index) throws MalformedURLException, IOException {
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
