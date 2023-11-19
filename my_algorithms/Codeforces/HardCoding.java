//package kg.my_algorithms.Codeforces;
import java.io.*;
import java.util.*;
public class HardCoding {
    public static void main(String[] args) throws IOException {
        FileWriter myWriter = new FileWriter(new File("src\\kg\\my_algorithms\\Codeforces\\first.txt"));
        List<Integer> primes = new ArrayList<>();
        int N = 1_000_001;
        boolean[] visited = new boolean[N];
        for(int i=2;i<N;i++){
            if(!visited[i]){
                primes.add(i);
                for(int j=i;j<N;j+=i) visited[j] = true;
            }
        }
        myWriter.write("{");
        for(int i=0;i<primes.size()-1;i++){
            myWriter.write(primes.get(i)+", ");
            if(i%300 == 0) myWriter.write("\n");
        }
        myWriter.write(primes.get(primes.size()-1));
        myWriter.write("}\n");
        myWriter.write(primes.get(primes.size()-1));
        System.out.println(primes);
        myWriter.close();
    }
}
class FileInput{
    BufferedReader br;
    StringTokenizer st;
    final File file = new File("src\\kg\\my_algorithms\\input2.txt");
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