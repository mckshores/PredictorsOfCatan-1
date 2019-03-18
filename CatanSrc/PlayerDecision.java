package CatanSrc;

/* 0 Play Dev Card
 * 1 Build City
 * 2 Build Settlement
 * 3 Draw Dev Card
 * 4 Build Road
 * 5 Trade
 */

public class PlayerDecision {
	
	private boolean[] availableActions = new boolean[] {false, false, false, false, false, false};
	private Hand playerHand = null;
	private int oreUsed = 0, grainUsed = 0, brickUsed = 0, lumberUsed = 0, livestockUsed = 0, actionCounter = 0;
	
	public PlayerDecision() {}
	
	public int[] makeDecision(Player player) {

		playerHand = player.getHand();
		
		if(playerHand.isEmpty()) {
			return null;
		}
		playDevCard();
		buildCity();
		buildSettlement();
		drawDevCard();
		buildRoad();
		trade();
		if(actionCounter == 0) {
			return null;
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
	
	public void buildCity() {
		
		if((playerHand.getGrainVector().size() - grainUsed) >= 2 && (playerHand.getOreVector().size() - oreUsed) >= 3) {
			availableActions[1] = true;
			actionCounter ++;
			grainUsed += 2;
			oreUsed += 3;
		}
		
	}
	
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
	
	public void buildRoad() {
		
		if((playerHand.getBrickVector().size() - brickUsed) >= 1 &&
				(playerHand.getLumberVector().size() - lumberUsed) >= 1) {
			availableActions[4] = true;
			actionCounter ++;
			lumberUsed ++;
			brickUsed++;
		}
		
	}
	
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