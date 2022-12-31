import java.util.*;
public class BinarySearch {
    public static int searchInsert(int[] nums, int target) {
        int lo=0,hi=nums.length-1;
        while(lo<=hi){
            int m=lo+(hi-lo)/2;
            if(nums[m]==target) return m;
            if(nums[m]>target) hi=m-1;
            else lo=m+1;
        }
        return lo;
    }
    public static int searchInsert(List<Integer> nums, int target) {
        int lo=0,hi=nums.size()-1;
        while(lo<=hi){
            int m=lo+(hi-lo)/2;
            if(nums.get(m) ==target) return m;
            if(nums.get(m) >target) hi=m-1;
            else lo=m+1;
        }
        return lo;
    }
    private static int smallestNumberIndexGreaterThanOrEqualToTarget(int[] arr, int target){
        int lo=0,hi=arr.length-1;
        while(lo<=hi){
            int m = lo+hi>>1;
            if(arr[m]>=target) hi=m-1;
            else lo=m+1;
        }
        return hi+1;
    }
    public static int findClosest(int[] arr, int target)
    {
        int n = arr.length;
        int index = findInsertionPositionWithDuplicatesLeftAlign(arr,target);
        if(index==0) return arr[index];
        if(index==n) return arr[index-1];
        if(target-arr[index-1]<arr[index]-target) return arr[index-1];
        return arr[index];
    }
    private static int findInsertionPositionWithDuplicatesLeftAlign(int[] arr, int target){
        int lo=0,hi=arr.length-1;
        while(lo<=hi){
            int m = (lo+hi)/2;
            if(arr[m]>=target) hi=m-1;
            else lo=m+1;
        }
        return hi+1;
    }
    private static int search(List<Integer> list, int target){
        int lo=0,hi=list.size()-1;
        while(lo <= hi) {
            int m = (lo+hi)/2;
            if(list.get(m) == target) return m;
            else if(list.get(m) > target) hi = m-1;
            else lo=m+1;
        }
        return -1;
    }
    private static int numberValuesBetween(int left, int right,List<Integer> list){
        int left_index = left_search(list,left);
        int right_index = right_search(list,right);
        if(right_index>=left_index) return right_index-left_index+1;
        return 0;
    }
    private static int left_search(List<Integer> list, int target){
        int lo = 0, hi = list.size()-1;
        while(lo <= hi){
            int m = (lo+hi)>>1;
            if(list.get(m) >= target) hi = m-1;
            else lo = m+1;
        }
        return hi+1;
    }
    private static int right_search(List<Integer> list, int target){
        int lo=0,hi=list.size()-1;
        while(lo<=hi){
            int m = (lo+hi)>>1;
            if(list.get(m) > target) hi=m-1;
            else lo=m+1;
        }
        return lo-1;
    }
}
