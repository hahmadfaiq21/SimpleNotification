# SimpleNotification
<p align="center">
  <img src="https://github.com/user-attachments/assets/acab7ff4-397f-4e3c-ab16-90c6a45a5b48">
</p>

Notifications in Android are essential tools for keeping users informed and engaged with timely updates, even when they are not actively using an app. These notifications appear in various places such as the status bar, notification drawer, and lock screen, providing users with reminders, messages, or other important information. By leveraging features like notification channels, groups, and actions, developers can create rich, interactive notifications that enhance user experience and ensure critical information is always accessible. For more: https://developer.android.com/develop/ui/views/notifications

# Notification Anatomy
<p align="center">
  <img src="https://github.com/user-attachments/assets/689cf040-35f4-44a8-a161-da93f4017c61">
</p>

The most common parts of a notification are as follows: <br>
**a. Small icon**: required; set using setSmallIcon(). <br>
**b. App name**: provided by the system. <br>
**c. Time stamp**: provided by the system, but you can override it using setWhen() or hide it using setShowWhen(false). <br>
**d. Title**: optional; set using setContentTitle(). <br>
**e. Text**: optional; set using setContentText(). <br>
**f. SubText**: optional; set using setContentSubText(). <br>

# Notification Builder
```Kotlin
NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText(subtext)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
```
Breakdown code: <br>
**a. NotificationCompat.Builder(this, CHANNEL_ID)** <br>
Creates a NotificationCompat.Builder object, which will be used to configure the notification. The this parameter refers to the current context (likely an Activity or Service), and CHANNEL_ID is the ID of the notification channel, which is required for Android 8.0 (Oreo) and above to group notifications. <br>
**b. setSmallIcon(R.drawable.ic_notification)** <br>
Sets a small icon to be displayed in the status bar and on the notification itself. R.drawable.ic_notification refers to a drawable resource in the app that serves as the icon for the notification. <br>
**c. setContentTitle(title)** <br>
Sets the title of the notification, typically a short string that summarizes the purpose of the notification. The title variable holds the text value for the title. <br>
**d. setContentText(message)** <br>
Sets the main text content of the notification, which provides more details about what the notification is about. The message variable holds this text. <br>
**e. setSubText(subtext)** <br>
Adds a smaller piece of text, often displayed below the content text in the notification, which can provide additional information. The subtext variable holds this value. <br>
**f. setContentIntent(pendingIntent)** <br>
Defines an action to take when the user taps the notification. The pendingIntent is a wrapper around an Intent that defines what should happen (e.g., opening an Activity). <br>
**g. setAutoCancel(true)** <br>
Ensures that the notification is automatically dismissed when the user taps it. <br>
**h. setPriority(NotificationCompat.PRIORITY_DEFAULT)** <br>
Sets the priority of the notification. PRIORITY_DEFAULT indicates that the notification should have the standard priority, meaning it will appear in the status bar without being overly intrusive. <br>
<br>
This notification will show a small icon, a title, a message, and an optional subtext, and it will open an Activity or perform some other action when tapped. It will disappear once the user interacts with it.

# Back Stack
**Back stack** is a crucial concept that manages the navigation history of activities within an application. It operates as a **"last in, first out" (LIFO)** data structure, meaning the most recently added activity is the first to be removed when the user presses the back button. This mechanism ensures a seamless user experience by allowing users to navigate back through their previous screens in the order they were accessed. Understanding and effectively managing the back stack is essential for creating intuitive and user-friendly Android applications. For more: https://developer.android.com/guide/navigation/backstack

<p align="center">
  <img src="https://github.com/user-attachments/assets/d5eb731f-324e-429a-9c31-49076d0e1f03">
</p>

### AndroidManifest.xml
Add **android:launchMode="singleTop"** attribute to **MainActivity** and **android:parentActivityName=".MainActivity"** to **DetailActivity**.

```XML
...
<activity
            android:name=".MainActivity"
            android:launchMode="singleTop"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".DetailActivity"
            android:parentActivityName=".MainActivity">
        </activity>
...
```
#### Explanation:
**a. android:launchMode="singleTop"** <br>
This attribute defines how the activity will be launched if it is already in the back stack. With **singleTop**, if the activity is already at the top of the back stack, Android will not create a new instance of that activity but will reuse the existing one. This is useful for **avoiding duplicate activities**, especially in scenarios like opening the app through a notification or navigating back to the same activity. An example where **launchMode="singleTop"** is relevant:
If MainActivity is already at the top of the back stack and an intent tries to open that activity again, the existing MainActivity will be used instead of creating a new instance.

**b. android:parentActivityName=".MainActivity"** <br>
This attribute indicates that the mentioned activity (in this case, **DetailActivity**) has a parent activity, which is **MainActivity**. This means that when the user is in **DetailActivity**, if they press the **back button**, they will be taken back to **MainActivity**. This helps **define a hierarchical navigation structure** within the app. It is often associated with the use of **NavUtils** or **AppBarConfiguration** in the code to manage navigation between activities.

# SimpleNotification Interfaces
<p align="center">
  <img src="https://github.com/user-attachments/assets/4ec8d677-8833-4b4e-a21b-04e8c78ce998">
</p>
