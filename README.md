# Primer-paso-para-gram-atica-SLR

Implementacion en Java de la funcion CERRADURA (Closure) para items LR(0).

## Video explicativo

https://studio.youtube.com/video/oJixDP4Ot-U/edit

## Estructura del proyecto

- `Gramatica.java` - Lee la gramatica desde un archivo y guarda las producciones.
- `Item.java` - Representa un item LR(0) y permite parsearlo desde texto.
- `Cerradura.java` - Implementa el algoritmo de cerradura.
- `Main.java` - Programa principal que coordina la ejecucion.
- `gramatica.txt` - Archivo de entrada con la gramatica.
- `items.txt` - Archivo de entrada con los items iniciales.

## Formato de los archivos de entrada

### gramatica.txt

Una produccion por linea, con el formato `lado_izquierdo -> lado_derecho`.
Los simbolos del lado derecho se separan con espacios. Si una produccion
tiene alternativas (con `|`), se escribe cada alternativa en una linea
distinta.

Ejemplo:

```
E' -> E
E -> E + T
E -> T
T -> T * F
T -> F
F -> ( E )
F -> id
```

### items.txt

Un item por linea. El punto `.` va pegado al simbolo que le sigue.

Ejemplo:

```
E' -> .E
```

## Como compilar

Desde la carpeta del proyecto, en la terminal:

```
javac *.java
```

## Como ejecutar

Usando los archivos por defecto (`gramatica.txt` e `items.txt`):

```
java Main
```

Usando archivos con otros nombres:

```
java Main mi_gramatica.txt mis_items.txt
```

## Salida del programa

El programa imprime tres secciones:

1. La gramatica cargada.
2. Los items iniciales de entrada.
3. El proceso paso a paso, mostrando cada item revisado y que se agrega.
4. La cerradura final.
