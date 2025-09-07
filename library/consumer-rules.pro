-dontwarn dalvik.system.VMRuntime

-if class red.androhidapi.HiddenApiBypass
-keepclassmembers class red.androhidapi.Helper$* { *; }

-assumenosideeffects class android.util.Property{
    public static *** of(...);
}
