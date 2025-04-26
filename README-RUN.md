# Running the Grocery App on a Device

This guide provides instructions on how to run the Grocery App on a physical Android device or an emulator.

## Prerequisites

1. Android Studio installed on your computer
2. USB debugging enabled on your Android device (for physical devices)
3. Android SDK installed and configured

## Option 1: Using Android Studio (Recommended)

### Running on a Physical Device

1. Connect your Android device to your computer using a USB cable
2. Enable USB debugging on your device:
   - Go to Settings > About phone
   - Tap "Build number" 7 times to enable Developer options
   - Go back to Settings > System > Developer options
   - Enable "USB debugging"
   - Accept the debugging authorization prompt on your device

3. Open the project in Android Studio:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the Grocery App project directory and open it

4. Run the app:
   - Wait for the project to sync and index
   - Select your connected device from the device dropdown in the toolbar
   - Click the "Run" button (green triangle) or press Shift+F10
   - The app will be installed and launched on your device

### Running on an Emulator

1. Open the project in Android Studio
2. Create an emulator if you don't have one:
   - Click on "AVD Manager" in the toolbar
   - Click "Create Virtual Device"
   - Select a device definition (e.g., Pixel 6)
   - Select a system image (e.g., API 33)
   - Configure the AVD and click "Finish"

3. Run the app:
   - Select your emulator from the device dropdown in the toolbar
   - Click the "Run" button (green triangle) or press Shift+F10
   - The emulator will start, and the app will be installed and launched

## Option 2: Using Command Line (If Android Studio is not available)

If you're experiencing timeouts with Gradle commands, try these approaches:

### Increase Gradle Memory

Add the following to your `gradle.properties` file:

```
org.gradle.jvmargs=-Xmx4g -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
```

### Build and Install Separately

1. Build the APK:
   ```
   ./gradlew assembleDebug --info
   ```

2. Install the APK manually:
   ```
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

3. Launch the app:
   ```
   adb shell am start -n com.sokhal.groceryapp/.MainActivity
   ```

## API Configuration for Physical Devices

When running the app on a physical device, you need to configure the API base URL to point to your development machine or backend server:

1. Open `app/build.gradle.kts`
2. Find the `buildTypes` section
3. In the `debug` block, locate the `buildConfigField` for `API_BASE_URL`
4. Uncomment and modify the line with your computer's IP address:
   ```kotlin
   buildConfigField("String", "API_BASE_URL", "\"http://192.168.1.100:8080\"")
   ```
   Replace `192.168.1.100` with your computer's actual IP address on the same network as your device

5. Comment out the emulator-specific URL:
   ```kotlin
   // buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080\"")
   ```

6. Rebuild the app

## Troubleshooting

### No Devices Found

If no devices are detected:
- For physical devices, ensure USB debugging is enabled
- Check your USB cable
- Try a different USB port
- Install/update device drivers if needed

### Build Failures

If the build fails:
- Check the error messages in the build output
- Ensure all dependencies are correctly configured
- Try cleaning the project: `./gradlew clean`
- Invalidate caches in Android Studio: File > Invalidate Caches / Restart

### App Crashes on Launch

If the app installs but crashes when launched:
- Check the logcat output in Android Studio for error messages
- Ensure the device's Android version is compatible with the app's minimum SDK (24)
- Verify that all required permissions are granted to the app

### API Connectivity Issues

If the app runs but can't connect to the backend server:
- Verify that your backend server is running and accessible
- Check that you've configured the correct API base URL for your environment:
  - For emulators: `http://10.0.2.2:8080` (points to host machine's localhost)
  - For physical devices: `http://YOUR_COMPUTER_IP:8080` (your development machine's IP address)
- Ensure your device and development machine are on the same network
- Check if your computer's firewall is blocking connections to the server port
- Try accessing the API endpoint directly from a browser on your device to verify connectivity
- Look for network-related errors in the logcat output

## Running on a Specific Device

To list all connected devices:
```
adb devices
```

To install on a specific device when multiple are connected:
```
adb -s <device-id> install app/build/outputs/apk/debug/app-debug.apk
```

Replace `<device-id>` with the ID from the `adb devices` command output.
