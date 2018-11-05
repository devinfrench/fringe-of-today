package fringeoftoday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlayerData {

	private static Map<String, String> playerInfo = new HashMap<String, String>();
	

	public static void playerFileSetup() {
		File playerF = new File("player.txt");
		if (!playerF.exists()) {
			try {
				// Make new file if one doesn't exist
				playerF.createNewFile();
			} catch (IOException e1) {
				System.out.println("Couldn't create file");
				e1.printStackTrace();
			}

			try {
				// Fill it in with default values of 0
				FileWriter fw = new FileWriter("player.txt");
				fw.write("Coin:0,");
				fw.write("HPUpgrades:0,");
				fw.write("MeleeUpgrades:0,");
				fw.write("RangedUpgrades:0,");
				fw.write("SpeedUpgrades:0,");
				fw.write("PreviousRun:0,");
				fw.write("GOAT:0,");
				fw.write("Tutorial:0,");
				fw.close();
			} catch (IOException e2) {
				System.out.println("No write");
				e2.printStackTrace();
			}
		}
		readPlayerFile();
	}

	public static void readPlayerFile() {
		String text = null;
		try {
			Scanner sc = new Scanner(new File("player.txt"));
			text = sc.useDelimiter("\\A").next();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file");
			e.printStackTrace();
		}
		//print the text:
		//System.out.println(text);
		initalizeMap(text);
	}
	
	public static Map<String, String> getMap() {
		return playerInfo;
	}
	
	private static void initalizeMap(String text) {
		String[] infoArr = text.split(":|,");
		for (int i = 0; i < infoArr.length; i = i + 2) {
			playerInfo.put(infoArr[i], infoArr[i + 1]);
		}
		//Print the array:
		//playerInfo.forEach((key, value) -> System.out.println(key + ":" + value));
	}

	public static void updateMap(String key, int i) {
		playerInfo.put(key, Integer.toString(i));
		
	}
}
