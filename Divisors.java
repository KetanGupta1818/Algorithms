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
