//chinese remainder theorem
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
