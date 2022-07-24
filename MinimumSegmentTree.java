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
