class MaximalSumSegmentTree{
    int lengthOfArray;
    Node[] segmentArray;

    public MaximalSumSegmentTree(long[] arr){
        this.lengthOfArray = arr.length;
        int heightOfSegmentTree = (int)Math.ceil(Math.log(lengthOfArray)/Math.log(2));
        int sizeOfSegmentArray = 2*(1<<heightOfSegmentTree)-1;
        this.segmentArray = new Node[sizeOfSegmentArray];
        constructorUtil(0,lengthOfArray-1,0,arr);
    }
    public Node constructorUtil(int segmentStart, int segmentEnd, int segmentIndex, long[] arr){
        if(segmentEnd==segmentStart){
            if(arr[segmentStart]>=0){
                return segmentArray[segmentIndex] = new Node(arr[segmentStart],arr[segmentStart],arr[segmentStart],arr[segmentStart]);
            }
            return segmentArray[segmentIndex] = new Node(0,0,arr[segmentStart],0);
        }
        int mid = getMid(segmentStart,segmentEnd);
        Node leftChild = constructorUtil(segmentStart,mid,segmentIndex*2+1,arr);
        Node rightChild = constructorUtil(mid+1,segmentEnd,segmentIndex*2+2,arr);
        return segmentArray[segmentIndex] = new Node(Math.max(leftChild.prefixSum,leftChild.totalSum+rightChild.prefixSum),
                Math.max(rightChild.suffixSum,rightChild.totalSum+ leftChild.suffixSum),
                leftChild.totalSum+ rightChild.totalSum,
                Math.max(leftChild.suffixSum+ rightChild.prefixSum,Math.max(leftChild.maximumSegmentSum,rightChild.maximumSegmentSum)));
    }
    private int getMid(int segmentStart, int segmentEnd){
        return (segmentStart+segmentEnd)>>1;
    }
    public Node updateQuery(int index, long newValue){
        return updateUtil(0,lengthOfArray-1,0,index,newValue);
    }
    private Node updateUtil(int segmentStart, int segmentEnd, int segmentIndex, int index, long newValue){
        if(index<segmentStart || index>segmentEnd) return segmentArray[segmentIndex];
        if(index==segmentStart && index==segmentEnd) {
            if(newValue>=0){
                return segmentArray[segmentIndex] = new Node(newValue,newValue,newValue,newValue);
            }
            return segmentArray[segmentIndex] = new Node(0,0,newValue,0);
        }
        int mid = getMid(segmentStart,segmentEnd);
        Node leftChild = updateUtil(segmentStart,mid,segmentIndex*2+1,index,newValue);
        Node rightChild = updateUtil(mid+1,segmentEnd,segmentIndex*2+2,index,newValue);
        return segmentArray[segmentIndex] = new Node(Math.max(leftChild.prefixSum,leftChild.totalSum+rightChild.prefixSum),
                Math.max(rightChild.suffixSum,rightChild.totalSum+ leftChild.suffixSum),
                leftChild.totalSum+ rightChild.totalSum,
                Math.max(leftChild.suffixSum+ rightChild.prefixSum,Math.max(leftChild.maximumSegmentSum,rightChild.maximumSegmentSum)));
    }
}
class Node{
    long prefixSum;
    long suffixSum;
    long totalSum;
    long maximumSegmentSum;

    public Node(long prefixSum, long suffixSum, long totalSum, long maximumSegmentSum) {
        this.prefixSum = prefixSum;
        this.suffixSum = suffixSum;
        this.totalSum = totalSum;
        this.maximumSegmentSum = maximumSegmentSum;
    }
}
