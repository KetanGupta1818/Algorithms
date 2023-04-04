//If there are more than 1 maximum element, the left_most index is returned
/*   To change this 
     if(left.maximumElement>=right.maximumElement) table[i][j] = new Pair21(left); remove = sign
     if(p1.maximumElement>=p2.maximumElement) return p1; remove = sign
     
*/
 
class MaxSparseTableWithIndex {
    int n;
    int[] logs;
    long[] arr;
    Pair21[][] table;
    MaxSparseTableWithIndex(long[] a){
        this.n = a.length;
        this.arr = new long[n];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        int maxPowerOfTwo = Math.max(1,(int)Math.ceil(Math.log(n)/Math.log(2)));
        this.logs = new int[n+1];
        computeLogs();
        this.table = new Pair21[maxPowerOfTwo+1][n];
        for(int i=0;i<=logs[n];i++){
            int curLen = 1<<i;
            for(int j=0;j<=n-curLen;j++){
                if(curLen==1) {
                    table[i][j] = new Pair21(arr[j],j);
                }
                else {
                    Pair21 left = table[i-1][j];
                    Pair21 right = table[i-1][j+(curLen/2)];
                    if(left.maximumElement>=right.maximumElement) table[i][j] = new Pair21(left);
                    else table[i][j] = new Pair21(right);
                }
            }
        }
    }
    private void computeLogs(){
        for(int i=2;i<=n;i++) logs[i] = logs[i/2]+1;

    }
    public Pair21 getMaximum(int left, int right){
        int p = logs[right-left+1];
        int pLen = 1 << p;
        Pair21 p1 = table[p][left];
        Pair21 p2 = table[p][right-pLen+1];
        if(p1.maximumElement>=p2.maximumElement) return p1;
        return p2;
    }

}
class Pair21 {
    long maximumElement;
    int index;

    public Pair21(long maximumElement, int index) {
        this.maximumElement = maximumElement;
        this.index = index;
    }
    public Pair21(Pair21 p){
        this.maximumElement = p.maximumElement;
        this.index = p.index;
    }
}
