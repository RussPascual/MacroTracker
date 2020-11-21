# My Personal Project - Macro Tracker

## Description

This application is a *Macro tracker* which allows its users to track their daily meals in accordance with individual
daily nutritional goals or *macros*. Macro calculation will not be a function of this project due to needing a database
for the detailed calculations. The user can preset their caloric goal and/or weight goal. This application will track
progress and informs the user when targets have been hit. This application will update as the user records the food
eaten, the starting weight in a day, or changes their goals. The macros measured with the app include:
- Protein
- Fat
- Carbohydrates

The *Macro tracker* can be used by anyone but caters to those who have weight loss or muscle gain goals and want to do
so without professional help. This project came up because I am exactly like the user described above. I also used a
similar app named "MyFitnessPal" which offered similar services and helped me with my goals to model my project after.

## User Stories
- As a user, I want to be able to change/update my personal data and goals
- As a user, I want to be able to add food eaten into my food records for a specific date
- As a user, I want to be able to view my remaining macro requirements 
- As a user, I want to be able to track and view my progression towards my macro and weight goal
- As a user, I want to be able to save combinations of foods as a meal as a 'favourite' food
- As a user, I want to be able to make notes and add them to my daily diary for later read
- As a user, I want to be able to be alerted when I have reached my macro goals for the day
- As a user, I want to be able to copy a day's food journal onto another day's food journal
- As a user, I want to be able to save my data (information and logs) to file for later use
- As a user, I want to be able to access my previously saved data when I reopen the app
- As a user, I want to be able to be prompted to save data right before quitting the application

## Phase 4: Task 2 - Made *User* class robust including methods
- *updateMacros*
- *setMacroGoals*
- *updateWeight*
- *updateUser*

## Phase 4: Task 3 - Refactoring possibilities
- Utilize the *Composite Pattern* for the Food (Component), FoodItem (Leaf), and Meal (Composite) classes
- Make Journal start with 1 DayLog upon construction rather than calling it separately in ui implementation
- Create an Information class for use in User to handle user information methods
- Refactor all progress related functionality from User class into a *Progress* class
- Make *Progress* class utilize the *Observer Pattern*
- Move Food methods regarding adding and removing macros to the Meal class since FoodItem does not use it
- Make Panel classes handle visuals separately from user input then utilize the *Observer Pattern*