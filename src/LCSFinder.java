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

//    public static void myTest() {
//        Scanner scanner = new Scanner(System.in);
//
//        do {
//            System.out.print("Input Target Sequence: ");
//            String command = scanner.nextLine();
//            if(command.equals("exit")) break;
//
//            try {
//                printHairpin(command.toCharArray());
//            } catch (NoBiologicalSequenceException e) {
//                System.out.println(e.getMessage());
//            }
//        } while(true);
//
//        scanner.close();
//    }

    public static void main(String[] args) {
        File seq1File = new File("seq1.txt");
        File seq2File = new File("seq2.txt");
        try {
            Scanner scanner = new Scanner(seq1File);
            Scanner scanner2 = new Scanner(seq2File);
            String seq1 = scanner.nextLine();
            String seq2 = scanner2.nextLine();
            try {
                printHairpin(seq1.toCharArray());
                printHairpin(seq2.toCharArray());
            } catch (NoBiologicalSequenceException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("file is not exist or wrong file path");
        }
    }

    public static void printHairpin(char[] x) throws NoBiologicalSequenceException{
        if(!isBioSequence(x)) throw new NoBiologicalSequenceException();
        else {
            char[] compliment = convertToReverseCompliment(x);
            printLCS(x,compliment);
        }
    }

    public static void printLCS(char[] x,char[] y) {
        if(x.length==0 || y.length==0) return;
        fillArrays(x,y);
        printSequence(x,x.length,y.length);
        System.out.println();
    }
//    private static void printArrays() {
//
//        System.err.println("c array : ");
//
//        for(int i=0; i<c.length; i++) {
//            for(int j=0; j<c.length; j++) {
//                System.err.print(c[i][j]+" ");
//            }
//            System.err.println();
//        }
//
//        System.err.println("b array: ");
//
//        for(int i=0; i<b.length; i++) {
//            for(int j=0 ; j<b.length; j++) {
//                System.err.print(b[i][j]+" ");
//            }
//            System.err.println();
//        }
//    }

    private static char[] convertToReverseCompliment(char[] x) {
        char[] reverse = new char[x.length];
        for(int i=0 ; i<x.length; i++) {
            switch (x[i]) {
                case 'A':
                    reverse[x.length-1-i] = 'T';
                    break;
                case 'T':
                    reverse[x.length-1-i] = 'A';
                    break;
                case 'C':
                    reverse[x.length-1-i] = 'G';
                    break;
                case 'G':
                    reverse[x.length-1-i] = 'C';
                    break;
            }
        }
        return reverse;
    }

    private static boolean isBioSequence(char[] x) {
        for(int i=0; i<x.length; i++) {
            char ch = x[i];
            if(!(ch=='A' || ch=='G' || ch=='T' || ch=='C')) return false;
        }
        return true;
    }

    private static void fillArrays(char[] x, char[] y) {
        c = new int[x.length+1][y.length+1];
        b = new int[x.length+1][y.length+1];

        for(int i=0; i<y.length+1; i++) {
            c[0][i] = 0;
        }
        for(int i=1; i<x.length+1; i++) {
            c[i][0] = 0;
        }

        for(int i=1; i<x.length+1; i++) {
            for(int j=1; j<y.length+1; j++) {
                if(x[i-1]==y[j-1]) { // diagonal
                    c[i][j] = c[i-1][j-1]+1;
                    b[i][j] = DIAG;
                } else if(c[i-1][j]>=c[i][j-1]) { // up
                    c[i][j] = c[i-1][j];
                    b[i][j] = UP;
                } else { // left
                    c[i][j] = c[i][j-1];
                    b[i][j] = LEFT;
                }
            }
        }
    }

    // get Longest Common Subsequence using filled arrays
    private static void printSequence(char[] x,int i, int j) {
        if(i==0 || j==0) return;

        if(b[i][j] == DIAG) {
            printSequence(x,i-1,j-1);
            System.out.print(x[i-1]);
        } else if(b[i][j] == UP) {
            printSequence(x,i-1,j);
        } else printSequence(x,i,j-1);
    }
}

class NoBiologicalSequenceException extends Exception {
    @Override
    public String getMessage() {
        return "This is not a biological Sequence";
    }
}
