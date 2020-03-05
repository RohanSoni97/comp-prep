import java.util.Deque;
import java.util.LinkedList;

class MaximumHistogram {

    public int maxHistogram(int input[]){
        Deque<Integer> stack = new LinkedList<Integer>();
        int maxArea = 0;
        int area = 0;
        int i;
        for(i=0; i < input.length;){
            if(stack.isEmpty() || input[stack.peekFirst()] <= input[i]){
                stack.offerFirst(i++);
            }else{
                int top = stack.pollFirst();
                               if(stack.isEmpty()){
                    area = input[top] * i;
                }

                else{
                    area = input[top] * (i - stack.peekFirst() - 1);
                }
                if(area > maxArea){
                    maxArea = area;
                }
            }
        }
        while(!stack.isEmpty()){
            int top = stack.pollFirst();

            if(stack.isEmpty()){
                area = input[top] * i;
            }

            else{
                area = input[top] * (i - stack.peekFirst() - 1);
            }
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }


}
public class Solution2 {
    public int  maximalRectangle(int input[][]){
        int temp[] = new int[input[0].length];
        MaximumHistogram mh = new MaximumHistogram();
        int maxArea = 0;
        int area = 0;
        for(int i=0; i < input.length; i++){
            for(int j=0; j < temp.length; j++){
                if(input[i][j] == 0){
                    temp[j] = 0;
                }else{
                    temp[j] += input[i][j];
                }
            }
            area = mh.maxHistogram(temp);
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
    public static void main(String args[]){
        int input[][] = {{1,1,1,0},
                {1,1,1,1},
                {0,1,1,0},
                {0,1,1,1},
                {1,0,0,1},
                {1,1,1,1}};
        Solution2 mrs = new Solution2();
        int maxRectangle = mrs. maximalRectangle(input);
        System.out.println(maxRectangle);
        //assert maxRectangle == 8;
    }
}
