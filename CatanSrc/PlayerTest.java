package CatanSrc;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	Catan game = new Catan();
	Board board = game.getBoard();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConstuctor() {
		
		Player player = game.getPlayers()[1];
		Vector<Placement> placements = player.getPlacements();
		assertTrue(placements.isEmpty());
		int settlements = player.getSettlements();
		assertTrue(settlements == 0);
		int cities = player.getCities();
		assertTrue(cities == 0);
		Hand hand = player.getHand();
		assertTrue(hand.isEmpty());
		int vpTotal = player.getVP();
		assertTrue(vpTotal == 0);
		
	}
	
	@Test
	void testInitialization() {
		
		Player player = game.getPlayers()[0];
		player.initPlacement();
		assertTrue(player.getPlacements().size() == 1);
		player.initPlacement();
		assertTrue(player.getPlacements().size() == 2);
		assertTrue(player.getLongestRoad() == 1);
		int grain = 0;
		int livestock = 0;
		int lumber = 0;
		int ore = 0;
		int brick = 0;
		Vector<Placement> placements = player.getPlacements();
		for(Placement p : placements) {
			BoardHex[] tiles = p.getTiles();
			for(BoardHex tile : tiles) {
				switch(tile.getType()) {
				case "grain":
					grain++;
					return;
				case "livestock":
					livestock++;
					return;
				case "lumber":
					lumber++;
					return;
				case "ore":
					ore++;
					return;
				case "brick":
					brick++;
				}
			}
		}
		Hand hand = player.getHand();
		assertTrue(hand.getBrickVector().size() == brick);
		assertTrue(hand.getGrainVector().size() == grain);
		assertTrue(hand.getLumberVector().size() == lumber);
		assertTrue(hand.getOreVector().size() == ore);
		assertTrue(hand.getLivestockVector().size() == livestock);
		
		
	}
	
	@Test
	void testResourseAllocation() {
		
		Player player = game.getPlayers()[3];
		player.initPlacement();
		player.initPlacement();
		int dieRoll = player.getPlacements().firstElement().getTiles()[0].getValue();
		if(dieRoll == 0) {
			dieRoll = player.getPlacements().firstElement().getTiles()[1].getValue();
		}
		if(dieRoll == 0) {
			dieRoll = player.getPlacements().firstElement().getTiles()[2].getValue();
		}
		int handSize = player.getHand().size();
		player.allocateRes(dieRoll);
		if(dieRoll != 0)
			assertTrue(player.getHand().size() > handSize);
		
	}
	
	@Test
	void testAdjustHand() {
		
		assertTrue(board.getBrickDeck().size() == 19);
		assertTrue(board.getGrainDeck().size() == 19);
		assertTrue(board.getLumberDeck().size() == 19);
		assertTrue(board.getLivestockDeck().size() == 19);
		assertTrue(board.getOreDeck().size() == 19);
		Player player = game.getPlayers()[2];
		player.setHand(new Card[] {board.draw("grain"), board.draw("lumber"), board.draw("ore"), board.draw("livestock")});
		assertTrue(board.getGrainDeck().size() == 18);
		assertTrue(board.getLumberDeck().size() == 18);
		assertTrue(board.getLivestockDeck().size() == 18);
		assertTrue(board.getOreDeck().size() == 18);
		assertTrue(player.getHand().size() == 4);
		player.adjustHand(new Card[] {new Card("grain"), new Card("livestock"), new Card("ore")});
		assertTrue(player.getHand().size() == 1);
		assertTrue(board.getGrainDeck().size() == 19);
		assertTrue(board.getLivestockDeck().size() == 19);
		assertTrue(board.getOreDeck().size() == 19);
		
	}
	
	@Test
	void testRollSeven() {
		
		Player player = game.getPlayers()[0];
		player.setHand(new Card[] {board.draw("grain"), board.draw("lumber"), board.draw("ore"), board.draw("livestock"), board.draw("grain"), board.draw("lumber"), board.draw("ore"), board.draw("livestock"), board.draw("grain"), board.draw("lumber"), board.draw("ore"), board.draw("livestock")});
		assertTrue(player.getHand().size() == 12);
		player.checkHand();
		assertTrue(player.getHand().size() == 6);
		
	}
	
	@Test 
	void testUpgradePlacement() {
		
		Player player = game.getPlayers()[0];
		player.initPlacement();
		assertTrue(player.getPlacements().firstElement().getWorth() == 1);
		assertTrue(player.getSettlements() == 1);
		assertTrue(player.getCities() == 0);
		player.upgradePlacement();
		assertTrue(player.getPlacements().firstElement().getWorth() == 2);
		assertTrue(player.getSettlements() == 0);
		assertTrue(player.getCities() == 1);
		
	}
	
	@Test
	void testBuildPlacement() {
		
		Player player = game.getPlayers()[1];
		player.initPlacement();
		player.initPlacement();
		player.setHand(new Card[] {board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick"), board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick")});
		player.buildRoad();
		player.buildPlacement();
		assertTrue(player.getPlacements().size() == 3);
		assertTrue(player.getSettlements() == 3);
		/*Vector<Integer> places = player.getPlacementIDs();
		for(Integer place : places) {
			System.out.println("Placement " + place);
		}
		Vector<Integer> IDs = player.getRoadPlacements();
		for(Integer ID : IDs) {
			System.out.println("Road " + ID);
		}*/
		
		
	}
	
	@Test
	void testDrawDevCard() {
		
		int devSize = board.getDevelopmentDeck().size();
		Player player = game.getPlayers()[1];
		player.setHand(new Card[] {new Card("livestock"), new Card("ore"), new Card("grain")});
		player.drawDevCard();
		assertTrue(player.getHand().size() == 0 && player.getHand().devSize() == 1);
		assertTrue(board.getDevelopmentDeck().size() == devSize - 1);
		
	}
	
	@Test 
	void testBuildRoad() {
		
		Player player = game.getPlayers()[3];
		player.initPlacement();
		player.initPlacement();
		player.clearHand();
		player.setHand(new Card[] {board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick"), board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick")});
		player.buildRoad();
		assertTrue(player.getLongestRoad() == 5);
		/*Vector<Integer> places = player.getPlacementIDs();
		for(Integer place : places) {
			System.out.println("Placement " + place);
		}*/
		
	}
	
	@Test
	void testTrade() {
		
		Player player = game.getPlayers()[2];
		player.setHand(new Card[] {new Card("grain"), new Card("grain"), new Card("grain"), new Card("grain"), new Card("grain"), new Card("lumber")});
		player.trade();
		assertTrue(player.getHand().size() == 3);
		
	}
	
	@Test
	void testMonopoly() {
		
		Player player0 = game.getPlayers()[0];
		Player player1 = game.getPlayers()[1];
		Player player2 = game.getPlayers()[2];
		Player player3 = game.getPlayers()[3];
		player1.setHand(new Card[] {new Card("grain")});
		player2.setHand(new Card[] {new Card("grain"), new Card("ore"), new Card("lumber")});
		player3.setHand(new Card[] {new Card("grain"), new Card("grain"), new Card("grain"), new Card("ore"), new Card("lumber")});
		player0.monopoly("grain");
		assertTrue(player0.getHand().size() == 5);
		assertTrue(player1.getHand().size() == 0);
		assertTrue(player2.getHand().size() == 2);
		assertTrue(player3.getHand().size() == 2);
		player0.monopoly("lumber");
		assertTrue(player0.getHand().size() == 7);
		assertTrue(player1.getHand().size() == 0);
		assertTrue(player2.getHand().size() == 1);
		assertTrue(player3.getHand().size() == 1);
		
	}
	
	@Test 
	void testYearOfPlenty() {
		
		Player player = game.getPlayers()[1];
		assertTrue(player.getHand().size() == 0);
		player.grabTwo();
		assertTrue(player.getHand().size() == 2);
		
	}
	
	@Test
	void testKnight() {
		
		game.initPlayerPlacements();
		Player player = game.getPlayers()[1];
		BoardHex oldRobber = board.getRobber();
		int oldTotal = game.getPlayers()[0].getHand().size() + game.getPlayers()[2].getHand().size() + game.getPlayers()[3].getHand().size();
		int oldSize = player.getHand().size();
		player.moveKnight();
		BoardHex newRobber = board.getRobber();
		int newTotal = game.getPlayers()[0].getHand().size() + game.getPlayers()[2].getHand().size() + game.getPlayers()[3].getHand().size();
		int newSize = player.getHand().size();
		assertTrue(oldRobber != newRobber);
		assertTrue(oldTotal - 1 == newTotal);
		assertTrue(oldSize + 1 == newSize);
		
	}
	
	@Test
	void testUpdateVP() {
		
		Player player = game.getPlayers()[3];
		player.initPlacement();
		player.initPlacement();
		player.updateVP();
		assertTrue(player.getVP() == 2);
		player.setHand(new Card[] {board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick"), board.draw("brick"), board.draw("lumber"), board.draw("lumber"), board.draw("brick")});
		player.buildRoad();
		player.updateVP();
		assertTrue(player.getVP() == 4);
		player.setHand(new Card[] {new Card("victorypoint")});
		player.updateVP();
		assertTrue(player.getVP() == 5);
		player.setHand(new Card[] {new Card("knight"), new Card("knight"), new Card("knight")});
		player.updateVP();
		assertTrue(player.getVP() == 7);
		
	}

}