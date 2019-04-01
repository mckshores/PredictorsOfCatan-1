package CatanSrc;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Running");
		Catan game = new Catan();
		game.playGame();
		Player Winner = game.getWinner();
		if(Winner == game.getPlayers()[0]) {
			System.out.println("Winner: 0 "  + game.getPlayers()[0].getVP());
		}
		if(Winner == game.getPlayers()[1]) {
			System.out.println("Winner: 1 "  + game.getPlayers()[1].getVP());
		}
		if(Winner == game.getPlayers()[2]) {
			System.out.println("Winner: 2 "  + game.getPlayers()[2].getVP());
		}
		if(Winner == game.getPlayers()[3]) {
			System.out.println("Winner: 3 "  + game.getPlayers()[3].getVP());
		}

	}

}
