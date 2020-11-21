import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LCSFinder {
    private static final int UP = -1;
    private static final int LEFT = 0;
    private static final int DIAG = 1;
    static private int[][] c;
    static private int[][] b;

    public static void myTest() {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Input Target Sequence: ");
            String command = scanner.nextLine();
            if(command.equals("exit")) break;

            try {
                System.out.println(getHairpin(command));
            } catch (NoBiologicalSequenceException e) {
                System.out.println(e.getMessage());
            }
        } while(true);

        scanner.close();
    }

    public static void main(String[] args) {
//        myTest();
        File seq1File = new File("/seq1.txt");
        File seq2File = new File("/seq2.txt");
        try {
            Scanner scanner = new Scanner(seq1File);
            Scanner scanner2 = new Scanner(seq2File);
            String seq1 = scanner.nextLine();
            String seq2 = scanner2.nextLine();
            try {
                System.out.println(getHairpin(seq1));
                System.out.println(getHairpin(seq2));
            } catch (NoBiologicalSequenceException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("file is not exist or wrong file path");
        }
    }

    public static String getHairpin(String x) throws NoBiologicalSequenceException{
        if(!isBioSequence(x)) throw new NoBiologicalSequenceException();
        else {
            String compliment = convertToReverseCompliment(x);
            return getLCS(x,compliment);
        }
    }

    public static String getLPS(String x) {
        String reverse = (new StringBuffer(x)).reverse().toString();
        return getLCS(x,reverse);
    }

    public static String getLCS(String x,String y) {
        if(x.length()==0 || y.length()==0) return "";
        fillArrays(x,y);
        return getSequence(x,x.length(),y.length());
    }

    private static String convertToReverseCompliment(String x) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0 ; i<x.length(); i++) {
            switch (x.charAt(i)) {
                case 'A':
                    stringBuilder.append('T');
                    break;
                case 'T':
                    stringBuilder.append('A');
                    break;
                case 'C':
                    stringBuilder.append('G');
                    break;
                case 'G':
                    stringBuilder.append('C');
                    break;
            }
        }
        return (new StringBuffer(stringBuilder.toString())).reverse().toString();
    }

    private static boolean isBioSequence(String x) {
        for(int i=0; i<x.length(); i++) {
            char ch = x.charAt(i);
            if(!(ch=='A' || ch=='G' || ch=='T' || ch=='C')) return false;
        }
        return true;
    }

    private static void fillArrays(String x, String y) {
        c = new int[x.length()+1][y.length()+1];
        b = new int[x.length()][y.length()];

        for(int i=0; i<y.length()+1; i++) {
            c[0][i] = 0;
        }
        for(int i=1; i<x.length()+1; i++) {
            c[i][0] = 0;
        }

        for(int i=1; i<x.length()+1; i++) {
            for(int j=1; j<y.length()+1; j++) {
                if(x.charAt(i-1)==y.charAt(j-1)) { // diagonal
                    c[i][j] = c[i-1][j-1]+1;
                    b[i-1][j-1] = DIAG;
                } else if(c[i-1][j]>=c[i][j-1]) { // up
                    c[i][j] = c[i-1][j];
                    b[i-1][j-1] = UP;
                } else { // left
                    c[i][j] = c[i][j-1];
                    b[i-1][j-1] = LEFT;
                }
            }
        }
    }

    // get Longest Common Subsequence using filled arrays
    private static String getSequence(String x,int i, int j) {
        List<Character> characterList = new LinkedList<>();

        int row = i-1;
        int col = j-1;
        while(row!=-1 && col!=-1) {
            if(b[row][col] == DIAG) {
                characterList.add(x.charAt(row));
                row--;
                col--;
            } else if(b[row][col] == UP) {
                row--;
            } else col--;
        }
        Collections.reverse(characterList);

        // append characters into a string
        StringBuilder result = new StringBuilder();
        for(Character character : characterList) {
            result.append(character);
        }

        return result.toString();
    }
}

class NoBiologicalSequenceException extends Exception {
    @Override
    public String getMessage() {
        return "This is not a biological Sequence";
    }
}
