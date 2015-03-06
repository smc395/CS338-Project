import java.util.ArrayList;

/**
 * Ghis class is to hold a list of ingredients to search through and compare
 * @author Sung Yan Chao
 * CS338 Project
 */
public class Ingredients {

	private ArrayList<String> ingredients = new ArrayList<String>();
	
	public Ingredients(){}
	
	public Ingredients( ArrayList<String> I ){
		setIngredients( I );
	}
	
	public void setIngredients( ArrayList<String> ing ){ ingredients = ing; }
	
	/**
	 * add an ingredient to the ingredient list
	 * @param i
	 */
	public void addIngredient( String i ){ ingredients.add( i ); }
	
	/**
	 * get ingredients
	 * @return
	 */
	public ArrayList<String> getIngredients(){ return ingredients; }
	
	/**
	 * compares the ingredients list passed with this ingredients list
	 * @param ing
	 * @return a string of missing ingredients
	 */
	public ArrayList<String> compareIngredients( Ingredients ing )
	{
		// create array to store missing ingredients
		ArrayList<String> missingIngredients = new ArrayList<String>();
		
		// create array to store the ingredients of the recipe from Yummly
		ArrayList<String> yummlyIngredients = ing.getIngredients();
		
		// for every ingredient in the ingredients list from Yummly
		for( int y = 0; y < yummlyIngredients.size(); y++)
		{
			boolean foundIngredient = false;
			int u = 0;
			// while the ingredient is not found and has not reached the end of the user ingredients list, search for a match
			while( foundIngredient == false && (u != ingredients.size()) )
			{
				String yumIngredient = yummlyIngredients.get(y);
				String userIngredient = ingredients.get(u);
				if( yumIngredient.toLowerCase().contains(userIngredient.toLowerCase() ) )
				{	
					foundIngredient = true;
				}
				u++;
			}
			// if after looping through the user list and the ingredient still hasn't been found, add to missing ingredients list
			if( foundIngredient == false )
			{
				missingIngredients.add(yummlyIngredients.get(y));
			}			
		}
		
		return missingIngredients;
	}
}
