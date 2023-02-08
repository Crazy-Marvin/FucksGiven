# Contributing To Fucks Given

👍🎉 First off, thanks for taking the time to contribute! 🎉👍

The following is a set of guidelines for contributing to [Fucks Given](https://poopjournal.rocks/FucksGiven/), which are hosted on [GitHub](https://github.com/Crazy-Marvin/FucksGiven/).
These are just guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

## What should I know before I get started?

### Contact

If you have any questions or are unsure about something just drop a line to [marvin@poopjournal.rocks](mailto:marvin@poopjournal.rocks).

### Design Decisions

If you plan to make a significant decision in how to maintain the project and what it can or cannot support please send an email beforehand. 

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for [Fucks Given](https://poopjournal.rocks/FucksGiven/) software. Following these guidelines helps maintainers and the community understand your report 📝, reproduce the behavior 📱💻🎮, and find related reports 🔎.

Before creating bug reports, please check this list as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible.

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for [Fucks Given](https://poopjournal.rocks/FucksGiven/), including completely new features and minor improvements to existing functionality. Following these guidelines helps maintainers and the community understand your suggestion 📝 and find related suggestions 🔎.

Before creating enhancement suggestions, please check this list as you might find out that you don't need to create one. When you are creating an enhancement suggestion, please include as many details as possible.

### Pull Requests

+ Include screenshots and animated GIFs in your pull request whenever possible.
+ Create a [branch](https://guides.github.com/introduction/flow/) for your edit
    
### Git Commit Messages

+ Use the present tense ("Add feature" not "Added feature")
+ Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
+ Limit the first line to 72 characters or less
+ Reference issues and pull requests liberally
+ When only changing documentation, include [ci skip] in the commit description
+ Consider starting the commit message with an applicable emoji:

        🎨 :art: when improving the format/structure of the code
        🐎 :racehorse: when improving performance
        🚱 :non-potable_water: when plugging memory leaks
        📝 :memo: when writing docs
        🐧 :penguin: when fixing something on Linux
        🍎 :apple: when fixing something on macOS
        🏁 :checkered_flag: when fixing something on Windows
        🐛 :bug: when fixing a bug
        🔥 :fire: when removing code or files
        💚 :green_heart: when fixing the CI build
        ✅ :white_check_mark: when adding tests
        🔒 :lock: when dealing with security
        ⬆️ :arrow_up: when upgrading dependencies
        ⬇️ :arrow_down: when downgrading dependencies
        👕 :shirt: when removing linter warnings
        
       
### Getting Started 🚀

This project contains 3 flavors:

- development
- staging
- production

To run the desired flavor either use the launch configuration in VSCode/Android Studio or use the following commands:

```sh
# Development
$ flutter run --flavor development --target lib/main_development.dart

# Staging
$ flutter run --flavor staging --target lib/main_staging.dart

# Production
$ flutter run --flavor production --target lib/main_production.dart
```

_\*Fucks Given works on Android, iOS, Web, Linux, Windows and macOS. We focus on Android and only create builds for Android at the moment._

---

### Running Tests 🧪

To run all unit and widget tests use the following command:

```sh
$ flutter test --coverage --test-randomize-ordering-seed random
```

To view the generated coverage report you can use [lcov](https://github.com/linux-test-project/lcov).

```sh
# Generate Coverage Report
$ genhtml coverage/lcov.info -o coverage/

# Open Coverage Report
$ open coverage/index.html
```

---

### Working with Translations 🌐

This project relies on [flutter_localizations](https://api.flutter.dev/flutter/flutter_localizations/flutter_localizations-library.html) and follows the [official internationalization guide for Flutter](https://flutter.dev/docs/development/accessibility-and-localization/internationalization).

### Adding Strings

1. To add a new localizable string, open the `app_en.arb` file at `lib/l10n/arb/app_en.arb`.

```arb
{
    "@@locale": "en",
    "counterAppBarTitle": "Counter",
    "@counterAppBarTitle": {
        "description": "Text shown in the AppBar of the Counter Page"
    }
}
```

2. Then add a new key/value and description

```arb
{
    "@@locale": "en",
    "counterAppBarTitle": "Counter",
    "@counterAppBarTitle": {
        "description": "Text shown in the AppBar of the Counter Page"
    },
    "helloWorld": "Hello World",
    "@helloWorld": {
        "description": "Hello World Text"
    }
}
```

3. Use the new string

```dart
import 'package:fucks_given/l10n/l10n.dart';

@override
Widget build(BuildContext context) {
  final l10n = context.l10n;
  return Text(l10n.helloWorld);
}
```

### Adding Supported Locales

Update the `CFBundleLocalizations` array in the `Info.plist` at `ios/Runner/Info.plist` to include the new locale.

```xml
    ...

    <key>CFBundleLocalizations</key>
	<array>
		<string>en</string>
		<string>es</string>
	</array>

    ...
```

### Adding Translations

1. For each supported locale, add a new ARB file in `lib/l10n/arb`.

```
├── l10n
│   ├── arb
│   │   ├── app_en.arb
│   │   └── app_es.arb
```

2. Add the translated strings to each `.arb` file:

`app_en.arb`

```arb
{
    "@@locale": "en",
    "counterAppBarTitle": "Counter",
    "@counterAppBarTitle": {
        "description": "Text shown in the AppBar of the Counter Page"
    }
}
```

`app_es.arb`

```arb
{
    "@@locale": "es",
    "counterAppBarTitle": "Contador",
    "@counterAppBarTitle": {
        "description": "Texto mostrado en la AppBar de la página del contador"
    }
}
```

__Thank you so much! 😘__
