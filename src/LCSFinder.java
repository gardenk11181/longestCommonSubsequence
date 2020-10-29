import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LCSFinder {
    private static final int UP = -1;
    private static final int LEFT = 0;
    private static final int DIAG = 1;
    static private int[][] c;
    static private int[][] b;

    public static String getLPS(String x) {
        String reverse = (new StringBuffer(x)).reverse().toString();
        return getLCS(x,reverse);
    }

    public static String getLCS(String x,String y) {
        if(x.length()==0 || y.length()==0) return "";
        fillArrays(x,y);
        return getSequence(x,x.length(),y.length());
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
