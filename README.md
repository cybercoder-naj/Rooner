# Rooner

A Basic code editor that executes any Kotlin script. 
This application is written in [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
intended for desktop applications. 

## Screenshots and Demo

**_Coming soon!_**

## Installation

### Prerequities

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
- [ ] Clickable links on error messages
- [ ] ETA of code execution

## Known Issues

 - Entering tab on the editor makes rectangular boxes. Advised to use spaces instead.
 - Output having multiple lines is not scrollable.

## Authors

 - [@cybercoder-naj](https://www.github.com/cybercoder-naj)

## Acknowledgements

 - [Getting Started with Compose Multiplatform | Jetbrains](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html#next-step)
 - [Java ProcessBuilder API | Baeldung](https://www.baeldung.com/java-lang-processbuilder-api)
 - [Icons | FontAwesome](https://fontawesome.com/icons)
 - [Process Exit Values | Baeldung](https://www.baeldung.com/linux/status-codes)

