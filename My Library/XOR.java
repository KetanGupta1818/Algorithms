package kg.my_algorithms;

import java.util.HashMap;
import java.util.Map;

public class XOR {
    public long numberOfSubarraysWithXorEqualsK(long[] a, long k) {
        Map<Long,Long> map = new HashMap<>();
        int n = a.length;
        long[] pref = new long[n];
        pref[0] = a[0];
        for(int i=1;i<n;i++){
            pref[i] = pref[i-1]^a[i];
        }
        long cnt = 0;
        for(int i=0;i<n;i++){
            long c = pref[i];
            if(c == k) cnt++;
            cnt += map.getOrDefault(c^k,0L);
            map.put(c,map.getOrDefault(c,0L)+1);
        }
        return cnt;
    }
}
