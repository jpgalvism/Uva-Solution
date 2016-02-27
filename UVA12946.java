

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class UVA12946 {

	private static final boolean DEB = false;
	private static BufferedReader br;
	private static StringBuilder answer = new StringBuilder();
	private static int[][] complexPolinomial;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String line;
		int number;
		File file = new File("in_12946.txt");
		char[] binaryRep;
		int  realPart,complexPart;

		if (file.exists())
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		else
			br = new BufferedReader(new InputStreamReader(System.in));

		complexPolinomial = new int[31][2];

		fullComplexSum();

		if (DEB) {

			for (int i = 0; i < complexPolinomial.length; ++i) {
				System.out.println(i + ": " + complexPolinomial[i][0] + "  " + complexPolinomial[i][1] + "  i");
			}
		}

		while ((line = br.readLine()) != null) {


			number = Integer.parseInt(line);
			binaryRep = Integer.toBinaryString(number).toCharArray();

			if (DEB) {
				System.out.println("---------------------");
				System.out.println(number);
				System.out.println(binaryRep);
				System.out.println(binaryRep.length);

			}

			realPart= 0;
			complexPart = 0;
			for (int i = 0; i < binaryRep.length; ++i) {

				realPart += (Integer.valueOf(String.valueOf(binaryRep[(binaryRep.length - 1) - i])) * complexPolinomial[i][0]);
				complexPart += (Integer.valueOf(String.valueOf(binaryRep[(binaryRep.length - 1) - i])) * complexPolinomial[i][1]);
			}

			answer.append(realPart + " " +  complexPart+"\n");
		}

		System.out.print(answer.toString());
		br.close();

	}

	private static void fullComplexSum() {

		complexPolinomial[0][0] = 1;
		complexPolinomial[0][1] = 0;

		complexPolinomial[1][0] = -1;
		complexPolinomial[1][1] = 1;

		complexPolinomial[2][0] = 0;
		complexPolinomial[2][1] = -2;

		
		//(a + bi) * (c + di) = (ac -bd) + (ad + bc)i
		for (int i = 2; i < complexPolinomial.length; ++i) {
			// Real Part
			complexPolinomial[i][0] = (-1 * complexPolinomial[i - 1][0]) - (1 * complexPolinomial[i - 1][1]);

			// Img Part
			complexPolinomial[i][1] = (-1 * complexPolinomial[i - 1][1]) + (1 * complexPolinomial[i - 1][0]);

		}
	}
}
