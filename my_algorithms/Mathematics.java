package kg.my_algorithms;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Mathematics {
    private static final long MOD = 2L;
    private Mathematics() throws Exception {
        throw new Exception("Not allowed to create instance of utility classes");
    }
    private static long mod_inverse(long num){
        return powMODRec(num%MOD,MOD-2);
    }
    private static long powMODRec(long x, long n){
        if(n == 0) return 1L;
        long t = powMODRec(x,n/2);
        if(n%2 == 0) return t*t%MOD;
        return t*t%MOD*x%MOD;
    }
    private static long powMod(long x, long n){
        return powMODRec(x%MOD,n);
    }
    //Use mod one

    private static long powModRec(long x, long n,long MOD){
        if(n == 0) return 1L;
        long t = powModRec(x,n/2,MOD)%MOD;
        if(n%2 == 0) return t*t%MOD;
        return t*t%MOD*x%MOD;
    }
    private static long powMODRec(long x, long n, long MOD){
        return powModRec(x%MOD,n,MOD);
    }
    private static long powMODRec(long a, long b, long c, long MOD){
        if(a%MOD == 0) return 0L;
        return powMODRec(a, powMODRec(b,c,MOD-1),MOD);
    }
    public static boolean isMultiplicationOverflow(long a, long b) {
        if (a == 0 || b == 0) return false;
        long result = a * b;
        return a != result / b;
    }
    //Very slow as compared to Math.sqrt()
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
    //0 included
    private static int get_mex(long[] arr){
        int n = arr.length;
        int[] map = new int[n];
        for(long a: arr) if(a<n) map[(int)a]++;
        for(int i=0;i<n;i++) if(map[i] == 0) return i;
        return n;
    }
    //0 included
    private static int get_mex(int[] arr){
        int n = arr.length;
        int[] map = new int[n];
        for(int a: arr) if(a<n) map[a]++;
        for(int i=0;i<n;i++) if(map[i] == 0) return i;
        return n;
    }
    private static void shuffle(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int j = ThreadLocalRandom.current().nextInt(i, arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
/*
This Combinatorics is slow. If you can afford O(n^2) memory than use Small Combinatorics.
 */
class Combinatorics{
    private static final long MOD = 1_000_000_007;
    long[] fac;

    public Combinatorics(int n){
        this.fac = new long[n+1];
        fac[0] = 1L;
        for(int i=1;i<=n;i++) fac[i] = fac[i-1]*i%MOD;
    }

    private long powMod(long x, long n){
        if(n == 0) return 1L;
        long t = powMod(x,n/2);
        if(n%2 == 0) return t*t%MOD;
        return t*t%MOD*x%MOD;
    }

    public long C(int n,int r){
        if(r == 0) return 1L;
        return (fac[n]*powMod(fac[n-r]*fac[r]%MOD,MOD-2))%MOD;
    }
    public long P(int n,int r){
        return (fac[n]*powMod(fac[n-r]%MOD,MOD-2))%MOD;
    }
}
/*
Make this nCr matrix inside your main class to reduce time by 60ms.
n <= 1000
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
class FastCombinatorics {
    private long MOD;
    long[] fac;
    long[] inv;
    public FastCombinatorics(int n,long MOD){
        this.fac = new long[n+1];
        this.inv = new long[n+1];
        fac[0] = 1L;
        this.MOD = MOD;
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
class CRT{
    public static CRT_Result solveForX(long[] divisors, long[] remainders){
        long lcm = divisors[0];
        long ans = remainders[0];
        for(int i=1;i<divisors.length;i++){
            long[] pom = extended_EuclideanAlgorithm(lcm,divisors[i]);
            long x1 = pom[0];
            long d = pom[2];
            if((remainders[i] - ans) % d != 0) {
                return new CRT_Result(false,-1,-1);
            }
            ans = normalize(ans + x1 * (remainders[i] - ans) / d % (divisors[i] / d) * lcm, lcm * divisors[i] / d);
            lcm = LCM(lcm, divisors[i]);
        }
        return new CRT_Result(true,lcm,ans);
    }
    private static long[] extended_EuclideanAlgorithm(long a, long b){
        if(b == 0) return new long[]{1,0,a};
        long[] res = extended_EuclideanAlgorithm(b, a % b);
        return new long[]{res[1], res[0] - a / b * res[1], res[2]};
    }
    private static long GCD(long  a, long  b) { return (b == 0) ? a : GCD(b, a % b); }
    private static long LCM(long  a, long b) { return a / GCD(a, b) * b; }
    private static long normalize( long x,  long mod) { x %= mod; if (x < 0) x += mod; return x; }
}
class CRT_Result{
    boolean doesSolutionExits;
    long lcm; //LCM of divisors
    long x;   //The answer of CRT

    public CRT_Result(boolean doesSolutionExits, long lcm, long x) {
        this.doesSolutionExits = doesSolutionExits;
        this.lcm = lcm;
        this.x = x;
    }
}
class Divisors{
    private final int sq;
    private final List<Long> primes = new ArrayList<>();
    public Divisors(long MAX_N){
        this.sq = (int)(Math.sqrt(MAX_N))+1;
        fillPrimes();
    }
    private void fillPrimes(){
        boolean[] visited = new boolean[sq+1];
        for(int i=2;i<=sq;i++){
            if(!visited[i]){
                primes.add((long)i);
                for(int j=i;j<=sq;j+=i) visited[j] = true;
            }
        }
    }
    public List<Long> getAllDivisors(long n){
        List<long[]> prime_factors = new ArrayList<>();
        for(long prime: primes){
            if(n == 1) break;
            long cnt = 0;
            while(n%prime==0){
                n /= prime;
                cnt++;
            }
            if(cnt>0) prime_factors.add(new long[]{prime,cnt});
        }
        if(n>sq) prime_factors.add(new long[]{n,1});
        List<Long> divisors = new ArrayList<>();
        rec(0,divisors,1,prime_factors);
        divisors.sort(null);
        return divisors;
    }
    private void rec(int index, List<Long> divisors, long cur, List<long[]> prime_factors){
        if(index == prime_factors.size()){
            divisors.add(cur);
            return;
        }
        long mul = 1, p = prime_factors.get(index)[0], cnt = prime_factors.get(index)[1];
        for(int i=0;i<=cnt;i++){
            rec(index+1,divisors,cur*mul,prime_factors);
            mul *= p;
        }
    }
}
/*
//Miller Rabin Prime check
//increase iteration for accuracy, 13 iteration was AC
#include<bits/stdc++.h>
using namespace std;
#define ll long long int
int prime[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
ll mulmod(ll a,ll b,ll c) {	//return (a*b)%c   O(log(N))
    ll x=0,y=a%c;
    while(b>0) {
        if(b%2==1) x=(x+y)%c;
        y=(y*2)%c;
        b/=2;
    }
    return x%c;
}
ll mulmodfast(ll a,ll b,ll mod) {	//return (a*b)%mod  O(1)
a%=mod;
b%=mod;
long double res = a;
res*=b;
ll c = (ll)(res/mod);
a*=b;
a-=(c*mod);
a%=mod;
if(a<0)a+=mod;
return a;
}

ll modulo(ll a,ll b,ll c){
    ll x=1,y=a; // long long is taken to avoid overflow of intermediate results
    while(b>0){
        if(b%2 == 1) x=mulmod(x,y,c);	//we can use mulmod or multiply fxn here
        y=mulmod(y,y,c); // squaring the base
        b/=2;
    }
    return x%c;
}
bool Miller(ll p,int iteration){
    if(p<2) return 0;
    if(p!=2&&p%2==0) return 0;
for(int i=0;i<25;i++) {
if(p==prime[i]) return 1;
else if(p%prime[i]==0) return 0;
}
    long long s=p-1;
    while(s%2==0) s/=2;
    for(int i=0;i<iteration;i++){
        long long a=rand()%(p-1)+1,temp=s;
        long long mod=modulo(a,temp,p);
        while(temp!=p-1&&mod!=1&&mod!=p-1){
            mod=mulmod(mod,mod,p);
            temp*=2;
        }
        if(mod!=p-1 && temp%2==0) return 0;
    }
    return 1;
}

 */
/*
TODO https://cp-algorithms.com/algebra/factorization.html
 */