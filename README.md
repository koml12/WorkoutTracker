# WorkoutTracker
Android app for tracking your workouts

# Update (10/18/17)
+ Haven't updated the README in quite a while but lots of things have happened in the past couple of weeks.
+ Updated the app design so it follows material guidelines (for the most part, took some liberties with some design choices).
+ Added specific back button functions, so that for example, if the user adds an exercise, is taken back to the full workout view, and presses back, they are taken to the workout list, instead of back to the screen where they added the exercise.
+ Added weight tracking using a new database table linked to each exercise, and displayed the data using MPAndroidChart (open source Android chart library that can be found on GitHub).
+ Finally finished the To-Do item about notifications not starting a new instance of StartWorkoutActivity. Surprisingly simple change, just one line added to the manifest XML file.
+ This last round of To-Do's is really the last stuff I can think that this app is still lacking. After this, it'll be presentable enough to use daily/publish on the Play Store.

# To-Do (10/18/17)
+ There is a LOT of redundant code right now from testing and just removed (or further developed) features in general.
+ Add a little "notes" section (no more than 1-2 lines long) to each exercise where the user can enter in a little comment about the exercise. Should be in the same dialog where the weight is entered, and it should be able to be empty. The notes should be displayed with the rest of the exercise information.
+ Make the tracking weights button always show up on the app bar with an icon for it.
+ Add an icon for the app, and any other miscellaneous design fixes.

# Bugs (10/18/17)
