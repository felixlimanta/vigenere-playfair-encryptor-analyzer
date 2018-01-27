package com.felixlimanta.VigenerePlayfair;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VigenereCipherTest {

  @Test
  public void setKey() throws Exception {
    String key = "ABCDEabcde12345";
    VigenereCipher cipher = new VigenereCipher(key);
    assertEquals("ABCDEABCDE", cipher.getKey());
  }

  @Test
  public void encrypt() throws Exception {
    VigenereCipher cipher = new VigenereCipher("VIGENERECIPHER");
    assertEquals(
        "WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY",
        cipher.encrypt("Beware the Jabberwock, my son! The jaws that bite, the claws that catch!", false)
            .replaceAll("[^A-Z]", "")
    );
  }

  @Test
  public void decrypt() throws Exception {
    VigenereCipher cipher = new VigenereCipher("VIGENERECIPHER");
    assertEquals(
        "BEWARETHEJABBERWOCKMYSONTHEJAWSTHATBITETHECLAWSTHATCATCH",
        cipher.decrypt("WMCEEIKLGRPIFVMEUGXQPWQVIOIAVEYXUEKFKBTALVXTGAFXYEVKPAGY", false)
    );
  }

  @Test
  public void fullEncryptDecrypt() throws Exception {
    VigenereCipher cipher = new VigenereCipher("VIGENERECIPHER");

    String text = "BEWARETHEJABBERWOCKMYSONTHEJAWSTHATBITETHECLAWSTHATCATCH";
    String encrypted = cipher.encrypt("BEWARETHEJABBERWOCKMYSONTHEJAWSTHATBITETHECLAWSTHATCATCH", true);
    String processed = cipher.decrypt(encrypted,true);

    assertEquals(text, processed);
  }
}