package mypckg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hacker {
    List<Character> alphabet = new ArrayList<Character>();
    List<Character> alphabetFull = new ArrayList<Character>();

    private String InternalText;
    private int pointer; //указывает на текущий символ, который надо извлечь в findShift
    private int shift; //вычисленный сдвиг
    private boolean isUsagesCounted;

    //массив встречаемости символов русского алфавита
    //оеаинтсрвлкмдпуяыьгзбчйхжшюцщэфъ
    private final String lettersString = "оеаинтсрвлкмдпуяыьгзбчйхжшюцщэфъ";

    /**
    private final int lettersStatistic[] = {52295949, 40392978, 38081816, 35075552, 31900994,
            30084462, 26058590, 22595850, 21582499, 20678280, 16599539, 15252377,
            14173134, 13349597, 12452612, 9528713, 9036813, 8263123, 8031521,
            7811723, 7579289, 6904749, 5753983, 4597146, 4476464, 3420179, 3044673,
            2314208, 1719607, 1573696, 1268926, 175908};
     /**/

    private int usages[] = new int[32];

    public Hacker() {
        InternalText = "";
        pointer = -1;
        shift = 0;
        isUsagesCounted = false;
        for (char c = 'а'; c <= 'я'; c++) {
            alphabet.add(c);
        }
        for (char c = 'А'; c <= 'я'; c++) {
            alphabetFull.add(c);
        }
    }

    public void setInternalText(String str){
        InternalText = str;
    }

    public void loadFile(File F) throws FileNotFoundException {
        String str = "";
        Scanner in = new Scanner(F);
        while (in.hasNextLine())
            str = str + in.nextLine();
        in.close();

        InternalText = str;

    }

    public void countUsages(){
        String str;
        str = InternalText.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int index = lettersString.indexOf(c);

            if (index == -1) {continue;}
            else{
                usages[index]++;
            }
        }
        isUsagesCounted = true;
    }

    public void findShift(){
        if (!isUsagesCounted){
            countUsages();
        }
        int max_usage = 0;
        int max_position = 0;
        for (int i=0; i < usages.length; i++){
            if (max_usage < usages[i]){
                max_usage = usages[i];
                max_position = i;
            }
        }
        char c = lettersString.charAt(max_position);
        pointer++;
        if (pointer>alphabet.size()) pointer = 0;
        shift = alphabet.indexOf(c) - alphabet.indexOf(lettersString.charAt(pointer));

    }

    public void decryption(){
        findShift();

        int n = alphabetFull.size();
        int half_n = n/2;
        shift = shift % n;

        StringBuilder decryptedText = new StringBuilder();
        //блок дешифрования данных
        for (int i = 0; i < InternalText.length(); i++) {
            char c = InternalText.charAt(i);
            int index = alphabetFull.indexOf(c);

            if (index == -1) {decryptedText.append(c); continue;}

            if (index<32) {
                index = (index - shift + n) % half_n;
            }
            else {
                index = (index - shift + n) % half_n + half_n;

            }

            decryptedText.append(alphabetFull.get(index));
        }
        InternalText = decryptedText.toString();
    }

    public int getShift(){
        return shift;
    }

    public String getInternalText(){
        return InternalText;
    }

    public void restart(){
        pointer = -1;
        shift = 0;
        InternalText = "";
        isUsagesCounted = false;
        for (int i=0; i<usages.length; i++) usages[i]=0;
    }

}
