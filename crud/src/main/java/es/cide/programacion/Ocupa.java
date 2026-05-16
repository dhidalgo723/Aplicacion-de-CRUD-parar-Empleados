package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ocupa {

    // ruta del archivo SQLite
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // abre y devuelve una conexion a la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static String tabla = "OCUPA";
    private static String[] columnas = {"NSS_EMPLEAT", "CODI_PLACA", "DATA_INICI", "DATA_FI"};
    private static String[] registros = {"NSS del empleado: ", "Codi placa de la plaza:", "Fecha de inicio en formato AAAA-MM-DD: ", "Fecha de finalización en formato AAAA-MM-DD: "};
    private static String[] pk = {"NSS_EMPLEAT", "CODI_PLACA"};
    private static String[] references = new String[2];
    private static Empleados emp;
    private static Plaza pla;
    private static TreeMap<String, ArrayList<JTextField>> campos = new TreeMap<>();

    // constructor 1
    Ocupa(String tabla, String[] columnas, String[] registros) {
        this.tabla = tabla;
        this.columnas = columnas;
        this.registros = registros;
    }

    // constructor 2
    Ocupa() {

    }

    public void setReferences(String empreference, String plareference) {
        references[0] = plareference;
        references[1] = empreference;
    }

    // funcion para insertar datos a la base de datos
    public static void insertar() {
        // variables
        String[] dato_registro = new String[columnas.length]; // array donde guardo los datos q pone el usuario
        String sql = ("INSERT INTO " + tabla + " VALUES "); // comando sqlite para ejecutar en la base de datos
        String dato = ""; // dato que pone el usuario para eejcutarlo en el comando sqlite
        String columna = ""; // columna para ejecutarlo en el comando sqlite

        // preguntamos al usuario por cada campo en orden
        for (int j = 0; j < columnas.length; j++) {
            if (columnas[j].equals("NSS_EMPLEAT")) {
                dato_registro[j] = pedirFK("EMPLEAT", "NSS", registros[j]);
            } else if (columnas[j].equals("CODI_PLACA")) {
                dato_registro[j] = pedirFK("PLACA", "CODI", registros[j]);
            } else {
                dato_registro[j] = JOptionPane.showInputDialog(null, registros[j]);
            }
            if (dato_registro[j] == null) {
                return;
            }
        }

        // hago un for para q convertirlo en un string el array y poder ejecutarlo en la base de datos
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

        // lo ejecuto en la base de datos
        sql = ("INSERT INTO " + tabla + " (" + columna + ") VALUES (" + dato + ")");
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro añadido correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion de pedir fk
    private static String pedirFK(String tablaRef, String colRef, String etiqueta) {
        ArrayList<String> valores = new ArrayList<>(); // array de valores que pediremos en la base de datos
        String sqlFK = "SELECT " + colRef + " FROM " + tablaRef; // comando sqlite
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlFK);
            // cargamos todos los valores existentes en la lista
            while (rs.next()) {
                valores.add(rs.getString(colRef));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer " + tablaRef + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // si la tabla referenciada esta vacia no elegimos FK
        if (valores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay registros en " + tablaRef + " para elegir.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // montamos el panel para q aparezca como un mensaje
        JComboBox<String> combo = new JComboBox<>(valores.toArray(new String[0]));
        JPanel panel = new JPanel();
        panel.add(new JLabel(etiqueta));
        panel.add(combo);

        String[] opciones = {"Aceptar", "Cancelar"};
        int input = JOptionPane.showOptionDialog(null, panel, "Insertar en " + tabla,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (input != 0) {
            return null;
        }
        return (String) combo.getSelectedItem();
    }

    // funcion de eliminar un dato
    public static void delete() {
        String sql = "";
        String del_pk = JOptionPane.showInputDialog(null, pk[1] + " a eliminar de " + tabla + ":"); // pedimos como mensaje la primary key

        sql = ("DELETE FROM " + tabla + " WHERE " + pk[1] + " = '" + del_pk + "'"); // comando sqlite

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            if (del_pk != null) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con " + pk[1] + " " + del_pk + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion de actualizar un campo de la base de datos
    public static void update() {
        String upd_pk = JOptionPane.showInputDialog(null, pk[0] + " a actualizar de " + tabla + ":");
        if (upd_pk == null) {
            return;
        }

        JComboBox<String> combo = new JComboBox<>(registros); // combobox con los nombres de columna para q el usuario elija cual cambiar
        JTextField campo = new JTextField(15); // campo de texto donde escribe el nuevo valor

        JPanel panel = new JPanel();
        panel.add(combo);
        panel.add(campo);

        // mensaje
        String[] opciones = {"Actualizar", "Cancelar"};
        int input = JOptionPane.showOptionDialog(null, panel, "Actualizar " + tabla,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (input != 0) {
            return;
        }

        // el indice del combobox coincide con el indice en columnas[], asi obtenemos el nombre real de la columna
        String choosecol = columnas[combo.getSelectedIndex()];
        String newdato = campo.getText();

        // comando sqlite
        String sql = "UPDATE " + tabla + " SET " + choosecol + " = '" + newdato + "' WHERE " + pk[0] + " = '" + upd_pk + "'";

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion para mostrar los datos de la base de datos
    public static void select(int page, int numFilas) {
        // hacemos un for para vaciar todos los textfields pq sino no aparecen de manera ordenada
        for (ArrayList<JTextField> col : campos.values()) {
            for (JTextField tf : col) {
                tf.setText("");
                tf.setEditable(false);
            }
        }

        // construimos la lista de columnas separadas por coma para el comando sqlite
        String cols = "";
        for (int i = 0; i < columnas.length; i++) {
            cols += columnas[i];
            if (i < columnas.length - 1) {
                cols += ",";
            }
        }

        // LIMIT limita cuantas filas devuelve; OFFSET salta las filas de las paginas anteriores
        // esto lo hago para q funcione como un pasar pagina
        String sql = "SELECT " + cols + " FROM " + tabla
                + " ORDER BY " + columnas[0]
                + " LIMIT " + numFilas + " OFFSET " + (page * numFilas);

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            int row = 0; // indice de la fila visual (0 = primera fila de textfields)
            while (rs.next() && row < numFilas) {
                // para cada columna, ponemos el valor en el textfield de esa fila
                for (int c = 0; c < columnas.length; c++) {
                    String val = rs.getString(columnas[c]);
                    // si el valor es null (campo vacio en BD), ponemos cadena vacia para no mostrar "null"
                    campos.get(columnas[c]).get(row).setText(val != null ? val : "");
                }
                row++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al hacer select de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getColumnas() {
        return columnas;
    }

    public TreeMap<String, ArrayList<JTextField>> getCampos() {
        return campos;
    }

    public void setCampos(TreeMap<String, ArrayList<JTextField>> campos) {
        Ocupa.campos = campos;
    }
}
