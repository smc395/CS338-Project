import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * This class serves as the class to interact with the Yummly API and manipulation of responses
 * @author Sung Yan Chao
 * CS338 Project
 */
public class YummlySearch {
	
	private final static String appID = "0b6e7ae1";
	private final static String appKey = "8916a6aeaecc7b360377b30a9495b9ca";
	private final String apiEndpoint = "https://api.yummly.com/v1/api/recipes?";
	
	private String course;
	private String cuisine;
	private TimeObject totalTime;
	private String[] keywords;
	private int totalMatchCount;
	private Ingredients userIngredients;
	
	public YummlySearch( String co, String cu, TimeObject t, String k, Ingredients i){
		setCourse( co );
		setCuisine( cu );
		setTime( t );
		setKeywords( k );
		setIngredientsList( i );
	}

	private void setIngredientsList(Ingredients i) { userIngredients = i; }

	private void setKeywords(String k) { keywords = k.split(" "); }

	private void setTime(TimeObject t) { totalTime = t; }

	private void setCuisine(String cu) { cuisine = cu; }

	private void setCourse(String co) { course = co; }
	
	public Ingredients getIngredientsList(){ return userIngredients; }
	
	public String getCourse(){ return course; }
	
	public String getCuisine(){ return cuisine; }
	
	public String getTime(){ return totalTime.getSeconds(); }
	
	public String[] getKeywords(){ return keywords; }
	
	public void setMatchCount( String count ){ totalMatchCount = Integer.valueOf(count); }
	
	public int getMatchCount(){ return totalMatchCount; }
	
	public static String getAppKey(){ return appKey; }
	
	public static String getAppID(){ return appID; }
	
	/**
	 * Call the Yummly API and search recipes that match user query
	 * @return JsonObject of all the matched recipes or null if none matched
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public JsonObject searchRecipes() throws Exception
	{
		// Connect to the URL
		URL url = new URL( createURL() );
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		
		// Convert to a JSON object to print data
    	JsonParser parser = new JsonParser();
    	JsonElement root = parser.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonObject rootobj = root.getAsJsonObject();
		
    	//System.out.printf("Root object = %s\n", rootobj);
    	
    	// get total match count
		totalMatchCount = rootobj.get("totalMatchCount").getAsInt();
		
		// if there were recipes returned, create recipe objects
		if( totalMatchCount > 0 )
		{ 
			return rootobj;
		}
		else{
			return null;
		}
		
	}// end searchRecipes
	
	/**
	 * create an array of Recipes
	 * @param jObject
	 * @return array list of Recipe objects that matched the user's query
	 */
	public ArrayList<Recipe> createRecipes( JsonObject jObject )
	{
		// get recipes that matched the query
		JsonArray matches = jObject.get("matches").getAsJsonArray();
		
		// create array of Recipe objects
		ArrayList<Recipe> matchedRecipes = new ArrayList<Recipe>();
		
		// go through all the matched recipes to compare ingredients
		for( int k = 0; k < matches.size(); k++ )
		{
			// get recipe object
			JsonObject recipe = matches.get(k).getAsJsonObject();
			
			// get ingredients array
			JsonArray ingredients = recipe.get("ingredients").getAsJsonArray();
			
			// create Ingredients object of the recipe
			Ingredients yummlyIngredients = new Ingredients();
			
			for( int ingNum = 0; ingNum < ingredients.size(); ingNum++ )
			{
				yummlyIngredients.addIngredient(ingredients.get(ingNum).getAsString());
			}
			
			ArrayList<String> missing = userIngredients.compareIngredients( yummlyIngredients );
			Ingredients missingIngredients = new Ingredients( missing );
			
			// get recipe name
			String recipeName = recipe.get("recipeName").getAsString();
			
			// get recipe id
			String id = recipe.get("id").getAsString();
			
			// create new recipe object
			Recipe recipeEntry = new Recipe();
			recipeEntry.setMissingIngredients(missingIngredients);
			recipeEntry.setRawIngredientsList(yummlyIngredients);
			recipeEntry.setCourse(course);
			recipeEntry.setCuisine(cuisine);
			recipeEntry.setName(recipeName);
			recipeEntry.setID(id);
			recipeEntry.setIngredientMatchCount();
			
			matchedRecipes.add(recipeEntry);
		}// end for
		
		return matchedRecipes;
	}
	
	/**
	 * create the URL used in the Yummly API search
	 * @return
	 */
	public String createURL()
	{
		String baseURL = apiEndpoint + "_app_id=" + appID + "&_app_key=" + appKey;
		
		String courseSearch = "&allowedCourse[]=";
		String cuisineSearch = "&allowedCuisine[]=";
		String timeSearch = "&maxTotalTimeInSeconds=" + getTime();
		StringBuilder query = new StringBuilder("&q=");
		
		// create the query string
		for(int i = 0; i < keywords.length; i++)
		{
			if( i == 0 ){ query.append(keywords[i]); }
			else{ query.append("+" + keywords[i]); }
		}
		
		// create URL
		String sURL = baseURL + courseSearch + course.replaceAll(" ", "%20") + cuisineSearch + cuisine.replaceAll(" ", "%20") + timeSearch  + "&maxResult=100" + query;
		return sURL;
		
	}// end createURL()
	
}// end class
