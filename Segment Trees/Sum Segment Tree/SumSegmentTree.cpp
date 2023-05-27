class SumSegmentTree{
    private:
    int n,*sa,h,st_len;
    int constructor(int ss, int se, int si, int a[]){
        if(ss == se) return sa[si] = a[ss];
        int mid = (ss+se)>>1;
        return sa[si] = constructor(ss,mid,2*si+1,a) + constructor(mid+1,se,2*si+2,a);
    }
    int sum(int ss, int se, int si, int qs, int qe){
        if(qe<ss || se<qs) return 0;
        if(qs<=ss && qe>=se) return sa[si];
        int mid = (ss+se)>>1;
        return sum(ss,mid,2*si+1,qs,qe) + sum(mid+1,se,2*si+2,qs,qe);
    }
    int update(int ss, int se, int si, int index, int value){
        if(index<ss || index>se) return sa[si];
        if(index == ss && index == se) return sa[si] = value;
        int mid = (ss+se) >> 1;
        return sa[si] = update(ss,mid,2*si+1,index,value) + update(mid+1,se,2*si+2,index,value);
    }
    public: 
    SumSegmentTree(int a[], int a_len){
        n = a_len;
        h = ceil(log(n)/log(2));
        st_len = (1<<(h+1))-1;
        sa = (int*)malloc(st_len*sizeof(int));
        constructor(0,n-1,0,a);
    }
    int getSum(int left, int right){
        if(left > right) return 0;
        return sum(0,n-1,0,left,right);
    }
    void updateIndex(int index, int value){
        update(0,n-1,0,index,value);
    }
};
