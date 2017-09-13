# WorkoutTracker
Android app for tracking your workouts

# Update (9/13/17)
+ Added another table to the database that holds workout names and "keys" that are unique to each workout (auto generated, but ended up being 1,2,3,...)
+ Exercises have a corresponding "key" to their workout, which enables selection of all exercises that are part of a workout with a simple SQL query (i.e. WHERE "key" = key)
+ The app now launches on a list of all the user's workouts, and the user can click on workouts to view the exercises in them and start their workouts

# To-Do (9/13/17)
+ Users get a notification when their rest time is up.
+ Add weight logging during/after exercise has been completed (is it possible to make a list of weights for progression purposes?)
+ Make the app not ugly.
