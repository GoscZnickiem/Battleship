#!/bin/bash

# Uruchomienie skryptu build.sh
echo "Uruchamianie skryptu build.sh..."
./build.sh

# Sprawdzenie, czy skrypt build.sh został pomyślnie wykonany
if [ $? -eq 0 ]; then
    echo "Skrypt build.sh zakończony sukcesem."

    # Sprawdzenie czy istnieje plik .jar
    if [ -f "Battleship.jar" ]; then
        # Uruchomienie pliku JAR
        echo "Uruchamianie pliku JAR..."
        java -jar Battleship.jar
    else
        echo "Błąd: Plik JAR nie istnieje."
    fi
else
    echo "Błąd: Skrypt build.sh zakończony niepowodzeniem."
fi
