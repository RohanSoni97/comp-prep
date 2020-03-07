import java.io.BufferedReader;
import java.io.InputStreamReader;



class TestClass {
    public static void main(String args[] ) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(br.readLine());                // Reading input from STDIN
        int arr[]=new int[len];
        int sum=0;
        String inputs=br.readLine();
        args=inputs.split(" ");
        for(int i=0;i<len;i++)
        {
            arr[i]=Integer.parseInt(args[i]);
        }
        int Q = Integer.parseInt(br.readLine());
        //System.out.println("Hi, " + name + ".");    // Writing output to STDOUT
        for(int i=0;i<Q;i++)
        {
            inputs=br.readLine();
            args=inputs.split(" ");
            if(args[0].equals("Increment"))
            {
                arr[(Integer.parseInt(args[1])+sum-1)%len]++;
            }
            else if(args[0].equals("Update"))
            {
                arr[(Integer.parseInt(args[1])+sum-1)%len]=Integer.parseInt(args[2]);
            }
            else if(args[0].equals("Left"))
            {
                sum=(sum+1)%len;
            }
            else if(args[0].equals("Right"))
            {
                sum=(sum+len-1)%len;
            }
            else if(args[0].equals("?")) {
                System.out.println(arr[(Integer.parseInt(args[1])+sum-1)%len]);
            }
        }

    }
}
/*//Scanner
10
0 3 3 8 0 6 9 3 2 8
10
Increment 3
Increment 1
Left
Increment 5
Left
? 9
Right
Left
Left
? 4
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();                 // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        */