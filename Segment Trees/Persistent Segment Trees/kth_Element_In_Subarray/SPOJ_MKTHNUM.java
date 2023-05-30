import java.io.*;
import java.util.*;
class KthElementInASubarrayTree{
    private final List<Node> heads;
    private final List<Long> list = new ArrayList<>();
    private final int n;
    public KthElementInASubarrayTree(long[] arr){
        n = arr.length;
        heads = new ArrayList<>();
        for(long a: arr) list.add(a);
        list.sort(null);
        Map<Long,Integer> map = new HashMap<>();
        for(int i = 0; i< n; i++){
            map.put(list.get(i),i);
        }
        heads.add(construct(0, n -1,new int[n]));
        for(int i = 0; i< n; i++){
            heads.add(update(0, n -1,heads.get(i),map.get(arr[i]),1));
        }

    }
    private Node construct(int ss, int se, int[] arr){
        if(ss == se) return new Node(arr[ss]);
        int mid = (ss+se)>>1;
        Node left = construct(ss,mid,arr);
        Node right = construct(mid+1,se,arr);
        return new Node(left.value + right.value, left, right);
    }
    private Node update(int ss, int se, Node node, int index, int value){
        if(index<ss || index>se) return node;
        if(index == ss && index == se) return new Node(value);
        int mid = (ss+se)>>1;
        Node left = update(ss,mid,node.left,index,value);
        Node right = update(mid+1,se,node.right,index,value);
        return new Node(left.value + right.value, left, right);
    }
    public long kthNumberInSubarray(int left, int right, int k){
        return kthFunction(0,n-1,heads.get(left),heads.get(right+1),k);
    }
    private long kthFunction(int ss, int se, Node leftEnd, Node rightEnd, int k){
        if(ss == se) return list.get(ss);
        int mid = (ss+se)>>1;
        int left_numbers = rightEnd.left.value - leftEnd.left.value;
        if(left_numbers<k) return kthFunction(mid+1,se,leftEnd.right, rightEnd.right,k-left_numbers);
        return kthFunction(ss,mid,leftEnd.left,rightEnd.left,k);
    }
    private static class Node{
        Node left,right;
        int value;
        public Node(int value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public Node(int value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
public class Main{
    private static final Reader fr = new Reader();
    public static void main(String[] args) throws IOException {
        PrintWriter out= new PrintWriter(System.out);
        int n = fr.nextInt();
        int queries = fr.nextInt();
        long[] arr = new long[n];
        for(int i=0;i<n;i++) arr[i] = fr.nextLong();
        KthElementInASubarrayTree sg = new KthElementInASubarrayTree(arr);
        for(int q=1;q<=queries;q++){
            int left = fr.nextInt()-1;
            int right = fr.nextInt()-1;
            int k = fr.nextInt();
            out.println(sg.kthNumberInSubarray(left,right,k));
        }
        out.flush();
    }
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}

