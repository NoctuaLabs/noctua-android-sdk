# Noctua Android SDK


## Unity

After exported your Android project, run the `unity-android-export.tool.exe` to configure your project automatically. The first argument is the path to your exported Android project.

```
C:\Users\foobar\src\noctua-android-sdk>postexport.exe ..\UnitySDKTest_Exported

NoctuaGG Unity Post-Export script started...
Path exists: C:\Users\foobar\src\UnitySDKTest_Exported\unityLibrary\build.gradle
Path exists: C:\Users\foobar\src\UnitySDKTest_Exported\launcher\build.gradle
A valid exported Android project directory found.

Checking and fixing dependencies...C:\Users\foobar\src\UnitySDKTest_Exported
removeGeneratedByUnityLines...
removeGeneratedByUnityLines done.
updateGradleDependencies...
updateGradleDependencies done.
addGoogleServicesPlugin...
addGoogleServicesPlugin done.
addGoogleServicesClasspath...
Found different version of google-services classpath. Replacing with: classpath 'com.google.gms:google-services:4.2.0'
addGoogleServicesClasspath done.
Checking configuration files...C:\Users\foobar\src\UnitySDKTest_Exported
Path exists: C:\Users\foobar\src\UnitySDKTest_Exported\launcher\google-services.json

Your exported project is configured properly.
Please restart Android Studio through File > Invalidate Cache / Restart, then click Invalidate and Restart.
After the Android Studio restarted, all Gradle tasks are resolved.

C:\Users\foobar\src\noctuagg-unity-postexport>

```
