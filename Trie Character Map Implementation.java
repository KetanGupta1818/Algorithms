class TrieNode{
    private Map<Character,TrieNode> children;
    private boolean end;

    public TrieNode(){
        this.children = new HashMap<>();
    }

    public boolean hasLinkToCharacter(char c){
        return children.containsKey(c);
    }

    public TrieNode getLinkToCharacter(char c){
        return children.get(c);
    }

    public void putLinkToCharacter(char c){
        children.put(c,new TrieNode());
    }

    public boolean isEnd(){
        return end;
    }

    public void setEnd(){
        end = true;
    }
}

class Trie{
    private TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    public void insert(String word){
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char cur = word.charAt(i);
            if(!node.hasLinkToCharacter(cur)){
                node.putLinkToCharacter(cur);
            }
            node = node.getLinkToCharacter(cur);
        }
        node.setEnd();
    }

    private TrieNode prefixChecker(String word){
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char cur = word.charAt(i);
            if(!node.hasLinkToCharacter(cur)) return null;
            node = node.getLinkToCharacter(cur);
        }
        return node;
    }

    public boolean search(String word){
        TrieNode node = prefixChecker(word);
        return node!=null && node.isEnd();
    }

    public boolean startsWith(String word){
        TrieNode node = prefixChecker(word);
        return node!=null;
    }
}
