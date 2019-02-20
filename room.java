/*
Clark Rossi
CSC 162
Wumpus Game
room.java
*/
public class room
{
	private boolean breeze, smell, glimmer; //For detection values
	private boolean pit, wumpus, gold; //For contents of room

	//Room empty constructor
	public room()
	{
	}
	
//----------------------------------- Accessors ----------------------------------- 
	public boolean getbreeze()
	{
		return this.breeze;
	}
	public boolean getsmell()
	{
		return this.smell;
	}
	public boolean getglimmer()
	{
		return this.glimmer;
	}	
	public boolean getpit()
	{
		return this.pit;
	}	
	public boolean getwumpus()
	{
		return this.wumpus;
	}	
	public boolean getgold()
	{
		return this.gold;
	}

//----------------------------------- Mutators -----------------------------------
	public void setbreeze(boolean newBreeze)
	{
		this.breeze = newBreeze;
	}
	public void setsmell(boolean newSmell)
	{
		this.smell = newSmell;
	}
	public void setglimmer(boolean newGlimmer)
	{
		this.glimmer = newGlimmer;
	}
	public void setpit(boolean newPit)
	{
		this.pit = newPit;
	}
	public void setwumpus(boolean newWumpus)
	{
		this.wumpus = newWumpus;
	}
	public void setgold(boolean newGold)
	{
		this.gold = newGold;
	}		
}