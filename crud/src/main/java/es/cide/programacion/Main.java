package es.cide.programacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

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

        // pestaña tipos de plaza
        JPanel panel_tiposplaza = new JPanel(new GridBagLayout());
        panel_tiposplaza.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listartplaza = new JPanel(new GridBagLayout());
        panel_listartplaza.setBorder(BorderFactory.createTitledBorder("Listar"));

        // botones de tipos de plaza
        JButton add_tiposplaza = new JButton("Crear Tipo de Plaza");
        JButton remove_tiposplaza = new JButton("Eliminar Tipo de Plaza");
        JButton update_tiposplaza = new JButton("Actualizar Tipo de Plaza");

        add_tiposplaza.setPreferredSize(new java.awt.Dimension(add_tiposplaza.getPreferredSize().width, 50));
        remove_tiposplaza.setPreferredSize(new java.awt.Dimension(remove_tiposplaza.getPreferredSize().width, 50));
        update_tiposplaza.setPreferredSize(new java.awt.Dimension(update_tiposplaza.getPreferredSize().width, 50));

        // guardo los textfields en un array para leerlos despues
        int num_filas = 6;
        JTextField[] campos_nom_tplaza = new JTextField[num_filas];
        JTextField[] campos_fun_tplaza = new JTextField[num_filas];

        // listeners de tipos de plaza
        add_tiposplaza.addActionListener(e -> insertar(
                "tiposplaza",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nombre del tipo de plaza:", "Descripcion del tipo de plaza:"}
        ));
        remove_tiposplaza.addActionListener(e -> delete("tiposplaza", "nombre"));
        update_tiposplaza.addActionListener(e -> update(
                "tiposplaza",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nuevo nombre:", "Nueva descripcion:"}
        ));

        // pestaña de plaza
        JPanel panel_plaza = new JPanel(new GridBagLayout());

        JPanel panel_botonesplaza = new JPanel(new GridBagLayout());
        panel_botonesplaza.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarplaza = new JPanel(new GridBagLayout());
        panel_listarplaza.setBorder(BorderFactory.createTitledBorder("Listar"));

        // botones de plaza
        JButton add_plaza = new JButton("Crear Plaza");
        JButton remove_plaza = new JButton("Eliminar Plaza");
        JButton update_plaza = new JButton("Actualizar Plaza");

        add_plaza.setPreferredSize(new java.awt.Dimension(add_plaza.getPreferredSize().width, 50));
        remove_plaza.setPreferredSize(new java.awt.Dimension(remove_plaza.getPreferredSize().width, 50));
        update_plaza.setPreferredSize(new java.awt.Dimension(update_plaza.getPreferredSize().width, 50));

        // textfields de plazas
        JTextField[] campos_codi_pla = new JTextField[num_filas];
        JTextField[] campos_nom_pla = new JTextField[num_filas];
        JTextField[] campos_salari_pla = new JTextField[num_filas];
        JTextField[] campos_info_pla = new JTextField[num_filas];
        JTextField[] campos_codiplaza_pla = new JTextField[num_filas];
        JTextField[] campos_nomplaza_pla = new JTextField[num_filas];

        // listeners de plazas
        add_plaza.addActionListener(e -> insertar(
                "plazas",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nombre de la plaza:", "Descripcion de la plaza:"}
        ));
        remove_plaza.addActionListener(e -> delete("plazas", "CODI"));
        update_plaza.addActionListener(e -> update(
                "plazas",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nuevo nombre:", "Nueva descripcion:"}
        ));

        // pestaña empleados
        JPanel panel_empleados = new JPanel(new GridBagLayout());

        JPanel panel_botonesempleados = new JPanel(new GridBagLayout());
        panel_botonesempleados.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarempleados = new JPanel(new GridBagLayout());
        panel_listarempleados.setBorder(BorderFactory.createTitledBorder("Listar"));

        // botones de empleado
        JButton add_empleado = new JButton("Crear Empleado");
        JButton remove_empleado = new JButton("Eliminar Empleado");
        JButton update_empleado = new JButton("Actualizar Empleado");

        add_empleado.setPreferredSize(new java.awt.Dimension(add_empleado.getPreferredSize().width, 50));
        remove_empleado.setPreferredSize(new java.awt.Dimension(remove_empleado.getPreferredSize().width, 50));
        update_empleado.setPreferredSize(new java.awt.Dimension(update_empleado.getPreferredSize().width, 50));

        JTextField[] campos_nss_emp = new JTextField[num_filas];
        JTextField[] campos_nom_emp = new JTextField[num_filas];
        JTextField[] campos_llin_emp = new JTextField[num_filas];
        JTextField[] campos_email_emp = new JTextField[num_filas];
        JTextField[] campos_iban_emp = new JTextField[num_filas];

        // listeners de empleados
        add_empleado.addActionListener(e -> insertar(
                "empleados",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nombre del empleado:", "Descripcion del empleado:"}
        ));
        remove_empleado.addActionListener(e -> delete("empleados", "NSS"));
        update_empleado.addActionListener(e -> update(
                "empleados",
                new String[]{"nombre", "descripcion"},
                new String[]{"Nuevo nombre:", "Nueva descripcion:"}
        ));

        // pestaña de nominas
        JPanel panel_nominas = new JPanel(new GridBagLayout());
        JPanel panel_botonesnominas = new JPanel(new GridBagLayout());
        panel_botonesnominas.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarnominas = new JPanel(new GridBagLayout());
        panel_listarnominas.setBorder(BorderFactory.createTitledBorder("Listar"));

        // botones nomina
        JButton add_nomina = new JButton("Crear Nomina");
        JButton remove_nomina = new JButton("Eliminar Nomina");
        JButton update_nomina = new JButton("Actualizar Nomina");

        add_nomina.setPreferredSize(new java.awt.Dimension(add_nomina.getPreferredSize().width, 50));
        remove_nomina.setPreferredSize(new java.awt.Dimension(remove_nomina.getPreferredSize().width, 50));
        update_nomina.setPreferredSize(new java.awt.Dimension(update_nomina.getPreferredSize().width, 50));

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
        remove_nomina.addActionListener(e -> delete("nominas", "ID_NOMINA"));
        update_nomina.addActionListener(e -> update(
                "nominas",
                new String[]{"empleado_id", "cantidad", "fecha"},
                new String[]{"Nuevo ID empleado:", "Nueva cantidad:", "Nueva fecha (YYYY-MM-DD):"}
        ));

        // gbc (unico para todos los paneles)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1.0;

        // gbc tipos de plaza
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_tiposplaza.add(add_tiposplaza, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_tiposplaza.add(remove_tiposplaza, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_tiposplaza.add(update_tiposplaza, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_listartplaza.add(new JLabel("Nom"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_listartplaza.add(new JLabel("Funcion"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_nom_tplaza[i] = new JTextField();
            campos_fun_tplaza[i] = new JTextField();
            campos_fun_tplaza[i].setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listartplaza.add(campos_nom_tplaza[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listartplaza.add(campos_fun_tplaza[i], gbc);
        }

        JPanel tiposplaza = new JPanel(new BorderLayout());
        tiposplaza.add(panel_tiposplaza, BorderLayout.NORTH);
        tiposplaza.add(panel_listartplaza, BorderLayout.CENTER);
        tabs.addTab("Tipos de Plaza", new JScrollPane(tiposplaza));

        // gbc plaza
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_botonesplaza.add(add_plaza, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_botonesplaza.add(remove_plaza, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_botonesplaza.add(update_plaza, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Codi"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Salari"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Informe"), gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Codiplaza"), gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        panel_listarplaza.add(new JLabel("Nomplaza"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_codi_pla[i] = new JTextField();
            campos_nom_pla[i] = new JTextField();
            campos_nom_pla[i].setColumns(25);
            campos_salari_pla[i] = new JTextField();
            campos_info_pla[i] = new JTextField();
            campos_info_pla[i].setColumns(100);
            campos_codiplaza_pla[i] = new JTextField();
            campos_nomplaza_pla[i] = new JTextField();
            campos_nomplaza_pla[i].setColumns(25);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_codi_pla[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_nom_pla[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_salari_pla[i], gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_info_pla[i], gbc);
            gbc.gridx = 4;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_codiplaza_pla[i], gbc);
            gbc.gridx = 5;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_nomplaza_pla[i], gbc);
        }

        JPanel plaza = new JPanel(new BorderLayout());
        plaza.add(panel_botonesplaza, BorderLayout.NORTH);
        plaza.add(panel_listarplaza, BorderLayout.CENTER);
        tabs.addTab("Plazas", new JScrollPane(plaza));

        // gbc empleado
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_botonesempleados.add(add_empleado, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_botonesempleados.add(remove_empleado, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_botonesempleados.add(update_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("NSS"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("Llinatges"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("Llinatges"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("Email"), gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        panel_listarempleados.add(new JLabel("IBAN"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_nss_emp[i] = new JTextField();
            campos_nom_emp[i] = new JTextField();
            campos_nom_emp[i].setColumns(25);
            campos_llin_emp[i] = new JTextField();
            campos_llin_emp[i].setColumns(25);
            campos_email_emp[i] = new JTextField();
            campos_email_emp[i].setColumns(25);
            campos_iban_emp[i] = new JTextField();
            campos_iban_emp[i].setColumns(25);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_nss_emp[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_nom_emp[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_llin_emp[i], gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_email_emp[i], gbc);
            gbc.gridx = 4;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_iban_emp[i], gbc);
        }

        JPanel empleados = new JPanel(new BorderLayout());
        empleados.add(panel_botonesempleados, BorderLayout.NORTH);
        empleados.add(panel_listarempleados, BorderLayout.CENTER);
        tabs.addTab("Empleados", new JScrollPane(empleados));

        // gbc nominas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_botonesnominas.add(add_nomina, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_botonesnominas.add(remove_nomina, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_botonesnominas.add(update_nomina, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("Nom"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("Funcion"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_id_nom[i] = new JTextField();
            campos_nom_nom[i] = new JTextField();
            campos_fun_nom[i] = new JTextField();
            campos_fun_nom[i].setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_id_nom[i], gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_nom_nom[i], gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_fun_nom[i], gbc);
        }

        JPanel nominas = new JPanel(new BorderLayout());
        nominas.add(panel_botonesnominas, BorderLayout.NORTH);
        nominas.add(panel_listarnominas, BorderLayout.CENTER);
        tabs.addTab("Nominas", new JScrollPane(nominas));

        // añadimos
        frame.add(tabs, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void crearBaseDeDatos() {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:MakuPlazas.db"); Statement stmt = con.createStatement()) {

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

    public static void add(String tabla) {
        String add_id = JOptionPane.showInputDialog(null, "ID a añadir de " + tabla + ":");
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
        // para 3 columnas genera: INSERT INTO empleados (nombre,apellido,salario) VALUES ('val1','val2','val3')
        StringBuilder sql = new StringBuilder("INSERT INTO " + tabla + " (");
        for (int i = 0; i < columnas.length; i++) {
            sql.append(columnas[i]);
            if (i < columnas.length - 1) {
                sql.append(",");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < columnas.length; i++) {
            // los valores van entre comillas simples para que sqlite los trate como texto
            sql.append("'").append(valores[i]).append("'");
            if (i < columnas.length - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        // ejecutamos el INSERT con el Statement
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql.toString());
            JOptionPane.showMessageDialog(null, "Registro añadido correctamente en " + tabla + ".");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de eliminar
    // primero pide el valor de la pk, despues ejecuta el delete y avisa si no existia
    public static void delete(String tabla, String pk) {
        String del_id = JOptionPane.showInputDialog(null, pk + " a eliminar de " + tabla + ":");
        // cuando ya se ha eliminado lo devuelve
        if (del_id == null) {
            return;
        }

        // construimos el sql con el id directamente incrustado
        String sql = "DELETE FROM " + tabla + " WHERE " + pk + " = " + del_id;

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            int del_filas = stmt.executeUpdate(sql);
            if (del_filas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente de " + tabla + ".");
            } else {
                // executeUpdate devuelve 0 si el ID no existia en la tabla
                JOptionPane.showMessageDialog(null, "No se encontro ningun registro con " + pk + " " + del_id + " en " + tabla,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar de " + tabla + ":\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo de select
    // hace un SELECT de todas las filas de la tabla y devuelve una lista de arrays
    // cada array tiene los valores de las columnas que le pasamos
    public static List<String[]> select(String tabla, String[] columnas) {

        // lista donde guardaremos cada fila como un array de strings
        List<String[]> lista = new ArrayList<>();

        // construimos el SQL dinamicamente:
        // para 2 columnas genera: SELECT nombre,apellido FROM empleados
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < columnas.length; i++) {
            sql.append(columnas[i]);
            if (i < columnas.length - 1) {
                sql.append(",");
            }
        }
        sql.append(" FROM ").append(tabla);

        try (Connection con = getConnection(); Statement stmt = con.createStatement(); // executeQuery para SELECT, devuelve un ResultSet con las filas
                 ResultSet rs = stmt.executeQuery(sql.toString())) {

            // recorremos las filas una a una con rs.next()
            while (rs.next()) {
                // por cada fila creamos un array con tantas posiciones como columnas
                String[] fila = new String[columnas.length];
                for (int i = 0; i < columnas.length; i++) {
                    // rs.getString("nombre_columna") saca el valor de esa columna en la fila actual
                    fila[i] = rs.getString(columnas[i]);
                }
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al hacer select de " + tabla + ": " + e.getMessage());
        }

        return lista;
    }

    // metodo de actualizar
    // funciona igual que insertar --> pide los nuevos valores para cada columna,
    // pide el id del registro a modificar, construye el update y despues lo ejecuta
    public static void update(String tabla, String[] columna, String[] celda) {

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
        // para 3 columnas genera: UPDATE empleados SET nombre='val1',apellido='val2',salario='val3' WHERE id=1
        StringBuilder sql = new StringBuilder("UPDATE " + tabla + " SET ");
        for (int i = 0; i < columna.length; i++) {
            // los valores van entre comillas simples para que sqlite los trate como texto
            sql.append(columna[i]).append("='").append(valores[i]).append("'");
            if (i < columna.length - 1) {
                sql.append(",");
            }
        }
        sql.append(" WHERE id=").append(upd_id);

        // ejecutamos el UPDATE con el Statement
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            int upd_celdas = stmt.executeUpdate(sql.toString());
            if (upd_celdas > 0) {
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
