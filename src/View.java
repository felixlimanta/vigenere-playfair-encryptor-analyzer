package com.felixlimanta.VigenerePlayfair;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

  private static final String[] paneLabels = {
      "Plaintext: ", "Ciphertext: "
  };

  private static final String[] buttonLabels = {
      "Encrypt", "Save Encrypted File",
      "Decrypt", "Save Decrypted File"
  };

  private JComboBox algorithmSelectorComboBox;
  private JPanel rootPanel;
  private JRadioButton encryptRadio;
  private JRadioButton decryptRadio;
  private JButton openButton;
  private JLabel pathLabel;
  private JTextField keyField;
  private JPanel inputPanel;
  private JTextArea inputTextArea;
  private JPanel outputPanel;
  private JTextArea outputTextArea;
  private JButton processButton;
  private JButton saveOutputButton;
  private JFileChooser fileChooser;

  private Border plaintextBorder;
  private Border ciphertextBorder;

  private boolean encrypt = true;

  public static void main(String[] args) {
    new View();
  }

  public View() {
    super("Vigenere Cipher/Playfair Cipher Encryptor");
    setUpRadioListener();
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

  private void setUpOpenButtonListener() {
    openButton.addActionListener(e -> {
      fileChooser.setDialogTitle("Open encrypted file");
      int returnVal = fileChooser.showOpenDialog(rootPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String path = fileChooser.getSelectedFile().getPath();
        pathLabel.setText(path);
        inputTextArea.setText(loadFileToString(path));
      }
    });
  }

  private void setUpProcessButtonListener() {
    processButton.addActionListener(e -> {
      String key = keyField.getText();
      if (key.equals("")) {
        JOptionPane.showMessageDialog(rootPanel,
            "Key cannot be empty.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      String text = inputTextArea.getText();
      if (text.equals("")) {
        JOptionPane.showMessageDialog(rootPanel,
            "Input text area is empty. Load text or type it in the text area first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      outputTextArea.setText(processText(key, text));
    });
  }

  private void setUpSaveButtonListener() {
    saveOutputButton.addActionListener(e -> {
      String text = outputTextArea.getText();
      if (text.equals("")) {
        JOptionPane.showMessageDialog(rootPanel,
            "Encrypted text area is empty. Encrypt text first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      fileChooser.setDialogTitle("Save encrypted file");
      int returnVal = fileChooser.showSaveDialog(rootPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String path = fileChooser.getSelectedFile().getPath();
        saveStringToFile(path, text);
      }
    });
  }

  private String loadFileToString(String path) {
    try {
      byte[] contents = Files.readAllBytes(Paths.get(path));
      return new String(contents, "UTF-8");
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(rootPanel,
          "File does not exist or cannot be opened.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      return "";
    }
  }

  private void saveStringToFile(String path, String text) {
    try (FileWriter fw = new FileWriter(path)) {
      fw.write(text);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(rootPanel,
          "Error saving file to specified path.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
  }

  private String processText(String key, String text) {
    int selectedAlg = algorithmSelectorComboBox.getSelectedIndex();

    switch (selectedAlg) {
      case 0:
        VigenereCipher vigenereCipher = new VigenereCipher(key);
        if (encrypt) {
          return vigenereCipher.encrypt(text, false);
        } else {
          return vigenereCipher.decrypt(text, false);
        }

      case 1:
        vigenereCipher = new VigenereCipher(key);
        if (encrypt) {
          return vigenereCipher.encrypt(text, true);
        } else {
          return vigenereCipher.decrypt(text, true);
        }

      case 2:
        PlayfairCipher playfairCipher = new PlayfairCipher(key);
        if (encrypt) {
          return playfairCipher.encrypt(text);
        } else {
          return playfairCipher.decrypt(text);
        }

      default:
        return "";
    }
  }

}
