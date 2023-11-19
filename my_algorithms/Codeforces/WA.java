package kg.my_algorithms.Codeforces;

import java.io.*;
import java.util.*;

public class WA{
    private static final MyReader fr = new MyReader();
    private static final long MOD = 998244353L;

    public static void main(String[] args) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int testCases = fr.nextInt();
        for(int testCase=1;testCase<=testCases;testCase++) {

            int ans = solve(testCase);
            sb.append(ans).append("\n");

        }
        output.write(sb.toString());
        output.flush();
    }
    private static int solve(int tc){
        int n = fr.nextInt();
        String s = fr.next();
//        if(tc == 178){
//            System.out.println("n= " + n+ "     s=  " + s);
//        }
        if(n%2 == 1) return -1;
        int pairs = 0;
        int[] map = new int[26];
        int[] pm = new int[26];
        for(int i=0;i<n/2;i++){
            map[s.charAt(i)-'a']++;
            map[s.charAt(n-i-1)-'a']++;
            if(s.charAt(i)==s.charAt(n-i-1)) {
                pairs++;
                pm[s.charAt(i)-'a']++;
            }
        }
        List<Integer> list = new ArrayList<>();
        for(int a: pm){
            if(a>0) list.add(a);
        }
        list.sort(Collections.reverseOrder());
        int max = -1;
        for(int a: map) max = Math.max(max,a);
        if(max>n/2) return -1;
        int sum = 0;
        for(int i=1;i<list.size();i++) sum += list.get(i);
        if(list.size() == 0) list.add(-1);
   //     System.out.println(list);
        int dif = list.get(0) - sum;
        if(dif<=0)
            return pairs/2+pairs%2;
        return sum + dif;
    }

}
/*
1
12
cbcacabcacbc

 */



//Fast Input
class MyReader {
    BufferedReader br;
    StringTokenizer st;

    public MyReader()
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