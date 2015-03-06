/**
 * This class serves as the class to create objects for the prep time combo box.
 * @author Sung Yan Chao
 * CS338 Project
 */
public class TimeObject {

	private int seconds;
	
	public TimeObject( int sec ){
		setSeconds( sec );
	}

	/**
	 * set the total prep time in seconds
	 * @param sec - total prep time
	 */
	public void setSeconds( int sec ) { seconds = sec; }

	/**
	 * get the total prep time in seconds 
	 * @return String representing the total time in seconds
	 */
	public String getSeconds(){ return String.valueOf( seconds ); }
	
	/**
	 * how the object will be displayed in the combo box
	 */
	public String toString(){ return calcDisplayTime( seconds ); }
	
	/**
	 * determines how to display the time
	 * @param seconds
	 * @return
	 */
	public static String calcDisplayTime( int seconds )
	{	
		String display;
		
		// if the time is less than 1 hour
		if( seconds < 3600 )
		{
			
			int minutes = seconds / 60;
			display = "< " + minutes + " min";
		}
		// if the time is less than 12 hours
		else if( seconds <= 43200)
		{
			double hours = (double) seconds / 3600;
			if( hours == 1 )
			{
				display = "< " + hours + " hour";
			}
			else
			{
				display = "< " + hours + " hours";
			}
		}
		else
		{
			display = "Anytime";
		}
		
		return display;
	}
}
