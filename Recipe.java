import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class serves as the class to create recipe objects with information retrieved from Yummly's API 
 * @author Sung Yan Chao
 * CS338 Project
 */
public class Recipe {

	private String name;
	private String course;
	private String cuisine;
	private String totalTime;
	private String recipeSource;
	private String yield;
	private int ingredientMatchCount;
	private Ingredients missingIngredients;
	private Ingredients rawIngredients;
	private Ingredients ingredientsLines;
	private String recipeID;
	private String recipeImage;
	
	public Recipe(){
		ingredientsLines = new Ingredients();
	}
	
	public void setName( String n ){ name = n; }
	
	public void setCourse( String cou ){ course = cou; }
	
	public void setCuisine( String cui ){ cuisine = cui; }

	public void setTime( String t ){ totalTime = t; }
	
	public void setRecipeSource( String rs ){ recipeSource = rs; }
	
	public void setYieldAmount( String y ){ yield = y; }
	
	// set ingredient match count to the size of the ingredients list from yummly from the missing ingredients list
	public void setIngredientMatchCount()
	{ 
		ingredientMatchCount = ( rawIngredients.getIngredients().size() - missingIngredients.getIngredients().size() ); 
	}
	
	public void setMissingIngredients( Ingredients mi ){ missingIngredients = mi; }
	
	public void setRawIngredientsList( Ingredients ri ){ rawIngredients = ri; }
	
	public void setIngredientsLines( Ingredients il ){ ingredientsLines = il; }
	
	public void setID( String id ){ recipeID = id; }
	
	public void setRecipeImage( String img ){ recipeImage = img; }
	
	public String getName(){ return name; }
	
	public String getCourse(){ return course; }
	
	public String getCuisine(){ return cuisine; }
	
	public String getTime(){ return totalTime; }
	
	public String getRecipeSource(){ return recipeSource; }

	public String getRecipeID(){ return recipeID; }
	
	public Ingredients getMissingIngredients(){ return missingIngredients; }
	
	public Ingredients getRawIngredients(){ return rawIngredients; }
	
	public Ingredients getIngredientsLines(){ return ingredientsLines; }
	
	public int getMatchCount(){ return ingredientMatchCount; }
	
	public String getYieldAmount(){ return yield; }
	
	public String getRecipeImage(){ return recipeImage; }
	
	public void getRecipeDetails() throws IOException
	{
		String apiEndpoint = "https://api.yummly.com/v1/api/recipe/";
		String baseURL = apiEndpoint + recipeID + "?_app_id=" + YummlySearch.getAppID() + "&_app_key=" + YummlySearch.getAppKey();
		
		// Connect to the URL
		URL url = new URL( baseURL );
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		
		// Convert to a JSON object to get recipe details
    	JsonParser parser = new JsonParser();
    	JsonElement root = parser.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonObject rootobj = root.getAsJsonObject();
    	
    	// get ingredient details
    	JsonArray ingLines = rootobj.get("ingredientLines").getAsJsonArray();
    	for( int i = 0; i < ingLines.size(); i++ )
    	{
    		ingredientsLines.addIngredient( ingLines.get(i).getAsString() );
    	}
    	
    	// get yield amount
    	if( rootobj.get("yield").isJsonNull() )
    	{
    		setYieldAmount( "N/A" );
    	}
    	else
    	{ 
    		setYieldAmount( rootobj.get("yield").getAsString() ); 
    	}

    	// get total time
    	if( rootobj.get("totalTime").isJsonNull() )
    	{
    		setTime( rootobj.get("totalTimeInSeconds").getAsString() + " seconds" );
    	}
    	else
    	{
    		setTime( rootobj.get("totalTime").getAsString() );
    	}
  
    	// get recipe source
    	if( rootobj.get("source").getAsJsonObject().get("sourceRecipeUrl").isJsonNull() )
    	{
    		setRecipeSource( rootobj.get("source").getAsJsonObject().get("sourceSiteUrl").getAsString() );
    	}
    	else
    	{
    		setRecipeSource( rootobj.get("source").getAsJsonObject().get("sourceRecipeUrl").getAsString() );
    	}
    	
    	// get recipe image
    	JsonArray images = rootobj.get("images").getAsJsonArray();
    	if( images.size() != 0 )
    	{
    		JsonObject imageURL = images.get(0).getAsJsonObject();
    		JsonObject imageURLBySize = imageURL.get("imageUrlsBySize").getAsJsonObject();
    		
    		// check there is an image to get
    		if( !imageURLBySize.get("90").getAsString().substring(0,4).equals("null") || !imageURLBySize.get("360").getAsString().substring(0,4).equals("null") )
    		{
	    		// check if there is a hostedLargeUrl
	    		if( !imageURL.get("hostedLargeUrl").isJsonNull() )
	    		{
	    			setRecipeImage( imageURL.get("hostedLargeUrl").getAsString() );
	    		}
	    		// check if there is a hostedMediumUrl
	    		else if( !imageURL.get("hostedMediumUrl").isJsonNull() )
	    		{ 
	    			setRecipeImage( imageURL.get("hostedMediumUrl").getAsString() ); 
	    		}
	    		// check if there is a hostedSmallUrl
	    		else if( !imageURL.get("hostedSmallUrl").isJsonNull() )
	    		{
	    			setRecipeImage( imageURL.get("hostedSmallUrl").getAsString() );
	    		}
	    		else{
	    			setRecipeImage("");
	    		}
    		}
    		else{
    			setRecipeImage("");
    		}
    	}
	}
	
	public String toString(){
		return getName();
	}
}
