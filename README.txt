Resources
 - Notifications: https://developer.android.com/guide/topics/ui/notifiers/notifications
 
Splash Window
 - Start up app
 - Logging in/out
 - To Signup/From Signup

Priorities
 - Database connection
 - Phone Notifications (alerts/progress)
 - Run in background
 
 Code
 - Sign in activity
 - Profile activity/App Setting activity 
 - Task list activity
 - xml related to those

Activities (Purpose and Description)
*Sign in activity*
- Purpose: Allow user to sign in to application. If account doesn't exist, user can create an account.
- Description: Username and password must be entered into text field, press enter, and it will be run through the data base. Create account button will send user to the new_account activity.

*New Account Activity*
- Purpose: Allow user to create new account. New account info will be saved/sent to database for storage (Not sure how this will work but this is what I expect). Refer to HW for new information.
- Description: All text fields must be filled, some have special field requirements. Refer to HW1.

*Profile/App Setting Activity*
- Purpose: Allow user to update their profile information/app settings. According to requirement, this has to be a "sliding screen", so maybe after logging in, the main page is the main activity, and if you swipe right to left, it'll slide to the profile/app settings activity.
- Description: Must be able to change password, profile information, help, about, any other settings with regards to this app, and logout.

*Task List Activity*
- Purpose: This activity is responsible for displaying the existing tasks which correspond with the current user. Tasks will be obtained from the user's information from the database. A floating action button will exist in the corner of the application which allows the user to add new tasks (This will be a new activity).
- Description: The recycler view, ideally, would be updated everytime you enter this activity, similar to the car project in HW2. When you press on a task, it will open up a new activity which lets you see the specifics of the task that was pressed.

Activities to be defined
*New Task Activity*
*Task Details Activity (Could also be a fragment)*
