public class SquareRootDecomposition {
}
class SumSqrtQuery{
    long[] arr;
    long[] b;   //Precomputed result of each block
    int len;    //Length of each block and number of elements in each block.
    int n;      //Number of elements in arr
    public SumSqrtQuery(long[] a){
        n = a.length;
        arr = new long[n];
        for(int i=0;i<n;i++) arr[i] = a[i];
        len = (int)Math.ceil(Math.sqrt(n));
        b = new long[len];
        //Fill first k-1 blocks with len number of elements, as the last block may have less than len elements
        for(int k=0;k<len-1;k++){
            int start = k*len,end=(k+1)*len;
            for(int i=start;i<Math.min(n,end);i++) b[k] += arr[i];
        }
        for(int i=(len-1)*len;i<n;i++) b[len-1] += arr[i];
    }
    //Zero Based indexing
    //k is the block number of left
    //p is the block number of right
    public long getSumInRange(int left, int right){
        int k = left/len, p = right/len;
        long sum = 0L;
        if(k == p){
            for(int i=left;i<=right;i++) sum += arr[i];
        }
        else{
            for(int i=left;i<k*len+len;i++) sum += arr[i];
            for(int i=k+1;i<p;i++) sum += b[i];
            for(int i=p*len;i<=right;i++) sum += arr[i];
        }
        return sum;
    }
    public void update(int index, long value){
        int k = index/len;   //Block number of index
        b[k] += value-arr[index];
        arr[index] = value;
    }
}
