# Summery

This report documents my experience with creating notifications and implementing deep linking in
our Android application. The objective was to improve user engagement and navigation by
allowing users to seamlessly access specific content within the app via notifications.

## Progress

**Notification Creation**: I have successfully implemented the creation of notifications within the
Android app. Notifications were designed to provide users with relevant updates, messages,
and actions related to the app's content.

**Deep Linking**: I have integrated deep linking into our app, allowing users to open specific
screens or content directly from a deep link, whether the link is clicked within a web browser,
another app, email, or notification.

## Challenges

1. **Handling Deep Link**: Implementing the logic to handle deep links required careful
   consideration
   of intent filters in the AndroidManifest.xml file. Ensuring that the app correctly responds to
   various
   deep links correctly was complex.
2. **Notification Permission**: Before notifying user, we have to ensure that the user has given the
   notification permission. Even though, it only needed from Android version starting 13 however, it
   created another challenge to check the API version also.

## Solution

1. **Handling Deep Link**: I have carefully defined intent filters within the AndroidManifest.xml
   file,
   specifying the deep link schemes, hostnames, and paths for the activity.

2. **Notification Permission**: I have leverage the power of Kotlin extension function and property
   check permission seamlessly.

## Recommendations

1. For deep linking, we can use Android's Navigation Component that makes handling of deep linking
   easier and automatic navigates the user to the required destination.
   Also, it promotes Single Activity architecture which ensures that the application will be
   light weight as Fragments are used mostly instead of Activities.
2. For Notification Permission, we should show a rationale before asking for Notification
   Permission.
   In that way, that will make the user to understand better why the app needs notification
   permission.
3. Prioritize user experience when designing notifications. Ensure that notifications provide value
   and relevance to users, leading to increased engagement.
4. Implement analytics to track the effectiveness of notifications and deep links. Monitor user
   engagement and make data-driven decisions to improve notification content and timing.

## Conclusion

Deep linking and notifications are powerful tools for user retention and engagement, and we will
continue to leverage them to provide a seamless and user-centric experience.