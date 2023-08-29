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

1. Clone this repository and switch to mater branch(default).
2. Open it in the android studio.
3. Build it.
4. Install it in an Android devices with Android version from 6 to 13.

## Steps for creating Notification and Deep link:

Follow the steps in these files:

1. [notification_steps.md](resources/notification_steps.md) : Contains steps for creating and
   showing Notification
2. [deep_link_steps.md](resources/deep_link_steps.md) : Contains steps for creating deep link and
   notification integration.
3. [summery.md]() : Contains progress, challenges and their solution and recommendation about
   this project.

## Repository branches

This repository contains three branches.

1. [master](https://github.com/sDevPrem/notification-deep-link-demo): Main default branch.
2. [notification_implementation](https://github.com/sDevPrem/notification-deep-link-demo/tree/notification_implementation):
   Notification Implementation.
3. [deep_link_implementation](https://github.com/sDevPrem/notification-deep-link-demo/tree/deep_link_implementation):
   Deep link implementation with Notification.

## App Content

1. [MainActivity](app/src/main/java/com/sdevprem/notificationdeeplinkdemo/MainActivity.kt) :
   Main entry point which contains a TextView and a Button.
   After clicking the button, you will get a notification. Clicking on its action will navigate you
   to the `SecretActivity`.
2. [SecretActivity](app/src/main/java/com/sdevprem/notificationdeeplinkdemo/SecretActivity.kt) : An
   activity that can only be accessed either through deep link or notification action