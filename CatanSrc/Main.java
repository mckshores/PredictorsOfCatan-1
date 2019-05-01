package CatanSrc;

import java.io.FileWriter;
import java.io.IOException;

//If you are unfamiliar with the rules of "Settlers of  Catan" visit: https://www.catan.com/en/download/?SoC_rv_Rules_091907.pdf
public class Main {
	public static void main(String[] args) throws IOException {
		//Select the number of games you want to run
		for(int i = 0; i < 700; i++) {
			//Generate a new game each time
			CatanGame game = new CatanGame();
			game.playGame();
			//When the game terminates grab whichever player was the winner and how many rounds the game ran.
			Player Winner = game.getWinner();
			int rounds = game.getRound();
			if(Winner == game.getPlayers()[0]) {
				System.out.println("Winner: 0 "  + " Rounds: " + rounds);
			}
			if(Winner == game.getPlayers()[1]) {
				System.out.println("Winner: 1 "  + " Rounds: " + rounds);
			}
			if(Winner == game.getPlayers()[2]) {
				System.out.println("Winner: 2 "  + " Rounds: " + rounds);
			}
			if(Winner == game.getPlayers()[3]) {
				System.out.println("Winner: 3 "  + " Rounds: " + rounds);
			}
		}
	}
}
