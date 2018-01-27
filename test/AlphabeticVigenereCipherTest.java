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
  public void encipher() throws Exception {
    AlphabeticVigenereCipher cipher = new AlphabeticVigenereCipher("VIGENERECIPHER");
    assertEquals(
        "WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY",
        cipher.encipher("Beware the Jabberwock, my son! The jaws that bite, the claws that catch!")
            .replaceAll("[^A-Z]", "")
    );
  }

  @Test
  public void decipher() throws Exception {
    AlphabeticVigenereCipher cipher = new AlphabeticVigenereCipher("VIGENERECIPHER");
    assertEquals(
        "BEWARETHEJABBERWOCKMYSONTHEJAWSTHATBITETHECLAWSTHATCATCH",
        cipher.decipher("WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY")
    );
  }
}