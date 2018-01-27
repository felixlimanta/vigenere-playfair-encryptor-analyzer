import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javafx.util.Pair;

public class PlayfairCipher {

  private final static char pad = 'X';
  private final static char missingLetter = 'J';
  private final static char replacementLetter = 'I';

  private String key;
  private char[][] table;
  private int length;
  private Queue<Integer> padList;

  public PlayfairCipher(String key) {
    setKey(key);
  }

  public void setKey(String key) {
    this.key = preprocessText(key);

    // Remove duplicate letters
    Set<Character> charSet = new LinkedHashSet<>();
    for (char c: this.key.toCharArray()) {
      charSet.add(c);
    }

    // Add non-duplicates to queue
    Queue<Character> queue = new LinkedList<>();
    queue.addAll(charSet);

    // Add rest of characters to queue
    for (char c = 'A'; c <= 'Z'; ++c) {
      if (!charSet.contains(c) && c != missingLetter) {
        queue.add(c);
      }
    }

    // Build table
    table = new char[5][5];
    for (int i = 0; i < 5; ++i) {
      for (int j = 0; j < 5; ++j) {
        table[i][j] = queue.remove();
      }
    }
  }

  public String encrypt(String text) {
    String encoded = encodeAsDigraph(text);
    String encrypted = processDigraph(encoded, true);
    return recoverTextFormat(text, encoded, encrypted);
  }

  public String decrypt(String text) {
    String encoded = encodeAsDigraph(text);
    String encrypted = processDigraph(encoded, false);
    return recoverTextFormat(text, encoded, encrypted);
  }

  private String encodeAsDigraph(String text) {
    StringBuilder sb = new StringBuilder(preprocessText(text));
    padList = new LinkedList<>();

    // Insert pad between double-letter digraphs and last digraph (if needed)
    for (int i = 0; i < sb.length() - 1; i += 2) {
      if (sb.charAt(i) == sb.charAt(i + 1)) {
        sb.insert(i + 1, pad);
        padList.add(i + 1);
      }
    }
    if (sb.length() % 2 == 1) {
      sb.append(pad);
      padList.add(sb.length() - 1);
    }
    padList.add(-1);

    return sb.toString();
  }

  private String processDigraph(String text, boolean encrypt) {
    StringBuilder res = new StringBuilder();
    int offset = encrypt ? 1 : 4;

    for (int i = 0; i < text.length(); i += 2) {
      char a = text.charAt(i);
      char b = text.charAt(i + 1);

      Pair<Integer, Integer> p1 = getPoint(a);
      Pair<Integer, Integer> p2 = getPoint(b);
      if (p1 == null || p2 == null)
        return null;

      int r1 = p1.getKey();
      int c1 = p1.getValue();
      int r2 = p2.getKey();
      int c2 = p2.getValue();

      if (r1 == r2) {
        // Case 1: Same row -> shift right/left
        c1 = (c1 + offset) % 5;
        c2 = (c2 + offset) % 5;
      } else if (c1 == c2) {
        // Case 2: Same column -> shift down/up
        r1 = (r1 + offset) % 5;
        r2 = (r2 + offset) % 5;
      } else {
        // Case 3: Digraph forms rectangle -> swap columns
        int temp = c1;
        c1 = c2;
        c2 = temp;
      }

      // Look up character and add to result
      res.append(table[r1][c1]).append(table[r2][c2]);
    }
    return res.toString();
  }

  private Pair<Integer, Integer> getPoint(char c) {
    for (int i = 0; i < 5; ++i) {
      for (int j = 0; j < 5; ++j) {
        if (c == table[i][j])
          return new Pair<>(i, j);
      }
    }
    return null;
  }

  private String preprocessText(String text) {
    return text.toUpperCase()
        .replaceAll("[^A-Z]", "")
        .replace(Character.toString(missingLetter), Character.toString(replacementLetter));
  }

  private String recoverTextFormat(String plain, String encoded, String encrypted) {
    StringBuilder res = new StringBuilder();
    plain = plain.toUpperCase();
    int i, j, padLoc;

    padLoc = padList.remove();
    for (i = 0, j = 0; i < plain.length() && j < encrypted.length(); i++, j++) {
      if (j == padLoc) {
        res.append(encrypted.charAt(j++));
        padLoc = padList.remove();
      }
      while (i < plain.length() && !Character.isLetter(plain.charAt(i))) {
        res.append(plain.charAt(i++));
      }
      if (i < plain.length() && j < encrypted.length())
        res.append(encrypted.charAt(j));
    }
    while (i < plain.length() && !Character.isLetter(plain.charAt(i))) {
      res.append(plain.charAt(i++));
    }
    return res.toString();
  }
}
