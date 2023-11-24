# Rooner

A Basic code editor that executes any Kotlin script. 
This application is written in [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
intended for desktop applications. 

## Table of Contents

- [Screenshots and Demo](#screenshots-and-demo)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Run locally](#run-locally)
- [Roadmap](#roadmap)
- [Known Issues](#known-issues)
- [Navigating the project](#navigating-the-project)
- [Authors](#authors)
- [Acknowledgements](#acknowledgements)

## Screenshots and Demo

**_Coming soon!_**

## Installation

### Prerequisites

You need to have the following installed

 1. [Android Studio](https://developer.android.com/studio/install) ~> IDE for code editing
 2. [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html#sdkman) ~> Tool required by Rooner

**Note**: The current version of this build only runs on Linux.

### Run locally

Clone the project

```bash
  git clone https://www.github.com/cybercoder-naj/Rooner.git
```

Go to the project directory

```bash
  cd Rooner
```

Start the server

```bash
  ./gradlw :composeApp:run
```

## Roadmap

- [x] Structural UI
- [x] Code execution
- [x] Pipe output of code execution
- [x] Show errors in compilation/runtime
- [x] Indicators of current state
- [x] Syntax Highlighting
- [x] Clickable links on error messages
- [ ] ETA of code execution

## Known Issues

 - Entering tab on the editor makes rectangular boxes. Advised to use spaces instead.

## Navigating the project

All of my code for this project is under [composeApp/src/commonMain/kotlin](composeApp/src/commonMain/kotlin).
 - `App.kt` - this contains the parent composable called from `composeApp/src/desktopMain`.
 - `data/` - contains concrete implementations of the domain layer.
 - `di/` - A basic dependency injection singleton module.
 - `domain/` - The Business logic layer; contains the interfaces of core logic for the application.
 - `ui/` - The Presentation layer, which hosts its own ViewModel.
 - `utils/` - contains utility functions and variables for the application.

## Authors

 - [@cybercoder-naj](https://www.github.com/cybercoder-naj)

## Acknowledgements

 - [Getting Started with Compose Multiplatform | Jetbrains](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html#next-step)
 - [Java ProcessBuilder API | Baeldung](https://www.baeldung.com/java-lang-processbuilder-api)
 - [Icons | FontAwesome](https://fontawesome.com/icons)
 - [Process Exit Values | Baeldung](https://www.baeldung.com/linux/status-codes)
 - [Hard Keywords | KotlinLang](https://kotlinlang.org/docs/keyword-reference.html#hard-keywords)
