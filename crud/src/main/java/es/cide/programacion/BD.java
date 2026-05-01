package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BD {

    // variables para la base de datos
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // para tener la conexion en la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private String tabla;
    private String[] columnas;
    private String[] registros;

    public BD(String tabla, String[] columnas, String[] registros) {
        this.tabla = tabla;
        this.columnas = columnas;
        this.registros = registros;
    }

    public BD() {

    }

    public String getTabla() { return tabla; }
    public void setTabla(String tabla) { this.tabla = tabla; }

    public String[] getColumnas() { return columnas; }
    public void setColumnas(String[] columnas) { this.columnas = columnas; }

    public String[] getRegistros() { return registros; }
    public void setRegistros(String[] registros) { this.registros = registros; }

    // crea la base de datos si no esta creada ya
    public static void create() {
        try (Connection con = DriverManager.getConnection(URL); Statement stmt = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS TIPUS_PLACA (\r\n"
                    + "    NOM VARCHAR(25) PRIMARY KEY,\r\n"
                    + "    FUNCIO VARCHAR(200) NOT NULL\r\n"
                    + ");\r\n";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS PLACA (\r\n"
                    + "    CODI INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
                    + "    NOM VARCHAR(25) NOT NULL,\r\n"
                    + "    SALARI INTEGER NOT NULL,\r\n"
                    + "    INFORME_SUPERVISIO VARCHAR(100),\r\n"
                    + "    CODI_PLACA_SUPERVISORA INTEGER,\r\n"
                    + "    NOM_TIPUS_PLACA VARCHAR(25) NOT NULL,\r\n"
                    + "    FOREIGN KEY (CODI_PLACA_SUPERVISORA) REFERENCES PLACA (CODI),\r\n"
                    + "    FOREIGN KEY (NOM_TIPUS_PLACA) REFERENCES TIPUS_PLACA (NOM)\r\n"
                    + ");\r\n";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS EMPLEAT (\r\n"
                    + "    NSS INTEGER PRIMARY KEY,\r\n"
                    + "    NOM VARCHAR(25) NOT NULL,\r\n"
                    + "    LLINATGES VARCHAR(25) NOT NULL,\r\n"
                    + "    EMAIL VARCHAR(25),\r\n"
                    + "    IBAN VARCHAR(25) UNIQUE NOT NULL CHECK (IBAN LIKE 'ES%')\r\n"
                    + ");";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS OCUPA (\r\n"
                    + "    NSS_EMPLEAT INTEGER NOT NULL,\r\n"
                    + "    CODI_PLACA INTEGER NOT NULL,\r\n"
                    + "    DATA_INICI VARCHAR(20) NOT NULL,\r\n"
                    + "    DATA_FI VARCHAR(20),\r\n"
                    + "    PRIMARY KEY (NSS_EMPLEAT, CODI_PLACA),\r\n"
                    + "    FOREIGN KEY (NSS_EMPLEAT) REFERENCES EMPLEAT (NSS),\r\n"
                    + "    FOREIGN KEY (CODI_PLACA) REFERENCES PLACA (CODI)\r\n"
                    + ");\r\n";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS NOMINA (\r\n"
                    + "    ID_NOMINA INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
                    + "    IBAN_PAGAMENT VARCHAR(25) NOT NULL,\r\n"
                    + "    IMPORT REAL NOT NULL,\r\n"
                    + "    NSS_EMPLEAT INTEGER NOT NULL,\r\n"
                    + "    CODI_PLACA INTEGER NOT NULL,\r\n"
                    + "    FOREIGN KEY (NSS_EMPLEAT) REFERENCES EMPLEAT (NSS),\r\n"
                    + "    FOREIGN KEY (CODI_PLACA) REFERENCES PLACA (CODI)\r\n"
                    + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // metodo de insertar
    // le pasamos por parametros la tabla, las columnas, y cada columna del usuario
    // primero pide el dato al usuario, construye el sql y hace el insert
    public static void insertar(String tabla, String[] columnas, String[] registros) {

        // cogemos todas la longitud de las columnas
        // para ir preguntando al usuario cada registro e ir guardandolo en esta variable
        String[] dato_registro = new String[columnas.length];
        // hacemos el comando para el sql
        String sql = ("INSERT INTO " + tabla + " VALUES ");
        String dato = "";
        String columna = "";

        // pedimos cada dato al usuario en orden
        for (int j = 0; j < columnas.length; j++) {
            // guarda la respuesta del usuario
            dato_registro[j] = JOptionPane.showInputDialog(null, registros[j]);
            // si el usuario cancela cualquier dialogo, salimos sin hacer nada
            if (dato_registro[j] == null) {
                return;
            }
        }

        // ahora tienen coma las columnas y los registros
        for (int i = 0; i < columnas.length; i++) {
            columna += columnas[i];
            if (i < columnas.length - 1) {
                columna += ",";
            }
            dato += "('" + dato_registro[i] + "')";
            if (i < dato_registro.length - 1) {
                dato += ",";
            }
        }

        sql = ("INSERT INTO " + tabla + " (" + columna + ") VALUES (" + dato + ")"); // comando
        // nos conectamos al sql
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            // ejcutamos el comando de sqlite
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro añadido correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de eliminar
    // primero pide el valor de la pk, despues ejecuta el delete y avisa si no existia
    public static void delete(String tabla, String pk) {
        String sql = "";
        String del_pk = JOptionPane.showInputDialog(null, pk + " a eliminar de " + tabla + ":");
        // construimos el sql con el id directamente incrustado
        if (tabla.equals("TIPUS_PLACA")) {
            sql = ("DELETE FROM " + tabla + " WHERE " + pk + " = '" + del_pk + "'");
        } else {
            sql = ("DELETE FROM " + tabla + " WHERE " + pk + " = " + del_pk);
        }

        // hago la conexion a la base de datos
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            // ejecuto el comando de sql
            stmt.executeUpdate(sql);
            // si no existe la primary key
            if (del_pk != null) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else { // si no existe
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con " + pk + " " + del_pk + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void update() {

    }

    public void select(JTextField[][] campos, int page, int numFilas) {
        select(tabla, columnas, campos, page, numFilas);
    }

    // metodo de select: rellena los textfields de la pestaña correspondiente con paginacion
    public static void select(String tabla, String[] columnas, JTextField[][] campos, int page, int numFilas) {
        // limpiamos todos los textfields antes de rellenar
        for (JTextField[] col : campos) {
            for (JTextField tf : col) {
                tf.setText("");
                tf.setEditable(false);
            }
        }

        String cols = String.join(",", columnas);
        String sql = "SELECT " + cols + " FROM " + tabla
                + " ORDER BY " + columnas[0]
                + " LIMIT " + numFilas + " OFFSET " + (page * numFilas);

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            int row = 0;
            while (rs.next() && row < numFilas) {
                for (int c = 0; c < columnas.length; c++) {
                    String val = rs.getString(columnas[c]);
                    campos[c][row].setText(val != null ? val : "");
                }
                row++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al hacer select de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
