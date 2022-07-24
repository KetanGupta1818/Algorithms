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
