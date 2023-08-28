# Steps for showing notification

Showing a notification in an android phone involves few steps:

1. Create a notification channel (for Android 8.0 and above).
2. Create notification.
3. Check notification permission (for Android 13 and above).
4. Show notification.

We will be creating a utility Kotlin object `NotificationUtils` that will handle the notification
related work and expose some functions to show notification.

So let's start.

## Creating a notification channel

1. In `NotificationUtils.kt` create
   these [extension functions](https://kotlinlang.org/docs/extensions.html)
   and properties so that it can be called easily. They will help us to create
   notification channel.

```kotlin
object : NotificationUtils {
    private const val NOTIFICATION_CHANNEL_ID = "notification_deep_link_channel_id"

    private val Context.notificationManager
        get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun Context.createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID, //a unique id
                "App Notification", //channel name
                NotificationManager.IMPORTANCE_HIGH //priority of the notification from this channel
            ).also(notificationManager::createNotificationChannel) //create notification channel
    }

}
```

2. Create an `App` class which will extends `Application` class. It acts as the entry point and
   serves as a singleton instance that's created when the app is launched.

```kotlin
class App : Application() {

    //called before initiating any
    //component of the app like Activity
    override fun onCreate() {
        super.onCreate()

        //Create required notification channel before 
        //entering application.
        //if the channel already exist then 
        //it won't be created
        createNotificationChannel()
    }
}
```

Register this class in the `AndroidManifest.xml` so that Android will know about our
implementation of `Application` class and will use it.

```
    <application
        android:name=".App"
        android:allowBackup="true"
...
```

## Create Notification

1. In `NotificationUtils.kt`, create these extension functions. They will
   help us to show notification:

```kotlin
object NotificationUtils {
    private const val NOTIFICATION_ID = 23423
    private const val ENTER_APP_REQUEST_CODE = 92384

    val Context.isNotificationPermissionGranted
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        else true

    fun Context.sendNotification() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Demo notification Title") //title
            .setSmallIcon(R.drawable.ic_notification) //notification icon
            .setContentText("Click the action button to enter app") //notification content
            .setPriority(NotificationCompat.PRIORITY_HIGH) //notification priority
            .addAction(getEnterAppAction())
            .build()

        //show notification only when permission is granted
        if (isNotificationPermissionGranted)
            notificationManager.notify(
                //unique ID for notification for 
                //handling it later (e.g. cancelling it)
                NOTIFICATION_ID,
                notification
            )
    }

    private fun Context.getEnterAppAction() =
        NotificationCompat.Action.Builder(
            R.drawable.ic_notification,
            "Enter App",
            PendingIntent.getActivity(
                this,
                ENTER_APP_REQUEST_CODE,
                Intent(this, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
            .build()
}
```

* `getEnterAppAction()` returns a Notification actions. An action is a like a text button shown
  below the notification body by using which user can perform specific actions directly from the
  notification without fully opening the app. Notification actions are associated
  with `PendingIntent` instances that define the action's behavior when triggered. This can
  include opening an activity, service, or broadcast. In the above code, we are requesting to open
  an `MainActivity` when the action is clicked by the user.
* `isNotificationPermissionGranted` tells us whether the app has permission to show notification.
  If the Android version is greater or equal to 13 then check for Notification permission
  else return `true` because in lower android we don't need get notification permission to show
  notification.

## Showing notification

1. In `MainActivity` create a button which will be used to show notification when clicked

```kotlin
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it)
            runAfterPermissionGranted?.invoke()
    }

    //consider it as a pending task that needs to be executed 
    //after the permission is granted
    private var runAfterPermissionGranted: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notifyButton.setOnClickListener {
            //when button click, show notification
            checkAndNotify()
        }
    }

    private fun checkAndNotify() {
        //isNotificationPermissionGranted defined in NotificationUtils.kt

        if (isNotificationPermissionGranted)
        //permission granted, show notification
            sendNotification()
        else {
            Toast.makeText(
                this,
                "Please allow notification permission in order to show notification",
                Toast.LENGTH_SHORT
            ).show()

            //assign the task that will be executed if the 
            //permission is granted
            runAfterPermissionGranted = { sendNotification() }

            //if the permission is not granted, request for it
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
```

## Challenges

The big challenge was to check Notification permission before showing
notification. That I tackle with creating a extension property for Context
`isNotificationPermissionGranted` that returns boolean according to the permission
state. If the permission is not granted, I am requesting for it and then only
we are showing. I am also showing a toast, so that even permission dialog will not be shown
(happens if user declines two or more times), user get notified that that
the app needs notification permission.

## Improvements

1. We can check and show a rationale dialog before requesting permission
   stating why we need notification permission. In this way user will understand
   better why we need permission.
2. In the intent that is opening `MainActivity`, we can put some args and
   check it in the `onCreate` to cancel the notification if the args are present in
   the intent.
