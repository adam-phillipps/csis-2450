package battleship1410;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {
	private ArrayList<Battleship> aiShips = new ArrayList<Battleship>();
	int numGuess = 0;
	
	public void setUp(){
		aiShips.add(new Battleship("Sub", 3));
		aiShips.add(new Battleship("Battle Ship", 3));
		aiShips.add(new Battleship("Battle Ship", 3));
		setLocations();
		
	}
	
	public void play(String guess){
		
		String result;
		result = "miss";
		numGuess++;
		System.out.println("Enter a guess");
		System.out.println(guess);
		guess = guess.toUpperCase();
		for (int i = 0; i < aiShips.size(); i++){
			result = aiShips.get(i).check(guess);
			if(result.equals("kill")){
				result = ("You sunk " + aiShips.get(i).getName());
				aiShips.remove(i);
				break;
			}
			else if(result.equals("hit")){
				break;
			}
		}
		System.out.println(result);
		//finish();
	}
	
	private void finish(){
		
		if(numGuess == 9){
			System.out.println("Perfect Score");
		}
		else if(numGuess < 20){
			System.out.println("Good Job! It took you "+ numGuess +" guesses.");
		}
		else if(numGuess < 30){
			System.out.println("Maybe you will do better next time. It took you "+ numGuess +" guesses.");
		}
		else{
			System.out.println("You suck! It took you "+ numGuess +" guesses.");
		}
	}
	private void setLocations(){
		
		Random rand = new Random();
		ArrayList<String> locationToSet = new ArrayList<String>();
		ArrayList<String> temp = null;
		int let, num, incl, incn;
		String alpha = "ABCDEFG";
		boolean worked;
		for (int i = 0; i< aiShips.size(); i++){
			worked = false;
			start:
				while(!worked){
					locationToSet.clear();
					worked = true;
					let = rand.nextInt(5);
					num = 1 + rand.nextInt(5);
					if(num % 2 == 0){
						incl = 1;
						incn = 0;
					}
					else{
						incl = 0;
						incn = 1;
					}
					for (int j=0; j<aiShips.get(i).getSize(); j++){
						String loc = ""+ alpha.charAt(let)+num;
						let += incl;
						num += incn;
						
						for (int t=0; t<aiShips.size(); t++){
							if(t != i){
								temp = aiShips.get(t).getLocation();
								if(temp.contains(loc)){
									worked = false;
									continue start;
								}
							}
						}
						System.out.println(loc);
						locationToSet.add(loc);
					}
					aiShips.get(i).setLocation(locationToSet);
				}
		}
	}
}
									

