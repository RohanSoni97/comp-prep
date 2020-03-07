#include <bits/stdc++.h>

using namespace std;

int n,n1;
int arr [100 + 10][100 + 10];
int dirc [2][2] = {{1 , 1}, {1 , -1}};     // all the possible directions
int count=0;
bool vis1 [100 + 10][100 + 10];
bool dfs(int x, int y,  bool vis [][100 + 10]){

    if(y==3 && x == n1 - 1) return true;        // base case in first dfs

    vis[x][y] = true;

    bool ans = false;

    for(int i = 0; i < 2 && !ans; i++){

        int dx = x + dirc[i][0];                    // transition
        int dy = y + dirc[i][1];

        if(dx < 0 || dx >= n || dy < 0|| dy >= 3) continue;     // out of the board

        if(!vis[dx][dy])
            ans |= dfs(dx, dy, comp, vis);
            if(ans)
            {
            count;
            }
    }

    return ans;
}

int main()
{
    ios::sync_with_stdio(false);cin.tie(0);             // decrease the time for cin, cout

    cin >>     n           ;




    for(int i = 0; i < n; i++){
        cin>>n1;
         ones = dfs(0 , 0 , vis1);      // check for a path from the first row
    }



    return 0;
}
/*
#include <iostream>
using namespace std;

// Returns count of possible paths to reach cell at
// row number m and column  number n from the topmost
// leftmost cell (cell at 1, 1)
int count1[10000000][3];
int numberOfPaths(int m, int count[][3])
{
    // Create a 2D table to store results of subproblems
     int count1[10000000][3];

    count[0][0]=1;
    // Calculate count of paths for other cells in
    // bottom-up manner using the recursive solution
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < 3; j++)

            // By uncommenting the last part the code calculatest he total
            // possible paths if the diagonal Movements are allowed
            count[i][j] = count[i - 1][j-1] + count[i-1][j + 1]; //+ count[i-1][j-1];
    }
    return count[m - 1][2];
}

// Driver program to test above functions
int main()
{ int n,m;
cin>>n;
    while(n-->0){
    cin>>m;
    if(count1[m-1][2]!=0)
    {
        cout<<count1[m-1][2]<<"\n";
    }
    else{
    cout << numberOfPaths(m,count1)<<"\n";
    }


    }
    return 0;
}

-----
#include <iostream>
using namespace std;

// Returns count of possible paths to reach cell at
// row number m and column  number n from the topmost
// leftmost cell (cell at 1, 1)
int count1[10000000][4];
int numberOfPaths(int m, int count[][4])
{
    // Create a 2D table to store results of subproblems


    count[0][0]=1;
    // Calculate count of paths for other cells in
    // bottom-up manner using the recursive solution
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < 3; j++)

            // By uncommenting the last part the code calculatest he total
            // possible paths if the diagonal Movements are allowed
            count[i][j] = count[i - 1][j-1] + count[i-1][j + 1]; //+ count[i-1][j-1];
    }
    return count[m][2];
}

// Driver program to test above functions
int main()
{ int n,m;
cin>>n;
    while(n-->0){
    cin>>m;
    if(count1[m-1][2]!=0)
    {
        cout<<count1[m-1][2]<<"\n";
    }
    else{
    cout << numberOfPaths(m,count1)<<"\n";
    }


    }
    return 0;
}

*
/