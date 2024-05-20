#!/bin/bash

# Ścieżka do folderu źródłowego
SRC_DIR="src"

# Nazwa pliku jar
OUTPUT_JAR="Battleship.jar"

# Katalog tymczasowy dla plików .class
TEMP_DIR="temp_classes"

# Stworzenie katalogu na pliki .class
mkdir -p $TEMP_DIR

# Kompilacja plików źródłowych
javac -d $TEMP_DIR $SRC_DIR/*.java

# Sprawdzenie czy kompilacja zakończyła się sukcesem
if [ $? -eq 0 ]; then
    echo "Kompilacja zakończona sukcesem."

    # Utworzenie pliku jar
    jar cfe $OUTPUT_JAR Main -C $TEMP_DIR .

    # Sprawdzenie czy plik jar został utworzony
    if [ $? -eq 0 ]; then
        echo "Utworzono plik $OUTPUT_JAR."
    else
        echo "Błąd przy tworzeniu pliku jar."
    fi
else
    echo "Błąd przy kompilacji."
fi

# Usunięcie plików .class
rm -rf $TEMP_DIR

echo "Wygenerowane pliki .class zostały usunięte."

