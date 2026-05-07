# Alternativa para el metodo `select`

Aqui tienes otra forma de hacer el `select`, mas parecida al estilo de `insertar`, `delete` y `update`:
no recibe parametros, construye el SQL a mano con concatenacion y muestra los resultados en un `JOptionPane`
en vez de rellenar los `JTextField` de la pestaña.

```java
// metodo de select alternativo
// no recibe parametros, pide opcionalmente un filtro y muestra los resultados en un dialogo
public static void select() {
    // preguntamos al usuario si quiere filtrar por la pk (puede dejarlo vacio para traer todo)
    String filtro = JOptionPane.showInputDialog(null,
            "Filtrar por " + pk + " (vacio = todos):");
    // si pulsa cancelar, salimos
    if (filtro == null) {
        return;
    }

    // construimos las columnas separadas por coma
    String cols = "";
    for (int i = 0; i < columnas.length; i++) {
        cols += columnas[i];
        if (i < columnas.length - 1) {
            cols += ",";
        }
    }

    // construimos el sql; si hay filtro añadimos WHERE
    String sql = "SELECT " + cols + " FROM " + tabla;
    if (!filtro.isEmpty()) {
        if (tabla.equals("TIPUS_PLACA")) {
            sql += " WHERE " + pk + " = '" + filtro + "'";
        } else {
            sql += " WHERE " + pk + " = " + filtro;
        }
    }

    // hacemos la conexion y ejecutamos
    try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
        java.sql.ResultSet rs = stmt.executeQuery(sql);

        // construimos el texto que va a aparecer en el dialogo
        String resultado = "";
        // primero la cabecera con los nombres de las columnas
        for (int i = 0; i < columnas.length; i++) {
            resultado += columnas[i];
            if (i < columnas.length - 1) {
                resultado += " | ";
            }
        }
        resultado += "\n----------------------------------------\n";

        // recorremos las filas
        int filas = 0;
        while (rs.next()) {
            for (int c = 0; c < columnas.length; c++) {
                String val = rs.getString(columnas[c]);
                resultado += (val != null ? val : "");
                if (c < columnas.length - 1) {
                    resultado += " | ";
                }
            }
            resultado += "\n";
            filas++;
        }

        // si no hay filas avisamos, sino mostramos
        if (filas == 0) {
            JOptionPane.showMessageDialog(null,
                    "No se encontro ningun registro en " + tabla,
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado,
                    "Resultados de " + tabla, JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,
                "Error al hacer select de " + tabla + ":\n" + e.getMessage(),
                "Error SQL", JOptionPane.ERROR_MESSAGE);
    }
}
```

## Como funciona

Va paso a paso, igual que las demas funciones del CRUD:

### 1. Preguntar al usuario un filtro opcional
```java
String filtro = JOptionPane.showInputDialog(null, "Filtrar por " + pk + " (vacio = todos):");
if (filtro == null) return;
```
Sale un cuadro de dialogo. Si el usuario escribe un valor, se filtrara por la PK; si lo deja vacio,
trae toda la tabla. Si pulsa **Cancelar**, `showInputDialog` devuelve `null` y se sale del metodo
sin hacer nada (mismo patron que `insertar()`).

### 2. Montar la lista de columnas
```java
String cols = "";
for (int i = 0; i < columnas.length; i++) {
    cols += columnas[i];
    if (i < columnas.length - 1) cols += ",";
}
```
Recorre el array `columnas` y las une separadas por comas.
Es exactamente el mismo bucle que usas en `insertar()` para construir la parte de columnas del INSERT.
(Tambien podrias hacerlo con `String.join(",", columnas)`, pero asi se ve mas claro lo que esta pasando.)

### 3. Construir el SQL
```java
String sql = "SELECT " + cols + " FROM " + tabla;
if (!filtro.isEmpty()) {
    if (tabla.equals("TIPUS_PLACA")) {
        sql += " WHERE " + pk + " = '" + filtro + "'";
    } else {
        sql += " WHERE " + pk + " = " + filtro;
    }
}
```
Empezamos con `SELECT col1,col2,... FROM tabla`. Si el filtro NO esta vacio le añadimos un `WHERE`.
La comprobacion `tabla.equals("TIPUS_PLACA")` es la misma que tienes en `delete()`: en esa tabla la
PK es texto (`NOM`), asi que necesita comillas; en las demas la PK es numerica y van sin comillas.

### 4. Conectarse y ejecutar la query
```java
try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
    java.sql.ResultSet rs = stmt.executeQuery(sql);
```
- `getConnection()` te abre la conexion a `MakuPlazas.db`.
- `createStatement()` crea el objeto que ejecuta SQL.
- `executeQuery(sql)` se usa para los SELECT (devuelve un `ResultSet` con las filas). Para INSERT/UPDATE/DELETE
  se usa `executeUpdate`, que es lo que usan las otras funciones.
- El `try-with-resources` cierra `con` y `stmt` automaticamente al salir.

### 5. Construir el texto del resultado
```java
String resultado = "";
for (int i = 0; i < columnas.length; i++) {
    resultado += columnas[i];
    if (i < columnas.length - 1) resultado += " | ";
}
resultado += "\n----------------------------------------\n";
```
Primero pone la cabecera con los nombres de las columnas separadas por ` | ` y una linea de guiones.

### 6. Recorrer las filas
```java
while (rs.next()) {
    for (int c = 0; c < columnas.length; c++) {
        String val = rs.getString(columnas[c]);
        resultado += (val != null ? val : "");
        if (c < columnas.length - 1) resultado += " | ";
    }
    resultado += "\n";
    filas++;
}
```
- `rs.next()` avanza una fila y devuelve `true` mientras queden filas.
- Por cada fila, lee el valor de cada columna con `rs.getString(nombreColumna)` y lo añade al string.
- El `(val != null ? val : "")` evita escribir literalmente "null" si el campo es `NULL` en la BD.
- `filas++` cuenta cuantas filas trajo para saber si mostrar resultado o aviso.

### 7. Mostrar el resultado al usuario
```java
if (filas == 0) {
    JOptionPane.showMessageDialog(null, "No se encontro ningun registro...",
            "Aviso", JOptionPane.WARNING_MESSAGE);
} else {
    JOptionPane.showMessageDialog(null, resultado,
            "Resultados de " + tabla, JOptionPane.INFORMATION_MESSAGE);
}
```
Si no hubo filas, sale un aviso. Si las hubo, sale un dialogo informativo con la "tabla" en texto plano.

### 8. Capturar errores SQL
```java
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, "Error al hacer select de " + tabla + ":\n" + e.getMessage(),
            "Error SQL", JOptionPane.ERROR_MESSAGE);
}
```
Mismo manejo que en las otras funciones: si algo falla en la conexion o la query, salta una ventana de error
con el mensaje de la excepcion.

## Diferencias con tu `select` actual

| Aspecto | `select` actual | Esta alternativa |
|---|---|---|
| Parametros | recibe `JTextField[][] campos, int page, int numFilas` | no recibe nada |
| Donde muestra | rellena los `JTextField` de la pestaña | muestra un `JOptionPane` |
| Paginacion | si (`LIMIT` + `OFFSET`) | no, trae todo (o filtrado por PK) |
| Filtrado | siempre todas las filas de la pagina | opcional por PK |
| Estilo | mas "integrado" con la GUI | mas parecido a `insertar`/`delete`/`update` |

## Cosas que pierdes si la usas

- **Paginacion**: la version actual respeta `num_filas` y la pagina actual, esta no.
- **Edicion en sitio**: con la actual los datos quedan en los `JTextField` de la pestaña; con esta solo
  se ven en el dialogo y se cierran al pulsar OK.
- Si la tabla tiene muchas filas, el `JOptionPane` se haria gigante (la actual siempre muestra como mucho `num_filas`).

Por eso, aunque esta version "encaje" mejor con el estilo de las demas funciones, la que ya tienes
seguramente es mas util para tu interfaz con pestañas. Tu decides cual prefieres.
