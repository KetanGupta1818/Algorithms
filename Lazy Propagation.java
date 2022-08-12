class SegmentTree{
    long[] segmentArray;
    long[] lazy;
    int length;

    public SegmentTree(int n){
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
            lazy[si] = value;
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
