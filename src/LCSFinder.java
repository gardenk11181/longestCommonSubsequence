public class LCSFinder {
    private static final int UP = -1;
    private static final int LEFT = 0;
    private static final int DIAG = 1;

    public static String getLCS(String x,String y) {
        int[][] c = new int[x.length()+1][y.length()+1];
        int[][] b = new int[x.length()][y.length()];

        for(int i=0; i<y.length()+1; i++) {
            c[0][i] = 0;
        }
        for(int i=1; i<x.length()+1; i++) {
            c[i][0] = 0;
        }

        for(int i=1; i<x.length()+1; i++) {
            for(int j=1; j<y.length()+1; j++) {
                if(x.charAt(i)==y.charAt(j)) { // diagonal
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
}
