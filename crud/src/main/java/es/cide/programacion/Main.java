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
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Main {

    // variables para la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_bbdd";
    private static final String USER = "Maku"; // pon aqui el usuario
    private static final String PASSWORD = "Makuku123";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // =========================================================================
    // METODO GENERICO: insertar
    //
    // En lugar de tener un metodo por cada tabla, este unico metodo sirve
    // para cualquier INSERT. Recibe:
    //   - tabla:    nombre de la tabla donde insertar ("empleados", "plazas"...)
    //   - columnas: array con los nombres de las columnas ["nombre","apellido"...]
    //   - mensajes: array con lo que se le pregunta al usuario por cada columna
    //               ["Nombre del empleado:", "Apellido del empleado:"...]
    //
    // Funciona asi:
    //   1. Pide cada dato al usuario con un JOptionPane
    //   2. Construye el SQL dinamicamente segun cuantas columnas haya
    //   3. Ejecuta el INSERT
    // =========================================================================
    public static void insertar(String tabla, String[] columnas, String[] mensajes) {

        // array donde guardaremos las respuestas del usuario
        String[] valores = new String[columnas.length];

        // pedimos cada dato al usuario en orden
        for (int i = 0; i < mensajes.length; i++) {
            valores[i] = JOptionPane.showInputDialog(null, mensajes[i]);
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
                sql.append(","); // coma entre columnas, no al final

            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < columnas.length; i++) {
            sql.append("?");
            if (i < columnas.length - 1) {
                sql.append(","); // coma entre ?, no al final

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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // frame
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // tabbedpane para controlar las pantallas
        JTabbedPane tabs = new JTabbedPane();

        // gbc
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // pestaña de empleados
        JPanel panel_empleados = new JPanel(new GridBagLayout());

        JButton add_empleado = new JButton("Crear Empleado");
        JButton remove_empleado = new JButton("Eliminar Empleado");
        JButton read_empleado = new JButton("Listar Empleados");
        JButton update_empleado = new JButton("Actualizar Empleado");

        JTextArea textarea_empleados = new JTextArea(15, 30);
        textarea_empleados.setEditable(false);

        // llamada al metodo de insertar y pasamos por parametros lo de cada boton
        //   columnas --> los campos de la tabla (sin el id)
        //   mensajes --> lo q escribira el usuario
        add_empleado.addActionListener(e -> insertar(
                "empleados",
                new String[]{"nombre", "apellido", "salario"},
                new String[]{"Nombre del empleado:", "Apellido del empleado:", "Salario del empleado:"}
        ));

        // llama a la funcion con lambda y pasa por parametros el nombre de la tabla
        remove_empleado.addActionListener(e -> eliminar("empleados"));

        // pestaña de plazas
        JPanel panel_plaza = new JPanel(new GridBagLayout());

        JButton add_plaza = new JButton("Crear Plaza");
        JButton remove_plaza = new JButton("Eliminar Plaza");
        JButton read_plaza = new JButton("Listar Plazas");
        JButton update_plaza = new JButton("Actualizar Plaza");

        JTextArea textarea_plazas = new JTextArea(15, 30);
        textarea_plazas.setEditable(false);

        // mismo funcionamiento q antes
        add_plaza.addActionListener(e -> insertar(
                "plazas",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nombre de la plaza:", "Descripcion de la plaza:"}
        ));

        remove_plaza.addActionListener(e -> eliminar("plazas"));

        // pestaña de nominas
        JPanel panel_nominas = new JPanel(new GridBagLayout());

        JButton add_nomina = new JButton("Crear Nomina");
        JButton remove_nomina = new JButton("Eliminar Nomina");
        JButton read_nomina = new JButton("Listar Nominas");
        JButton update_nomina = new JButton("Actualizar Nomina");

        JTextArea textarea_nominas = new JTextArea(15, 30);
        textarea_nominas.setEditable(false);

        // mismo funcionamiento
        add_nomina.addActionListener(e -> insertar(
                "nominas",
                new String[]{"empleado_id", "cantidad", "fecha"},
                new String[]{"ID del empleado:", "Cantidad de la nomina:", "Fecha (YYYY-MM-DD):"}
        ));

        remove_nomina.addActionListener(e -> eliminar("nominas"));

        // gbc de empleados
        gbc.gridy = 0;
        panel_empleados.add(new JLabel("Empleados"), gbc);
        gbc.gridy = 1;
        panel_empleados.add(add_empleado, gbc);
        gbc.gridy = 2;
        panel_empleados.add(remove_empleado, gbc);
        gbc.gridy = 3;
        panel_empleados.add(read_empleado, gbc);
        gbc.gridy = 4;
        panel_empleados.add(update_empleado, gbc);
        gbc.gridy = 5;
        panel_empleados.add(new JScrollPane(textarea_empleados), gbc);

        tabs.addTab("Empleados", panel_empleados);

        // gbc de plaza
        gbc.gridy = 0;
        panel_plaza.add(new JLabel("Plazas"), gbc);
        gbc.gridy = 1;
        panel_plaza.add(add_plaza, gbc);
        gbc.gridy = 2;
        panel_plaza.add(remove_plaza, gbc);
        gbc.gridy = 3;
        panel_plaza.add(read_plaza, gbc);
        gbc.gridy = 4;
        panel_plaza.add(update_plaza, gbc);
        gbc.gridy = 5;
        panel_plaza.add(new JScrollPane(textarea_plazas), gbc);

        tabs.addTab("Plazas", panel_plaza);

        // gbc de nomina
        gbc.gridy = 0;
        panel_nominas.add(new JLabel("Nominas"), gbc);
        gbc.gridy = 1;
        panel_nominas.add(add_nomina, gbc);
        gbc.gridy = 2;
        panel_nominas.add(remove_nomina, gbc);
        gbc.gridy = 3;
        panel_nominas.add(read_nomina, gbc);
        gbc.gridy = 4;
        panel_nominas.add(update_nomina, gbc);
        gbc.gridy = 5;
        panel_nominas.add(new JScrollPane(textarea_nominas), gbc);

        tabs.addTab("Nominas", panel_nominas);

        // añadimos
        frame.add(tabs, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // para q se centre
        frame.setVisible(true); // para q sea visible
    }

    // metodo de eliminar
    // primero pide el id, despues ejecuta el delete y avisa si el id no existia
    public static void eliminar(String tabla) {

        String del_id = JOptionPane.showInputDialog(null, "ID a eliminar de " + tabla + ":");
        if (del_id == null) {
            return; // usuario cancelo
        }
        try {
            int id = Integer.parseInt(del_id);

            try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement("DELETE FROM " + tabla + " WHERE id = ?")) {

                stmt.setInt(1, id);
                int del_filas = stmt.executeUpdate();

                if (del_filas > 0) {
                    JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
                } else {
                    // executeUpdate devuelve 0 si el ID no existia en la tabla
                    JOptionPane.showMessageDialog(null, "No se encontro ningun registro con ID " + id + " en " + tabla,
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
