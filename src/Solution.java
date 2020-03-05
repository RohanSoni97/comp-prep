public class Solution {
    // DO NOT MODIFY THE ARGUMENTS WITH "final" PREFIX. IT IS READ ONLY
    public static int canCompleteCircuit(final int[] A, final int[] B) {
        int max_so_far = 0,
                max_ending_here = 0,start = 0,
                end = 0, s = 0;
        int len=A.length;
        int temp[]=new int[len];
        for(int i=0;i<len;i++)
        {
            temp[i]=A[i]-B[i];
        }
        for (int i = 0; i < len; i++)
        {
            max_ending_here += temp[i];
            max_so_far += temp[i];
            if(i == len-1 && max_so_far < 0)
            {
                start=1;
            }
            if (max_ending_here < 0)
            {
                max_ending_here = 0;
                if(i!=len-1){
                    s = i + 1;
                }
            }
        }
        if(start==1) {
            s=-1;
        }

        return s;

    }

    public static void main(String[] args) {
        int A[]={1,2};
        int B[]={2,1};
        System.out.println(canCompleteCircuit(A,B));
    }
}
