package kg.my_algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;






/*
        //BE CAREFUL WITH OVERFLOW, this is for INT
Initialize the multiset by array
Multiset Properties->
    1. remove element in log(N)
    2. calculate the numberOfElements between any range [left, right]

 */
public class MyMultiset {
}
class SumSegmentTree1 {
    int[] sa;
    int n;

    public SumSegmentTree1(int[] arr){
        this.n = arr.length;
        int height = (int)Math.ceil(Math.log(n)/Math.log(2));
        int sl = 2*(1<<height)-1;
        this.sa = new int[sl];
        constructor(0,n-1,0,arr);
    }
    private int constructor(int ss, int se, int si, int[] arr){
        if(ss == se) return sa[si] = arr[ss];
        int mid = (ss+se)>>1;
        return sa[si] = constructor(ss,mid,2*si+1,arr)+constructor(mid+1,se,2*si+2,arr);
    }
    public int getSum(int left, int right){
        return sum_util(0,n-1,0,left,right);
    }
    private int sum_util(int ss, int se, int si, int qs, int qe){
        if(se<qs || qe<ss) return 0;
        if(qs<=ss && qe>=se) return sa[si];
        int mid = (ss+se)>>1;
        return sum_util(ss,mid,2*si+1,qs,qe)+sum_util(mid+1,se,2*si+2,qs,qe);
    }
    public void remove(int index){
        this.decrease(0,n-1,0,index);
    }
    private int decrease(int ss, int se, int si, int index){
        if(index>se || index<ss) return sa[si];
        if(index == se && index == ss) return sa[si] = sa[si]-1;
        int mid = (ss+se)>>1;
        return sa[si] = decrease(ss,mid,2*si+1,index)+decrease(mid+1,se,2*si+2,index);
    }
}
class MultiSet{
    int[] arr;
    TreeSet<Integer> set;
    Map<Integer,Integer> map;
    int[] segment_arr;
    SumSegmentTree1 segmentTree;
    public MultiSet(int[] array){
        set = new TreeSet<>();
        map = new HashMap<>();
        this.arr = new int[array.length];
        for(int i = 0; i< array.length; i++) {
            arr[i] = array[i];
            set.add(array[i]);
        }
        int i=1;
        for(int s: set){
            map.put(s,i++);
        }
        int distinct_numbers = map.size();
        segment_arr = new int[distinct_numbers+1];
        for(int a: arr) segment_arr[map.get(a)]++;
        segmentTree = new SumSegmentTree1(segment_arr);
    }
    public void remove(int a){
        segmentTree.remove(map.get(a));
    }
    public int numberOfValuesInRange(int left, int right){
        Integer left_limit = set.ceiling(left);
        Integer right_limit = set.floor(right);
        if(left_limit == null || right_limit == null) return 0;
        int ll  = left_limit;
        int rr = right_limit;
        if(ll>rr) return 0;
        return segmentTree.getSum(map.get(ll),map.get(rr));
    }
}