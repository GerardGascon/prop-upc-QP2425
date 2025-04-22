# Joc de Scrabble

<!-- TOC -->
* [Joc de Scrabble](#joc-de-scrabble)
  * [Autors](#autors)
  * [Executar](#executar)
  * [Tests](#tests)
  * [Manual d'ús](#manual-dús)
  * [Estructura](#estructura)
    * [Arquitectura](#arquitectura)
    * [Gestió de dependències](#gestió-de-dependències)
<!-- TOC -->

## Autors

Escofet González, Gina\
Gascón Moliné, Gerard\
Martínez Lassalle, Felipe\
Pérez Silvestre, Biel\
Usero Martinez, Albert

[gina.escofet@estudiantat.upc.edu](mailto:gina.escofet@estudiantat.upc.edu)\
[gerard.gascon@estudiantat.upc.edu](mailto:gerard.gascon@estudiantat.upc.edu)\
[felipe.martinez.lassalle@estudiantat.upc.edu](mailto:felipe.martinez.lassalle@estudiantat.upc.edu)\
[biel.perez@estudiantat.upc.edu](mailto:biel.perez@estudiantat.upc.edu)\
[albert.usero@estudiantat.upc.edu](mailto:albert.usero@estudiantat.upc.edu)

## Executar

Dins la carpeta [EXE/classes/java/main](EXE/classes/java/main), s'ha d'executar el programa:

- scrabble.bat en cas d'estar en Windows
- scrabble.sh en cas d'estar en Linux

També es pot executar utilitzant la següent comanda:

```shell
./gradlew run
```

## Tests

Per executar els tests, es pot fer utilitzant la següent comanda:

```shell
./gradlew test
```

## Manual d'ús

Per poder jugar a la versió per terminal de la primera entrega, es fa utilitzant el següent:

1. Seleccionar l'idioma amb les següents comandes:
   ```
   next: Selecciona l'idioma següent
   previous: Selecciona l'idioma anterior
   submit: Selecciona aquell idioma
   ```
2. Seleccionar la mida del tauler:
   ```
   next: Selecciona la mida següent
   previous: Selecciona la mida anterior
   submit: Selecciona aquella mida
   ```
3. Seleccionar el nombre de jugadors:
    1. Escriu el nom dels jugadors humans
       ```
       submit: Selecciona aquells jugadors
       ```
    2. Selecciona el nombre de jugadors controlats per l'ordinador
       ```
       next: Afegeix un jugador controlat per ordinador
       previous: Elimina un jugador controlat per ordinador
       submit: Selecciona aquells jugadors
       ```
4. Començar a jugar
   1. Selecciona el moviment a fer:
      ```
      put: col·locar una paraula al tauler
      draw: intercanviar fitxes amb les de la bossa
      pass: passa de torn sense fer res
      ```
      1. Per col·locar una paraula segueix el següent format:
         ```
         PARAULA AB
         Si la A és un número, la paraula serà en vertical
         Si la A és una lletra, la paraula serà en horitzontal
         ```
         És important escriure tant la paraula com les coordenades en majúscula
      2. Per intercanviar fitxes:
         ```
         A, B, C, D
         ```
         Has d'escriure les fitxes de la teva mà separades per comes

## Estructura

### Arquitectura

El projecte està estructurat seguint una arquitectura en 3 capes. En aquest cas hem seguit el model **MVP**.

- **[M]** Capa de dades: Conté tota la informació sobre l'estat actual del programa. És útil si es vol implementar un
  sistema de persistència, ja que facilita el procés de guardar i restaurar l'estat.
- **[V]** Capa de presentació: Conté les diferents vistes presents en el programa. Per a fer funcionar aquestes vistes
  utilitzem el patró **vista passiva**, el qual significa que l'única lògica present en aquestes classes serveix per
  enviar missatges al domini i rebre callbacks eventualment.
- **[P]** Capa de domini: Conté tots els controladors per a gestionar el flux del programa. Aquí utilitzem el principi
  de responsabilitat única, per tant, cada classe té una única funcionalitat i aquesta és gestionada mitjançant la crida
  al seu mètode `run()`.

### Gestió de dependències

Per a facilitar la gestió de dependències, utilitzem **injecció de dependències**. Durant la construcció de les
diferents escenes presents al joc, hi ha un mètode encarregat de resoldre les dependències de tots els objectes
presents a l'escena per a la posterior injecció que es realitza a les constructores d'aquests objectes.