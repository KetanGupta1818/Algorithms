package kg.my_algorithms.meta_hackercup;
import java.util.*;
import java.io.*;
public class Solution {

    private static final long MOD = 998244353L;
    private static final FileInput fr;
    private static final FileWriter out;
    static {
        try {
            out = new FileWriter("src\\kg\\my_algorithms\\meta_hackercup\\output.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fr = new FileInput();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {


        int testCases = fr.nextInt();
        for(int testCase=1;testCase<=testCases;testCase++) {

        }

        out.flush();
    }

}


class FileInput{
    BufferedReader br;
    StringTokenizer st;
    final File file = new File("src\\kg\\my_algorithms\\meta_hackercup\\input.txt");
    public FileInput() throws FileNotFoundException {
        br = new BufferedReader(new FileReader(file));
    }
    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            if(st.hasMoreTokens()){
                str = st.nextToken("\n");
            }
            else{
                str = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}