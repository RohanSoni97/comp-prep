import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Test {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input=br.readLine();
        evaluateEXpression(input);
        
    }

    private static void evaluateEXpression(String input) {
        Stack<Character> stack = new Stack<Character>();
        int operator=0,first,second;
        for(int i=0;i<input.length();i++)
        {
            if(input.charAt(i)=='+' || input.charAt(i)=='-' || input.charAt(i)=='*' || input.charAt(i)=='/' )
            {first=Character.getNumericValue(stack.pop());
            second=Character.getNumericValue(stack.pop());
                switch (input.charAt(i)){
                    case '+':
                       operator=second+first;
                       break;
                    case '-':
                        operator=second-first;
                        break;
                    case '*':
                        operator=second*first;
                        break;
                    case '/':
                        operator=second/first;
                        break;

                }

              stack.push(String.valueOf(operator).charAt(0));
            }
            else{
            stack.push( input.charAt(i));
        }
        }
        System.out.println(stack.pop());
    }
}
