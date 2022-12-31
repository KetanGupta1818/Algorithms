import java.util.Arrays;

public class Range {
}
class MinSparseTable{
    int n;
    int[] logs;
    int[] arr;
    int[][] table;
    MinSparseTable(int[] a){
        this.n = a.length;
        this.arr = new int[n];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        int maxPowerOfTwo = Math.max(1,(int)Math.ceil(Math.log(n)/Math.log(2)));
        this.logs = new int[n+1];
        computeLogs();
        this.table = new int[maxPowerOfTwo+1][n];
        for(int i=0;i<=logs[n];i++){
            int curLen = 1<<i;
            for(int j=0;j<=n-curLen;j++){
                if(curLen==1) table[i][j] = arr[j];
                else table[i][j] = Math.min(table[i-1][j],table[i-1][j+(curLen/2)]);
            }
        }
    }
    private void computeLogs(){
        for(int i=2;i<=n;i++) logs[i] = logs[i/2]+1;

    }
    public int getMinimum(int left, int right){
        int p = logs[right-left+1];
        int pLen = 1 << p;
        return Math.min(table[p][left],table[p][right-pLen+1]);
    }
}
class MaxSparseTable{
    int n;
    int[] logs;
    int[] arr;
    int[][] table;
    MaxSparseTable(int[] a){
        this.n = a.length;
        this.arr = new int[n];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        int maxPowerOfTwo = Math.max(1,(int)Math.ceil(Math.log(n)/Math.log(2)));
        this.logs = new int[n+1];
        computeLogs();
        this.table = new int[maxPowerOfTwo+1][n];
        for(int i=0;i<=logs[n];i++){
            int curLen = 1<<i;
            for(int j=0;j<=n-curLen;j++){
                if(curLen==1) table[i][j] = arr[j];
                else table[i][j] = Math.max(table[i-1][j],table[i-1][j+(curLen/2)]);
            }
        }
    }
    private void computeLogs(){
        for(int i=2;i<=n;i++) logs[i] = logs[i/2]+1;
    }
    public int getMaximum(int left, int right){
        int p = logs[right-left+1];
        int pLen = 1 << p;
        return Math.max(table[p][left],table[p][right-pLen+1]);
    }
}
class SumSegmentTree{
    long[] arr;
    int lengthOfArray;
    int heightOfSegmentTree;
    long[] segmentArray;
    public SumSegmentTree(long[] a){
        this.lengthOfArray = a.length;
        this.arr = new long[lengthOfArray];
        for(int i=0;i<lengthOfArray;i++) arr[i] = a[i];
        this.heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int segmentArrayLength = 2*(1<<heightOfSegmentTree) - 1;
        this.segmentArray = new long[segmentArrayLength];
        SumSegmentTreeConstructorUtil(0,lengthOfArray-1,0);
    }
    public SumSegmentTree(int[] a){
        this.lengthOfArray = a.length;
        this.arr = new long[lengthOfArray];
        for(int i=0;i<lengthOfArray;i++) arr[i] = (long)a[i];
        this.heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int segmentArrayLength = 2*(1<<heightOfSegmentTree) - 1;
        this.segmentArray = new long[segmentArrayLength];
        SumSegmentTreeConstructorUtil(0,lengthOfArray-1,0);
    }

    private long SumSegmentTreeConstructorUtil(int segmentStart, int segmentEnd, int segmentIndex){
        if(segmentStart == segmentEnd) return segmentArray[segmentIndex] = arr[segmentStart];
        int mid = getMid(segmentStart,segmentEnd);
        return segmentArray[segmentIndex] = SumSegmentTreeConstructorUtil(segmentStart,mid,segmentIndex*2+1) +
                SumSegmentTreeConstructorUtil(mid+1,segmentEnd,segmentIndex*2+2);
    }
    private int getMid(int segmentStart, int segmentEnd){
        return (segmentStart+segmentEnd)>>>1;
    }

    public long getInclusiveSumOfRange(int left, int right){
        if(left<0 || right>=lengthOfArray) {
            System.out.println("Wrong query!... ");
            return -1L;
        }
        return getSumUtil(0,lengthOfArray-1,left,right,0);
    }
    private long getSumUtil(int segmentStart, int segmentEnd, int queryStart, int queryEnd, int segmentIndex){
        if(segmentEnd<=queryEnd && segmentStart>=queryStart) return segmentArray[segmentIndex];
        if(queryStart > segmentEnd || queryEnd<segmentStart) return 0L;

        int mid = getMid(segmentStart,segmentEnd);
        return getSumUtil(segmentStart,mid,queryStart,queryEnd,segmentIndex*2+1)+
                getSumUtil(mid+1,segmentEnd,queryStart,queryEnd,segmentIndex*2+2);
    }

    public void updateIndex(int index, long value){
        long difference = value - arr[index];
        arr[index] = value;
        updateIndexUtil(0,lengthOfArray-1,index,difference,0);
    }
    private void updateIndexUtil(int segmentStart, int segmentEnd, int index, long difference,int segmentIndex){
        if(index<segmentStart || index>segmentEnd) return;
        segmentArray[segmentIndex] += difference;
        if(segmentStart==segmentEnd) return;
        int mid = getMid(segmentStart,segmentEnd);
        updateIndexUtil(segmentStart,mid,index,difference,segmentIndex*2+1);
        updateIndexUtil(mid+1,segmentEnd,index,difference,segmentIndex*2+2);
    }
    public void printSegmentTree(){
        System.out.println(Arrays.toString(segmentArray));
    }
}
class SumSegmentTreeClean{
    long[] sa;
    int n;

    public SumSegmentTreeClean(long[] arr){
        this.n = arr.length;
        int height = (int)Math.ceil(Math.log(n)/Math.log(2));
        int size = 2*(1<<height)-1;
        this.sa = new long[size];
        constructor(0,n-1,0,arr);
    }
    private long constructor(int ss, int se, int si, long[] arr){
        if(ss == se) return sa[si] = arr[ss];
        int mid = (ss+se)/2;
        return sa[si] = constructor(ss,mid,2*si+1,arr) + constructor(mid+1,se,2*si+2,arr);
    }
    public long getSum(int left, int right){
        return sum(0,n-1,0,left,right);
    }
    private long sum(int ss, int se, int si, int qs, int qe){
        if(qe<ss || se<qs) return 0L;
        if(qs<=ss && se<=qe) return sa[si];
        int mid = (ss+se)/2;
        return sum(ss,mid,2*si+1,qs,qe)+sum(mid+1,se,2*si+2,qs,qe);
    }
    public void update(int index, long val){
        updateSum(0,n-1,0,index,val);
    }
    private long updateSum(int ss, int se, int si, int index, long val){
        if(index>se || index<ss) return sa[si];
        if(index == se && index == ss) return sa[si] = val;
        int mid = (ss+se)/2;
        return sa[si] = updateSum(ss,mid,2*si+1,index,val)+updateSum(mid+1,se,2*si+2,index,val);
    }
}
class MinimumSegmentTree{
    private int lengthOfArray;
    private long[] arr;
    private long[] segmentArray;
    private int heightOfSegmentTree;

    public MinimumSegmentTree(long[] a){
        this.lengthOfArray = a.length;
        this.arr = new long[lengthOfArray];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        this.heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int lengthOfSegmentArray = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new long[lengthOfSegmentArray];
        minimumSegmentTreeConstructorUtil(0,lengthOfArray-1,0);
    }

    private long minimumSegmentTreeConstructorUtil(int segmentStart, int segmentEnd, int segmentIndex){
        if(segmentEnd == segmentStart) return segmentArray[segmentIndex] = arr[segmentStart];
        int mid = getMid(segmentStart,segmentEnd);
        return segmentArray[segmentIndex] = Math.min(minimumSegmentTreeConstructorUtil(segmentStart,mid,segmentIndex*2+1),
                minimumSegmentTreeConstructorUtil(mid+1,segmentEnd,segmentIndex*2+2));
    }

    private int getMid(int segmentStart, int segmentEnd){
        return (segmentStart+segmentEnd)>>1;
    }

    public long getMinimumInclusiveOfRange(int left, int right){
        return getMinimumUtil(0,lengthOfArray-1,left,right,0);
    }
    private long getMinimumUtil(int segmentStart, int segmentEnd, int queryStart, int queryEnd, int segmentIndex){
        if(queryStart<=segmentStart && segmentEnd<=queryEnd) return segmentArray[segmentIndex];
        if(queryEnd<segmentStart || segmentEnd<queryStart) return Long.MAX_VALUE;
        int mid = getMid(segmentStart,segmentEnd);
        return Math.min(getMinimumUtil(segmentStart,mid,queryStart,queryEnd,segmentIndex*2+1),
                getMinimumUtil(mid+1,segmentEnd,queryStart,queryEnd,segmentIndex*2+2));
    }
    public void update(int index, long value){
        updateConstructorUtil(0,lengthOfArray-1,0,index,value);
    }
    private long updateConstructorUtil(int segmentStart, int segmentEnd, int segmentIndex, int index, long newValue){
        if(index>segmentEnd || index<segmentStart) return segmentArray[segmentIndex];
        if(segmentStart==index && segmentEnd==index) return segmentArray[segmentIndex]=newValue;
        int mid = getMid(segmentStart,segmentEnd);
        return segmentArray[segmentIndex]=Math.min(updateConstructorUtil(segmentStart,mid,2*segmentIndex+1,index,newValue),
                updateConstructorUtil(mid+1,segmentEnd,2*segmentIndex+2,index,newValue));
    }
}
class MinimumSegmentTreeWithFrequencyCount{
    int lengthOfArray;
    int[] arr;
    Pair[] segmentArray;
    int heightOfSegmentTree;

    public MinimumSegmentTreeWithFrequencyCount(int[] a){
        this.lengthOfArray = a.length;
        this.arr = new int[lengthOfArray];
        for(int i=0;i<lengthOfArray;i++) arr[i] = a[i];
        this.heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int sizeOfSegmentTree = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new Pair[sizeOfSegmentTree];
        constructorUtil(0,lengthOfArray-1,0);
    }
    private Pair constructorUtil(int segmentStart, int segmentEnd, int segmentIndex){
        if(segmentStart==segmentEnd) return segmentArray[segmentIndex] = new Pair(arr[segmentStart],1);
        int mid = getMid(segmentStart,segmentEnd);
        Pair leftChild = constructorUtil(segmentStart,mid,segmentIndex*2+1);
        Pair rightChild = constructorUtil(mid+1,segmentEnd,segmentIndex*2+2);
        if(leftChild.minimumElement<rightChild.minimumElement) segmentArray[segmentIndex] = new Pair(leftChild.minimumElement, leftChild.frequency);
        else if(leftChild.minimumElement>rightChild.minimumElement) segmentArray[segmentIndex] = new Pair(rightChild.minimumElement, rightChild.frequency);
        else segmentArray[segmentIndex] = new Pair(rightChild.minimumElement, leftChild.frequency+rightChild.frequency);
        return segmentArray[segmentIndex];
    }
    private int getMid(int segmentStart, int segmentEnd){
        return (segmentStart+segmentEnd)>>1;
    }
    public Pair getMinimumWithFrequencyInclusiveRange(int left, int right){
        return minimumUtil(0,lengthOfArray-1,0,left,right);
    }
    private Pair minimumUtil(int segmentStart, int segmentEnd, int segmentIndex, int queryStart, int queryEnd){
        if(queryStart<=segmentStart && segmentEnd<=queryEnd) return segmentArray[segmentIndex];
        if(queryEnd<segmentStart || segmentEnd<queryStart) return new Pair();
        int mid = getMid(segmentStart, segmentEnd);
        Pair leftChild = minimumUtil(segmentStart,mid,segmentIndex*2+1,queryStart,queryEnd);
        Pair rightChild = minimumUtil(mid+1,segmentEnd,segmentIndex*2+2,queryStart,queryEnd);
        if(leftChild.minimumElement<rightChild.minimumElement) return leftChild;
        else if(rightChild.minimumElement<leftChild.minimumElement) return rightChild;
        return new Pair(leftChild.minimumElement,leftChild.frequency+rightChild.frequency);
    }
    public void update(int index, int newValue){
        updateUtil(0,lengthOfArray-1,0,index,newValue);
    }
    private Pair updateUtil(int segmentStart, int segmentEnd, int segmentIndex, int index, int newValue){
        if(index<segmentStart || index>segmentEnd) return segmentArray[segmentIndex];
        if(index==segmentStart && index==segmentEnd) return segmentArray[segmentIndex] = new Pair(newValue,1);
        int mid = getMid(segmentStart,segmentEnd);
        Pair leftChild = updateUtil(segmentStart,mid,segmentIndex*2+1,index,newValue);
        Pair rightChild = updateUtil(mid+1,segmentEnd,segmentIndex*2+2,index,newValue);
        if(leftChild.minimumElement<rightChild.minimumElement) segmentArray[segmentIndex] = new Pair(leftChild.minimumElement, leftChild.frequency);
        else if(leftChild.minimumElement>rightChild.minimumElement) segmentArray[segmentIndex] = new Pair(rightChild.minimumElement, rightChild.frequency);
        else segmentArray[segmentIndex] = new Pair(rightChild.minimumElement, leftChild.frequency+rightChild.frequency);
        return segmentArray[segmentIndex];
    }
    public void printSGA(){
        System.out.println(Arrays.toString(segmentArray));
    }
}
class Pair{
    int minimumElement;
    int frequency;

    public Pair(int minimumElement, int frequency){
        this.minimumElement = minimumElement;
        this.frequency = frequency;
    }
    public Pair(){
        this.minimumElement = Integer.MAX_VALUE;
        this.frequency = -1;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "minimumElement=" + minimumElement +
                ", frequency=" + frequency +
                '}';
    }
}

class MaximumSegmentTree{
    private int lengthOfArray;
    private long[] arr;
    private long[] segmentArray;
    private int heightOfSegmentTree;

    public MaximumSegmentTree(long[] a){
        this.lengthOfArray = a.length;
        this.arr = new long[lengthOfArray];
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        this.heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int lengthOfSegmentArray = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new long[lengthOfSegmentArray];
        maximumSegmentTreeConstructorUtil(0,lengthOfArray-1,0);
    }

    private long maximumSegmentTreeConstructorUtil(int segmentStart, int segmentEnd, int segmentIndex){
        if(segmentEnd == segmentStart) return segmentArray[segmentIndex] = arr[segmentStart];
        int mid = getMid(segmentStart,segmentEnd);
        return segmentArray[segmentIndex] = Math.max(maximumSegmentTreeConstructorUtil(segmentStart,mid,segmentIndex*2+1),
                maximumSegmentTreeConstructorUtil(mid+1,segmentEnd,segmentIndex*2+2));
    }

    private int getMid(int segmentStart, int segmentEnd){
        return (segmentStart+segmentEnd)>>1;
    }

    public long getMaximumInclusiveOfRange(int left, int right){
        return getMaximumUtil(0,lengthOfArray-1,left,right,0);
    }
    private long getMaximumUtil(int segmentStart, int segmentEnd, int queryStart, int queryEnd, int segmentIndex){
        if(queryStart<=segmentStart && segmentEnd<=queryEnd) return segmentArray[segmentIndex];
        if(queryEnd<segmentStart || segmentEnd<queryStart) return -1;
        int mid = getMid(segmentStart,segmentEnd);
        return Math.max(getMaximumUtil(segmentStart,mid,queryStart,queryEnd,segmentIndex*2+1),
                getMaximumUtil(mid+1,segmentEnd,queryStart,queryEnd,segmentIndex*2+2));
    }
    public void update(int index, long value){
        updateConstructorUtil(0,lengthOfArray-1,0,index,value);
    }
    private long updateConstructorUtil(int segmentStart, int segmentEnd, int segmentIndex, int index, long newValue){
        if(index>segmentEnd || index<segmentStart) return segmentArray[segmentIndex];
        if(segmentStart==index && segmentEnd==index) return segmentArray[segmentIndex]=newValue;
        int mid = getMid(segmentStart,segmentEnd);
        return segmentArray[segmentIndex]=Math.max(updateConstructorUtil(segmentStart,mid,2*segmentIndex+1,index,newValue),
                updateConstructorUtil(mid+1,segmentEnd,2*segmentIndex+2,index,newValue));
    }
}
class GCDSegmentTree{
    int[] sa;
    int n;

    public GCDSegmentTree(int[] arr){
        this.n = arr.length;
        int height = (int)Math.ceil(Math.log(n)/Math.log(2));
        int size = 2*(1<<height) - 1;
        this.sa = new int[size];
        constructor(0,n-1,0,arr);
    }
    private int constructor(int ss, int se, int si, int[] arr){
        if(ss == se) return sa[si] = arr[ss];
        int m = getMid(ss,se);
        return sa[si] = getGCD(constructor(ss,m,2*si+1,arr),constructor(m+1,se,2*si+2,arr));
    }
    private int getMid(int ss, int se){
        return (ss+se)>>1;
    }
    private int getGCD(int a, int b){
        if(a%b == 0) return b;
        return getGCD(b,a%b);
    }
    private int queryGCD(int aa, int bb){
        if(aa==0) return bb;
        if(bb==0) return aa;
        return getGCD(Math.max(aa,bb),Math.min(aa,bb));
    }
    public int getGCDInclusiveRange(int left, int right){
        return query(0,n-1,0,left,right);
    }
    private int query(int ss, int se, int si, int qs, int qe){
        if(se<qs || qe<ss) return 0;
        if(qs<=ss && qe>=se) return sa[si];
        int m = getMid(ss,se);
        return queryGCD(query(ss,m,2*si+1,qs,qe),query(m+1,se,2*si+2,qs,qe));
    }
}
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
