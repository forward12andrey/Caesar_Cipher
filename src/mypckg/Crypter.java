package mypckg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
Для исходного текст:
                заменяем А..Я (0..32) вкруговую
                заменяем а..я (0..32) вкруговую
                остальное не трогаем
/**/

public class Crypter {
    List<Character> alphabet = new ArrayList<Character>();

    private String InternalText;

    public Crypter() {
        InternalText = "";
        for (char c = 'А'; c <= 'я'; c++) {
            alphabet.add(c);
        }
    }

    public void setInternalText(String str){
        InternalText = str;
    }

    public String getInternalText(){
        return InternalText;
    }

    public void encryption(int shift){
        int n = alphabet.size();
        int half_n = n/2;
        //shift = shift % n;
        shift = shift % half_n;
        if (shift<0){
            shift = shift*-1;
            shift = half_n - shift;
        }
        StringBuilder encryptedText = new StringBuilder();

        //блок шифрования данных
        for (int i = 0; i < InternalText.length(); i++) {
            char c = InternalText.charAt(i);
            int index = alphabet.indexOf(c);

            if (index == -1) {encryptedText.append(c); continue;}

            if (index<32) {
                index = (index + shift) % half_n;
            }
            else {
                index = (((index + shift) % half_n) + half_n);
            }

            encryptedText.append(alphabet.get(index));
        }
        InternalText = encryptedText.toString();
    }

    public void decryption(int shift) {
        int n = alphabet.size();
        int half_n = n/2;
        shift = shift % n;

        StringBuilder decryptedText = new StringBuilder();
        //блок дешифрования данных
        for (int i = 0; i < InternalText.length(); i++) {
            char c = InternalText.charAt(i);
            int index = alphabet.indexOf(c);

            if (index == -1) {decryptedText.append(c); continue;}

            if (index<32) {
                index = (index - shift + n) % half_n;
            }
            else {
                index = (index - shift + n) % half_n + half_n;

            }

            decryptedText.append(alphabet.get(index));
        }
        InternalText = decryptedText.toString();

    }

    public void loadFile(File F) throws FileNotFoundException {
    String str = "";
    Scanner in = new Scanner(F);
    while (in.hasNextLine())
        str = str + in.nextLine();
    in.close();

    InternalText = str;
    }

    /**
    public void saveFile(String filename){
        try {
            FileWriter writer = new FileWriter(filename + ".txt", false);
            writer.write(InternalText);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**/
}