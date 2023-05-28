class PersistentSumSegmentTree{
    private final List<Node> heads;
    private final int n;
    public PersistentSumSegmentTree(long[] arr){
        this.n = arr.length;
        heads = new ArrayList<>();
        heads.add(construct(0,n-1,arr));
    }
    private Node construct(int ss, int se, long[] arr){
        if(ss == se) return new Node(arr[ss]);
        int mid = (ss+se)>>1;
        Node left = construct(ss,mid,arr);
        Node right = construct(mid+1,se,arr);
        return new Node(left.value + right.value, left, right);
    }
    public void updateIndex(int version, int index, long value){
        heads.add(update(0,n-1,heads.get(version),index,value));
    }
    private Node update(int ss, int se, Node node, int index, long value){
        if(index<ss || index>se) return node;
        if(index == ss && index == se) return new Node(value);
        int mid = (ss+se)>>1;
        Node left = update(ss,mid,node.left,index,value);
        Node right = update(mid+1,se,node.right,index,value);
        return new Node(left.value + right.value, left, right);
    }
    public long getSum(int version, int left, int right){
        return sum(0,n-1,heads.get(version),left,right);
    }
    private long sum(int ss, int se, Node node, int qs, int qe){
        if(qe<ss || se<qs) return 0L;
        if(qs<=ss && qe>=se) return node.value;
        int mid = (ss+se)>>1;
        return sum(ss,mid,node.left,qs,qe) + sum(mid+1,se,node.right,qs,qe);
    }
    private static class Node{
        Node left,right;
        long value;
        public Node(long value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public Node(long value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
