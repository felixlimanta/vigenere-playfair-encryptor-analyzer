import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AlphabeticVigenereCipherTest {

  @Test
  public void setKey() throws Exception {
    String key = "ABCDEabcde12345";
    AlphabeticVigenereCipher cipher = new AlphabeticVigenereCipher(key);
    assertEquals("ABCDEABCDE", cipher.getKey());
  }

  @Test
  public void encrypt() throws Exception {
    AlphabeticVigenereCipher cipher = new AlphabeticVigenereCipher("VIGENERECIPHER");
    assertEquals(
        "WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY",
        cipher.encrypt("Beware the Jabberwock, my son! The jaws that bite, the claws that catch!")
            .replaceAll("[^A-Z]", "")
    );
  }

  @Test
  public void decrypt() throws Exception {
    AlphabeticVigenereCipher cipher = new AlphabeticVigenereCipher("VIGENERECIPHER");
    assertEquals(
        "BEWARETHEJABBERWOCKMYSONTHEJAWSTHATBITETHECLAWSTHATCATCH",
        cipher.decrypt("WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY")
    );
  }
}