package dataProcessing.src.dataProcessing;

import java.io.*;
import java.util.*;

public class processBoostedTrees {
	public static void main(String args[]) throws IOException {

		int[][] data = getData("C:\\Users\\awinegar\\Senior Project\\PredictorsOfCatan\\Src\\PredictorsOfCatan\\predictorsDataPoints.csv");
		data = setRoundAndWinner(data);
		//print(data);
		FileWriter writer = new FileWriter("C:\\Users\\awinegar\\Senior Project\\PredictorsOfCatan\\Src\\PredictorsOfCatan\\boostedbinaryTraining.csv");
		FileWriter tester = new FileWriter("C:\\Users\\awinegar\\Senior Project\\PredictorsOfCatan\\Src\\PredictorsOfCatan\\boostedbinaryTesting.csv");
		writer.append("Winner,R-Strength,H-Strength,VP,Cities,Dev,Round");

		writer.append("\n");
		tester.append("Winner,R-Strength,H-Strength,VP,Cities,Dev,Round");
		tester.append("\n");
		Vector<Integer> random = getRandom(data.length);
		String temp = "";
		for(int i = 0; i < (int)(data.length-1 * .5); i++) {
			temp += String.valueOf(data[i][7] + ",");			
			for(int j = 1; j < 7; j++) {
				temp += String.valueOf(data[i][j] + ",");
			}
			temp += "\n";
			/*if(random.contains(i))
				tester.append(temp);
			else
				writer.append(temp);*/
			if((i+1)% 4 == 0) {
				if(random.contains(i))
					tester.append(temp);
				else
					writer.append(temp);
				temp = "";
			}
			
			if(i % 1000 == 0)
				System.out.print(".");
		}
		System.out.println("Finished");
		writer.close();
		tester.close();
		
	}
	public static Vector<Integer> getRandom(int length){
		System.out.println("Shuffling");
		Vector<Integer> numbers = new Vector<Integer>();
		int runLen = (int) (length *.2);
		System.out.println(runLen);
		Random rand = new Random();
		for(int i = 0; i < (int)(runLen * .5); i++) {
			int selected = rand.nextInt(length);
			while(numbers.contains(selected))
				selected = rand.nextInt(length);
			numbers.add(selected);
			System.out.println("Remaining: " + (runLen - i));
		}
		System.out.println("Done shuffling");
		return numbers;
	}
	public static int[][] setRoundAndWinner(int[][] data){
		int gameCount = 1;
		for(int i = 1; i < data.length; i++) {
			if(data[i][6] < data[i-1][6])
				gameCount++;
		}
		int start = 1;
		int gameLength = 0;
		while(gameCount > 0) {
			for(int i = start; i < data.length; i++) {
				if(data[i][6] < data[i-1][6] || i+1 == data.length) {
					gameLength = (i - start) + 1; //1 is to make up for start  = 1
					break;
				}	
			}
			data = setRound(data, start, gameLength );
			data = setWinner(data, start, gameLength + start);
			gameCount --;
			start += gameLength + 1;
		}
		return data;
	}
	public static int[][] setWinner(int[][] data, int start, int end){
		int winner = -1;
		for(int i = end-4; i < end; i++) {
			if(data[i][7] == 1) {
				winner = data[i][0];
				break;
			}
		}
		for(int i = start-1; i < end; i++) {
			if(data[i][0] ==  winner)
				data[i][7] = 1;
			else
				data[i][7] = 0;
		}
		return data;
	}
	public static int[][] setRound(int[][] data, int start, int gameLength){
		int third = (int) gameLength/3;
		for(int i = start-1; i <= gameLength + start-1; i++) {
			if(i < (third + start))
				data[i][6] = 1;
			else if(i < (third * 2) +start)
				data[i][6] = 2;
			else
				data[i][6] = 3;	
		}
		return data;
	}
	public static int[][] getData(String fileName) throws IOException{
		int length;
		int iteration = 0;
		Scanner firstScanner = new Scanner(new File(fileName));
        length = getLines(firstScanner);
        firstScanner.close();
        String read[] = new String[length];
        Scanner scanner = new Scanner(new File(fileName));
        int instances = read.length;
        while (scanner.hasNextLine()) {  
            String line = scanner.nextLine();
            read[iteration] = line;
            iteration++;
            
        }
        scanner.close();
        return splitData(read);
	}
	public static int[][] splitData(String[] read){
		int[][] data = new int[read.length][8];
		for(int i = 0; i < read.length; i++) {
			for(int j = 0; j < 8; j++) {
				String temp = read[i].split(",")[j];
				data[i][j] = Integer.parseInt(temp);
			}
		}
		return data;
	}
	public static int getLines(Scanner file) {
    	int count = 0;
    	while (file.hasNextLine()) {  
           file.nextLine();
           count++;
            
        }
    	return count;
    }
	public static void print(int[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}