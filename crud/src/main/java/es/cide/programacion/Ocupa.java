package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ocupa {

    // variables para la base de datos
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // para tener la conexion en la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static String tabla = "OCUPA";
    private static String[] columnas = {"NSS_EMPLEAT", "CODI_PLACA", "DATA_INICI", "DATA_FI"};
    private static String[] registros = {"NSS del empleado: ", "Codi placa de la plaza:", "Fecha de inicio: ", "Fecha de finalización: "};
    private static String[] pk = {"NSS_EMPLEAT", "CODI_PLACA"};
    private static String[] references = new String[2];
    private static Empleados emp;
    private static Plaza pla;

    Ocupa() {

    }

    public void setReferences(String empreference, String plareference) {
        references[0] = plareference;
        references[1] = empreference;
    }

    // metodo de insertar
    // le pasamos por parametros la tabla, las columnas, y cada columna del usuario
    // primero pide el dato al usuario, construye el sql y hace el insert
    public static void insertar() {

        // cogemos todas la longitud de las columnas
        // para ir preguntando al usuario cada registro e ir guardandolo en esta variable
        String[] dato_registro = new String[columnas.length];
        // hacemos el comando para el sql
        String sql = ("INSERT INTO " + tabla + " VALUES ");
        String dato = "";
        String columna = "";

        // pedimos cada dato al usuario en orden
        for (int j = 0; j < columnas.length - 2; j++) {
            // guarda la respuesta del usuario
            dato_registro[j] = JOptionPane.showInputDialog(null, registros[j]);
            // si el usuario cancela cualquier dialogo, salimos sin hacer nada
            if (dato_registro[j] == null) {
                return;
            }
        }

        // ahora tienen coma las columnas y los registros
        for (int i = 0; i < columnas.length - 2; i++) {
            columna += columnas[i];
            if (i < columnas.length - 1) {
                columna += ",";
            }
            dato += "('" + dato_registro[i] + "')";
            if (i < dato_registro.length - 1) {
                dato += ",";
            }
        }

        columna = columna + "," + columnas[2] + "," + columnas[3];
        dato = dato + "," + references[0] + "," + references[1];

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
    public static void delete() {
        String sql = "";
        String del_pk = JOptionPane.showInputDialog(null, pk[1] + " a eliminar de " + tabla + ":");
        // construimos el sql con el id directamente incrustado

        sql = ("DELETE FROM " + tabla + " WHERE " + pk[1] + " = '" + del_pk + "'");

        // hago la conexion a la base de datos
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            // ejecuto el comando de sql
            stmt.executeUpdate(sql);
            // si no existe la primary key
            if (del_pk != null) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else { // si no existe
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con " + pk[1] + " " + del_pk + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de update
    // pide la pk, despues muestra un combobox con las columnas y un textfield con el nuevo valor
    public static void update() {
        // introduce el pk de la fila q quiere actualizar
        String upd_pk = JOptionPane.showInputDialog(null, pk[0] + " a actualizar de " + tabla + ":");
        // zi no introduce nada, sale
        if (upd_pk == null) {
            return;
        }

        // combobox con los registros de las columnas
        JComboBox<String> combo = new JComboBox<>(registros);
        // el campo para q introduzca el nuevo valor
        JTextField campo = new JTextField(15);

        // añadimos los dos al panel
        JPanel panel = new JPanel();
        panel.add(combo);
        panel.add(campo);

        // muestra un combobox donde puedes elegir la columna a ctualizar
        String[] opciones = {"Actualizar", "Cancelar"};
        int input = JOptionPane.showOptionDialog(null, panel, "Actualizar " + tabla,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        // si pulsa cancelar o cierra el dialogo, salimos
        if (input != 0) {
            return;
        }

        // hace un getter de lo que ha escogido el usuario
        String choosecol = columnas[combo.getSelectedIndex()];
        String newdato = campo.getText();

        // comando sql q se ejecutara
        String sql = "UPDATE " + tabla + " SET " + choosecol + " = '" + newdato + "' WHERE " + pk[0] + " = '" + upd_pk + "'";

        // lo ejecutamos en el sql
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

}
