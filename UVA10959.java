import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class UVA10959 {

	private static BufferedReader in;
	private static StringBuilder answer = new StringBuilder();


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int numCase = -1;
		Scanner scan;
		String line = null;
		int numPerson = -1, numCouples = -1;

		File file = new File("in_10959");
		if (file.exists())
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		else
			in = new BufferedReader(new InputStreamReader(System.in));

		numCase = Integer.parseInt(in.readLine());

        while (numCase-- > 0) {
        	
			line = in.readLine();
			line = in.readLine();
			scan = new Scanner(line);
			numPerson = Integer.parseInt(scan.next());
			numCouples = Integer.parseInt(scan.next());

			
            boolean[][] dgCouple = new boolean[numPerson][numCouples];
            for (int i = 0; i < numCouples; i++) {
            	line = in.readLine();
    			scan = new Scanner(line);
    			int a =  Integer.parseInt(scan.next());
    			int b =  Integer.parseInt(scan.next());
    			dgCouple[a][b] = dgCouple[b][a] = true;
            }
 
            int[] distDg = new int[numPerson];
            Arrays.fill(distDg, -1);
            distDg[0] = 0;
            Queue<Integer> Q = new ArrayDeque<Integer>();
            Q.add(0);
            while (!Q.isEmpty()) {
                int i = Q.remove();
                for (int j = 0; j < numPerson; j++)
                    if (dgCouple[i][j] && distDg[j] == -1) {
                        distDg[j] = distDg[i] + 1;
                        Q.add(j);
                    }
            }
            
            for (int i = 1; i < numPerson; i++)
            	answer.append(distDg[i]+"\n");
            if (numCase != 0)
            	answer.append("\n");
        }
         
		System.out.print(answer);

	}


}
