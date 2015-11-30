package com.armadillo.slitherlink.altair;

/**
 * Class Position is used to determine the coordinates of an object
 * in the draw window.
 * 
 * @author John Pushnik
 * @version 04-24-06
 * @version 02-16-09 added equals() method, delta parameters
 */
public class Position
{
	// instance variables - replace the example below with your own
	protected int x;
	protected int y;

   /**
    * Creates a new Position object with no parameters.
    */
	public Position()
	{
		x = 0;
		y = 0;
	}
   /**
    * Creates a new Position object.
    * @param inputx x coordinate of Position.
    * @param inputy y coordinate of Position.
    */
	public Position(int inputx, int inputy)
	{
		x = inputx;
		y = inputy;
	}
	/**
	 * Creates a new Position object.
	 * @param inputx x coordinate of Position.
	 * @param inputy y coordinate of Position.
	 * @param scale scale for new Position.
	 */
	public Position(int inputx, int inputy, float scale)
	{
		x = (int)(inputx * scale);
		y = (int)(inputy * scale);
	}
   /**
    * Creates a new Position object.
    * @param base Position used to add for new Position.
    */
	public Position(Position base)
	{
		x = base.x;
		y = base.y;
	}
   /**
    * Creates a new Position object.
    * @param base Position used to add for new Position.
    * @param delta another Position to add for new Position.
    */
	public Position(Position base, Position delta)
	{
		x = base.x + delta.x;
		y = base.y + delta.y;
	}
   /**
    * Returns the x coordinate for a Position object.
    * @return x x coordinate of Position.
    */
	public int getX()
	{		return x;	}
   /**
    * Returns the y coordinate for a Position object.
    * @return y y coordinate of Position.
    */
	public int getY()
	{		return y;	}
   /**
    * Changes a Position object to new coordinates.
    * @param inputx New x coordinate of Position.
    * @param inputy New y coordinate of Position.
    */
	public void setPosition(int inputx, int inputy)
	{
		x = inputx;
		y = inputy;
	}
   /**
    * Sets a Position object to match another Position with offset.
    * @param input Position to be matched.
    */
	public void setPosition(Position input, int inputx, int inputy)
	{
		x = input.x + inputx;
		y = input.y + inputy;
	}
   /**
    * Sets a Position object to match another Position.
    * @param input Position to be matched.
    */
	public void setPosition(Position input)
	{
		x = input.x;
		y = input.y;
	}
   /**
    * Sets a Position object based on an offset from another Position.
    * @param base Position from which to start.
	* @param delta offset from existing position.
    */
	public void setPosition(Position base, Position delta)
	{
		x = base.x + delta.x;
		y = base.y + delta.y;
	}
   /**
    * Tests a Position object to see if it has the same Position.
    * @param input New Position to be matched.
    */
	public boolean equals(Position input)
	{
		if ((x == input.x) && (	y == input.y)) return true;
		return false;
	}
   /**
    * Sets a Position object to new x coordinate.
    * @param inputx New x coordinate of Position.
    */
	public void setX(int inputx)
	{		x = inputx;	}
   /**
    * Sets a Position object to new y coordinate.
    * @param inputy New y coordinate of Position.
    */
	public void setY(int inputy)
	{		y = inputy;	}
   /**
    * Returns the string x,y.
    */
	public String toString()
	{
	    return (""+ x + "," + y);	
	}

	public float getDist(Position delta) {
		float a = x - delta.getX();
		float b = y - delta.getY();
		return (float)Math.sqrt((double)(a * a) + (b * b));
	}

/* End Position */	
}


