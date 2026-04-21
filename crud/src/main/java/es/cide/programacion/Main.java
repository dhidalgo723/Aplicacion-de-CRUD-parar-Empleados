package es.cide.programacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Main {

    // variables para la base de datos
    private static final String URL = "jdbc:sqlite:base_de_datos_makush.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {

        // look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // frame
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // tabbedpane para controlar las pantallas
        JTabbedPane tabs = new JTabbedPane();

        // pestaña empleados
        JPanel panel_empleados = new JPanel(new GridBagLayout());

        // botones de empleado
        JButton add_empleado = new JButton("Crear Empleado");
        JButton remove_empleado = new JButton("Eliminar Empleado");
        JButton read_empleado = new JButton("Listar Empleados");
        JButton update_empleado = new JButton("Actualizar Empleado");

        // guardo los textfields en un array para leerlos despues
        int num_filas = 7;
        JTextField[] campos_id_emp = new JTextField[num_filas];
        JTextField[] campos_nom_emp = new JTextField[num_filas];
        JTextField[] campos_fun_emp = new JTextField[num_filas];

        // listeners de empleados
        add_empleado.addActionListener(e -> insertar(
                "empleados",
                new String[]{"nombre", "apellido", "salario"},
                new String[]{"Nombre del empleado:", "Apellido del empleado:", "Salario del empleado:"}
        ));
        remove_empleado.addActionListener(e -> eliminar("empleados"));
        update_empleado.addActionListener(e -> actualizar(
                "empleados",
                new String[]{"nombre", "apellido", "salario"},
                new String[]{"Nuevo nombre:", "Nuevo apellido:", "Nuevo salario:"}
        ));

        tabs.addTab("Empleados", new JScrollPane(panel_empleados));

        // pestaña de plaza
        JPanel panel_plaza = new JPanel(new GridBagLayout());

        // botones de plaza
        JButton add_plaza = new JButton("Crear Plaza");
        JButton remove_plaza = new JButton("Eliminar Plaza");
        JButton read_plaza = new JButton("Listar Plazas");
        JButton update_plaza = new JButton("Actualizar Plaza");

        // textfields de plazas
        JTextField[] campos_id_pla = new JTextField[num_filas];
        JTextField[] campos_nom_pla = new JTextField[num_filas];
        JTextField[] campos_fun_pla = new JTextField[num_filas];

        // listeners de plazas
        add_plaza.addActionListener(e -> insertar(
                "plazas",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nombre de la plaza:", "Descripcion de la plaza:"}
        ));
        remove_plaza.addActionListener(e -> eliminar("plazas"));
        update_plaza.addActionListener(e -> actualizar(
                "plazas",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nuevo nombre:", "Nueva descripcion:"}
        ));

        tabs.addTab("Plazas", new JScrollPane(panel_plaza));

        // pestaña de nominas
        JPanel panel_nominas = new JPanel(new GridBagLayout());

        // botones nomina
        JButton add_nomina = new JButton("Crear Nomina");
        JButton remove_nomina = new JButton("Eliminar Nomina");
        JButton read_nomina = new JButton("Listar Nominas");
        JButton update_nomina = new JButton("Actualizar Nomina");

        // textfields de nominas
        JTextField[] campos_id_nom = new JTextField[num_filas];
        JTextField[] campos_nom_nom = new JTextField[num_filas];
        JTextField[] campos_fun_nom = new JTextField[num_filas];

        // listeners de nominas
        add_nomina.addActionListener(e -> insertar(
                "nominas",
                new String[]{"empleado_id", "cantidad", "fecha"},
                new String[]{"ID del empleado:", "Cantidad de la nomina:", "Fecha (YYYY-MM-DD):"}
        ));
        remove_nomina.addActionListener(e -> eliminar("nominas"));
        update_nomina.addActionListener(e -> actualizar(
                "nominas",
                new String[]{"empleado_id", "cantidad", "fecha"},
                new String[]{"Nuevo ID empleado:", "Nueva cantidad:", "Nueva fecha (YYYY-MM-DD):"}
        ));

        // gbc (unico para todos los paneles)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1.0;

        // gbc empleados
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_empleados.add(add_empleado, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_empleados.add(remove_empleado, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel_empleados.add(read_empleado, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel_empleados.add(update_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_empleados.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_empleados.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_empleados.add(new JLabel("Funcion"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_id_emp[i] = new JTextField();
            campos_nom_emp[i] = new JTextField();
            campos_fun_emp[i] = new JTextField();
            campos_fun_emp[i].setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_empleados.add(campos_id_emp[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_empleados.add(campos_nom_emp[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_empleados.add(campos_fun_emp[i], gbc);
        }

        // gbc plazas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_plaza.add(add_plaza, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_plaza.add(remove_plaza, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel_plaza.add(read_plaza, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel_plaza.add(update_plaza, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_plaza.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_plaza.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_plaza.add(new JLabel("Funcion"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_id_pla[i] = new JTextField();
            campos_nom_pla[i] = new JTextField();
            campos_fun_pla[i] = new JTextField();
            campos_fun_pla[i].setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_plaza.add(campos_id_pla[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_plaza.add(campos_nom_pla[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_plaza.add(campos_fun_pla[i], gbc);
        }

        // gbc nominas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_nominas.add(add_nomina, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_nominas.add(remove_nomina, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel_nominas.add(read_nomina, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel_nominas.add(update_nomina, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_nominas.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_nominas.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_nominas.add(new JLabel("Funcion"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_id_nom[i] = new JTextField();
            campos_nom_nom[i] = new JTextField();
            campos_fun_nom[i] = new JTextField();
            campos_fun_nom[i].setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_nominas.add(campos_id_nom[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_nominas.add(campos_nom_nom[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_nominas.add(campos_fun_nom[i], gbc);
        }

        tabs.addTab("Nominas", new JScrollPane(panel_nominas));

        // añadimos
        frame.add(tabs, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // metodo de insertar
    // le pasamos por parametros la tabla, las columnas, y cada columna del usuario
    // primero pide el dato al usuario, construye el sql y hace el insert
    public static void insertar(String tabla, String[] columnas, String[] celda) {

        // array donde guardaremos las respuestas del usuario
        String[] valores = new String[columnas.length];

        // pedimos cada dato al usuario en orden
        for (int i = 0; i < celda.length; i++) {
            valores[i] = JOptionPane.showInputDialog(null, celda[i]);
            // si el usuario cancela cualquier dialogo, salimos sin hacer nada
            if (valores[i] == null) {
                return;
            }
        }

        // construimos el SQL dinamicamente:
        // para 3 columnas genera: INSERT INTO empleados (nombre,apellido,salario) VALUES (?,?,?)
        StringBuilder sql = new StringBuilder("INSERT INTO " + tabla + " (");
        for (int i = 0; i < columnas.length; i++) {
            sql.append(columnas[i]);
            if (i < columnas.length - 1) {
                sql.append(",");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < columnas.length; i++) {
            sql.append("?");
            if (i < columnas.length - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        // ejecutamos el INSERT con los valores recogidos
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql.toString())) {

            // asignamos cada valor al ? correspondiente (indice empieza en 1)
            for (int i = 0; i < valores.length; i++) {
                stmt.setString(i + 1, valores[i]);
            }

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro añadido correctamente en " + tabla + ".");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de eliminar
    // primero pide el id, despues ejecuta el delete y avisa si el id no existia
    public static void eliminar(String tabla) {
        String del_id = JOptionPane.showInputDialog(null, "ID a eliminar de " + tabla + ":");
        // cuando ya se ha eliminado lo devuelve
        if (del_id == null) {
            return;
        }
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement("DELETE FROM " + tabla + " WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(del_id));
            int del_filas = stmt.executeUpdate();

            if (del_filas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else {
                // executeUpdate devuelve 0 si el ID no existia en la tabla
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con ID " + del_id + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de actualizar
    // funciona igual que insertar --> pide los nuevos valores para cada columna,
    // pide el id del registro a modificar, construye el update y despues lo ejecuta
    public static void actualizar(String tabla, String[] columna, String[] celda) {

        // pedimos los nuevos valores al usuario
        String[] valores = new String[columna.length];
        for (int i = 0; i < celda.length; i++) {
            valores[i] = JOptionPane.showInputDialog(null, celda[i]);
            if (valores[i] == null) {
                return; // usuario cancelo
            }
        }

        // pedimos el id del registro que se quiere modificar
        String upd_id = JOptionPane.showInputDialog(null, "ID a actualizar de " + tabla + ":");
        if (upd_id == null) {
            return; // usuario cancelo
        }

        // construimos el SQL dinamicamente:
        // para 3 columna genera: UPDATE empleados SET nombre=?,apellido=?,salario=? WHERE id=?
        StringBuilder sql = new StringBuilder("UPDATE " + tabla + " SET ");
        for (int i = 0; i < columna.length; i++) {
            sql.append(columna[i]).append("=?");
            if (i < columna.length - 1) {
                sql.append(",");
            }
        }
        sql.append(" WHERE id=?");

        // ejecutamos el UPDATE con los valores recogidos
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql.toString())) {

            // asignamos los valores de las columnas
            for (int i = 0; i < valores.length; i++) {
                stmt.setString(i + 1, valores[i]);
            }
            // el ultimo ? es el id (indice = columnas.length + 1)
            stmt.setInt(columna.length + 1, Integer.parseInt(upd_id));

            int upd_filas = stmt.executeUpdate();

            if (upd_filas > 0) {
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente en " + tabla + ".");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con ID " + upd_id + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
