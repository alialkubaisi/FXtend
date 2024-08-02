# FXtend - Extending JavaFX Controls

![Java Version](https://img.shields.io/badge/Java-%3E%3D%2011-blue)
![JavaFX Version](https://img.shields.io/badge/JavaFX-%3E%3D%2011-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-green)

FXtend is an open-source JavaFX library that aims to provide additional, new, or improved controls for JavaFX applications. The library is designed to be easy to use and integrate seamlessly into existing JavaFX projects.

## Features

- 🚀 New JavaFX controls that extend the standard set of controls.
- 🔄 Improved versions of existing JavaFX controls with added functionalities.
- 🎨 Custom styles and themes for a unique look and feel.

## Installation

FXtend can be included in your JavaFX project as a dependency using Maven or Gradle.

### Maven
```xml
<dependency>
    <groupId>com.github.alialkubaisi</groupId>
    <artifactId>fxtend</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Gradle
```
implementation 'com.github.alialkubaisi:fxtend:1.0.0'
```


## Controls

### SecurePasswordField

The `SecurePasswordField` control in FXtend is an enhanced version of the default JavaFX `SecurePasswordField`. It includes the following features:

- **Eye Icon to Show/Hide Password:** Users can toggle the visibility of the password by clicking an eye icon.
- **Password Strength Validation:** An optional popup can display the strength of the password as the user types.
