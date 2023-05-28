#include<bits/stdc++.h>
using namespace std;
#define int long long int
class PersistentSumSegmentTree {
private:
    struct Node {
        Node* left;
        Node* right;
        int value;
        
        Node(int value) : value(value), left(nullptr), right(nullptr) {}
        Node(int value, Node* left, Node* right) : value(value), left(left), right(right) {}
    };

    vector<Node*> heads;
    int n;
    int sum(int ss, int se, Node* node, int qs, int qe) {
        if (qe < ss || se < qs) return 0LL;
        if (qs <= ss && qe >= se) return node->value;
        int mid = (ss + se) >> 1;
        return sum(ss, mid, node->left, qs, qe) + sum(mid + 1, se, node->right, qs, qe);
    }
    Node* update(int ss, int se, Node* node, int index, int value) {
        if (index < ss || index > se) return node;
        if (index == ss && index == se) return new Node(value);
        int mid = (ss + se) >> 1;
        Node* left = update(ss, mid, node->left, index, value);
        Node* right = update(mid + 1, se, node->right, index, value);
        return new Node(left->value + right->value, left, right);
    }
    Node* construct(int ss, int se, vector<int>& arr) {
        if (ss == se) return new Node(arr[ss]);
        int mid = (ss + se) >> 1;
        Node* left = construct(ss, mid, arr);
        Node* right = construct(mid + 1, se, arr);
        return new Node(left->value + right->value, left, right);
    }
public:
    PersistentSumSegmentTree(vector<int>& arr) {
        n = arr.size();
        heads.push_back(construct(0, n - 1, arr));
    }
    void updateIndex(int version, int index, int value) {
        heads.push_back(update(0, n - 1, heads[version], index, value));
    }
    int getSum(int version, int left, int right) {
        return sum(0, n - 1, heads[version], left, right);
    }

    
};
int32_t main(){
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n,queries;
    cin >> n;
    vector<int> a(n);
    for(int i=0;i<n;i++) cin >> a[i];
    cin >> queries;
    PersistentSumSegmentTree sg(a);
    while(queries--){
        int q,a,b,c;
        cin >> q>> a >> b >> c;
        if(q == 1){
            c += sg.getSum(a,b-1,b-1);
            sg.updateIndex(a,b-1,c);
        }
        else{
            cout << sg.getSum(a,b-1,c-1) << "\n";
        }
    }
}
