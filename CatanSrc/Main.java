package CatanSrc;

public class Main {

	public static void main(String[] args) {
		
		Catan game = new Catan();
		game.playGame();
		System.out.println("Winner: " + game.getWinner());

	}

}