import java.util.HashMap;

public class Solution1 {
    // DO NOT MODIFY THE ARGUMENTS WITH "final" PREFIX. IT IS READ ONLY
    public static int majorityElement(final int[] A) {
        HashMap<Integer,Integer> hashmap=new HashMap<>();
        int len=A.length;
        int i=0;
        for( i=0;i<len;i++)
        {
            if( hashmap.containsKey(A[i])){
                hashmap.put(A[i],hashmap.get(A[i])+1);
                if(hashmap.get(A[i])>len/2)
                {
                 break;
                }
            }
            else {
                hashmap.put(A[i],1);
                if(hashmap.get(A[i])>len/2)
                {
                    break;
                }
            }
        }
        return A[i];
    }

    public static void main(String[] args) {
        int A[]={100};
        System.out.println(majorityElement(A));
    }
}
