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

Klasa reprezentująca animowane obiekty do wyświetlenia w oknie. Klasa wspiera: animacje o różnej 
liczbie klatek, różnej prędkości, opcję wyświetlania tekstury w tle oraz opcji nałożenia tekstury na wierzch. 
Technicznie rzecz biorąc przy pomocy tej klasy można równierz tworzyć statycznie renderowane obiekty.

## Button

Klasa reprezentująca przycisk. Posiada podstawową funkcjonalność przycisku - pozwala sprawdzić np. czy przycisk jest
wciśnięty - oraz podstawową grafikę w postaci wyświetlania dwóch tekstur: gdy kursor jest nad przyciskiem oraz gdy nie jest.

## NetworkDevice

Klasa abstrakcyjna stanowiąca interfejs do posługiwania się gniazdami sieciowymi - nawiązywania połączenia, kończenia połączenia,
wysyłania oraz odbierania paczek informacji (GamePackage).

## Server

Urządzenie sieciowe pełniące rolę serwera. Spodziewa się i akceptuje przychodzące zapytania o ustanowienie połączenia.

## Client

Urządzenie sieciowe pełniące rolę klienta. Dokonuje próbę połączenia przez podany adres IP.

## Scene

Interfejs stanowiący warstwę abstrakcji dla scen - czyli obiektów determinujących zawartość okna, logikę itp.

## TransitionScene

Scena przejścia, odgrywająca prostą animację fade-in-out która służy do uatrakcyjnienia procesu zmiany scen.

## MenuScene

Scena menu głównego.

## StartGameScene

Scena do nawiązywania połączenia sieciowego jako serwer.

## JoinGameScene

Scena do nawiązywania połączenia sieciowego jako klient.

## GameScene 

Scena zawierająca logikę właściwej rozgrywki. 
(To raczej już do ciebie Michał)

## GamePackage

Klasa pełniąca rolę paczki do komunikacji pomiędzy dwoma urządzeniami sieciowymi celem wymiany informacji o grze. 
(Tu też twoje)
