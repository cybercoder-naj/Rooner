# Rooner

A Basic code editor that executes any Kotlin script. 
This application is written in [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
intended for desktop applications. 

## Installation

### Prerequities

You need to have the following installed

 1. [Android Studio](https://developer.android.com/studio/install)
 2. [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html#sdkman)

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