#!/bin/bash

APP_NAME="colinker"
APP_VERSION="1.0"

JAR_FILE="Colinker-JFX-1.0-SNAPSHOT.jar"
INPUT_DIR="target" # Chemin où se trouve le JAR
OUTPUT_DIR="./executable" # Chemin où le DMG sera créé
ICON_PATH="./src/main/resources/com/colinker/CoLinker-logo.icns" # Chemin de l'icône de l'application

# Configuration de Java et jpackage
JAVA_HOME="/Users/ben/Library/Java/JavaVirtualMachines/openjdk-19.0.2/Contents/Home"
JPACKAGE=$JAVA_HOME/bin/jpackage

JAVAFX_JMODS="/Users/ben/Downloads/javafx-jmods-22.0.1"

# Nettoyage du dossier de sortie
echo "Cleaning output directory..."
rm -rf $OUTPUT_DIR
mkdir -p $OUTPUT_DIR

if [ ! -f "$INPUT_DIR/$JAR_FILE" ]; then
  echo "Error: The main jar does not exist: $INPUT_DIR/$JAR_FILE"
  exit 1
fi

# Vérification du JAR
echo "Verifying the JAR file..."
unzip -l "$INPUT_DIR/$JAR_FILE" | grep 'com/colinker/App.class'
if [ $? -ne 0 ]; then
  echo "Error: The main class com.colinker.App is not found in the JAR file."
  exit 1
fi

# Génération du DMG
echo "Generating DMG package..."
$JPACKAGE --type dmg \
          --input $INPUT_DIR \
          --main-jar $JAR_FILE \
          --main-class com.colinker.App \
          --name "$APP_NAME" \
          --dest $OUTPUT_DIR \
          --app-version $APP_VERSION \
          --icon $ICON_PATH \
          --module-path "$JAVA_HOME/jmods:$JAVAFX_JMODS" \
          --add-modules javafx.controls,javafx.fxml \
          --verbose

echo "DMG package created at $OUTPUT_DIR/$APP_NAME-$APP_VERSION.dmg"
