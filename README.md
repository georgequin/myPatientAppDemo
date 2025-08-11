# My Patients App

A Kotlin MVVM Android app using Room, Hilt, Retrofit, and Jetpack Compose to manage patient records offline and sync with a mock REST API.

## Project Setup

1.  **Clone the repository:**
2.  Open the project in Android Studio.
3.  **Sync Gradle dependencies** when prompted.
4.  **Build and run the app:**
    *   **For a debug build:**
        *   Go to **Run > Run 'app'** (or click the green play button).
        *   Select an available emulator or a connected physical device.
    *   **For generating an APK:**
        *   **Unsigned APK:** Go to **Build > Build Bundle(s) / APK(s) > Build APK(s)**. Locate the APK via the notification popup once the build is complete.
        *   **Signed Release APK:** Go to **Build > Generate Signed Bundle / APK...**. Follow the on-screen instructions to select or create a keystore and build your signed APK.

## Libraries Used

*   **Jetpack Compose** – Declarative UI framework.
*   **Hilt** – Dependency Injection.
*   **Room** – Local database for offline storage.
*   **Retrofit** – REST API client.
*   **Moshi** – JSON serialization/deserialization.
*   **Kotlin Coroutines / Flow** – Asynchronous programming.
*   **Accompanist** – Jetpack Compose utilities.

## Known Issues / Limitations

*   Uses a mock API ([https://jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com)) for syncing; data is not persisted on a real server.
*   No authentication implemented.
*   Sync conflicts are not resolved (last write wins strategy).

## GitHub Link

[GitHub Repository](<https://github.com/georgequin/myPatientAppDemo>)
