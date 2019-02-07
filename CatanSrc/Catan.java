/* Catan
 * init game status to false (incomplete)
 * init winner to null 
 * init Board
 * init Players Array
 * PlayGame()
 * 
 * Board 
 * init BoardHex *19
 * init Port *9
 * init Robber to point to desert
 * init Dev deck
 * init Res deck
 * 
 * Player 
 * init placements (settlements/cities)
 * init road counter to 15
 * Place
 * init Hand (res allocation)
 * 
 * PlayGame
 * While(!gameStaus) {
 * 		for each player in Players{
 * 			player p = player.TakeTurn();
 * 			if(p != null) {
 * 				winner = p;
 * 				gameStatus = True;
 * 				break;
 * 			}
 * 		}
 * 		collectData(Players);
 * }
 * collectData();
 */