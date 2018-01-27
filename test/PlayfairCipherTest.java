package com.felixlimanta.VigenerePlayfair;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class PlayfairCipherTest {

  private static final String[] plain = {
      "Three rings for the elven-kings under the skyX.".toUpperCase(),
      "Seven for the dwarf lords in their halXls of stoneX.".toUpperCase(),
      "Nine for the mortal men doXomed to dieX.".toUpperCase(),
      "One, for the Dark Lord in his dark throne in the land of Mordor where shadows lie.".toUpperCase(),
  };
  private static final String[] cipher = {
      "Siubb uhohr ils ukc apzap-hhohr speas ukc uhzY.".toUpperCase(),
      "Uczal hmt sia evbqg mptbt ho skcgt fcnVnq li tupocZ.".toUpperCase(),
      "Ohpc ils ukc npsufq pbo cnYpnae yt iocZ.".toUpperCase(),
      "Poa, kmt sia Ebqf Pmti os nht ebug sitmpc ho sia pcli tg Lmtitw bkcub xnbemyq nkd.".toUpperCase(),
  };

  @Test
  public void encrypt() throws Exception {
    PlayfairCipher pc = new PlayfairCipher("abcdefg");
    String[] res = new String[4];
    for (int i = 0; i < 4; ++i) {
      res[i] = pc.encrypt(plain[i]);
    }
    assertArrayEquals(cipher, res);
  }

  @Test
  public void decrypt() throws Exception {
    PlayfairCipher pc = new PlayfairCipher("abcdefg");
    String[] res = new String[4];
    for (int i = 0; i < 4; ++i) {
      res[i] = pc.decrypt(cipher[i]);
    }
    assertArrayEquals(plain, res);
  }
}