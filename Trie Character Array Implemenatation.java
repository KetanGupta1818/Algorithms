class Trie {
    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char curChar = word.charAt(i);
            if(!node.hasLinkTo(curChar)) node.putLinkTo(curChar,new TrieNode());
            node = node.getLinkTo(curChar);
        }
        node.setIsEnd();
    }
    public TrieNode searchPrefix(String word){
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char curChar = word.charAt(i);
            if(!node.hasLinkTo(curChar)) return null;
            node = node.getLinkTo(curChar);
        }
        return node;
    }
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node!=null && node.getIsEnd();
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix)!=null;
    }
}
class TrieNode{
    private TrieNode[] links;
    private int R;
    private boolean isEnd;
    public TrieNode(){
        this.R = 26;
        this.links = new TrieNode[R];
    }
    public boolean hasLinkTo(char ch){
        return this.links[ch-'a']!=null;
    }
    public TrieNode getLinkTo(char ch){
        return this.links[ch-'a'];
    }
    public void putLinkTo(char ch, TrieNode node){
        this.links[ch-'a']=node;
    }
    public void setIsEnd(){
        this.isEnd = true;
    }
    public boolean getIsEnd(){
        return this.isEnd;
    }
}
