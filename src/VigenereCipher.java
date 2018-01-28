package com.felixlimanta.VigenerePlayfair;

public class VigenereCipher {

  private String key;
  private StringBuilder sb;

  private final static Operation encipherOp =
      (text, key) -> (char) ((text + key - 2 * 'A') % 26 + 'A');
  private final static Operation decipherOp =
      (text, key) -> (char) ((text - key + 26) % 26 + 'A');


  public VigenereCipher(String key) {
    setKey(key);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key.toUpperCase().replaceAll("[^A-Z]", "");
  }

  public String encrypt(String text) {
    return processVigenere(text, encipherOp);
  }

  public String decrypt(String text) {
    return processVigenere(text, decipherOp);
  }

  private String processVigenere(String text, Operation operation) {
    sb = new StringBuilder();
    text = text.toUpperCase();

    for (int i = 0, j = 0; i < text.length(); ++i) {
      char c = text.charAt(i);

      if (Character.isLetter(c)) {
        sb.append(operation.operate(c, key.charAt(j)));
        j = (j + 1) % key.length();
      } else {
        sb.append(c);
      }
    }

    return sb.toString();
  }

  private interface Operation {
    char operate(char text, char key);
  }

}
