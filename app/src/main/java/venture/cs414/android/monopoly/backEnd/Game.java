package venture.cs414.android.monopoly.backEnd;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	
	private List<Player> players;

	public Game(List<String> playerNames) {
		players = new ArrayList<Player>();
		for(String name : playerNames){
			players.add(new Player(name));

		}
		Collections.shuffle(players);
	}
	
	public Board setup(Context context){
		Board board = new Board(players, context);
		return board;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	//Gives an arraylist of player names in turn order for view to use
	public List<String> getPlayerNamesInOrder(){
		List<String> playerNames = new ArrayList<String>();
		for(Player player : players){
			playerNames.add(player.getName());
		}
		return playerNames;
	}
	
	//takes an arraylist of tokens in player turn order from view in order to set player tokens
	public void setPlayerTokensInOrder(List<String> playerTokens){
		for(Player player: players){
			//int index = playerTokens.indexOf(token);
			//players.get(index).setToken(token);
			player.setToken(playerTokens.get(players.indexOf(player)));
		}
	}

}
