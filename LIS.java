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
