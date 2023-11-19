package kg.my_algorithms;
import java.util.*;
public class General {
    private static int[] prevSmaller(List<Integer> arr){
        int n = arr.size();
        int[] pse = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for(int i=n-1;i>=0;i--){
            pse[i] = -1;
            while(!stack.isEmpty() && arr.get(stack.peek()) > arr.get(i)) pse[stack.pop()] = i;
            stack.push(i);
        }
        return pse;
    }
    private static int[] nextSmaller(List<Integer> arr){
        int n = arr.size();
        int[] nse = new int[n];
        ArrayDeque<Integer> stack  = new ArrayDeque<>();
        for(int i=0;i<n;i++){
            nse[i] = n;
            while(!stack.isEmpty() && arr.get(stack.peek()) > arr.get(i)) nse[stack.pop()] = i;
            stack.push(i);
        }
        return nse;
    }
    private static int[] prevSmaller(int[] arr){
        int n = arr.length;
        int[] pse = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for(int i=n-1;i>=0;i--){
            pse[i] = -1;
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]) pse[stack.pop()] = i;
            stack.push(i);
        }
        return pse;
    }
    private static int[] nextSmaller(int[] arr){
        int n = arr.length;
        int[] nse = new int[n];
        ArrayDeque<Integer> stack  = new ArrayDeque<>();
        for(int i=0;i<n;i++){
            nse[i] = n;
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]) nse[stack.pop()] = i;
            stack.push(i);
        }
        return nse;
    }
}
