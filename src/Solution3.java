import java.util.ArrayList;

public class Solution3 {
    public int jump(ArrayList<Integer> A) {
        if(A.size() == 1){
            return 0;
        }
        int jumps = 1;
        int lastReach = A.get(0);
        int maxReach = A.get(0);
        for(int i=1; i<A.size(); i++){
            if(maxReach < i){
                return -1;
            }
            if(lastReach < i){
                jumps++;
                lastReach = maxReach;
            }
            maxReach = Math.max(maxReach, i + A.get(i));
        }
        return jumps;
    }
}
