# Activitat 3

En aquesta activitat, cada part tindrà dues seccions:

- solució
- els tests de la solució usant `zio-test`

La part més complicada, en alguns casos, serà la implementació dels jocs de prova. Per la qual cosa, és convenient que, de cara a resoldre aquesta pràctica, prèviament hagueu llegit [Introduction to ZIO Test](https://zio.dev/reference/test/) i tingueu a mà [ZIO Test API docs](https://javadoc.io/doc/dev.zio/zio-test_3/latest/index.html).

Com sempre, si us trobeu amb alguna dificultat, podeu preguntar als fòrums o per missatge privat.

He afegit una especificació `AllTests` que podeu fer servir per executar els jocs de proves de tots els apartats. Com les especificacions també són descripcions, les podem combinar de forma molt senzilla.

Com sempre, abans d'obrir el projecte al `vscode`, executeu la comanda `scala-cli setup-ide .` des del directori arrel del projecte.

## Part 1

- Implementar `orElse` tenint en compte les anotacions de variància correctes a la classe `MyZIO`

  - A l'informe expliqueu el procediment per a trobar les variàncies correctes (podeu. òbviament, inspirar-vos en l'exemple que hi ha als apunts)

- En els tests haureu de comprovar les quatre combinacions de succeeds i fails per comprovar que el `orElse` es comporta correctament.

- __NOTA:__ Recordeu que esteu fent servir `MyZIO`, no el `ZIO` de veritat i que podeu fer servir directament el mètode `run` per saber el resultat d'un efecte.

## Part 2

- Fer un programa que demani un número a l'usuari i generi una llista d'enters d'aquesta longitud i l'escrigui a la sortida

- En aquesta versió, els workflows que definirem seran:

  - `val readNumber: ZIO[Any, Nothing, Int]`, workflow que demanarà un enter a l'usuario, mai fallarà, però que si li entrem alguna cosa que no és un enter, el tractarà com a defecte i l'aplicació fallarà.

  - `def generateList(n: Int): ZIO[Any, Nothing, List[Int]]`, workflow que donat un `n`, genera una llista aleatòria d'enters de longitud `n` usant el servei `Random`.

  - `val program: ZIO[Any, Nothing, Unit]`, workflow que representa el programa principal que demanarà l'enter, generarà la llista i l'escriurà a la consola.

- En quan al tests, haureu de comprovar:

  - per `readNumber` els cas exitós i el que provoca fallida (teniu exemples de com fer-ho als testos del projecte `is-zio-playground`). Useu `TestConsole` de cara a simular les dades d'entrada.

  - per `generateList`, haure de fer servir el `TestRandom` per tal de substituir el generador real i així poder comprovar el funcionament als tests.

  - per `program`, haureu de juntar i substituir tant la consola com el generador i comprovar el que escriu el programa.

- __NOTA__: De cara a que el que escriu el workflow a la sortida no aparegui durant els tests, podeu usar `TestConsole.silent` per a que no es mostri la sortida que ha fet el programa a la consola. El paràmetre d'aquest mètode és l'efecte que voleu silenciar.

## Part 3

- En aquesta part el que farem serà millorar el workflow `readNumber` de l'apartat anterior fent que ens asseguri que el que s'ha entrat és un número enter entre `0` i `10`, de manera que si el que s'ha entrat o bé no és un enter, o bé està forra de rang, es torni a demanar.

- La resta dels altres workflows seran els mateixos que a l'apartat anterior.

- En els tests del nou `readNumber` haureu de provar casos en els que es van _consumint_ elements de l'entrada fins arribar al primer que es correcte.

## Part 4

- En aquest apartat heu d'implementar el típic joc d'endevinar un número. El workflow del joc serà com segueix:

  - el programa demanarà un enter a l'usuari que serà el màxim nombre a escollir
  - anirem demanant números a l'usuari (entre 1 i el màxim que em triat abans) i
    - el joc acabarà si hem encertat el número correcte
    - si no, informarem de que l'objectiu ñes més gran o més petit que el número entrat, i tornarem a demanar un nou número

- Per exemple, una execució del programa ha estat:

  ```sh
  Enter a maximum number to guess: 10
  Enter a number between 1 and 10: 5
  target is HIGHER
  Enter a number between 1 and 10: 7
  target is HIGHER
  Enter a number between 1 and 10: 9
  target is LOWER
  Enter a number between 1 and 10: 8
  Congratulations, you have guessed OK
  ```

- En una primera versió no cal que us preocupeu de si el que s'entra és correcte o no i, si teniu temps, podeu anar millorant (amb idees que ja han aparegut a apartats anteriors) l'entrada de dades.

- En els tests, comproveu com a mínim, una execució del _happy-path_.

_ __NOTA__: Per aquesta part us pot ser útil analitzar l'exemple al paquet `files` del projecte `is-zio-playground`.
