package com.felixlimanta.VigenerePlayfair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextScorer {
  private static double[] tetragraphScores;
  private static boolean scoreLoaded = false;

  static {
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(
            TextScorer.class.getClassLoader().getResourceAsStream(
                "com/felixlimanta/VigenerePlayfair/tetragraph.txt"
            )
        )
    )) {
      tetragraphScores = br.lines()
          .mapToDouble(Double::valueOf)
          .toArray();
      scoreLoaded = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static double scoreText(String text) {
    double score = 0;
    for (int i = 0; i < text.length() - 3; ++i) {
      int[] temp = new int[4];
      temp[0] = text.charAt(i)-'A';
      temp[1] = text.charAt(i + 1)-'A';
      temp[2] = text.charAt(i + 2)-'A';
      temp[3] = text.charAt(i + 3)-'A';
      score += tetragraphScores[17576 * temp[0] + 676 * temp[1] + 26 * temp[2] + temp[3]];
    }
    return score;
  }
}
