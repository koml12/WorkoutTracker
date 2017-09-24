# WorkoutTracker
Android app for tracking your workouts

# Update (9/13/17)
+ Updated the "key" system for workouts by simply using a Cursor that could get all the workouts from the table, and count them with the getCount() method. Surprisingly simple solution to a very strange bug.
+ Notifications have been added in, but they don't do anything, reason being that StartWorkoutActivity has a lot of data that changes without specifically refreshing the activity, and when clicking on a notification, it runs onCreate() again, and all progress in the workout is deleted. Maybe there's a way to fix this by keeping the activity paused when the user is not in the app, and when they click on the notification, the activity can resume.

# To-Do (9/13/17)
+ Make the app not ugly.
+ Figure out a way to resume same version of StartWorkoutActivity on notification click.

# Bugs (9/13/17)
+ Find some way to take care of unwanted back button hits. They switch into the paused version of activities when most of the time it is necessary to run onCreate again.
