# FXtend - Extending JavaFX Controls

[![Maven Central](https://img.shields.io/maven-central/v/io.github.alialkubaisi/fxtend.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.alialkubaisi/fxtend/1.0.1)
![Java Version](https://img.shields.io/badge/Java-%3E%3D%2017-blue)
![JavaFX Version](https://img.shields.io/badge/JavaFX-%2022-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-green)

FXtend is an open-source JavaFX library that aims to provide additional, new, or improved controls for JavaFX
applications. The library is designed to be easy to use and integrate seamlessly into existing JavaFX projects.

## Features

- 🚀 New JavaFX controls that extend the standard set of controls.
- 🔄 Improved versions of existing JavaFX controls with added functionalities.
- 🎨 Custom styles and themes for a unique look and feel.

## Installation

FXtend can be included in your JavaFX project as a dependency using Maven or Gradle.

### Maven

```xml

<dependency>
    <groupId>io.github.alialkubaisi</groupId>
    <artifactId>fxtend</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```
implementation 'io.github.alialkubaisi:fxtend:1.0.1'
```

## Controls

### SecurePasswordField

The `SecurePasswordField` control in FXtend is an enhanced version of the default JavaFX `SecurePasswordField`. It
includes the following features:

- **Eye Icon to Show/Hide Password:** Users can toggle the visibility of the password by clicking an eye icon.
  ![SecurePasswordField](fxtend-demo/images/password_field_masked.png)
  ![SecurePasswordField](fxtend-demo/images/password_field_unmasked.png)
- **Password Strength Validation:** An optional popup can display the strength of the password as the user types.
  ![SecurePasswordField](fxtend-demo/images/password_strength_popup.gif)

### Usage

To use SecurePasswordField in your application, you can instantiate it with optional parameters to enable strength
validation and set a minimum length:

```java
// Create a SecurePasswordField with default settings
SecurePasswordField passwordField = new SecurePasswordField();

// Create a SecurePasswordField with password strength validation enabled
SecurePasswordField validationPasswordField = new SecurePasswordField(true, 12);
```

### AutoCompleteField

The `AutoCompleteField` control in FXtend provides a text field with autocomplete functionality. It includes
a `searchMode` parameter to determine whether suggestions should match based on "contains", "start with", or "end
with".<br />

#### Usage

To use `AutoCompleteField` in your application, you can instantiate it with a list of suggestions and optionally specify
the search mode:

```java
// suggestions is a list of strings, with default search mode (contains)
AutoCompleteField autoCompleteFieldContains = new AutoCompleteField(suggestions);
// Create AutoCompleteField with "contains" search mode
AutoCompleteField autoCompleteFieldContains = new AutoCompleteField(buildSuggestions(), SearchMode.CONTAINS);
```

![SecurePasswordField](fxtend-demo/images/auto_complete_fields.gif)

### ChatView

The `ChatView` control provides an interactive chat interface similar to popular messaging applications. It includes
features such as:

- **Send and Receive Messages:** Easily send and receive messages with built-in handling for both incoming and outgoing
  messages.
- **Customizable Styles:** Choose from several pre-defined styles (`DEFAULT`, `MODERN`, `DARK`) or create your own.
- **Optional Logo for Received Messages:** Display a logo image on the received message bubbles.
- **Timestamp Display:** Automatically display timestamps for each message, enhancing the chat experience.
- **Message Status Indicators:** Display message statuses like "Sent", "Received", or "Read" with visual indicators.

#### Usage

You can instantiate the `ChatView` in several ways, depending on your requirements:

```java
ChatView chatView = new ChatView(); // Apply default style without adding header
ChatView chatViewWithTitle = new ChatView("title"); // This will add a header in the chat view
ChatView chatViewModern = new ChatView(ChatView.Style.MODERN); // Apply modern style without adding header
ChatView chatViewDarkWithTitle = new ChatView("title", ChatView.Style.DARK); // Apply dark style with title as header
```

Messages can be sent using the `TextField` within the `ChatView` or programmatically using the `sendMessage` method. To
receive messages, use the `receiveMessage` method.

```java
ChatView chatView = new ChatView();
chatView.sendMessage("Hello, this is a sent message!");
chatView.receiveMessage("Hi! This is a received message.",new Image("/path/to/logo.png")); // Received message with optional logo
```
![ChatViewDefault](fxtend-demo/images/chat_view_default.png)
![ChatViewModern](fxtend-demo/images/chat_view_modern.png)
![ChatViewModernWithHeader](fxtend-demo/images/chat_view_modern_header.png)
![ChatViewDarkWithHeader](fxtend-demo/images/chat_view_dark_header.png)

