package kg.my_algorithms;

import java.util.*;

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
        if(queryEnd<segmentStart || segmentEnd<queryStart) return Long.MIN_VALUE;
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
    // return -1 if there is no element >= x
    // else return 0 based index
    public int smallestIndexGreaterThanEqualToXInRange(long x, int left, int right){
        return get_first(0,lengthOfArray-1,0,left,right,x);
    }

    private int get_first(int ss, int se, int si, int qs, int qe, long x){
        if(qe<ss || se<qs) return -1;
        if(qs<=ss && se<=qe){
            if(segmentArray[si]<x) return -1; //change to <= in case of strict equality
            while(ss!=se){
                int mid = getMid(ss,se);
                if(segmentArray[2*si+1]>=x){  //change to > in case of strict equality
                    se = mid;
                    si = 2*si+1;
                }
                else{
                    ss = mid+1;
                    si = 2*si+2;
                }
            }
            return ss;
        }
        int mid = getMid(ss,se);
        int res = get_first(ss,mid,2*si+1,qs,qe,x);
        if(res != -1) return res;
        return get_first(mid+1,se,2*si+2,qs,qe,x);
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
//If there are more than 1 minimum element, the right_most index is returned
/*   To change this
     if(left.maximumElement<right.maximumElement) table[i][j] = new Pair21(left); add = sign
     if(p1.maximumElement<p2.maximumElement) return p1; add = sign
*/
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

//No constructor available. Store array values in main function where you are calling this segment tree and add accordingly
//value at i -> sg.valueAtIndex(i) + arr[i];
class MassUpdateSumSegmentTree{
    int lengthOfArray;
    long[] segmentArray;

    public MassUpdateSumSegmentTree(long[] arr){
        this.lengthOfArray = arr.length;
        int heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int sizeOfSegmentTree = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new long[sizeOfSegmentTree];
    }

    public void addToSegment(int left, int right, long value){
        addUtil(0,lengthOfArray-1,0,left,right,value);
    }

    private void addUtil(int ss, int se, int si, int qs, int qe, long value){
        if(qe<ss || se<qs) return;
        if(qs<=ss && se<=qe) {
            segmentArray[si] += value;
            return;
        }
        int mid = (ss+se)>>1;
        addUtil(ss,mid,2*si+1,qs,qe,value);addUtil(mid+1,se,2*si+2,qs,qe,value);
    }

    public long getValueAtIndex(int index){
        return getIndex(0,lengthOfArray-1,0,index);
    }
    private long getIndex(int ss, int se, int si, int index){
        if(index<ss || index>se) return 0L;
        if(index == ss && index == se) return segmentArray[si];
        int mid = (ss+se)>>1;
        //  if(index == 2) System.out.println("mid: " + mid);
        return getIndex(ss,mid,2*si+1,index)+getIndex(mid+1,se,2*si+2,index)+segmentArray[si];
    }
    public void printSg(){
        System.out.println(Arrays.toString(segmentArray));
    }
}
class IntegerMassUpdateSumSegmentTree {
    int lengthOfArray;
    int[] segmentArray;

    public IntegerMassUpdateSumSegmentTree(int[] arr){
        this.lengthOfArray = arr.length;
        int heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int sizeOfSegmentTree = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new int[sizeOfSegmentTree];
    }

    public void addToSegment(int left, int right, int value){
        addUtil(0,lengthOfArray-1,0,left,right,value);
    }

    private void addUtil(int ss, int se, int si, int qs, int qe, int value){
        if(qe<ss || se<qs) return;
        if(qs<=ss && se<=qe) {
            segmentArray[si] += value;
            return;
        }
        int mid = (ss+se)>>1;
        addUtil(ss,mid,2*si+1,qs,qe,value);addUtil(mid+1,se,2*si+2,qs,qe,value);
    }

    public int getValueAtIndex(int index){
        return getIndex(0,lengthOfArray-1,0,index);
    }
    private int getIndex(int ss, int se, int si, int index){
        if(index<ss || index>se) return 0;
        if(index == ss && index == se) return segmentArray[si];
        int mid = (ss+se)>>1;
        //  if(index == 2) System.out.println("mid: " + mid);
        return getIndex(ss,mid,2*si+1,index)+getIndex(mid+1,se,2*si+2,index)+segmentArray[si];
    }
    public void printSg(){
        System.out.println(Arrays.toString(segmentArray));
    }
}

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
class PersistentSumSegmentTree{
    private final List<Node> heads;
    private final int n;
    public PersistentSumSegmentTree(long[] arr){
        this.n = arr.length;
        heads = new ArrayList<>();
        heads.add(construct(0,n-1,arr));
    }
    private Node construct(int ss, int se, long[] arr){
        if(ss == se) return new Node(arr[ss]);
        int mid = (ss+se)>>1;
        Node left = construct(ss,mid,arr);
        Node right = construct(mid+1,se,arr);
        return new Node(left.value + right.value, left, right);
    }
    public void updateIndex(int version, int index, long value){
        heads.add(update(0,n-1,heads.get(version),index,value));
    }
    private Node update(int ss, int se, Node node, int index, long value){
        if(index<ss || index>se) return node;
        if(index == ss && index == se) return new Node(value);
        int mid = (ss+se)>>1;
        Node left = update(ss,mid,node.left,index,value);
        Node right = update(mid+1,se,node.right,index,value);
        return new Node(left.value + right.value, left, right);
    }
    public long getSum(int version, int left, int right){
        return sum(0,n-1,heads.get(version),left,right);
    }
    private long sum(int ss, int se, Node node, int qs, int qe){
        if(qe<ss || se<qs) return 0L;
        if(qs<=ss && qe>=se) return node.value;
        int mid = (ss+se)>>1;
        return sum(ss,mid,node.left,qs,qe) + sum(mid+1,se,node.right,qs,qe);
    }
    private static class Node{
        Node left,right;
        long value;
        public Node(long value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public Node(long value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
class KthElementInASubarrayTree{
    private final List<Node> heads;
    private final List<Long> list = new ArrayList<>();
    private final int n;
    public KthElementInASubarrayTree(long[] arr){
        n = arr.length;
        heads = new ArrayList<>();
        for(long a: arr) list.add(a);
        list.sort(null);
        Map<Long,Integer> map = new HashMap<>();
        for(int i = 0; i< n; i++){
            map.put(list.get(i),i);
        }
        heads.add(construct(0, n -1,new int[n]));
        for(int i = 0; i< n; i++){
            heads.add(update(0, n -1,heads.get(i),map.get(arr[i]),1));
        }

    }
    private Node construct(int ss, int se, int[] arr){
        if(ss == se) return new Node(arr[ss]);
        int mid = (ss+se)>>1;
        Node left = construct(ss,mid,arr);
        Node right = construct(mid+1,se,arr);
        return new Node(left.value + right.value, left, right);
    }
    private Node update(int ss, int se, Node node, int index, int value){
        if(index<ss || index>se) return node;
        if(index == ss && index == se) return new Node(value);
        int mid = (ss+se)>>1;
        Node left = update(ss,mid,node.left,index,value);
        Node right = update(mid+1,se,node.right,index,value);
        return new Node(left.value + right.value, left, right);
    }
    public long kthNumberInSubarray(int left, int right, int k){
        return kthFunction(0,n-1,heads.get(left),heads.get(right+1),k);
    }
    private long kthFunction(int ss, int se, Node leftEnd, Node rightEnd, int k){
        if(ss == se) return list.get(ss);
        int mid = (ss+se)>>1;
        int left_numbers = rightEnd.left.value - leftEnd.left.value;
        if(left_numbers<k) return kthFunction(mid+1,se,leftEnd.right, rightEnd.right,k-left_numbers);
        return kthFunction(ss,mid,leftEnd.left,rightEnd.left,k);
    }
    private static class Node{
        Node left,right;
        int value;
        public Node(int value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public Node(int value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
class MassUpdateORTree{
    int n;
    long[] sa;
    long[] lazy;
    public MassUpdateORTree(long[] arr){
        this.n = arr.length;
        int h = (int)Math.ceil(Math.log(n)/Math.log(2));
        int len_sa = 2*(1<<h) - 1;
        this.sa = new long[len_sa];
        this.lazy = new long[len_sa];
        constructor(0,n-1,0,arr);
    }
    private long constructor(int ss, int se, int si, long[] arr){
        lazy[si] = -10;
        if(ss == se) return sa[si] = arr[ss];
        int mid = (ss+se)>>1;
        return sa[si] = constructor(ss,mid,2*si+1,arr) | constructor(mid+1,se,2*si+2,arr);
    }
    private void propagate(int si){
        if(lazy[si]!=-10){
            sa[si] = lazy[si];
            if(2*si+1<sa.length) lazy[2*si+1] = lazy[2*si+2] = lazy[si];
            lazy[si] = -10;
        }
    }
    private long rangeOr(int ss, int se, int si, int qs, int qe){
        propagate(si);
        if(se<qs || qe<ss) return 0L;
        if(qs<=ss && se<=qe) return sa[si];
        int mid = (ss+se)>>1;
        return  rangeOr(ss,mid,2*si+1,qs,qe) | rangeOr(mid+1,se,2*si+2,qs,qe);
    }
    private long updateRange(int ss, int se, int si, int qs, int qe, long value){
        propagate(si);
        if(se<qs || qe<ss) return sa[si];
        if(qs<=ss && se<=qe) return lazy[si] = value;
        int mid = (ss+se)>>1;
        return sa[si] = updateRange(ss,mid,2*si+1,qs,qe,value) | updateRange(mid+1,se,2*si+2,qs,qe,value);
    }
    public long getRangeOr(int left, int right){
        return rangeOr(0,n-1,0,left,right);
    }
    public void update(int left, int right, long value){
        updateRange(0,n-1,0,left,right,value);
    }
}
class MassAddSumSegmentTree{
    int n;
    long[] sa;
    long[] lazy;
    int[] sizes;
    public MassAddSumSegmentTree(long[] arr){
        this.n = arr.length;
        int h = (int)Math.ceil(Math.log(n)/Math.log(2));
        int len = 2*(1<<h) - 1;
        this.sa = new long[len];
        this.lazy = new long[len];
        this.sizes = new int[len];
        constructor(0,n-1,0,arr);
        fill_sizes(0,n-1,0);
    }
    private long constructor(int ss, int se, int si, long[] arr){
        if(ss == se) return sa[si] = arr[ss];
        int mid = (ss+se) >> 1;
        return sa[si] = constructor(ss,mid,2*si+1,arr) + constructor(mid+1,se,2*si+2,arr);
    }
    private int fill_sizes(int ss, int se, int si){
        if(ss == se) return sizes[si] = 1;
        int mid = (ss+se)>>1;
        return sizes[si] = fill_sizes(ss,mid,2*si+1) + fill_sizes(mid+1,se,2*si+2);
    }
    private void propagate(int si){
        if(lazy[si]!=0){
            sa[si] += lazy[si]*sizes[si];
            if(2*si+2 < sa.length) {
                lazy[2*si+1] += lazy[si];
                lazy[2*si+2] += lazy[si];
            }
            lazy[si] = 0;
        }
    }
    public long sum(int left, int right){
        return rangeSum(0,n-1,0,left,right);
    }
    public void addInRange(int left, int right, long value){
        update(0,n-1,0,left,right,value);
    }
    private long rangeSum(int ss, int se, int si, int qs, int qe){
        propagate(si);
        if(se<qs || qe<ss) return 0L;
        if(qs<=ss && se<=qe) return sa[si];
        int mid = (ss+se)>>1;
        return rangeSum(ss,mid,2*si+1,qs,qe) + rangeSum(mid+1,se,2*si+2,qs,qe);
    }
    private long update(int ss, int se, int si, int qs, int qe, long value){
        propagate(si);
        if(se<qs || qe<ss) return sa[si];
        if(qs<=ss && se<=qe) {
            lazy[si] += value;
            propagate(si);
            return sa[si];
        }
        int mid = (ss+se)>>1;
        return sa[si] = update(ss,mid,2*si+1,qs,qe,value) + update(mid+1,se,2*si+2,qs,qe,value);
    }
}
class MassAddMaximumSegmentTree {
    long[] segmentArray;
    long[] lazy;
    int length;

    public MassAddMaximumSegmentTree(int n){
        this.length = n;
        int height = (int)Math.ceil(Math.log(length)/Math.log(2));
        int lengthOfSegmentTree = 2*(1<<height)-1;
        this.segmentArray = new long[lengthOfSegmentTree];
        this.lazy = new long[lengthOfSegmentTree];
    }

    public void addToRange(int left, int right, long value){
        add(0,length-1,0,left,right,value);
    }
    private long add(int ss, int se, int si, int qs, int qe, long value){
        if(se<qs || qe<ss) return segmentArray[si];
        if(ss != se) propagate(si);
        if(qs<=ss && se<=qe){
            lazy[si] += value;
            return segmentArray[si] = segmentArray[si]+value;
        }
        int mid = (ss+se)>>1;
        return segmentArray[si] = Math.max(add(ss,mid,2*si+1,qs,qe,value),add(mid+1,se,2*si+2,qs,qe,value));
    }

    public long getMaximumInRange(int left, int right){
        return getMax(0,length-1,0,left,right);
    }

    private long getMax(int ss, int se, int si, int qs, int qe){
        if(se<qs || qe<ss) return Long.MIN_VALUE;
        if(ss != se) propagate(si);
        if(qs<=ss && se<=qe) return segmentArray[si];
        int mid = (ss+se)>>1;
        return Math.max(getMax(ss,mid,2*si+1,qs,qe), getMax(mid+1,se,2*si+2,qs,qe));
    }


    private void propagate(int si){
        if(lazy[si]!=0){
            segmentArray[2*si+1] += lazy[si];
            segmentArray[2*si+2] += lazy[si];
            lazy[2*si+1] += lazy[si];
            lazy[2*si+2] += lazy[si];
            lazy[si] = 0;
        }
    }

    public void print(){
        System.out.println("SA: " + Arrays.toString(segmentArray));
        System.out.println("Lazy: " + Arrays.toString(lazy));
    }
}
class MassAddMinimumSegmentTree {
    long[] segmentArray;
    long[] lazy;
    int length;

    public MassAddMinimumSegmentTree(int n){
        this.length = n;
        int height = (int)Math.ceil(Math.log(length)/Math.log(2));
        int lengthOfSegmentTree = 2*(1<<height)-1;
        this.segmentArray = new long[lengthOfSegmentTree];
        this.lazy = new long[lengthOfSegmentTree];
    }

    public void addToRange(int left, int right, long value){
        add(0,length-1,0,left,right,value);
    }
    private long add(int ss, int se, int si, int qs, int qe, long value){
        if(se<qs || qe<ss) return segmentArray[si];
        if(ss != se) propagate(si);
        if(qs<=ss && se<=qe){
            lazy[si] += value;
            return segmentArray[si] = segmentArray[si]+value;
        }
        int mid = (ss+se)>>1;
        return segmentArray[si] = Math.min(add(ss,mid,2*si+1,qs,qe,value),add(mid+1,se,2*si+2,qs,qe,value));
    }

    public long getMinimumInRange(int left, int right){
        return getMin(0,length-1,0,left,right);
    }

    private long getMin(int ss, int se, int si, int qs, int qe){
        if(se<qs || qe<ss) return Long.MAX_VALUE;
        if(ss != se) propagate(si);
        if(qs<=ss && se<=qe) return segmentArray[si];
        int mid = (ss+se)>>1;
        return Math.min(getMin(ss,mid,2*si+1,qs,qe),getMin(mid+1,se,2*si+2,qs,qe));
    }


    private void propagate(int si){
        if(lazy[si]!=0){
            segmentArray[2*si+1] += lazy[si];
            segmentArray[2*si+2] += lazy[si];
            lazy[2*si+1] += lazy[si];
            lazy[2*si+2] += lazy[si];
            lazy[si] = 0;
        }
    }

    public void print(){
        System.out.println("SA: " + Arrays.toString(segmentArray));
        System.out.println("Lazy: " + Arrays.toString(lazy));
    }
}