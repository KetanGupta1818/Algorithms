class StringHash{
    private static final long A = 911382323L;
    private static final long B = 972663749L;
    private long[] hash;
    private long[] powersOfA;

    public StringHash(String s){
        this.hash = new long[s.length()];
        this.powersOfA = new long[s.length()];
        hash[0] = (long)s.charAt(0);
        powersOfA[0] = 1L;
        for(int i=1;i<s.length();i++){
            hash[i] = (hash[i-1]*A + (long)s.charAt(i))%B;
            powersOfA[i] = (powersOfA[i-1]*A)%B;
        }
    }
    public long getHashOfSubstringInclusive(int left, int right){
        if(left<0 || right>=powersOfA.length){
            System.out.println("Index out bounds for substring [" + left + " ," +right+"]");
            return -1L;
        }
        if(left == 0) return this.hash[right];
        return Math.floorMod((hash[right]-hash[left-1]*powersOfA[right-left+1]),B); //Adding B is very important, else use Math.floorMod
    }
}
