# DOKUMENTACJA PROJEKTU

## Main

Punkt wejścia programu. Tworzy okno z odpowiednią zawartością.

## Game

Najważniejsza klasa programu. Umożliwia podstawową kontrolę aplikacji w tym:

 - Pętla główna - Game odpowiada za renderowanie i aktualizowanie stanu 60 razy na sekundę

 - Zamykanie aplikacji

 - Zmiana obecnej sceny

Klasa pełni rolę maszyny stanu dla scen, które są odpowiedzialne za właściwą zawartość gry.

Game umożliwia także przez siebie dostęp do istotnych systemów, takich jak menadżer tekstur oraz mysz.

## Mouse

Klasa stanowiąca interfejs do obsługi interakcji z użytkownikiem przy pomocy myszy. Pozwala sprawdzić
podstawowy stan w jakim obecnie znajduje się mysz m. in. położenie kursora w oknie, stan wciśnięcia
przycisków, przesunięcie kółka myszki.

## TextureManager

Odpowiada za załadowanie w trakcie uruchamiania gry wszystkich potrzebnych jej tekstur, trzymanie ich,
oraz udostępnianie jako BufferedImage.

## AnimatedSprite

Klasa reprezentująca obiekty wyświetlane z pewną animacją. 
