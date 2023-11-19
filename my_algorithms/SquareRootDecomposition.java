package kg.my_algorithms;

public class SquareRootDecomposition {
}
class SumSqrtQuery{
    long[] arr;
    long[] b;   //Precomputed result of each block
    int len;    //Length of each block and number of elements in each block.
    int n;      //Number of elements in arr
    public SumSqrtQuery(long[] a){
        n = a.length;
        arr = new long[n];
        for(int i=0;i<n;i++) arr[i] = a[i];
        len = (int)Math.ceil(Math.sqrt(n));
        b = new long[len];
        //Fill first k-1 blocks with len number of elements, as the last block may have less than len elements
        for(int k=0;k<len-1;k++){
            int start = k*len,end=(k+1)*len;
            for(int i=start;i<Math.min(n,end);i++) b[k] += arr[i];
        }
        for(int i=(len-1)*len;i<n;i++) b[len-1] += arr[i];
    }
    //Zero Based indexing
    //k is the block number of left
    //p is the block number of right
    public long getSumInRange(int left, int right){
        int k = left/len, p = right/len;
        long sum = 0L;
        if(k == p){
            for(int i=left;i<=right;i++) sum += arr[i];
        }
        else{
            for(int i=left;i<k*len+len;i++) sum += arr[i];
            for(int i=k+1;i<p;i++) sum += b[i];
            for(int i=p*len;i<=right;i++) sum += arr[i];
        }
        return sum;
    }
    public void update(int index, long value){
        int k = index/len;   //Block number of index
        b[k] += value-arr[index];
        arr[index] = value;
    }
}
//Mo's template
//import java.io.*;
//import java.util.*;
//
//public class Main {
//    private static final FastReader fr = new FastReader();
//    private static int mo_right = -1,mo_left = 0;
//    private static long answer = 0L;
//    private static final int[] freq = new int[2_000_01];
//    private static int[] arr;
//    public static void main(String[] args) throws IOException {
//        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
//        StringBuilder sb = new StringBuilder();
//        int n = fr.nextInt();
//        int qs = fr.nextInt();
//        arr = new int[n];
//        int block_size = (int)Math.sqrt(n);
//        for(int i=0;i<n;i++) arr[i] = fr.nextInt();
//        Queue<Query> queries = new PriorityQueue<>((q1,q2)->{
//            if(q1.left/q1.block_size == q2.left/q1.block_size) return q2.right - q1.right;
//            return q1.left/q1.block_size - q2.left/q1.block_size;
//        });
//
//        for(int i=0;i<qs;i++){
//            queries.offer(new Query(i,fr.nextInt()-1,fr.nextInt()-1,block_size));
//        }
//        long[] res = new long[qs];
//        while(!queries.isEmpty()){
//            Query query = queries.remove();
//            int left = query.left;
//            int right = query.right;
//            while(right>mo_right) mo_right_increment();
//            while(right<mo_right) mo_right_decrease();
//            while(left>mo_left) mo_left_increase();
//            while(left<mo_left) mo_left_decrease();
// //           System.out.println(Arrays.toString(freq) + " left= " + left+"  right= " + right);
//            res[query.index] = answer;
//        }
//        for(long a: res) sb.append(a).append("\n");
//        output.write(sb.toString());
//        output.flush();
//
//    }
//    private static long count(int num){
//        long f = freq[num];
//        if(f<=2) return 0L;
//        return (f*(f-1)*(f-2))/6;
//    }
//    private static void mo_right_increment(){
//        mo_right++;
//        answer -= count(arr[mo_right]);
//        freq[arr[mo_right]]++;
//        answer += count(arr[mo_right]);
//    }
//    private static void mo_right_decrease(){
//        answer -= count(arr[mo_right]);
//        freq[arr[mo_right]]--;
//        answer += count(arr[mo_right]);
//        mo_right--;
//    }
//    private static void mo_left_increase(){
//        answer -= count(arr[mo_left]);
//        freq[arr[mo_left]]--;
//        answer += count(arr[mo_left]);
//        mo_left++;
//    }
//    private static void mo_left_decrease(){
//        mo_left--;
//        answer -= count(arr[mo_left]);
//        freq[arr[mo_left]]++;
//        answer += count(arr[mo_left]);
//    }
//}
//class Query{
//    int index;
//    int left;
//    int right;
//    int block_size;
//
//    public Query(int index, int left, int right,int block_size) {
//        this.index = index;
//        this.left = left;
//        this.right = right;
//        this.block_size = block_size;
//    }
//}
//class FastReader {
//    BufferedReader br;
//    StringTokenizer st;
//
//    public FastReader()
//    {
//        br = new BufferedReader(new InputStreamReader(System.in));
//    }
//
//    String next() {
//        while (st == null || !st.hasMoreElements()) {
//            try {
//                st = new StringTokenizer(br.readLine());
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return st.nextToken();
//    }
//
//    int nextInt() { return Integer.parseInt(next()); }
//
//    long nextLong() { return Long.parseLong(next()); }
//
//    double nextDouble()
//    {
//        return Double.parseDouble(next());
//    }
//
//    String nextLine()
//    {
//        String str = "";
//        try {
//            if(st.hasMoreTokens()){
//                str = st.nextToken("\n");
//            }
//            else{
//                str = br.readLine();
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
//}