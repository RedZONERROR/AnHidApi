# Android Hidden API Bypass (Fork)

![Android](https://img.shields.io/badge/Android-9%20--%2016+-green.svg)
[![GitHub release](https://img.shields.io/github/v/release/RedZONERROR/AnHidApi)](https://github.com/RedZONERROR/AnHidApi/releases)
[![License](https://img.shields.io/github/license/RedZONERROR/AnHidApi)](https://github.com/RedZONERROR/AnHidApi/blob/main/LICENSE)
[![Build Status](https://github.com/RedZONERROR/AnHidApi/actions/workflows/auto-build-release.yml/badge.svg)](https://github.com/RedZONERROR/AnHidApi/actions)

A fork of [LSPosed/AndroidHiddenApiBypass](https://github.com/LSPosed/AndroidHiddenApiBypass) with renamed packages to avoid conflicts in multi-module projects.

## 🔄 Fork Information

This repository is a **fork** of the original [AndroidHiddenApiBypass](https://github.com/LSPosed/AndroidHiddenApiBypass) library created by the [LSPosed Team](https://github.com/LSPosed).

### Package Changes
- **Original Package**: `org.lsposed.hiddenapibypass`
- **Fork Package**: `red.androhidapi`

### Why Fork?
This fork was created to resolve package name conflicts when integrating multiple libraries or applications that use the same package namespace. The renamed package prevents conflicts while maintaining full API compatibility.

## 📖 About

Bypass restrictions on non-SDK interfaces in Android applications.

### Why HiddenApiBypass?
- **Pure Java**: No native code dependencies
- **Reliable**: Doesn't rely on specific behaviors, won't be blocked like meta-reflection or `dexfile`
- **Stable**: Doesn't rely on internal ART structures on Android 10+. Uses stable APIs like `Unsafe` and `setHiddenApiExemptions`

### LSPass Alternative
- **Fast**: No I/O operations, faster initialization than HiddenApiBypass
- **Safe**: Doesn't use `Unsafe`
- **Unreliable**: Can be blocked as easily as meta-reflection

## 🚀 Installation

### Option 1: Local Module Integration (Recommended)

1. **Add as Git Submodule:**
```bash
git submodule add https://github.com/RedZONERROR/AnHidApi.git
```

2. **Update settings.gradle:**
```gradle
include ':AnHidApi:library'
include ':AnHidApi:stub'
```

3. **Add dependency in your module's build.gradle:**
```gradle
android {
    // IMPORTANT: Required for Google Play Store compliance
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation project(':AnHidApi:library')
}
```

### Option 2: Direct Integration

1. **Download the latest release:**
   - [Latest Release](https://github.com/RedZONERROR/AnHidApi/releases/latest)

2. **Copy modules to your project:**
   - Copy `library/` and `stub/` folders to your project
   - Update `settings.gradle` and dependencies as shown above

## 📱 Usage

Import the renamed package classes:

```java
import red.androhidapi.HiddenApiBypass;
import red.androhidapi.LSPass;
```

### Basic Usage

This library provides two variants with identical APIs. LSPass initializes faster but may be blocked in future Android releases.

#### 1. Add Hidden API Exemptions
```java
// Add all classes to exemption list
boolean success = HiddenApiBypass.addHiddenApiExemptions("");

// Add specific classes or packages
HiddenApiBypass.addHiddenApiExemptions(
    "Landroid/content/pm/ApplicationInfo;", // specific class
    "Ldalvik/system",                       // all classes in package
    "Lx"                                    // all classes starting with 'x'
);
```

#### 2. Invoke Restricted Methods
```java
// Invoke a restricted method
HiddenApiBypass.invoke(ApplicationInfo.class, new ApplicationInfo(), "usesNonSdkApi");

// Get and invoke method manually
Method method = HiddenApiBypass.getDeclaredMethod(ApplicationInfo.class, "getHiddenApiEnforcementPolicy");
method.invoke(new ApplicationInfo());
```

#### 3. Access Restricted Constructors
```java
// Create instance using restricted constructor
Object instance = HiddenApiBypass.newInstance(Class.forName("android.app.IActivityManager$Default"));

// Get constructor manually
Constructor<?> ctor = HiddenApiBypass.getDeclaredConstructor(ClipDrawable.class);
```

#### 4. Access Restricted Fields
```java
// Get all instance fields (including restricted)
Field[] allInstanceFields = HiddenApiBypass.getInstanceFields(ApplicationInfo.class);
Field longVersionCode = Arrays.stream(allInstanceFields)
    .filter(f -> f.getName().equals("longVersionCode"))
    .findFirst().get();
longVersionCode.get(new ApplicationInfo());

// Get all static fields (including restricted)
Field[] allStaticFields = HiddenApiBypass.getStaticFields(ApplicationInfo.class);
Field hiddenField = Arrays.stream(allStaticFields)
    .filter(f -> f.getName().equals("HIDDEN_API_ENFORCEMENT_DEFAULT"))
    .findFirst().get();
hiddenField.get(null);
```

#### 5. Get All Methods
```java
// Get all methods including restricted ones
Method[] allMethods = HiddenApiBypass.getDeclaredMethods(ApplicationInfo.class);
Method usesNonSdkApi = Arrays.stream(allMethods)
    .filter(m -> m.getName().equals("usesNonSdkApi"))
    .findFirst().get();
usesNonSdkApi.invoke(new ApplicationInfo());
```

### Using LSPass (Alternative)

Replace `HiddenApiBypass` with `LSPass` for faster initialization:

```java
import red.androhidapi.LSPass;

// Same API, faster initialization
boolean success = LSPass.setHiddenApiExemptions("");
```

## ⚠️ Important Notes

### Google Play Store Compliance

**CRITICAL**: Google Play Store doesn't allow apps using hidden APIs. You **MUST** disable dependency info reporting:

```gradle
android {
    dependenciesInfo {
        includeInApk = false      // Prevents detection in APK
        includeInBundle = false   // Prevents detection in AAB
    }
}
```

### Compatibility

- **Android Versions**: 9+ - 16+
- **API Levels**: 28+ (Android 9) to latest
- **Architecture**: Pure Java (no native dependencies)

### Maintenance

- Keep the library updated for new Android versions
- Test thoroughly on target Android versions
- Monitor Android framework changes

## 🔗 Links

- **This Fork**: https://github.com/RedZONERROR/AnHidApi
- **Fork Releases**: https://github.com/RedZONERROR/AnHidApi/releases
- **Original Repository**: https://github.com/LSPosed/AndroidHiddenApiBypass
- **Original Releases**: https://github.com/LSPosed/AndroidHiddenApiBypass/releases
- **Original Documentation**: https://github.com/LSPosed/AndroidHiddenApiBypass/blob/main/README.md

## 🛠️ Development

### Building

```bash
# Build all modules
./gradlew build

# Build specific modules
./gradlew :library:build
./gradlew :stub:build
./gradlew :app:assembleDebug

# Run tests
./gradlew test
```

### Project Structure

```
AnHidApi/
├── library/          # Main HiddenApiBypass implementation
├── stub/             # Compile-time stubs for hidden APIs
├── app/              # Demo application
├── .github/          # GitHub Actions workflows
└── README.md         # This file
```

## 📝 Changelog

### Fork Changes
- Renamed package from `org.lsposed.hiddenapibypass` to `red.androhidapi`
- Converted Kotlin DSL to Gradle (Groovy) build files
- Added comprehensive documentation and examples
- Added GitHub Actions for automated releases
- Added demo application for testing
- Maintained full API compatibility with original

## 👥 Credits

### Original Authors
- **LSPosed Team** - Original implementation
- **Repository**: https://github.com/LSPosed/AndroidHiddenApiBypass

### Fork Maintainer
- **RedZONERROR** - Package renaming and maintenance
- **Repository**: https://github.com/RedZONERROR/AnHidApi

### Technical References
- **HiddenApiBypass Method**: [Unsafe Implementation](https://lovesykun.cn/archives/android-hidden-api-bypass.html)
- **LSPass Method**: [Property.of() Implementation](https://github.com/michalbednarski/LeakValue?tab=readme-ov-file#putting-it-all-together)

## 📄 License

```
Copyright 2021-2025 LSPosed
Fork modifications: RedZONERROR

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## ⭐ Support

If this fork helps you, please:
- ⭐ Star this repository
- ⭐ Star the [original repository](https://github.com/LSPosed/AndroidHiddenApiBypass)
- 🐛 Report issues in our [Issues](https://github.com/RedZONERROR/AnHidApi/issues)
- 🔄 Submit pull requests for improvements

---

**Disclaimer**: This library is for educational and development purposes. Use responsibly and in compliance with Google Play Store policies.