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
