import mypckg.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.*;
import java.io.*;


/**
        //that is for testing in console
        Crypter c = new Crypter();
        c.loadFile();
        c.printInternalText();
        c.encryption(18);
        c.printInternalText();
        c.saveFile("en");
        c.decryption(18);
        c.saveFile("Crypto Decrypted");

        System.out.println();
        System.out.println();

        Hacker h = new Hacker();
        h.loadFile();
        h.printInternalText();


        h.decryption();
        h.printInternalText();
        h.saveFile("Hacker Result");
 /**/


public class CaesarMain extends JFrame {
    private final int buttonHeight = 25;
    private final int buttonWidth = 90;
    private final int buttonDoubleWidth = 180;

    private JButton buttonSaveForCrypter = new JButton("Save");
    private JButton buttonLoadForCrypter = new JButton("Load");
    private JButton buttonEncrypt;
    private JButton buttonDecrypt;

    private JButton buttonHack = new JButton("Do hacking");
    private JButton buttonSaveForHacker = new JButton("Save");
    private JButton buttonLoadForHacker = new JButton("Load");

    private JButton buttonExport;


    private JButton buttonClearAll;
    private JButton buttonWipeHacker;

    private JTextArea textAreaCrypto;
    private JTextArea textAreaHacker;
    private JScrollPane scrollPaneCrypto;
    private JScrollPane scrollPaneHacker;

    private JTextField textFieldShift;
    private JTextField textFieldShiftFounded;
    private JLabel labelShift;
    private JLabel labelShiftFounded;

    private JLabel labelInfoAboutCrypter;
    private JLabel labelInfoAboutHacker;

    public CaesarMain() throws FileNotFoundException {

        Crypter crypter = new Crypter();
        Hacker hacker = new Hacker();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Caesar and Hacker");
        setBounds(200, 150, 775, 570);
        getContentPane().setLayout(null);

        //labelInfoAboutCrypter
        labelInfoAboutCrypter = new JLabel("Encryption and Decryption by Caesar's cypher");
        labelInfoAboutCrypter.setBounds(60, 40, 300, 30);
        getContentPane().add(labelInfoAboutCrypter);

        //labelInfoAboutHacker
        labelInfoAboutHacker = new JLabel("Hacking and Decryption by Hacker standalone");
        labelInfoAboutHacker.setBounds(445, 40, 300, 30);
        getContentPane().add(labelInfoAboutHacker);

        //buttonClearAll
        buttonClearAll = new JButton("All Text Wipe");
        buttonClearAll.setSize(140, buttonHeight);
        buttonClearAll.setLocation(310,10);
        getContentPane().add(buttonClearAll);
        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaCrypto.setText(null);
                textAreaHacker.setText(null);
                textFieldShift.setText(null);
                textFieldShiftFounded.setText("N/A");
            }
        });

        //buttonWipeHacker
        buttonWipeHacker = new JButton("Wipe Hacker Data");
        buttonWipeHacker.setSize(buttonDoubleWidth, buttonHeight - 10);
        buttonWipeHacker.setLocation(400, 65);
        getContentPane().add(buttonWipeHacker);
        buttonWipeHacker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacker.restart();
                textFieldShiftFounded.setText("N/A");
            }
        });

        //buttonExport
        buttonExport = new JButton(">");
        buttonExport.setSize(41, 20);
        buttonExport.setLocation(359, 300);
        getContentPane().add(buttonExport);
        buttonExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaHacker.setText(textAreaCrypto.getText());
                hacker.restart();
            }
        });

        //buttonSaveForCrypter
        buttonSaveForCrypter.setBounds(95, 10, buttonWidth, buttonHeight);
        getContentPane().add(buttonSaveForCrypter);
        buttonSaveForCrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
                JFileChooser fc = new JFileChooser("./");
                fc.setFileFilter(filter);
                fc.setMultiSelectionEnabled(false);
                if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                    try ( FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                        fw.write(crypter.getInternalText());
                        fw.close();
                    }
                    catch ( IOException ex ) {
                        JOptionPane.showMessageDialog(null, "Ошибка записи");
                    }
                }

            }
        });

        //buttonLoadForCrypter
        buttonLoadForCrypter.setBounds(95 + buttonWidth + 5, 10, buttonWidth, buttonHeight);
        getContentPane().add(buttonLoadForCrypter);
        buttonLoadForCrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
                    JFileChooser fc = new JFileChooser("./");
                    fc.setFileFilter(filter);
                    fc.setMultiSelectionEnabled(false);
                    int ret = fc.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        crypter.loadFile(file);
                        textAreaCrypto.setText(crypter.getInternalText());
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка открытия");
                }
            }
        });

        //buttonEncrypt
        buttonEncrypt = new JButton("Encrypt");
        buttonEncrypt.setBounds(10, 85, buttonWidth, buttonHeight);
        getContentPane().add(buttonEncrypt);
        buttonEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    int shift;
                    shift = Integer.parseInt(textFieldShift.getText());
                    crypter.encryption(shift);
                    textAreaCrypto.setText(crypter.getInternalText());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Необходимо заполнить поле Shift");
                }
            }
        });

        //buttonDecrypt
        buttonDecrypt = new JButton("Decrypt");
        buttonDecrypt.setBounds(175 + buttonWidth + 5, 85, buttonWidth, buttonHeight);
        getContentPane().add(buttonDecrypt);
        buttonDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int shift;
                    shift = Integer.parseInt(textFieldShift.getText());
                    crypter.decryption(shift);
                    textAreaCrypto.setText(crypter.getInternalText());

                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Необходимо заполнить поле Shift");
                }

            }
        });

        //buttonSaveForHacker
        buttonSaveForHacker.setBounds(490, 10, buttonWidth, buttonHeight);
        getContentPane().add(buttonSaveForHacker);
        buttonSaveForHacker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
                JFileChooser fc = new JFileChooser("./");
                fc.setFileFilter(filter);
                fc.setMultiSelectionEnabled(false);
                if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                    try ( FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                        fw.write(hacker.getInternalText());
                        fw.close();
                    }
                    catch ( IOException ex ) {
                        JOptionPane.showMessageDialog(null, "Ошибка записи");
                    }
                }

            }
        });

        //buttonLoadForHacker
        buttonLoadForHacker.setBounds(buttonSaveForHacker.getX() + buttonWidth + 5, 10, buttonWidth, buttonHeight);
        getContentPane().add(buttonLoadForHacker);
        buttonLoadForHacker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
                    JFileChooser fc = new JFileChooser("./");
                    fc.setFileFilter(filter);
                    fc.setMultiSelectionEnabled(false);
                    int ret = fc.showDialog(null, "Открыть файл");

                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        hacker.loadFile(file);
                        textAreaHacker.setText(hacker.getInternalText());
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка открытия");
                }
                hacker.restart();
                textFieldShiftFounded.setText("N/A");
            }
        });

        //buttonHack
        buttonHack.setBounds(400, 85, buttonDoubleWidth, buttonHeight);
        getContentPane().add(buttonHack);
        buttonHack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    hacker.setInternalText(textAreaHacker.getText());
                    hacker.decryption();
                    textFieldShiftFounded.setText(String.valueOf(hacker.getShift()));
                    textAreaHacker.setText(hacker.getInternalText());
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Произошла ошибка при расшифровке");
                }
            }
        });

        //textFieldShift
        textFieldShift = new JTextField();
        textFieldShift.setBounds(10 + buttonWidth + 15, 10 + buttonHeight + 55, buttonWidth + 20, 20);
        textFieldShift.setHorizontalAlignment(textFieldShift.CENTER);
        getContentPane().add(textFieldShift);
        textFieldShift.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null)
                    return;
                if ((getLength() + str.length()) <= 5) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        //labelShift
        labelShift = new JLabel("Shift");
        labelShift.setBounds(textFieldShift.getX() + buttonWidth + 20, textFieldShift.getY(), buttonWidth/2, 20);
        getContentPane().add(labelShift);

        //textFieldShiftFounded
        textFieldShiftFounded = new JTextField("N/A");
        textFieldShiftFounded.setEditable(false);
        textFieldShiftFounded.setHorizontalAlignment(textFieldShiftFounded.CENTER);
        textFieldShiftFounded.setBounds(buttonHack.getX() + buttonHack.getWidth() + 20, buttonHack.getY() + 5, buttonWidth/2, 20);
        getContentPane().add(textFieldShiftFounded);

        //labelShiftFounded
        labelShiftFounded = new JLabel("Shift Founded");
        labelShiftFounded.setBounds(textFieldShiftFounded.getX() + 50, textFieldShiftFounded.getY(), buttonWidth, 20);
        getContentPane().add(labelShiftFounded);

        //textAreaCrypto
        textAreaCrypto = new JTextArea("Область для шифрования/расшифрования шифром Цезаря");
        textAreaCrypto.setLineWrap(true);
        textAreaCrypto.setWrapStyleWord(true);
        textAreaCrypto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                crypter.setInternalText(textAreaCrypto.getText());
                super.keyTyped(e);
            }
        });

        //scrollPaneCrypto
        scrollPaneCrypto = new JScrollPane(textAreaCrypto);
        scrollPaneCrypto.setBounds(10, 120, 350, 400);
        scrollPaneCrypto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPaneCrypto);
        crypter.setInternalText(textAreaCrypto.getText());

        //textAreaHacker
        textAreaHacker = new JTextArea("Область для шифрования/расшифрования модулем Hacker");
        textAreaHacker.setLineWrap(true);
        textAreaHacker.setWrapStyleWord(true);
        textAreaHacker.setEditable(false);

        //scrollPaneCrypto
        scrollPaneHacker = new JScrollPane(textAreaHacker);
        scrollPaneHacker.setBounds(400, 120, 350, 400);
        scrollPaneHacker.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPaneHacker);
        hacker.setInternalText(textAreaHacker.getText());
    }

    public static void main(String[] args) throws FileNotFoundException {
        CaesarMain frame = new CaesarMain();
        frame.setVisible(true);
    }
}


