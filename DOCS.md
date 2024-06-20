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
Metoda update zajmuje się obsługą wszystkich funkcjonalności gry, a metoda render wyświetla wynikowy stan obiektów które zostały zmodyfikowane jako wynik działań graczy

## EndScene

Scena ekranu końcowego. Pozwala przejść do menu głownego. Wyświetla odpowiedni tekst w zależności od wyniku gry

## Player
Służy jako warstwa abstrakcji pomiędzy elementami takimi jak Plansze a grą, pośredniczy w większości funkcji które wywołuje GameScene

## Board
Reprezentuje planszę jako 100 obiektów klasy Space, a także zajmuje się wyświetlaniem planszy z odpowiednimi podpisami (w zależności od fazy gry)

## HomeBoard 
Zajmuje się metodami związanymi z ustawianiem statków na planszy np sprawdza czy pozycja jest poprawna

## ShootingBoard
Reprezentuje drugą planszę do strzelania. Jedyna różnica polega na innej implementacji metody sprawdzającej poprawność pozycji

## Space
Klasa reprezentująca pojedyncze pole na planszy. Zajmuje się zarządzaniem jego stanu i tym samym tekstur które mają zostać wyświetlone w zależności od tego czy pole było strzelone, stoi na nim statek itp. Przechowuje w sobie obiekt klasy Button. Jednocześnie przechowuje referencje do statku jeśli taki znajduje się na polu

## Ship
Klasa zajmująca się zarządzaniem statkiem oraz ich całą listą (ustawia wszystkie statki na ekranie)
Pozwala obracać czy zmieniać położenie statku. Jednocześnie zawiera zbiory pól zajmowanych przez statek i tych które tworzą jego otoczkę. 
Zarządza również teksturami i np pomaga wyświetlić animację wybuchu

## Position

Trywialna użytkowa klasa przechowująca parę liczb (x,y). Jest używana w większości metod pozostałych klas

## Używane globalnie Enumy:

### Orientation
horizontal, vertical

### ShootingResponse
killed, wounded, missed

Przekazywana w paczkach sieciowych 

### GameScene.Stage
shooting, setting

Oznacza etap na którym jest w danym momencie gra


## GamePackage

Klasa pełniąca rolę paczki do komunikacji pomiędzy dwoma urządzeniami sieciowymi celem wymiany informacji o grze. 
Przechowuje niezbędne elementy stanu całej gry takie jak informacja o wybranej pozycji, pola należące do zastrzelonego statku, odpowiedź na strzał gracza itp
