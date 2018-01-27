public class AlphabeticVigenereCipher {

  private String key;
  private StringBuilder res;

  private Operation encipherOp =
      (text, key) -> (char) ((text + key - 2 * 'A') % 26 + 'A');
  private Operation decipherOp =
      (text, key) -> (char) ((text - key + 26) % 26 + 'A');

  public AlphabeticVigenereCipher() { }

  public AlphabeticVigenereCipher(String key) {
    setKey(key);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key.toUpperCase().replaceAll("[^A-Z]", "");
  }

  public String encipher(String text) {
    return processVigenere(text, encipherOp);
  }

  public String decipher(String text) {
    return processVigenere(text, decipherOp);
  }

  private String processVigenere(String text, Operation operation) {
    res = new StringBuilder();
    text = text.toUpperCase();

    for (int i = 0, j = 0; i < text.length(); ++i) {
      char c = text.charAt(i);

      if (Character.isLetter(c)) {
        res.append(operation.operate(c, key.charAt(j)));
        j = (j + 1) % key.length();
      } else {
        res.append(c);
      }
    }

    return res.toString();
  }

  private interface Operation {
    char operate(char text, char key);
  }
}
