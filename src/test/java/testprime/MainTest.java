package testprime;

import org.junit.Test;

/*
 * This class is not intended to Continuous test.
 * just alternation of main() method to execute each method.
 */
public class MainTest {

  @Test
  public void testDownloadPrimeZip() throws Exception {
    Main.downloadPrimeZip(2);
  }

  @Test
  public void testExtractPrimeFile() throws Exception {
    Main.extractPrimeFile(2);
  }
}

