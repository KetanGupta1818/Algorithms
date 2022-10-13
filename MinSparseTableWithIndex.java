class MinSparseTableWithIndex{
    int n;
    int[] logs;
    int[] arr;
    Pair1[][] table;
    MinSparseTableWithIndex(int[] a){
        this.n = a.length;
        this.arr = new int[n];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        int maxPowerOfTwo = Math.max(1,(int)Math.ceil(Math.log(n)/Math.log(2)));
        this.logs = new int[n+1];
        computeLogs();
        this.table = new Pair1[maxPowerOfTwo+1][n];
        for(int i=0;i<=logs[n];i++){
            int curLen = 1<<i;
            for(int j=0;j<=n-curLen;j++){
                if(curLen==1) {
                    table[i][j] = new Pair1(arr[j],j);
                }
                else {
                    Pair1 left = table[i-1][j];
                    Pair1 right = table[i-1][j+(curLen/2)];
                    if(left.minimumElement<right.minimumElement) table[i][j] = new Pair1(left);
                    else table[i][j] = new Pair1(right);
                }
            }
        }
    }
    private void computeLogs(){
        for(int i=2;i<=n;i++) logs[i] = logs[i/2]+1;

    }
    public Pair1 getMinimum(int left, int right){
        int p = logs[right-left+1];
        int pLen = 1 << p;
        Pair1 p1 = table[p][left];
        Pair1 p2 = table[p][right-pLen+1];
        if(p1.minimumElement<p2.minimumElement) return p1;
        return p2;
    }
    
}
class Pair1 {
    int minimumElement;
    int index;

    public Pair1(int minimumElement, int index) {
        this.minimumElement = minimumElement;
        this.index = index;
    }
    public Pair1(Pair1 p){
        this.minimumElement = p.minimumElement;
        this.index = p.index;
    }
}
