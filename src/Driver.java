package com.felixlimanta.VigenerePlayfair;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Driver {

  private static Scanner in;

  public static void main(String[] args) throws Exception {
    in = new Scanner(System.in);
    int alg = 0;
    int method = 0;

    while (alg < 1 || alg > 3) {
      System.out.println("Select algorithm: ");
      System.out.println("1. Vigenere cipher (Alphabets only)");
      System.out.println("2. Vigenere cipher (Full)");
      System.out.println("3. Playfair cipher");
      System.out.print("Algorithm: ");
      alg = in.nextInt();
      System.out.println();
    }

    while (method < 1 || method > 2) {
      System.out.println("Select method: ");
      System.out.println("1. Encrypt");
      System.out.println("2. Decrypt");
      System.out.print("Method: ");
      method = in.nextInt();
      System.out.println();
      in.nextLine();
    }

    System.out.println("Enter path to file. Leave blank to enter text from console.");
    System.out.print("Path to file: ");
    String path = in.nextLine();
    String text = "";

    if (!path.equals("")) {
      text = readFile(path, Charset.defaultCharset());
      System.out.println("Text:");
      System.out.println(text);
      System.out.println();
    } else {
      System.out.println("Enter newline-separated text:");
      text = in.nextLine();
    }
    System.out.println();

    System.out.print("Enter key: ");
    String key = in.nextLine();

    String result = "";
    switch (alg) {
      case 1:
        if (method == 1)
          result = new VigenereCipher(key).encrypt(text, false);
        else
          result = new VigenereCipher(key).decrypt(text, false);
        break;

      case 2:
        if (method == 1)
          result = new VigenereCipher(key).encrypt(text, true);
        else
          result = new VigenereCipher(key).decrypt(text, true);
        break;

      case 3:
        if (method == 1)
          result = new PlayfairCipher(key).encrypt(text);
        else
          result = new PlayfairCipher(key).decrypt(text);
        break;
    }

    System.out.println("Result (plaintext format):");
    System.out.println(result);
    System.out.println();

    String strippedResult = stripNonalphabeticChars(result);
    System.out.println("Result (letters only):");
    System.out.println(strippedResult);
    System.out.println();

    String groupedResult = insertSpaces(strippedResult, 5);
    System.out.println("Result (grouped):");
    System.out.println(groupedResult);
    System.out.println();

    System.out.print("Save ciphertext? [y/N] ");
    String save = in.nextLine();
    if (!save.equals("") && (save.charAt(0) == 'Y' || save.charAt(0) == 'y')) {
      System.out.print("Path to file: ");
      path = in.nextLine();
      try (PrintWriter out = new PrintWriter(path, "UTF-8")) {
        out.write(result);
      }
    }
  }

  private static String readFile(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  private static String stripNonalphabeticChars(String text) {
    return text.toUpperCase()
        .replaceAll("[^A-Z]", "");
  }

  private static String insertSpaces(String text, int interval) {
    StringBuilder sb = new StringBuilder(text);
    int length = text.length() / 5 * 6;
    for (int i = interval; i < length; i += (interval + 1)) {
      sb.insert(i, ' ');
    }
    return sb.toString();
  }
}
