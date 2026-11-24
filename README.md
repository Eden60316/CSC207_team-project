# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.

# Recipe Finder

## Project Specification

Recipe Finder is a Java application that helps users search for recipes using an external recipe API.
Users can search for recipes, view recipe details, filter results, and save recipes they like.

## User Stories

1. As a family member, I want to search for recipes by keyword so that I can find meals I want to cook.(Lindsay)

## 2. As a cook, I want to view the details of a recipe so that I can see its ingredients and cooking instructions.

(Shangzhe Jin)
### Use Case 2: View Recipe Details

**Lead:** Shangzhe Jin

**Primary Actor:** User

**Goal:** Allow users to view the ingredients and cooking instructions of a selected recipe.

**Preconditions:**
- The user has selected a recipe from search or filter results.
- A valid recipe ID is available.

**Main Success Scenario:**
    1. The user clicks "View Details" on a recipe.
    2. The system receives the selected recipe ID.
    3. The system retrieves the recipe details from TheMealDB API.
    4. The system displays the recipe name, image, category, ingredients, measurements, and cooking instructions.

**Alternative Scenarios:**
- If the recipe cannot be found, an error message is displayed.
- If the API request fails, the user is informed that the recipe details could not be loaded.

**Postconditions:**
- The selected recipe's details are displayed to the user.
- The user can return to the previous page.

3. As a cook, I want to filter recipes by category or dietary preference so that I can find recipes that fit my needs.(Eden)
### Use Case 3: Filter Recipes

**Lead:** Eden

**Primary Actor:** User

**Goal:** Allow users to filter recipes by category or dietary preference to find recipes that match their needs.

**Preconditions:**
- The user is on the recipe search or browsing page.
- Recipe data and available filtering options can be accessed.

**Main Success Scenario:**
1. The user selects a category or dietary preference from the available filter options.
2. The system receives the selected filter criteria.
3. The system retrieves recipe data from TheMealDB API and applies the selected filters.
4. The system displays a list of recipes that match the selected criteria.
5. The user can select a recipe to view its details.

**Alternative Scenarios:**
- If no recipes match the selected filters, the system displays a message indicating that no matching recipes were found.
- If the API request fails, the user is informed that the filtered recipes could not be loaded.
- If the user clears the filters, the system displays the unfiltered recipe results.

**Postconditions:**
- The user can view recipes that match the selected category or dietary preference.
- The user can modify or clear the filters to explore other recipes.

4. As a cook, I want to save recipes to my favorites so that I can easily find them again later.(Isabella)
## Use Case 4: Manage Favorite Recipes

**Lead:** Isabella Mei

**User Story:**

As a cook, I want to save recipes to my favorites so that I can easily find them again later.

### 1. Use Case Description

This use case allows users to save recipes to their favorites, view their saved recipes, and remove recipes from their favorites list.

### 2. Primary Actor

User (Cook)

### 3. Preconditions

- The user has selected a recipe from the search results or recipe details page.
- The selected recipe has a valid recipe ID.

### 4. Main Success Scenario

1. The user opens a recipe's details page.
2. The user clicks the "Add to Favorites" button.
3. The system saves the selected recipe to the favorites list.
4. The system updates the UI to indicate that the recipe has been saved.
5. The user opens the Favorites page.
6. The system displays all saved recipes.
7. The user can select a saved recipe to view its details.

### 5. Alternative Scenarios

**A. Recipe Already Saved**
- If the recipe is already in the favorites list, the system prevents duplicate entries.

**B. Remove a Favorite**
1. The user clicks "Remove from Favorites" on a saved recipe.
2. The system removes the recipe from the favorites list.
3. The UI updates to reflect the change.

**C. Empty Favorites List**
- If the user has not saved any recipes, the system displays a message such as "No favorite recipes yet."

**D. Save Failure**
- If the system cannot save the recipe, it displays an error message and does not indicate that the operation succeeded.

### 6. Postconditions

- The favorites list reflects any successful additions or removals.
- The user can access their saved recipes from the Favorites page.
- If persistent storage is implemented, saved recipes remain available after restarting the application.
