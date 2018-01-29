package com.felixlimanta.VigenerePlayfair;

public class FullVigenereCipher implements Cipher {

  private String key;

  private final static Operation encipherOp =
      (text, key) -> (char) ((text + key) % 256);
  private final static Operation decipherOp =
      (text, key) -> (char) ((text - key + 256) % 256);

  public FullVigenereCipher(String key) {
    setKey(key);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String encrypt(String text) {
    return processVigenere(text, encipherOp);
  }

  public String decrypt(String text) {
    return processVigenere(text, decipherOp);
  }

  private String processVigenere(String text, Operation operation) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0, j = 0; i < text.length(); ++i, j = (j + 1) % key.length()) {
      char c = text.charAt(i);
      sb.append(operation.operate(c, key.charAt(j)));
    }
    return sb.toString();
  }
}
