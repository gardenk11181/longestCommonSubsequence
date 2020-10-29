public class Test {
    public static void main(String[] args) {
        String x = "10010101";
        String y = "010110110";
        String z = "ABCBBA";
        System.out.println(LCSFinder.getLCS(x,y));
        System.out.println(LCSFinder.getLPS(z));
    }
}
