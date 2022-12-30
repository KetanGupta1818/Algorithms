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
