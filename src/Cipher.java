package com.felixlimanta.VigenerePlayfair;

public interface Cipher {

  void setKey(String key);
  String getKey();

  String encrypt(String text);
  String decrypt(String text);

  interface Operation {
    char operate(char text, char key);
  }
}
