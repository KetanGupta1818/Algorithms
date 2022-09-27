//Java implementation of https://cp-algorithms.com/string/suffix-array.html
class SuffixArray{
    String string;
    int[] s;
    int n;
    public SuffixArray(String st){
        this.string = st + "$";
        this.n = this.string.length();
        this.s = new int[n];
        for(int i=0;i<n;i++) s[i] = this.string.charAt(i);
    }

    public int[] getSuffixArray(){
        int[] p = new int[n];
        int[] c = new int[n];
        int[] cnt = new int[256];
        for(int i=0;i<n;i++) cnt[s[i]]++;
        for(int i=1;i<256;i++) cnt[i] += cnt[i-1];
        for(int i=0;i<n;i++) p[--cnt[s[i]]] = i;
        int classes = 1;
        c[p[0]] = 0;
        for(int i=1;i<n;i++){
            if(s[p[i]]!=s[p[i-1]]) classes++;
            c[p[i]] = classes-1;
        }

        int[] pn = new int[n];
        int[] cn = new int[n];
        for(int h=0;(1<<h)<n;h++){
            for(int i=0;i<n;i++){
                pn[i] = p[i] - (1<<h);
                if(pn[i]<0) pn[i] += n;
            }
            cnt = new int[classes];
            for(int i=0;i<n;i++) cnt[c[pn[i]]]++;
            for(int i=1;i<classes;i++) cnt[i] += cnt[i-1];
            for(int i=n-1;i>=0;i--) p[--cnt[c[pn[i]]]] = pn[i];
            cn[p[0]] = 0;
            classes = 1;
            for(int i=1;i<n;i++){
                int curLeft = c[p[i]];
                int curRight = c[(p[i]+(1<<h))%n];
                int prevLeft = c[p[i-1]];
                int prevRight = c[(p[i-1]+(1<<h))%n];
                if(curLeft!=prevLeft || curRight!=prevRight) classes++;
                cn[p[i]] = classes-1;
            }
            swap(c,cn);
        }
        // return p; //Uncomment, if empty string is a valid suffix
        return removeDollar(p);
    }
    private int[] removeDollar(int[] p){
        int[] res = new int[p.length-1];
        for(int i=0;i<res.length;i++) res[i] = p[i+1];
        return res;
    }
    private void swap(int[] c, int[] cn){
        for(int i=0;i<c.length;i++){
            int temp = c[i];
            c[i] = cn[i];
            cn[i] = temp;
        }
    }
    public int[] getLCPArray(int[] sa){
        int n = sa.length;
        int[] lcp = new int[n];
        int[] rank = new int[n];
        int k = 0;
        for(int i=0;i<n;i++) rank[sa[i]] = i;
        for(int i=0;i<n;i++){
            if(rank[i]==n-1) {
                k=0;
                continue;
            }
            int j = sa[rank[i]+1];
            while(i+k<n && j+k<n && string.charAt(i+k)==string.charAt(j+k)) k++;
            lcp[rank[i]+1] = k;
            if(k>0) k--;
        }
        return lcp;
    }
    //sa -> suffix array
    //lcp -> longest common prefix of two consecutive prefix
    public long numberOfDifferentSubStrings(int[] sa, int[] lcp){
        int n = sa.length;
        long res = 0L;
        for(int i=0;i<n;i++) res += n-sa[i]-lcp[i];
        return res;
    }
    public static String longestCommonSubstring(String s1, String s2){
        SuffixArray obj = new SuffixArray(s1+"@"+s2);
        int[] sa = obj.getSuffixArray();
        int[] lcp = obj.getLCPArray(sa);
        int len1 = s1.length();
        int max = 0;
        int startIndex=-1;
        for(int i=2;i<sa.length;i++){
            int prev = sa[i-1];
            int cur = sa[i];
            if((prev>len1 && cur<len1) || (prev<len1 && cur>len1)) {
                if(max<lcp[i]){
                    max = lcp[i];
                    startIndex = Math.min(prev,cur);
                }
            }
        }
        if(max ==0 ) return "";
        return s1.substring(startIndex,startIndex+max);
    }
}
