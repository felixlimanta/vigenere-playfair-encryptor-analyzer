package com.felixlimanta.VigenerePlayfair;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PlayfairCracker extends PlayfairCipher {

  public final static int TEMP = 100;
  public final static double STEP = 2;
  public final static int COUNT = 10000;

  private static SecureRandom random;

  private String cipherText;
  private String plainText;

  static {
    try {
      random = SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      random = new SecureRandom();
    }
  }

  public PlayfairCracker(String cipherText) {
    super("");
    this.cipherText = encodeAsDigraph(cipherText);
  }

  public String getCipherText() {
    return cipherText;
  }

  public void setCipherText(String cipherText) {
    this.cipherText = cipherText;
  }

  public String getPlainText() {
    return plainText;
  }

  public double crack() {
    char[][] maxTable = new char[5][5];
    char[][] bestTable = new char[5][5];
    copyTable(table, maxTable);
    copyTable(table, bestTable);

    plainText = decryptEncoded(cipherText);
    double maxScore = TextScorer.scoreText(plainText);
    double bestScore = maxScore;

    int count = 0;
    for (double T = TEMP; T >= 0; T -= STEP) {
      double prevBestScore = bestScore;

      for (int i = 0; i < COUNT; ++i) {
        copyTable(maxTable, table);
        modifyTable();

        plainText = decryptEncoded(cipherText);
        double score = TextScorer.scoreText(plainText);

        double dF = score - maxScore;
        if (dF >= 0) {
          maxScore = score;
          copyTable(table, maxTable);
        } else {
          if (T > 0) {
            double p = Math.exp(dF / T);
            if (p > random.nextDouble()) {
              maxScore = score;
              copyTable(table, maxTable);
            }
          }
        }

        if (maxScore > bestScore) {
          bestScore = maxScore;
          copyTable(maxTable, bestTable);
        }
      }

      if (bestScore == prevBestScore) {
        count++;
        if (count >= 10)
          break;
      } else {
        count = 0;
      }
    }
    copyTable(bestTable, table);
    plainText = decryptEncoded(cipherText);
    return bestScore;
  }

  private void modifyTable() {
    switch (getRandomInt(50)) {
      case 0: swapTwoRows(); break;
      case 1: swapTwoColumns(); break;
      case 2: reverseTable(); break;
      case 3: flipTableRows(); break;
      case 4: flipTableColumns(); break;
      default: swapTwoLetters();
    }
  }

  private void swapTwoLetters() {
    int x1 = getRandomInt(5), y1 = getRandomInt(5);
    int x2 = getRandomInt(5), y2 = getRandomInt(5);
    char temp = table[x1][y1];
    table[x1][y1] = table[x2][y2];
    table[x2][y2] = temp;
  }

  private void swapTwoRows() {
    int x1 = getRandomInt(5), x2 = getRandomInt(5);
    for (int i = 0; i < 5; ++i) {
      char temp = table[x1][i];
      table[x1][i] = table[x2][i];
      table[x2][i] = temp;
    }
  }

  private void swapTwoColumns() {
    int y1 = getRandomInt(5), y2 = getRandomInt(5);
    for (int i = 0; i < 5; ++i) {
      char temp = table[i][y1];
      table[i][y1] = table[i][y2];
      table[i][y2] = temp;
    }
  }

  private void reverseTable() {
    for (int i = 0; i <= 2; ++i) {
      for (int j = 0; j < 5; ++j) {
        char temp = table[i][j];
        table[i][j] = table[4-i][4-j];
        table[4-i][4-j] = temp;
      }
    }
  }

  private void flipTableRows() {
    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < 5; ++j) {
        char temp = table[i][j];
        table[i][j] = table[4-i][j];
        table[4-i][j] = temp;
      }
    }
  }

  private void flipTableColumns() {
    for (int i = 0; i < 5; ++i) {
      for (int j = 0; j < 2; ++j) {
        char temp = table[i][j];
        table[i][j] = table[i][4-j];
        table[i][4-j] = temp;
      }
    }
  }

  private static void copyTable(char[][] src, char[][] dest) {
    for (int i = 0; i < 5; ++i) {
      System.arraycopy(src[i], 0, dest[i], 0, 5);
    }
  }
  
  private static int getRandomInt(int bound) {
    int x = random.nextInt() % bound;
    if (x < 0)
      x += bound;
    return x;
  }
}
