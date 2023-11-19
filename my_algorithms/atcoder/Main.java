package kg.my_algorithms.atcoder;


/*
                                                   /\  /\
                                                  /  \/  \
                                                 /        \
                                            ---------------------
                                                 \        /
                                                  \  /\  /
                                                   \/  \/
*/

import java.io.*;
import java.util.*;
public class Main implements Runnable{
    private static final FastReader fr = new FastReader();
    private static final long MOD = 998244353L;
    public static void main(String[] args) throws IOException {
        new Thread(null, new Main(), "atcoder", 1<<26).start();
    }
    @Override
    public void run() {
        PrintWriter out = new PrintWriter(System.out);
//        int testCases = fr.nextInt();
//        for(int testCase=1;testCase<=testCases;testCase++) {
//
//        }

        out.flush();
    }


}

class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
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