package kg.my_algorithms;

import java.util.*;

public class DP {
    int distinctSubsequences(String S) {
        //https://atcoder.jp/contests/abc214/editorial/2447   (source)
        final long MOD = 1_000_000_007L;
        int[] map = new int[26];
        char[] s = S.toCharArray();
        int n = s.length;
        long[] P = new long[n+1];
        long ans = 1L;
        P[0] = 1L;
        for(int i=1;i<=n;i++){
            char c = s[i-1];
            int prev_index = map[c - 'a'];
            map[c - 'a'] = i;
            long cur;
            if(prev_index == 0) cur = P[i-1]%MOD;
            else cur = (Math.floorMod(P[i-1] - P[prev_index-1],MOD))%MOD;
            P[i] = (cur + P[i-1])%MOD;
            ans = (ans + cur)%MOD;
        }
        return (int)(ans%MOD);
    }
}
class LIS{
    int n;
    int[] arr;
    int[] d;  //d[i] => the smallest last number of increasing subsequence of length i
    public LIS(int[] arr){
        this.n = arr.length;
        this.arr = arr;
        this.d = new int[n+1];
        Arrays.fill(d,Integer.MAX_VALUE);
        d[0] = Integer.MIN_VALUE;
    }
    private int binarySearch(int target){
        int lo=0,hi=n;
        while(lo<=hi){
            int m = (lo+hi)>>1;
            if(d[m]<=target) lo=m+1;
            else hi=m-1;
        }
        return lo;
    }
    public int get_len(){
        for(int i=0;i<n;i++){
            int pos = binarySearch(arr[i]);
            if(d[pos-1]<arr[i] && arr[i]<d[pos]) d[pos] = arr[i];
        }
        for(int i=n;i>=1;i--) if(d[i]!=Integer.MAX_VALUE) return i;
        return -1;
    }
}