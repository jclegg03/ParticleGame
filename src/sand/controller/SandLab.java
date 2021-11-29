package sand.controller;

import java.awt.*;
import java.util.*;

import sand.view.SandDisplay;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int ICE = 4;
  public static final int ANAKIN = 5;
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[6];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[ICE] = "Ice";
    names[ANAKIN] = "Anakin";
    //1. Add code to initialize the data member grid with same dimensions
   this.grid = new int[numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
    for(int row = 0; row < grid.length; row++)
    {
    	for(int col = 0; col < grid[row].length; col++)
    	{
    		if(grid[row][col] == METAL)
    		{
    			display.setColor(row, col, Color.GRAY);
    		}
    		else if(grid[row][col] == EMPTY)
    		{
    			display.setColor(row, col, Color.BLACK);
    		}
    		else if(grid[row][col] == SAND)
    		{
    			display.setColor(row, col, Color.YELLOW);
    		}
    		else if(grid[row][col] == WATER)
    		{
    			display.setColor(row, col, Color.CYAN);
    		}
    		else if(grid[row][col] == ICE)
    		{
    			display.setColor(row, col, Color.BLUE);
    		}
    		else if(grid[row][col] == ANAKIN)
    		{
    			display.setColor(row, col, Color.RED);
    		}
    	}
    }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int row = (int) (Math.random() * grid.length);
    int col = (int) (Math.random() * grid[row].length);
    if(row + 1 != grid.length && grid[row][col] == SAND && (grid[row + 1][col] == EMPTY || grid[row + 1][col] == WATER))
    {
      if(grid[row + 1][col] == WATER)
      {
        grid[row][col] = WATER;
        grid[row + 1][col] = SAND;
      }
      else
      {
        grid[row][col] = EMPTY;
        grid[row + 1][col] = SAND;
      }
      return;
    }
    else if(row + 1 != grid.length && grid[row][col] == ICE && grid[row + 1][col] == EMPTY)
    {
    	grid[row][col] = EMPTY;
    	grid[row + 1][col] = ICE;
    	return;
    }
    else if(row > 0 && grid[row][col] == ICE && grid[row - 1][col] == WATER)
    {
    	grid[row][col] = WATER;
    	grid[row - 1][col] = ICE;
    }
    
    
    int direction = (int) (Math.random() * 4);
    final int STILL = 0;
    final int RIGHT = 1;
    final int LEFT = 2;
    final int DOWN = 3;
    if(direction == STILL)
    {
    	return;
    }
    else if(grid[row][col] == WATER)
    {
    	if(direction == RIGHT && grid[row].length != col + 1 && grid[row][col + 1] == EMPTY)
    	{
    		grid[row][col] = EMPTY;
    		grid[row][col + 1] = WATER;
    		return;
    	}
    	else if(direction == LEFT && col != 0 && grid[row][col - 1] == EMPTY)
    	{
    		grid[row][col] = EMPTY;
    		grid[row][col - 1] = WATER;
    		return;
    	}
    	else if(direction == DOWN && row != grid.length - 1 && grid[row + 1][col] == EMPTY)
    	{
    		grid[row][col] = EMPTY;
    		grid[row + 1][col] = WATER;
    		return;
    	}
    }
    else if(grid[row][col] == ANAKIN)
    {
    	//finish later
    }
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
