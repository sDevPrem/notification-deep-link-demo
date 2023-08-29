# NotificationDeepLinkDemo

It is a two activity application one of which can be accessed either through
deep link or notification action created through `MainActivity`.

## Deep Links supported

1. [dlapp://deep-link.demo/secret-screen](dlapp://deep-link.demo/secret-screen) (used by
   notification action)
2. [http://deep-link.demo/secret-screen](http://deep-link.demo/secret-screen)

They will navigate the user
to [SecretActivity](app/src/main/java/com/sdevprem/notificationdeeplinkdemo/SecretActivity.kt)

## Installation

1. Clone this repository.
2. Open it in the android studio.
3. Build it.
4. Install it in an Android devices with Android version from 6 to 13.

## Steps for creating Notification and Deep link:

Follow the steps in these files:

1. [notification_steps.md](resources/notification_steps.md) : Contains steps for creating and
   showing Notification
2. [deep_link_steps.md](resources/deep_link_steps.md) : Contains steps for creating deep link and
   notification integration.

## App Content

1. [MainActivity](app/src/main/java/com/sdevprem/notificationdeeplinkdemo/MainActivity.kt) :
   Main entry point which contains a TextView and a Button.
   After clicking the button, you will get a notification. Clicking on its action will navigate you
   to the `SecretActivity`.
2. [SecretActivity](app/src/main/java/com/sdevprem/notificationdeeplinkdemo/SecretActivity.kt) : An
   activity that can only be accessed either through deep link or notification action