// Java implementation of finding length of longest
// Common substring using Dynamic Programming
public class LongestCommonSubSequence
{
    /*
    Returns length of longest common substring
    of X[0..m-1] and Y[0..n-1]
    */
    public static boolean isSubsequence(String s, String t) {
        int m = s.length();
        int n = t.length();
        char X[]=s.toCharArray();
        char Y[]=t.toCharArray();
        int LCStuff[][] = new int[m + 1][n + 1];
        int result = 0; // To store length of the longest common substring

        // Following steps build LCSuff[m+1][n+1] in bottom up fashion
        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                    LCStuff[i][j] = 0;
                else if (X[i - 1] == Y[j - 1])
                {
                    LCStuff[i][j] = LCStuff[i - 1][j - 1] + 1;
                    result = Integer.max(result, LCStuff[i][j]);
                }
                else
                    LCStuff[i][j] = 0;
            }
        }
        return m==result;
    }
    static int LCSubStr(char X[], char Y[], int m, int n)
    {

        int LCStuff[][] = new int[m + 1][n + 1];
        int result = 0; // To store length of the longest common substring

        // Following steps build LCSuff[m+1][n+1] in bottom up fashion
        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                    LCStuff[i][j] = 0;
                else if (X[i - 1] == Y[j - 1])
                {
                    LCStuff[i][j] = LCStuff[i - 1][j - 1] + 1;
                    result = Integer.max(result, LCStuff[i][j]);
                }
                else
                    LCStuff[i][j] = 0;
            }
        }
        return result;
    }

    // Driver Program to test above function
    public static void main(String[] args)
    {
        String X = "abc";
        String Y = "ahbgdc";

       if(isSubsequence(X,Y))
       {
           System.out.println(true);
       }
       else {
           System.out.println(false);
       }

        //System.out.println("Length of Longest Common Substring is "
          //      + LCSubStr(X.toCharArray(), Y.toCharArray(), m, n));
    }
}

