import java.io.*;
import java.util.*; 
import java.util.Random;
import java.lang.*;
import java.io.LineNumberReader;
/*
Clark Rossi
CSC 162
Wumpus Game
wumpusgame.java
*/
public class wumpusgame
{	
	public static void main(String[] args)
	{
		Scanner s = new Scanner (System.in); //Calls scanner object
		Random r = new Random(); //Calls random object	

		//Booleans for exception handling
		boolean validGame = false, validMapSize = false;
		boolean endGame = false;
		
		int mapSize = 4; //Variable to change game size
		int playerX = 0, playerY = 0;
		int newOrLoad = 0;
		
		//Initializes pit, wumpus, and gold counts 
		int pitCount = 0, wumpusCount = 0, goldCount = 0;
		
		room playArea[][] = null;
		boolean loadArray[] = new boolean[6];

		File fileoutput;
		PrintWriter writer;
		Scanner reader;

		//playArea = new room[mapSize][mapSize]
		//Checks for valid new or load game input
		while (!validGame)
		{
			try
			{
				System.out.println ("Would you like to play a new game or load one? Enter 0 for a new game or 1 to load.");
				newOrLoad = s.nextInt();
				
				if ((newOrLoad >= 0) && (newOrLoad < 2))
					validGame = true;
			}
			catch (InputMismatchException e)
			{
				s.nextLine();
			}
		}	
		
		//---------------------------------- Runs new game ----------------------------------
		if (newOrLoad == 0)
		{
			//Checks if difficulty input is a number between 4 - 10
			while (!validMapSize)
			{
				System.out.printf ("Please enter game difficulty as a number between 4(easy) - 10(very hard): ");
				try
				{
					int playerInput = s.nextInt();
					
					if((playerInput > 3) && (playerInput < 11))
					{
						mapSize = playerInput;
						validMapSize = true;
					}
				}
				catch (InputMismatchException e)
				{
					s.nextLine();				
				}
			}
			
			//Sets pitCount and wumpusCount to percentage of total rooms
			pitCount = (int)Math.floor ((20.0 / 100.0) * (mapSize * mapSize));
			wumpusCount = (int)Math.floor ((10.0 / 100.0) * (mapSize * mapSize));
			
			//Changes gold amount to 2 if mapSize*mapSize > 50
			goldCount = 1;
			if ((mapSize * mapSize) > 50)
				goldCount = 2;
			
			//Creates mapSize*mapSize object array of rooms
			playArea = new room[mapSize][mapSize];	
			//System.out.println(mapSize);
			
			//Loops through playArea array and adds rooms		
			for (int x = 0; x < mapSize; x++)
			{
				for (int y = 0; y < mapSize; y++)
				{
					playArea[x][y] = new room(); //Creates new room in playArea array 				
				}
			}
		
			//Adds pit(s) to map
			while (pitCount > 0)
			{
				int randX = r.nextInt(mapSize - 1);
				int randY = r.nextInt(mapSize - 1); 			
				
				//Sets pit to true at X:randX, Y:randY
				playArea[randX][randY].setpit(true);
				pitCount--;			
				
				//Sets breeze around pits
				if ((randX - 1) >= 0)
					playArea[randX - 1][randY].setbreeze(true);
				if ((randX + 1) <= mapSize)
					playArea[randX + 1][randY].setbreeze(true);
				if ((randY - 1) >= 0)
					playArea[randX][randY - 1].setbreeze(true);
				if ((randY + 1) <= mapSize)			
					playArea[randX][randY + 1].setbreeze(true);				
			}
			//Adds wumpus(s)? to map	
			while (wumpusCount > 0)
			{
				int randX = r.nextInt(mapSize - 1);
				int randY = r.nextInt(mapSize - 1); 				
				
				//Checks if pits were placed
				if (playArea[randX][randY].getpit() == false)
				{
					playArea[randX][randY].setwumpus(true);
					wumpusCount--;
					
					//Sets smell around wumpus
					if ((randX - 1) >= 0)
						playArea[randX - 1][randY].setsmell(true);
					if ((randX + 1) <= mapSize)
						playArea[randX + 1][randY].setsmell(true);
					if ((randY - 1) >= 0)
						playArea[randX][randY - 1].setsmell(true);
					if ((randY + 1) <= mapSize)			
						playArea[randX][randY + 1].setsmell(true);					
				}
			}				
			//Adds gold to map
			while (goldCount > 0)
			{
				int randX = r.nextInt(mapSize - 1);
				int randY = r.nextInt(mapSize - 1); 						
				
				//Checks if pits and wumpus were placed
				if (playArea[randX][randY].getpit() == false && playArea[randX][randY].getwumpus() == false)
				{
					playArea[randX][randY].setgold(true);
					goldCount--;
					
					//Sets glimmer around gold
					if ((randX - 1) >= 0)
						playArea[randX - 1][randY].setglimmer(true);
					if ((randX + 1) <= mapSize)
						playArea[randX + 1][randY].setglimmer(true);
					if ((randY - 1) >= 0)
						playArea[randX][randY - 1].setglimmer(true);
					if ((randY + 1) <= mapSize)			
						playArea[randX][randY + 1].setglimmer(true);					
				}
			}
		}
		
		//---------------------------------- Runs load game ----------------------------------
		if (newOrLoad == 1)
		{
			//System.out.printf ("Please enter a file name to load: ");
			//String fileName = s.nextLine();
			
			//Gets number of lines in file
			//Loops through playArea array and adds rooms		
			
			//Gets file line length
			try
			{
				//Reads file length
				File file =new File("wumpsave.sav");
				FileReader fr = new FileReader(file);
				LineNumberReader lnr = new LineNumberReader(fr);
				
				int numLines = 0;
				while (lnr.readLine() != null)
					numLines++;
				lnr.close();	
				
				//Sets map size based on file line length
				if (numLines == 96)
					mapSize = 4;
				else if (numLines == 150)
					mapSize = 5;
				else if (numLines == 216)
					mapSize = 6;
				else if (numLines == 294)
					mapSize = 7;
				else if (numLines == 384)
					mapSize = 8;
				else if (numLines == 486)
					mapSize = 9;
				else
					mapSize = 10;
				
				//Re-creates map grid
				playArea = new room[mapSize][mapSize];				
				for (int x = 0; x < mapSize; x++)
				{
					for (int y = 0; y < mapSize; y++)
					{
						playArea[x][y] = new room();
					}
				}							

				try
				{	
					fileoutput = new File("wumpsave.sav");
					reader = new Scanner(fileoutput);				
				
					for (int x = 0; x < mapSize; x++)
					{	
						for(int y = 0; y < mapSize; y++)
						{
							for (int i = 0; i < 6; i++)
							{
								loadArray[i] = reader.nextBoolean();
							}
							playArea[x][y].setpit(loadArray[0]);
							playArea[x][y].setwumpus(loadArray[1]);
							playArea[x][y].setgold(loadArray[2]);
							playArea[x][y].setbreeze(loadArray[3]);
							playArea[x][y].setsmell(loadArray[4]);
							playArea[x][y].setglimmer(loadArray[5]);								
						}
					}
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}					
			}	
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			/* For debugging 
			for (int x = 0; x < mapSize; x++)
			{
				for (int y = 0; y < mapSize; y++)
				{
					System.out.println("x: "+x+" y: "+y+" | values: B "+playArea[x][y].getbreeze()+"| S: "+playArea[x][y].getsmell()+"| Gl: "+playArea[x][y].getglimmer()+"| P: "+playArea[x][y].getpit()+"| W: "+playArea[x][y].getwumpus()+"| Go: "+playArea[x][y].getgold());
				}
			}*/		
		}
	
		/* For debugging 
		for (int x = 0; x < mapSize; x++)
		{
			for (int y = 0; y < mapSize; y++)
			{
				System.out.println("x: "+x+" y: "+y+" | values: B "+playArea[x][y].getbreeze()+"| S: "+playArea[x][y].getsmell()+"| Gl: "+playArea[x][y].getglimmer()+"| P: "+playArea[x][y].getpit()+"| W: "+playArea[x][y].getwumpus()+"| Go: "+playArea[x][y].getgold());									
			}
		}*/

		//Continues to loop while validLoc and endGame are false
		while(!endGame)
		{	
			//For exception handling within loop
			boolean validSave = false, validLoc = false;
			int saveChoice = 0;
	
			try
			{
				while (!validLoc)
				{
					//Captures players X and Y coordinate
					System.out.printf("Please enter X then Y coordinate between 0 - "+(mapSize - 1)+":\n");					
					
					playerX = s.nextInt();	
					playerY = s.nextInt();
					
					if(((playerX >= 0) && (playerX < mapSize)) && ((playerY >= 0) && (playerY < mapSize)))
						validLoc = true;					
				}
				validLoc = false;
			}
			catch(InputMismatchException e)
			{
				s.nextLine();	
			}
			
			//Checks room status
			if (playArea[playerX][playerY].getpit() == true)
			{
				System.out.println("You fell in a pit. You Lose.");
				endGame = true;
			}
			else if (playArea[playerX][playerY].getwumpus() == true)
			{
				System.out.println("You stumbled into the wumpus's layer. You lose.");
				endGame = true;					
			}
			else if (playArea[playerX][playerY].getgold() == true)
			{
				System.out.println("You've found the gold! You win.");
				endGame = true;					
			}
			else
			{
				System.out.println("You enter an empty room.");

				//Detection phrases
				if (playArea[playerX][playerY].getbreeze() == true)
					System.out.println("You feel a cool breeze.");
				if (playArea[playerX][playerY].getsmell() == true)
					System.out.println("You smell an awful oder.");
				if (playArea[playerX][playerY].getglimmer() == true)
					System.out.println("You spot a faint glimmer.");					
			}
			
			//Checks if player enters 1 or 0
			while (!validSave)
			{
				try 
				{
					//Asks player if they want to save
					System.out.println ("Do you want to save? Enter 0 for yes or 1 for no.");
					saveChoice = s.nextInt();
					
					if ((saveChoice >= 0) && (saveChoice < 2))
						validSave = true;
				}
				catch (InputMismatchException e)
				{
					s.nextLine();
				}
				
			}
			
			//Saves game if 0
			if (saveChoice == 0)
			{
				try
				{
					fileoutput = new File ("wumpsave.sav");
					writer = new PrintWriter(fileoutput);
					
					for (int x = 0; x < mapSize; x++)
					{
						for (int y = 0; y < mapSize; y++)
						{				
							writer.println (playArea[x][y].getpit());
							writer.println (playArea[x][y].getwumpus());
							writer.println (playArea[x][y].getgold());
							writer.println (playArea[x][y].getbreeze());
							writer.println (playArea[x][y].getsmell());
							writer.println (playArea[x][y].getglimmer());
						}
					}
					writer.close();
				}
				catch (FileNotFoundException e)
				{
					System.out.println ("Um...");
				}
				System.out.println ("game saved");				
			}
		}								
	}
}