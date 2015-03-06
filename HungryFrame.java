import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.google.gson.JsonObject;

/**
 * This class handles the layout and GUI components
 * @author Sung Yan Chao
 * CS338 Project
 */
public class HungryFrame extends JFrame 
{
	private static final long serialVersionUID = -5003408627621968166L;
	JButton addButton;
	JButton removeButton;
	JButton searchButton;
	
	JList<String> userList;
	JList<Recipe> recipeList;
	JList<String> missingList;
	
	JTextField ingredient;
	JTextField keywords;
	
	JLabel titleLabel;
	JLabel yourIngredients;
	JLabel enterSearch;
	JLabel recipes;
	JLabel missing;
	JLabel cuisine;
	JLabel course;
	JLabel prep;
	JLabel details;
	
	JLabel enterIngredientLabel;
	JLabel dupIngredientLabel;
	JLabel noMatchLabel;
	JLabel noIngredientsLabel;
	JLabel enterKeywordsLabel;
	JLabel selectRecipeLabel;
	JLabel recipeGetError;
	JLabel searching;
	
	JEditorPane recipeDetails;
	
	JScrollPane recipeScrollPane;
	
	JComboBox<String> courseCombo;
	JComboBox<String> cuisineCombo;
	JComboBox<TimeObject> prepCombo;
	
	DefaultListModel<String> userListModel;
	DefaultListModel<Recipe> recipeListModel;
	DefaultListModel<String> missingListModel;
	
	JPanel titlePanel;
	JPanel userPanel;
	JPanel searchPanel;
	JPanel recipePanel;
	JPanel detailsPanel;
	JPanel addRemovePanel;
	JPanel comboPanel;
	JPanel bigPanel;
	JPanel centerPanel;
	
	public HungryFrame()
	{
		super("I'm Hungry"); // application name
		
		// create title labels
		titleLabel = new JLabel("I'm Hungry");
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 32 ));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		yourIngredients = new JLabel("Your Ingredients");
		yourIngredients.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 14));
		yourIngredients.setAlignmentX(CENTER_ALIGNMENT);
		
		enterSearch = new JLabel("Enter Search Criteria");
		enterSearch.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 14));
		enterSearch.setAlignmentX(CENTER_ALIGNMENT);
		
		recipes = new JLabel("Recipes");
		recipes.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 14));
		recipes.setAlignmentX(CENTER_ALIGNMENT);
		
		details = new JLabel("Recipe Details");
		details.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 14));
		details.setAlignmentX(CENTER_ALIGNMENT);
		
		missing = new JLabel("Missing Ingredients");
		missing.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 14));
		missing.setAlignmentX(CENTER_ALIGNMENT);
		
		// create combo box labels	
		cuisine = new JLabel("Cuisine");
		cuisine.setHorizontalAlignment(JLabel.CENTER);
		
		course = new JLabel("Course");
		course.setHorizontalAlignment(JLabel.CENTER);
		
		prep = new JLabel("Total time");
		prep.setHorizontalAlignment(JLabel.CENTER);
		
		// create text field labels
		enterIngredientLabel = new JLabel("Enter an ingredient:");
		enterIngredientLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		enterKeywordsLabel = new JLabel("Enter keywords (example: parmesean chicken bake):");
		enterKeywordsLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		selectRecipeLabel = new JLabel("Select a recipe to view details:");
		selectRecipeLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// create error labels
		dupIngredientLabel = new JLabel("Ingredient already in the list, please enter another ingredient");
		dupIngredientLabel.setVisible(false);
		dupIngredientLabel.setAlignmentX(CENTER_ALIGNMENT);
		dupIngredientLabel.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		noMatchLabel = new JLabel("Sorry, no recipes match those keywords");
		noMatchLabel.setVisible(false);
		noMatchLabel.setAlignmentX(CENTER_ALIGNMENT);
		noMatchLabel.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		noIngredientsLabel = new JLabel("No ingredients to search against, please add ingredients under 'Your Ingredients'");
		noIngredientsLabel.setVisible(false);
		noIngredientsLabel.setAlignmentX(CENTER_ALIGNMENT);
		noIngredientsLabel.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		recipeGetError = new JLabel("Error retrieving recipe details");
		recipeGetError.setVisible(false);
		recipeGetError.setAlignmentX(CENTER_ALIGNMENT);
		recipeGetError.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		searching = new JLabel("Searching...");
		searching.setVisible(false);
		searching.setAlignmentX(CENTER_ALIGNMENT);
		searching.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		
		// create editor pane for recipe details
		recipeDetails = new JEditorPane();
		recipeDetails.setEditable(false);
		JScrollPane scrollPane = new JScrollPane( recipeDetails );
		
		// create text fields
		DocumentHandler docHandler = new DocumentHandler();
		UserKeyHandler userKeyHandler = new UserKeyHandler();
		SearchHandler searchHandler = new SearchHandler();
		
		ingredient = new JTextField(15);
		ingredient.getDocument().addDocumentListener(docHandler);
		ingredient.addActionListener(userKeyHandler);
		ingredient.setAlignmentX(CENTER_ALIGNMENT);
		ingredient.setMaximumSize(new Dimension(200,30));
		
		// create keywords text field
		keywords = new JTextField(20);
		keywords.getDocument().addDocumentListener(docHandler);
		keywords.setAlignmentX(CENTER_ALIGNMENT);
		keywords.addActionListener(searchHandler);
		keywords.setMaximumSize(new Dimension(200,30));
		
		// create search button
		searchButton = new JButton("Search");
		searchButton.setActionCommand("search");
		searchButton.addActionListener(searchHandler);
		searchButton.setEnabled(false);
		searchButton.setAlignmentX(CENTER_ALIGNMENT);
		
		// create add/remove buttons
		ButtonHandler buttonHandler = new ButtonHandler();
		
		addButton = new JButton("Add");
		addButton.setActionCommand("add");
		addButton.addActionListener(buttonHandler);
		addButton.setEnabled(false);
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		
		removeButton = new JButton("Remove");
		removeButton.setActionCommand("remove");
		removeButton.addActionListener(buttonHandler);
		removeButton.setEnabled(false);
		removeButton.setAlignmentX(CENTER_ALIGNMENT);
		
		// create cuisine combo box
		String[] cuisines = { "American", "Asian", "Barbecue", "Cajun & Creole", "Chinese", "Cuban", "English", "French",
				"German", "Greek", "Hawaiian", "Hungarian", "Indian", "Irish", "Italian", "Japanese", "Kid-Friendly", "Mediterranean",
				"Mexican", "Moroccan", "Portuguese", "Southern & Soul Food", "Southwestern", "Spanish", "Swedish", "Thai" };
		
		cuisineCombo = new JComboBox<String>( cuisines );
		
		// create course combo box
		String[] courses = { "Appetizers", "Beverages", "Breads", "Breakfast and Brunch", "Cocktails", "Condiments and Sauces",
				"Desserts", "Lunch and Snacks", "Main Dishes", "Salads", "Side Dishes", "Soups"	};
		
		courseCombo = new JComboBox<String>( courses );
		
		// create prep times to add to combo box
		TimeObject fiveMin = new TimeObject( 300 );
		TimeObject tenMin = new TimeObject( 600 );
		TimeObject fifteenMin = new TimeObject( 900 );
		TimeObject twentyMin = new TimeObject( 1200 );
		TimeObject twentyFiveMin = new TimeObject( 1500 );
		TimeObject thirtyMin = new TimeObject( 1800 );
		TimeObject fourtyFiveMin = new TimeObject( 2700 );
		TimeObject oneHour = new TimeObject( 3600 );
		TimeObject oneHalfHour = new TimeObject( 5400 );
		TimeObject twoHour = new TimeObject( 7200 );
		TimeObject fourHour = new TimeObject( 14400 );
		TimeObject eightHour = new TimeObject( 28800 );
		TimeObject tweleveHour = new TimeObject( 43200 );
		TimeObject anytime = new TimeObject( 43201 );
		
		// create prep time combo box
		TimeObject[] times = { fiveMin, tenMin, fifteenMin, twentyMin, twentyFiveMin, thirtyMin, fourtyFiveMin, oneHour, 
				oneHalfHour, twoHour, fourHour, eightHour, tweleveHour, anytime};
		
		prepCombo = new JComboBox<TimeObject>( times );
		
		// create list for your ingredients
		userListModel = new DefaultListModel<String>();
		userList = new JList<String>( userListModel );
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		userList.setLayoutOrientation(JList.VERTICAL);
		userList.setMaximumSize(new Dimension(15,5));
	
		 // add listener for user list
		UserListHandler userListHandler = new UserListHandler();
		userList.addMouseListener(userListHandler);
		
		// add scroll bar for user list
		JScrollPane userScrollPane = new JScrollPane(userList);
		userScrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		// create handlers for recipe list
		RecipeListHandler recipeListHandler = new RecipeListHandler();
		RecipeKeyHandler recipeKeyHandler = new RecipeKeyHandler();
		
		// create list for recipes
		recipeListModel = new DefaultListModel<Recipe>();
		recipeList = new JList<Recipe>( recipeListModel );
		recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recipeList.setLayoutOrientation(JList.VERTICAL);
		recipeList.addMouseListener(recipeListHandler); // add listener for recipe list
		recipeList.addKeyListener(recipeKeyHandler);	// add listener for key pressed
		recipeScrollPane = new JScrollPane(recipeList);
		recipeScrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		// create list for missing ingredients
		missingListModel = new DefaultListModel<String>();
		missingList = new JList<String>( missingListModel );
		missingList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScrollPane3 = new JScrollPane(missingList);
		listScrollPane3.setAlignmentX(CENTER_ALIGNMENT);
		
		// panel for the add and remove buttons
		addRemovePanel = new JPanel();
		addRemovePanel.setLayout(new GridLayout(0,2,50,0));
		addRemovePanel.add(addButton);
		addRemovePanel.add(removeButton);
		addRemovePanel.setAlignmentX(CENTER_ALIGNMENT);
		addRemovePanel.setBackground(Color.yellow);
		addRemovePanel.setMaximumSize(new Dimension(300,20));
		addRemovePanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the your ingredients label, the user ingredients list, ingredients textfield, and add/remove buttons
		userPanel = new JPanel();
		userPanel.setLayout( new BoxLayout(userPanel, BoxLayout.Y_AXIS) );
		userPanel.add(yourIngredients);									// add title label
		userPanel.add(Box.createRigidArea(new Dimension(0,15)));
		userPanel.add(userScrollPane);									// add user ingredients list
		userPanel.add(Box.createRigidArea(new Dimension(0,40)));
		userPanel.add(enterIngredientLabel);							// add "Enter an ingredient" label
		userPanel.add(Box.createRigidArea( new Dimension(0,10)));
		userPanel.add(ingredient);										// add  text field to enter ingredient
		userPanel.add(Box.createRigidArea(new Dimension(0,15)));
		userPanel.add(addRemovePanel);									// add add/remove buttons
		userPanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the combo boxes and their labels
		comboPanel = new JPanel();
		comboPanel.setLayout( new GridLayout(6,1,0,5) );
		comboPanel.add(cuisine);										// add cuisine label
		comboPanel.add(cuisineCombo);									// add cuisine combo box
		comboPanel.add(course);											// add course label
		comboPanel.add(courseCombo);									// add course combo box
		comboPanel.add(prep);											// add prep time label
		comboPanel.add(prepCombo);										// add prep time combo box
		comboPanel.setAlignmentX(CENTER_ALIGNMENT);
		comboPanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the search title, combo panel, keywords label, keywords text field, search button, and error messages
		searchPanel = new JPanel();
		searchPanel.setLayout( new BoxLayout( searchPanel, BoxLayout.Y_AXIS));
		searchPanel.add(enterSearch);									// add title label
		searchPanel.add(comboPanel);									// add combo panel
		searchPanel.add(Box.createRigidArea(new Dimension(0,32)));
		searchPanel.add(enterKeywordsLabel);							// add keywords label
		searchPanel.add(Box.createRigidArea( new Dimension(0,15)));
		searchPanel.add(keywords);										// add keywords text field
		searchPanel.add(Box.createRigidArea(new Dimension(0,12)));
		searchPanel.add(searchButton);									// add search button
		searchPanel.setPreferredSize(new Dimension(300,500));
		searchPanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the recipes title, select label, recipe list, missing ingredients label, and missing ingredients list
		recipePanel = new JPanel();
		recipePanel.setLayout( new BoxLayout( recipePanel, BoxLayout.Y_AXIS));
		recipePanel.add(recipes);										// add recipes title
		recipePanel.add(selectRecipeLabel);								// add select recipe label
		recipePanel.add(Box.createRigidArea(new Dimension(0,15)));
		recipePanel.add(recipeScrollPane);								// add list to hold recipes
		recipePanel.add(Box.createRigidArea(new Dimension(0,20)));
		recipePanel.add(missing);										// add missing ingredients label
		recipePanel.add(Box.createRigidArea(new Dimension(0,20)));
		recipePanel.add(listScrollPane3);								// add list to hold missing ingredients
		recipePanel.setPreferredSize( new Dimension(250,500));
		recipePanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the recipe details title and editor pane with recipe information
		detailsPanel = new JPanel();
		detailsPanel.setLayout( new BoxLayout( detailsPanel, BoxLayout.Y_AXIS));
		detailsPanel.add(details);										// add recipe details title
		detailsPanel.add(scrollPane);									// add JEditorPane
		detailsPanel.setPreferredSize(new Dimension(400,500));
		detailsPanel.setBackground( new Color(255,153,51) );
		
		// panel to hold the other panels
		bigPanel = new JPanel();
		bigPanel.setLayout( new BoxLayout( bigPanel, BoxLayout.X_AXIS) );
		bigPanel.setBorder( new EmptyBorder(50,50,50,50));
		bigPanel.add(userPanel);
		bigPanel.add(Box.createRigidArea(new Dimension(30,0)));
		bigPanel.add(searchPanel);
		bigPanel.add(Box.createRigidArea(new Dimension(30,0)));
		bigPanel.add(recipePanel);
		bigPanel.add(Box.createRigidArea(new Dimension(30,0)));
		bigPanel.add(detailsPanel);
		bigPanel.setBackground( new Color(255,153,51) );
		
		// add error labels
		JPanel errorPanel = new JPanel();
		errorPanel.add(dupIngredientLabel);							
		errorPanel.add(noIngredientsLabel);
		errorPanel.add(noMatchLabel);
		errorPanel.add(searching);
		errorPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
		errorPanel.setBackground( new Color(255,153,51) );
		
		// title panel
		titlePanel= new JPanel();
		titlePanel.add(titleLabel);
		titlePanel.setBackground( new Color(255,153,51) );
		
		// main container panel
		centerPanel = new JPanel();
		centerPanel.setLayout( new BorderLayout() );
		centerPanel.add(titlePanel, BorderLayout.NORTH);
		centerPanel.add(bigPanel, BorderLayout.CENTER);
		centerPanel.add(errorPanel, BorderLayout.SOUTH);
		
		// add panel to frame
		add(centerPanel);
	}
	
	/**
	 * Handles events on the add and remove buttons
	 */
	private class ButtonHandler implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String ingred = ingredient.getText();
			
			// create flag to indicate if the ingredient already exists 
			boolean inList = false;
	
			// check if there is an ingredient in the list that matches the value in the text field
			for(int i = 0; i < userListModel.getSize(); i++)
			{
				if( userListModel.get(i).equals(ingred) )
				{
					inList = true;
					break;
				}
			}
			
			switch(e.getActionCommand())
			{
				case "add":
					// if the ingredient is already in the list print error message and do not allow it to be added to the list
					if( inList == true )
					{
						noMatchLabel.setVisible(false);
						dupIngredientLabel.setVisible(true);
					}
					// add ingredient to the list
					else
					{
						userListModel.addElement(ingred);
						
						ingredient.setText("");
						addButton.setEnabled(false);
						
						// hide error message
						dupIngredientLabel.setVisible(false);
						noIngredientsLabel.setVisible(false);
					}
					break;
				case "remove":
						// remove selected ingredients from the list
						int[] selectedIngredients = userList.getSelectedIndices();
						int selectCount = selectedIngredients.length;
						
						while( selectCount != 0 )
						{
								userListModel.remove(selectedIngredients[selectCount-1]);
								selectCount = selectCount - 1;
						}
						// disable remove buttons
						removeButton.setEnabled(false);
						
						// hide error message
						dupIngredientLabel.setVisible(false);
					break;
			}
		}
	}// end class ButtonHandler

	/**
	 * Handles events on the search button
	 */
	private class SearchHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			if(!(keywords.getText().equals("")))
			{
				noMatchLabel.setVisible(false);
				noIngredientsLabel.setVisible(false);
				dupIngredientLabel.setVisible(false);
				searching.setVisible(true);
				
				// if there are no ingredients in the user ingredients list show error message
				if( userListModel.getSize() == 0 )
				{
					searching.setVisible(false);
					noIngredientsLabel.setVisible(true);
				}
				else
				{
					// indicate that program is searching
					recipeListModel.removeAllElements();
					ArrayList<Recipe> r = getRecipes();

					// SORT RECIPES
					ArrayList<ArrayList<Recipe>> sortedRecipes = new ArrayList<ArrayList<Recipe>>(40);
					
					for( int g = 0; g < 40; g++ )
					{
						ArrayList<Recipe> recipeHolder = new ArrayList<Recipe>();
						sortedRecipes.add(recipeHolder);
					}
					
					/* create hash map for recipes
					 * 
					 * for every recipe retrieved, go to the array index equal to the ingredient match count of the recipe 
					 * then add that recipe to the list
					 */
					for( int z = 0; z < r.size(); z++ )
					{
						int index = r.get(z).getMatchCount();
						Recipe re = r.get(z);
						
						sortedRecipes.get(index).add(re);
					}
					
					/* starting at the recipes with the most matched ingredients, populate the recipe list
					 */
					for( int v = sortedRecipes.size()-1; v > -1; v-- )
					{
						for( int m = 0; m < sortedRecipes.get(v).size(); m++)
						{
							if( sortedRecipes.get(v).size() != 0)
							{
								// add recipes to recipeListModel
								recipeListModel.addElement( sortedRecipes.get(v).get(m) );
							}
						}
					}
				}
				
				searching.setVisible(false);
			}
		}// end action performed
		
		/**
		 * get the recipes from the yummly API
		 * @return
		 */
		private ArrayList<Recipe> getRecipes()
		{
			Ingredients inList = new Ingredients();
			
			// create a list of ingredients that the user has
			for (Enumeration<String> ulm = userListModel.elements(); ulm.hasMoreElements();)	
			{
			       inList.addIngredient( ulm.nextElement() );		
			}
						
			// create search object
			YummlySearch search = new YummlySearch( (String) courseCombo.getSelectedItem(), (String) cuisineCombo.getSelectedItem(), 
					(TimeObject) prepCombo.getSelectedItem(), keywords.getText(), inList );
			
			// create array list to store matched recipes
			ArrayList<Recipe> recipes = new ArrayList<Recipe>();
			
			try {
				// search API
				JsonObject rootobject = search.searchRecipes();
				
				// if there were matches
				if( rootobject != null )
				{
					recipes = search.createRecipes(rootobject);
				}
				else
				{
					noMatchLabel.setVisible(true);
				}
			} catch (Exception ex){
				noMatchLabel.setVisible(true);
				ex.printStackTrace();
			}
			
			return recipes;
		}
	}// end SearchHandler
	
	/**
	 * Handles events on the user ingredient list
	 */
	private class UserListHandler extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			// if the user did not select anything from the list, disable the remove button
			if( userList.getSelectedIndices().length != 0 )
			{	
				removeButton.setEnabled(true);
			}
		}	
	}
	
	/**
	 * Handles key events for the user ingredient list
	 */
	private class UserKeyHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(!(ingredient.getText().equals("")))
			{
				String ingred = ingredient.getText();
				
				// create flag to indicate if the ingredient already exists 
				boolean inList = false;
		
				// check if there is an ingredient in the list that matches the value in the text field
				for(int i = 0; i < userListModel.getSize(); i++)
				{
					if( userListModel.get(i).equals(ingred) )
					{
						inList = true;
						break;
					}
				}
				
				// if the ingredient is already in the list print error message and do not allow it to be added to the list
				if( inList == true )
				{
					dupIngredientLabel.setVisible(true);
				}
				// add ingredient to the list
				else
				{
					userListModel.addElement(ingred);
					
					ingredient.setText("");
					addButton.setEnabled(false);
					
					// hide error message
					dupIngredientLabel.setVisible(false);
					noIngredientsLabel.setVisible(false);
				}
			}
		}
	}// end UserKeyHandler

	/**
	 * Handles events on the recipe list
	 */
	private class RecipeListHandler extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if( recipeList.getSelectedIndex() != -1 )
			{
				recipeGetError.setVisible(false);
				// clear JTextArea
				recipeDetails.setText("");
				// clear missing ingredients
				missingListModel.removeAllElements();
				
				// get selected recipe
				Recipe selectedRecipe = recipeListModel.get( recipeList.getSelectedIndex() );
				
				// get selected recipe details
				try {
					selectedRecipe.getRecipeDetails();
				} catch (IOException e1) {
					recipeGetError.setVisible(true);
				}
				
				// get the missing ingredients list from the recipe
				Ingredients missing = selectedRecipe.getMissingIngredients();
				
				// populate the missing ingredients list
				for( int h = 0; h < missing.getIngredients().size(); h++)
				{
					missingListModel.addElement( missing.getIngredients().get(h) );
				}
				
				// build HTML page to show
				StringBuilder html = new StringBuilder("<html><body>");
				html.append("<h2>"+ selectedRecipe.getName() + "</h2>" );
				html.append("<p><img src=\""+ selectedRecipe.getRecipeImage() +"\"</img></p>");
				html.append("<p>" + "<u>Yield:</u> " + selectedRecipe.getYieldAmount() + "</p>");
				html.append("<p>" + "<u>Total Time:</u> " + selectedRecipe.getTime() + "</p>");
				html.append("<p>" + "<u>Course Type:</u> " + selectedRecipe.getCourse() + "</p>");
				html.append("<p>" + "<u>Cuisine:</u> " + selectedRecipe.getCuisine() + "</p>");
				html.append("<p>" + "<u>Source:</u> " + selectedRecipe.getRecipeSource() + "</p>");
				html.append("<p><u>Ingredients:</u></p>");
				
				ArrayList<String> il = selectedRecipe.getIngredientsLines().getIngredients();
				for( int f = 0; f < il.size(); f++ )
				{
					html.append("<p>" + il.get(f) + "</p>" );
				}
				
				// show recipe details
				recipeDetails.setContentType("text/html");
				recipeDetails.setText(html+"</body></html>");
			}
		}
	}
	
	/**
	 * Handles key events on recipe list
	 */
	private class RecipeKeyHandler extends KeyAdapter
	{

		@Override
		public void keyPressed(KeyEvent e) 
		{
			// if the user hit enter on the list and the list is not empty
			if( (e.getKeyCode() == KeyEvent.VK_ENTER) && (recipeList.getSelectedIndex() != -1) )
			{
				recipeGetError.setVisible(false);
				
				// clear display area
				recipeDetails.setText("");
				
				// clear missing ingredients
				missingListModel.removeAllElements();
				
				// get selected recipe
				Recipe selectedRecipe = recipeListModel.get( recipeList.getSelectedIndex() );
				
				// get selected recipe details
				try {
					selectedRecipe.getRecipeDetails();
				} catch (IOException e1) {
					recipeGetError.setVisible(true);
				}
				
				// get the missing ingredients list from the recipe
				Ingredients missing = selectedRecipe.getMissingIngredients();
				
				// populate the missing ingredients list
				for( int h = 0; h < missing.getIngredients().size(); h++)
				{
					missingListModel.addElement( missing.getIngredients().get(h) );
				}
				
				// build HTML page to show
				StringBuilder html = new StringBuilder("<html><body>");
				html.append("<h2>"+ selectedRecipe.getName() + "</h2>" );
				html.append("<p><img src=\""+ selectedRecipe.getRecipeImage() +"\"</img></p>");
				html.append("<p>" + "<u>Yield:</u> " + selectedRecipe.getYieldAmount() + "</p>");
				html.append("<p>" + "<u>Total Time:</u> " + selectedRecipe.getTime() + "</p>");
				html.append("<p>" + "<u>Course Type:</u> " + selectedRecipe.getCourse() + "</p>");
				html.append("<p>" + "<u>Cuisine:</u> " + selectedRecipe.getCuisine() + "</p>");
				html.append("<p>" + "<u>Source:</u> " + selectedRecipe.getRecipeSource() + "</p>");
				html.append("<p><u>Ingredients:</u></p>");
				
				ArrayList<String> il = selectedRecipe.getIngredientsLines().getIngredients();
				for( int f = 0; f < il.size(); f++ )
				{
					html.append("<p>" + il.get(f) + "</p>" );
				}
				
				// show recipe details
				recipeDetails.setContentType("text/html");
				recipeDetails.setText(html+"</body></html>");
			}
		}
	}
	
	/**
	 * Handles events on the text fields
	 */
	private class DocumentHandler implements DocumentListener
	{
		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			// if ingredient text field is not empty, enable the add button
			if(!(ingredient.getText().equals("")))
			{
				addButton.setEnabled(true);
			}
			else // if ingredient text field is empty, disable the add button
			{
				addButton.setEnabled(false);
			}
			if(!(keywords.getText().equals("")))
			{
				searchButton.setEnabled(true);				
			}
			else
			{
				searchButton.setEnabled(false);				
			}			
		}

		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			// if ingredient text field is not empty, enable the add button
			if(!(ingredient.getText().equals("")))
			{
				addButton.setEnabled(true);
			}
			else // if ingredient text field is empty, disable the add button
			{
				addButton.setEnabled(false);
			}
			if(!(keywords.getText().equals("")))
			{
				searchButton.setEnabled(true);				
			}
			else
			{
				searchButton.setEnabled(false);				
			}			
		}

		@Override
		public void changedUpdate(DocumentEvent e) 
		{
			// if ingredient text field is not empty, enable the add button
			if(!(ingredient.getText().equals("")))
			{
				addButton.setEnabled(true);
			}
			else // if ingredient text field is empty, disable the add button
			{
				addButton.setEnabled(false);
			}
			if(!(keywords.getText().equals("")))
			{
				searchButton.setEnabled(true);				
			}
			else
			{
				searchButton.setEnabled(false);				
			}
		}
	}// end DocumentHandler
	
}// end class HungryFrame
