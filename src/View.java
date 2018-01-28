package com.felixlimanta.VigenerePlayfair;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class View extends JFrame {

  private static final String[] algorithms = {
      "Vigenere cipher (alphabetic)",
      "Vigenere cipher (extended)",
      "Playfair cipher"
  };
  private static final String[] buttonLabels = {
      "Encrypt", "Save Encrypted File",
      "Decrypt", "Save Decrypted File"
  };

  private JPanel rootPanel;

  private JComboBox algorithmSelectorComboBox;
  private JRadioButton encryptRadio;
  private JRadioButton decryptRadio;
  private JCheckBox binaryCheckbox;

  private JButton openButton;
  private JLabel pathLabel;

  private JTextField keyField;

  private JPanel inputPanel;
  private JTextArea inputTextArea;
  private JButton processButton;

  private JPanel outputPanel;
  private JTabbedPane outputFormatTabs;
  private JTextArea preservedOutputTextArea;
  private JTextArea strippedOutputTextArea;
  private JTextArea groupedOutputTextArea;
  private JButton saveOutputButton;

  private JFileChooser fileChooser;
  private Border plaintextBorder;
  private Border ciphertextBorder;

  private boolean encrypt = true;

  private byte[] inputBytes;
  private byte[] outputBytes;
  private boolean binary = false;

  public static void main(String[] args) {
    new View();
  }

  public View() {
    super("Vigenere Cipher/Playfair Cipher Encryptor");
    setUpRadioListener();
    setUpBinarySelectorListener();
    setUpOpenButtonListener();
    setUpProcessButtonListener();
    setUpSaveButtonListener();

    setContentPane(rootPanel);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  private void createUIComponents() {
    algorithmSelectorComboBox = new JComboBox<>(algorithms);
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  }

  private void setUpRadioListener() {
    plaintextBorder = inputPanel.getBorder();
    ciphertextBorder = outputPanel.getBorder();
    encryptRadio.addActionListener(e -> {
      if (encryptRadio.isSelected()) {
        encrypt = true;
        inputPanel.setBorder(plaintextBorder);
        outputPanel.setBorder(ciphertextBorder);
        processButton.setText(buttonLabels[0]);
        saveOutputButton.setText(buttonLabels[1]);
      }
    });
    decryptRadio.addActionListener(e -> {
      if (decryptRadio.isSelected()) {
        encrypt = false;
        inputPanel.setBorder(ciphertextBorder);
        outputPanel.setBorder(plaintextBorder);
        processButton.setText(buttonLabels[2]);
        saveOutputButton.setText(buttonLabels[3]);
      }
    });
  }

  private void setUpBinarySelectorListener() {
    algorithmSelectorComboBox.addActionListener(e -> {
      boolean canBinary = algorithmSelectorComboBox.getSelectedIndex() == 1;
      binaryCheckbox.setEnabled(canBinary);
      if (!canBinary)
        binary = false;
    });
    binaryCheckbox.addActionListener(e -> {
      binary = binaryCheckbox.isSelected();
      inputPanel.setEnabled(!binary);
      inputTextArea.setEnabled(!binary);
      outputPanel.setEnabled(!binary);
      outputFormatTabs.setEnabled(!binary);
      preservedOutputTextArea.setEnabled(!binary);
      strippedOutputTextArea.setEnabled(!binary);
      groupedOutputTextArea.setEnabled(!binary);
    });
  }

  private void setUpOpenButtonListener() {
    openButton.addActionListener(e -> {
      fileChooser.setDialogTitle("Open encrypted file");
      int returnVal = fileChooser.showOpenDialog(rootPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String path = fileChooser.getSelectedFile().getPath();
        pathLabel.setText(path);
        inputTextArea.setText(loadFile(path));
      }
    });
  }

  private void setUpProcessButtonListener() {
    processButton.addActionListener(e -> {
      String key = keyField.getText();
      if (key.equals("")) {
        showError("Key cannot be empty.");
        return;
      }

      if (binary) {
        if (inputBytes == null || inputBytes.length == 0) {
          showError("Input not set. Load file first.");
          return;
        }
        outputBytes = processBinary(key, inputBytes);
      } else {
        String text = inputTextArea.getText();
        if (text.equals("")) {
          showError("Input not set. Load file or type it in the text area first.");
          return;
        }
        String preserved = processText(key, text);
        String stripped = stripNonalphabeticChars(preserved);
        String grouped = insertSpaces(stripped, 5);

        preservedOutputTextArea.setText(preserved);
        strippedOutputTextArea.setText(stripped);
        groupedOutputTextArea.setText(grouped);
        outputBytes = preserved.getBytes();
      }
      showInfo(encrypt ? "Encryption done" : "Decryption done");
    });
  }

  private void setUpSaveButtonListener() {
    saveOutputButton.addActionListener(e -> {
      String text = "";
      String path = "";

      if (binary) {
        if (outputBytes == null || outputBytes.length == 0) {
          showError("Nothiing to save. Encrypt file first.");
          return;
        }
      } else {
        switch (outputFormatTabs.getSelectedIndex()) {
          case 0: text = preservedOutputTextArea.getText(); break;
          case 1: text = strippedOutputTextArea.getText(); break;
          case 2: text = groupedOutputTextArea.getText(); break;
        }
        if (text.equals("")) {
          showError("Encrypted text area is empty. Encrypt text first.");
          return;
        }
      }

      fileChooser.setDialogTitle("Save encrypted file");
      int returnVal = fileChooser.showSaveDialog(rootPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getPath();
        if (binary)
          saveBinaryFile(path, outputBytes);
        else
          saveFile(path, text);
      }
      showInfo("File saved to " + path);
    });
  }

  private String loadFile(String path) {
    try {
      inputBytes = Files.readAllBytes(Paths.get(path));
      if (binary)
        return "";
      return new String(inputBytes, "UTF-16");
    } catch (Exception ex) {
      showError("File does not exist or cannot be opened.");
      return "";
    }
  }

  private void saveFile(String path, String text) {
    try (FileWriter fw = new FileWriter(path)) {
      fw.write(text);
    } catch (Exception ex) {
      showError("Error saving file to specified path.");
      ex.printStackTrace();
    }
  }

  private void saveBinaryFile(String path, byte[] contents) {
    try {
      Files.write(Paths.get(path), contents);
    } catch (Exception ex) {
      showError("Error saving file to specified path.");
      ex.printStackTrace();
    }
  }

  private String processText(String key, String text) {
    switch (algorithmSelectorComboBox.getSelectedIndex()) {
      case 0:
        VigenereCipher vigenereCipher = new VigenereCipher(key);
        if (encrypt)
          return vigenereCipher.encrypt(text);
        else
          return vigenereCipher.decrypt(text);

      case 1:
        try {
          FullVigenereCipher fullVigenereCipher = new FullVigenereCipher(key);
          inputBytes = text.getBytes();
          if (encrypt)
            return new String(fullVigenereCipher.encrypt(inputBytes), "UTF-16");
          else
            return new String(fullVigenereCipher.encrypt(inputBytes), "UTF-16");
        } catch (Exception ex) {
          return "";
        }

      case 2:
        PlayfairCipher playfairCipher = new PlayfairCipher(key);
        if (encrypt)
          return playfairCipher.encrypt(text);
        else
          return playfairCipher.decrypt(text);

      default:
        return "";
    }
  }

  private byte[] processBinary(String key, byte[] contents) {
    FullVigenereCipher fullVigenereCipher = new FullVigenereCipher(key);
    if (encrypt)
      return fullVigenereCipher.encrypt(contents);
    else
      return fullVigenereCipher.decrypt(contents);
  }

  private String stripNonalphabeticChars(String text) {
    return text.toUpperCase()
        .replaceAll("[^A-Z]", "");
  }

  private String insertSpaces(String text, int interval) {
    StringBuilder sb = new StringBuilder(text);
    int length = text.length() / 5 * 6;
    for (int i = interval; i < length; i += (interval + 1)) {
      sb.insert(i, ' ');
    }
    return sb.toString();
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(rootPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showInfo(String message) {
    JOptionPane.showMessageDialog(rootPanel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
  }
}
