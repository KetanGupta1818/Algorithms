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
