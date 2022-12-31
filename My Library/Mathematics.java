package kg.my_algorithms;

import java.util.ArrayList;
import java.util.List;

public class Mathematics {
    private Mathematics() throws Exception {
        throw new Exception("Not allowed to create instance of utility classes");
    }
    public static long mySqrt(long x) {
        long lo=0,hi=1_000_000_001;
        while(lo<=hi){
            long m=lo+(hi-lo)/2;
            if(x==m*m) return m;
            else if(x>m*m) lo=m+1;
            else hi=m-1;
        }
        return lo-1;
    }
    public static long getGCD(long x, long y){
        long a = Math.max(x,y);
        long b = Math.min(x,y);

        while(b != 0){
            long temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }

    public static int getGCD(int x, int y){
        int a = Math.max(x,y);
        int b = Math.min(x,y);
        while(b!=0){
            int temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }
    public static int getGCDOfArray(int[] arr){
        if(arr.length == 1) return arr[0];
        int prevGCD = getGCD(arr[0],arr[1]);
        for(int i=2;i<arr.length;i++) prevGCD = getGCD(prevGCD,arr[i]);
        return prevGCD;
    }

    public static long getGCDOfArray(long[] arr){
        if(arr.length == 1) return arr[0];
        long prevGCD = getGCD(arr[0],arr[1]);
        for(int i=2;i<arr.length;i++) prevGCD = getGCD(prevGCD,arr[i]);
        return prevGCD;
    }
}
class FastCombinatorics {
    private static final long MOD = 1_000_000_007;
    long[] fac;
    long[] inv;
    public FastCombinatorics(int n){
        this.fac = new long[n+1];
        this.inv = new long[n+1];
        fac[0] = 1L;

        for(int i=1;i<=n;i++) {
            fac[i] = fac[i-1]*i%MOD;

        }
        inv[n] = powMod(fac[n],MOD-2);
        for(int i=n-1;i>=0;i--){
            inv[i] = inv[i+1]*(i+1)%MOD;
        }
    }

    private long powMod(long x, long n){
        if(n == 0) return 1L;
        long t = powMod(x,n/2);
        if(n%2 == 0) return t*t%MOD;
        return t*t%MOD*x%MOD;
    }

    public long C(int n,int r){
        if(r == 0) return 1L;
        return (fac[n]*inv[r]%MOD*inv[n-r])%MOD;
    }
}
/*
Make this nCr matrix inside your main class to reduce time by 60ms.
 */
class SmallCombinatorics{
    int n;
    long MOD;
    long[][] nCr;
    public SmallCombinatorics(int n,long MOD){
        this.n = n;
        this.MOD = MOD;
        this.nCr = new long[n+1][n+1];
        nCr[0][0] = 1L;
        for(int row=1;row<=n;row++){
            nCr[row][0] = 1L;
            for(int col=1;col<=row;col++) nCr[row][col] = (nCr[row-1][col]+nCr[row-1][col-1])%MOD;
            nCr[row][row] = 1L;
        }
    }
    public long C(int n, int r){
        return nCr[n][r];
    }
}
//maximumValue*log(maximumValue) preprocessing
//O(1) query
class NumberOfDivisors{
    private final int[] divisors;
    public NumberOfDivisors(int maximumValue){
        this.divisors = new int[maximumValue+1];
        for(int i=1;i<=maximumValue;i++) for(int j=i;j<=maximumValue;j+=i) divisors[j]++;
    }
    public int getNumberOfDivisorsOfX(int x){
        return divisors[x];
    }
}

//If gives tle then use this functionality in main function and not as a dependency
//time difference of 100ms to 400ms
class NumberOfPrimeNumbers{
    int n;
    List<Integer> primes;
    public NumberOfPrimeNumbers(int n) {
        this.n = n;
        primes = new ArrayList<>();
        boolean[] visited = new boolean[n+1];
        for(int i=2;i<=n;i++) {
            if(!visited[i]){
                primes.add(i);
                visited[i] = true;
            }
        }
    }
    public int numberOfPrimeFactors(int num){
        if(num==1) return 0;
        int cur_num = num;
        int cnt = 0;
        for(int prime: primes){

            while(cur_num>1 && cur_num%prime==0){
                cur_num = cur_num/prime;
                cnt++;
            }
            if(num<prime) break;
        }
        if(cur_num>1) cnt++;
        return cnt;
    }

}
class LCM {
    private long gcd(long a , long b)
    {
        if(a == 0)
            return b;
        return gcd(b%a, a);
    }

    private long lcm(long a , long b)
    {
        return (a*b)/gcd(a,b);
    }

    public long lcmOfArrayWithMOD(int[] A) {
        int N = A.length;
        long ans = 1;
        for (int j : A) ans = lcm(ans, j) % 1000000007;
        return ans;
    }
    public long lcmOfArrayWithoutMOD(int[] A) {
        int N = A.length;
        long ans = 1;
        for (int j : A) ans = lcm(ans, j);
        return ans;
    }
}
