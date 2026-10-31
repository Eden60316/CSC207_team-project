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

4. As a cook, I want to save recipes to my favorites so that I can easily find them again later.(Isabella)