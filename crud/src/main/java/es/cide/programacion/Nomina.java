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

public class Nomina {

    // ruta del archivo SQLite
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // abre y devuelve una conexion a la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static String tabla = "NOMINA";
    private static String[] columnas = {"ID_NOMINA", "IBAN_PAGAMENT", "IMPORT", "NSS_EMPLEAT", "CODI_PLACA"};
    private static String[] registros = {"ID de la nómina", "IBAN de pago", "Importe", "NSS del empleado", "Código de la plaza"};
    private static String pk = "ID_NOMINA";
    private static TreeMap<String, ArrayList<JTextField>> campos = new TreeMap<>();

    public Nomina(String tabla, String[] columnas, String[] registros) {
        this.tabla = tabla;
        this.columnas = columnas;
        this.registros = registros;
    }

    // funcion de insertar en la base de datos
    public static void insertar() {
        String[] dato_registro = new String[columnas.length];
        String sql = ("INSERT INTO " + tabla + " VALUES ");
        String dato = "";
        String columna = "";

        // preguntamos al usuario por cada campo en orden
        for (int j = 0; j < columnas.length; j++) {
            if (columnas[j].equals("CODI_PLACA_SUPERVISORA")) {
                dato_registro[j] = pedirFK("PLACA", "CODI", registros[j]);
            } else if (columnas[j].equals("NOM_TIPUS_PLACA")) {
                dato_registro[j] = pedirFK("TIPUS_PLACA", "NOM", registros[j]);
            } else {
                dato_registro[j] = JOptionPane.showInputDialog(null, registros[j]);
            }
            if (dato_registro[j] == null) {
                return;
            }
        }

        // hacemos un for para convertirlo todo en un string para pasarlo por el comando de sqlite
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

        // lo ejecutamos
        sql = ("INSERT INTO " + tabla + " (" + columna + ") VALUES (" + dato + ")");
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro añadido correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion de pedir la fk
    private static String pedirFK(String tablaRef, String colRef, String etiqueta) {
        ArrayList<String> valores = new ArrayList<>(); // array de los valores q pediremos
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

        // si la tabla referenciada esta vacia no podemos elegir FK valida
        if (valores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay registros en " + tablaRef + " para elegir.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // montamos el panel con el combobox para q el usuario eliga
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

    // funcion de eliminar cosas 
    public static void delete() {
        String sql = "";
        String del_pk = JOptionPane.showInputDialog(null, pk + " a eliminar de " + tabla + ":");
        sql = ("DELETE FROM " + tabla + " WHERE " + pk + " = '" + del_pk + "'");

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            // del_pk es null solo si el usuario pulso cancelar, en ese caso avisamos
            if (del_pk != null) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con " + pk + " " + del_pk + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion de actualizar
    public static void update() {
        String upd_pk = JOptionPane.showInputDialog(null, pk + " a actualizar de " + tabla + ":");
        // si el usuario cancela, salimos
        if (upd_pk == null) {
            return;
        }

        JComboBox<String> combo = new JComboBox<>(registros);
        JTextField campo = new JTextField(15);

        JPanel panel = new JPanel();
        panel.add(combo);
        panel.add(campo);

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
        String sql = "UPDATE " + tabla + " SET " + choosecol + " = '" + newdato + "' WHERE " + pk + " = '" + upd_pk + "'";

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // funcion de listar la base de datos
    public static void select(int page, int numFilas) {
        String cols = "";
        for (int i = 0; i < columnas.length; i++) {
            cols += columnas[i];
            if (i < columnas.length - 1) {
                cols += ",";
            }
        }

        // LIMIT limita cuantas filas devuelve; OFFSET salta las filas de las paginas anteriores
        String sql = "SELECT " + cols + " FROM " + tabla
                + " ORDER BY " + columnas[0]
                + " LIMIT " + numFilas + " OFFSET " + (page * numFilas);

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            int row = 0; // indice de la fila visual
            while (rs.next() && row < numFilas) {
                // para cada columna, ponemos el valor en el textfield de esa fila
                for (int j = 0; j < columnas.length; j++) {
                    String dato = rs.getString(columnas[j]);
                    // si el valor es null (campo vacio en BD), ponemos cadena vacia para no mostrar "null"
                    campos.get(columnas[j]).get(row).setText(dato != null ? dato : "");
                }
                row++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al hacer select de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Nomina() {

    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public String[] getRegistros() {
        return registros;
    }

    public void setRegistros(String[] registros) {
        this.registros = registros;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public TreeMap<String, ArrayList<JTextField>> getCampos() {
        return campos;
    }

    public void setCampos(TreeMap<String, ArrayList<JTextField>> campos) {
        this.campos = campos;
    }

}
