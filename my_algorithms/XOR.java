package kg.my_algorithms;

import java.util.HashMap;
import java.util.Map;

public class XOR {
    public long numberOfSubarraysWithXorEqualsK(long[] a, long k) {
        Map<Long,Long> map = new HashMap<>();
        int n = a.length;
        long[] pref = new long[n];
        pref[0] = a[0];
        for(int i=1;i<n;i++){
            pref[i] = pref[i-1]^a[i];
        }
        long cnt = 0;
        for(int i=0;i<n;i++){
            long c = pref[i];
            if(c == k) cnt++;
            cnt += map.getOrDefault(c^k,0L);
            map.put(c,map.getOrDefault(c,0L)+1);
        }
        return cnt;
    }
}
class BinaryTrie{
    TrieNode root;
    int[] masks;
    public BinaryTrie(){
        root = new TrieNode();
        masks = new int[31];
        for(int i=30;i>=0;i--) masks[i] = 1<<i;
    }
    public void add_num(int num){
        TrieNode node = root;
        for(int i=30;i>=0;i--){
            if((masks[i]&num) == 0){
                if(node.left == null) node.left = new TrieNode();
                node = node.left;
            }
            else{
                if(node.right == null) node.right = new TrieNode();
                node = node.right;
            }
            node.freq++;
        }
    }
    public void remove(int num){
        TrieNode node = root;
        for(int i=30;i>=0;i--){
            if((masks[i]&num) == 0) node = node.left;
            else node = node.right;
            node.freq--;
        }
    }
    public int maxXor(int num){
        TrieNode node = root;
        int res = 0;
        for(int i=30;i>=0;i--){
            if((masks[i]&num) == 0){
                if(node.right!=null && node.right.freq>0) {
                    res += masks[i];
                    node = node.right;
                }
                else node = node.left;
            }
            else{
                if(node.left != null && node.left.freq>0){
                    res += masks[i];
                    node = node.left;
                }
                else node = node.right;
            }

        }
        return res;
    }
    static class TrieNode{
        TrieNode left;
        TrieNode right;
        int freq;
        public TrieNode(){
            left = null;
            right = null;
            freq=0;
        }

    }
}
