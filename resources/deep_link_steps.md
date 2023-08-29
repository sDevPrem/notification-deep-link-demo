# Steps for creating Deep Link

A deep link in Android is a URL that points to a specific location within a mobile app rather than
a website. Deep links allow users to directly access specific content or features within an app by
clicking on a link, either in a web browser, another app, or even a notification.

In this steps, we are going to create a `SecretActivity` and deep links that
will open this activity when clicked.

## Create Activity and define links for it

1. Create Activity

```kotlin
class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)
    }

    companion object {
        //deep links
        const val SECRET_SCREEN_DEEP_LINK1 = "dlapp://deep-link.demo/secret-screen"
        const val SECRET_SCREEN_DEEP_LINK2 = "http://deep-link.demo/secret-screen"
    }
}
```

2. Modify `MainActivity` in the Android Manifest file with the deep links

```xml

<activity android:name=".MainActivity" android:exported="true" android:launchMode="singleTask">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>


    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="http" android:host="deep-link.demo"
            android:pathPrefix="/secret-screen" />
    </intent-filter>
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="dlapp" android:host="deep-link.demo"
            android:pathPrefix="/secret-screen" />
    </intent-filter>
</activity>
```

### Modifications

* `android:launchMode="singleTask"`: We have set the launch mode to `singleTask` so that
  if there will be an instance of this activity already present in the current task than
  that it will be reused. The `onNewIntent` method of the activity will be called with new Intent.
* `intent-filter` - Intent filters tell the android which type of intent the activity can handle.
  Here we define category of `BROWSABLE`, `DEFAULT` and `VIEW` with the deep link schemas to
  make sure that it will redirect user to this app if the user clicks any of the two deep links.

### Deep Links defined for this app

1. dlapp://deep-link.demo/secret-screen
2. http://deep-link.demo/secret-screen

## Modify `MainActivity` to handle the deep links

Here we will modify it such that if this activity is launched with any of the two
deep links, then it will redirect it to the `SecretActivity`

1. Define this method in the `MainActvity`. It will analyze the given intent
   and decide whether the activity is opened by the deep links. If it is then it
   will redirect the user to `SecretActivity`.

```kotlin
private fun handleIntent(intent: Intent?) = intent?.data?.toString()?.takeIf {
    //check if the uri of the intent is equal to the deep links
    //of the secret screen
    return@takeIf it == SecretActivity.SECRET_SCREEN_DEEP_LINK2 ||
            it == SecretActivity.SECRET_SCREEN_DEEP_LINK1
}?.let {
    //if it is the uri of the secret screen
    //take the user to the secret screen
    Intent(this, SecretActivity::class.java)
        .also(::startActivity)
    return@let
}
```

2. Call it from two places from the `MainActvity`.

```
class MainActivity : AppCompatActivity() {
...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        ...
    } 


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //this will be called if there is
        //an instance of MainActivity already exist
        //in the current task
        //with the new intent
        handleIntent(intent)
    }
    
```

## Integration with Notification

We are going to modify the notification action that we made
in [notification_steps.md](resources/notification_steps.md)
to go the `SecretActivity`.

```kotlin
private fun Context.getEnterAppAction() =
    NotificationCompat.Action.Builder(
        R.drawable.ic_notification,
        "Enter secret screen", //change title (optional)
        PendingIntent.getActivity(
            this,
            ENTER_APP_REQUEST_CODE,
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SecretActivity.SECRET_SCREEN_DEEP_LINK1) //give any one of the uri
            ),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    )
        .build()
```

Here we are just changing the `Intent` creation. We are using `Intent.ACTION_VIEW` action with
one of the two URI that we just created.

That's all.

## Challenges

1. **Handling redirection** : We have to check for the
   intent that is opening the `MainActivity`. If it contains the Deep Link URI,
   we have to redirect the user to the Correct Activity.
2. `launchMode` : If we don't provide the `singleTask` launch mode then it will launch the
   activity in another task. That we don't want. It can be useful if the screen is a
   separate module / feature.