package CatanSrc;

public class PlayerDecision {
	
	private boolean[] availableActions = new boolean[] {false, false, false, false, false, false};
	private Hand playerHand = null;
	private int oreUsed = 0, grainUsed = 0, brickUsed = 0, lumberUsed = 0, livestockUsed = 0, actionCounter = 0;
	
	//Empty Constructor
	public PlayerDecision() {}
	
	/* 0 Play Dev Card
	 * 1 Build City
	 * 2 Build Settlement
	 * 3 Draw Dev Card
	 * 4 Build Road
	 * 5 Trade
	 */
	//Returns an integer array containing the moves(see above) that a Player should try to make based on what they have in their hand
	public int[] makeDecision(Player player) {
		playerHand = player.getHand();
		if(playerHand.isEmpty()) {
			return new int[0];
		}
		playDevCard();
		buildCity();
		buildSettlement();
		drawDevCard();
		buildRoad();
		trade();
		if(actionCounter == 0) {
			return new int[0];
		}
		int[] actions = new int[actionCounter];
		for(int i = 0, j = 0; i < availableActions.length; i++) {
			if(availableActions[i] == true) {
				actions[j] = i;
				j++;
			}
		}
		availableActions = new boolean[] {false, false, false, false, false, false};
		playerHand = null;
		oreUsed = 0; 
		grainUsed = 0; 
		brickUsed = 0; 
		lumberUsed = 0; 
		livestockUsed = 0; 
		actionCounter = 0;
		return actions;
	}
	
	//Determine if a Player is able to play a development card
	public void playDevCard() {
		if(!playerHand.getDevelopmentVector().isEmpty()) {
			for(Card card : playerHand.getDevelopmentVector()) {
				if(card.getType() != "victorypoint") {
					availableActions[0] = true;
					actionCounter ++;
					break;
				}
			}
		}
	}
	
	//Determine if a Player has the resource cards to upgrade a settlement to a city
	public void buildCity() {
		if((playerHand.getGrainVector().size() - grainUsed) >= 2 && (playerHand.getOreVector().size() - oreUsed) >= 3) {
			availableActions[1] = true;
			actionCounter ++;
			grainUsed += 2;
			oreUsed += 3;
		}
	}
	
	//Determine if a Player has the cards to build a new settlement
	public void buildSettlement() {
		if((playerHand.getGrainVector().size() - grainUsed) >= 1 && 
				(playerHand.getLivestockVector().size() - livestockUsed) >= 1 && 
				(playerHand.getBrickVector().size() - brickUsed) >= 1 &&
				(playerHand.getLumberVector().size() - lumberUsed) >= 1){
			availableActions[2] = true;
			actionCounter ++;
			grainUsed ++;
			lumberUsed ++;
			livestockUsed ++;
			brickUsed++;
		}
	}

	//Determine if a Player has the resources to draw a new development card
	public void drawDevCard() {
		if((playerHand.getGrainVector().size() - grainUsed) >= 1 && 
				(playerHand.getLivestockVector().size() - livestockUsed) >= 1 && 
				(playerHand.getOreVector().size() - oreUsed) >= 1) {
			availableActions[3] = true;
			actionCounter ++;
			grainUsed ++;
			oreUsed ++;
			livestockUsed ++;
		}
	}
	
	//Determine if a Player has the cards to build a road 
	public void buildRoad() {
		if((playerHand.getBrickVector().size() - brickUsed) >= 1 &&
				(playerHand.getLumberVector().size() - lumberUsed) >= 1) {
			availableActions[4] = true;
			actionCounter ++;
			lumberUsed ++;
			brickUsed++;
		}
	}
	
	//If all else fails, determine if a Player can make a trade with the board
	public void trade() {
		if((playerHand.getGrainVector().size() - grainUsed) >= 4 || 
				(playerHand.getLivestockVector().size() - livestockUsed) >= 4 || 
				(playerHand.getBrickVector().size() - brickUsed) >= 4 ||
				(playerHand.getLumberVector().size() - lumberUsed) >= 4 ||
				(playerHand.getOreVector().size() - oreUsed >= 4)) {
					availableActions[5] = true;
					actionCounter ++;
		}
	}
	
}