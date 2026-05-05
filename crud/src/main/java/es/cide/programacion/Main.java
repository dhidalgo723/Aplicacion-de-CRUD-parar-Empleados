// makush
package es.cide.programacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Main {

    // objetos de cada tabla
    static BD bd = new BD();
    static TPlaza tplazaobj = new TPlaza();
    static Nomina nominaobj = new Nomina();
    static Empleados empleadosobj = new Empleados();
    static Plaza plazaobj = new Plaza();

    // numero de filas en la lista
    static int num_filas = 6;

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

        add_tiposplaza.setPreferredSize(new Dimension(add_tiposplaza.getPreferredSize().width, 50));
        remove_tiposplaza.setPreferredSize(new Dimension(remove_tiposplaza.getPreferredSize().width, 50));
        update_tiposplaza.setPreferredSize(new Dimension(update_tiposplaza.getPreferredSize().width, 50));

        // guardo los textfields en un array para leerlos despues
        JTextField[] campos_nom_tplaza = new JTextField[num_filas];
        JTextField[] campos_fun_tplaza = new JTextField[num_filas];

        // listeners de tipos de plaza
        add_tiposplaza.addActionListener(e -> tplazaobj.insertar());
        remove_tiposplaza.addActionListener(e -> tplazaobj.delete());
        update_tiposplaza.addActionListener(e -> tplazaobj.update());

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

        add_plaza.setPreferredSize(new Dimension(add_plaza.getPreferredSize().width, 50));
        remove_plaza.setPreferredSize(new Dimension(remove_plaza.getPreferredSize().width, 50));
        update_plaza.setPreferredSize(new Dimension(update_plaza.getPreferredSize().width, 50));

        // textfield de plazas
        JTextField[] campos_codi_pla = new JTextField[num_filas];
        JTextField[] campos_nom_pla = new JTextField[num_filas];
        JTextField[] campos_salari_pla = new JTextField[num_filas];
        JTextField[] campos_info_pla = new JTextField[num_filas];
        JTextField[] campos_codiplaza_pla = new JTextField[num_filas];
        JTextField[] campos_nomplaza_pla = new JTextField[num_filas];

        // listeners de plazas
        add_plaza.addActionListener(e -> plazaobj.insertar());
        remove_plaza.addActionListener(e -> plazaobj.delete());
        update_plaza.addActionListener(e -> plazaobj.update());

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

        add_empleado.setPreferredSize(new Dimension(add_empleado.getPreferredSize().width, 50));
        remove_empleado.setPreferredSize(new Dimension(remove_empleado.getPreferredSize().width, 50));
        update_empleado.setPreferredSize(new Dimension(update_empleado.getPreferredSize().width, 50));

        JTextField[] campos_nss_emp = new JTextField[num_filas];
        JTextField[] campos_nom_emp = new JTextField[num_filas];
        JTextField[] campos_llin_emp = new JTextField[num_filas];
        JTextField[] campos_email_emp = new JTextField[num_filas];
        JTextField[] campos_iban_emp = new JTextField[num_filas];

        // listeners de empleados
        add_empleado.addActionListener(e -> empleadosobj.insertar());
        remove_empleado.addActionListener(e -> empleadosobj.delete());
        update_empleado.addActionListener(e -> empleadosobj.update());

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

        add_nomina.setPreferredSize(new Dimension(add_nomina.getPreferredSize().width, 50));
        remove_nomina.setPreferredSize(new Dimension(remove_nomina.getPreferredSize().width, 50));
        update_nomina.setPreferredSize(new Dimension(update_nomina.getPreferredSize().width, 50));

        // textfields de nominas
        JTextField[] campos_id_nom = new JTextField[num_filas];
        JTextField[] campos_nom_nom = new JTextField[num_filas];
        JTextField[] campos_fun_nom = new JTextField[num_filas];

        // listeners de nominas
        add_nomina.addActionListener(e -> nominaobj.insertar());
        remove_nomina.addActionListener(e -> nominaobj.delete());
        update_nomina.addActionListener(e -> nominaobj.update());

        // botones de pasar y recargar pagina - uno por cada pestaña (un componente solo puede tener un padre)
        int[] page_tplaza = {0};
        int[] page_plaza = {0};
        int[] page_emp = {0};
        int[] page_nom = {0};

        JButton prev_tplaza = new JButton("⬅️");
        JButton recharge_tplaza = new JButton("🔁");
        JButton next_tplaza = new JButton("➡️");

        JButton prev_plaza = new JButton("⬅️");
        JButton recharge_plaza = new JButton("🔁");
        JButton next_plaza = new JButton("➡️");

        JButton prev_emp = new JButton("⬅️");
        JButton recharge_emp = new JButton("🔁");
        JButton next_emp = new JButton("➡️");

        JButton prev_nom = new JButton("⬅️");
        JButton recharge_nom = new JButton("🔁");
        JButton next_nom = new JButton("➡️");

        // 2d arrays y columnas para cada tabla
        JTextField[][] campos_tplaza_2d = {campos_nom_tplaza, campos_fun_tplaza};
        String[] cols_tplaza = {"NOM", "FUNCIO"};

        JTextField[][] campos_plaza_2d = {campos_codi_pla, campos_nom_pla, campos_salari_pla, campos_info_pla, campos_codiplaza_pla, campos_nomplaza_pla};
        String[] cols_plaza = {"CODI", "NOM", "SALARI", "INFORME_SUPERVISIO", "CODI_PLACA_SUPERVISORA", "NOM_TIPUS_PLACA"};

        JTextField[][] campos_emp_2d = {campos_nss_emp, campos_nom_emp, campos_llin_emp, campos_email_emp, campos_iban_emp};
        String[] cols_emp = {"NSS", "NOM", "LLINATGES", "EMAIL", "IBAN"};

        JTextField[][] campos_nom_2d = {campos_id_nom, campos_nom_nom, campos_fun_nom};
        String[] cols_nom = {"ID_NOMINA", "IBAN_PAGAMENT", "IMPORT"};

        // listeners de tipos de plaza
        recharge_tplaza.addActionListener(e -> {
            page_tplaza[0] = 0;
            tplazaobj.select(campos_tplaza_2d, page_tplaza[0], num_filas);
        });
        next_tplaza.addActionListener(e -> {
            page_tplaza[0]++;
            tplazaobj.select(campos_tplaza_2d, page_tplaza[0], num_filas);
        });
        prev_tplaza.addActionListener(e -> {
            if (page_tplaza[0] > 0) {
                page_tplaza[0]--;
                tplazaobj.select(campos_tplaza_2d, page_tplaza[0], num_filas);
            }
        });

        // listeners de plaza
        recharge_plaza.addActionListener(e -> {
            page_plaza[0] = 0;
            plazaobj.select(campos_plaza_2d, page_plaza[0], num_filas);
        });
        next_plaza.addActionListener(e -> {
            page_plaza[0]++;
            plazaobj.select(campos_plaza_2d, page_plaza[0], num_filas);
        });
        prev_plaza.addActionListener(e -> {
            if (page_plaza[0] > 0) {
                page_plaza[0]--;
                plazaobj.select(campos_plaza_2d, page_plaza[0], num_filas);
            }
        });

        // listeners de empleados
        recharge_emp.addActionListener(e -> {
            page_emp[0] = 0;
            empleadosobj.select(campos_emp_2d, page_emp[0], num_filas);
        });
        next_emp.addActionListener(e -> {
            page_emp[0]++;
            empleadosobj.select(campos_emp_2d, page_emp[0], num_filas);
        });
        prev_emp.addActionListener(e -> {
            if (page_emp[0] > 0) {
                page_emp[0]--;
                empleadosobj.select(campos_emp_2d, page_emp[0], num_filas);
            }
        });

        // listeners de nominas
        recharge_nom.addActionListener(e -> {
            page_nom[0] = 0;
            nominaobj.select(campos_nom_2d, page_nom[0], num_filas);
        });
        next_nom.addActionListener(e -> {
            page_nom[0]++;
            nominaobj.select(campos_nom_2d, page_nom[0], num_filas);
        });
        prev_nom.addActionListener(e -> {
            if (page_nom[0] > 0) {
                page_nom[0]--;
                nominaobj.select(campos_nom_2d, page_nom[0], num_filas);
            }
        });

        // paneles de navegacion
        JPanel pageset_tplaza = new JPanel();
        pageset_tplaza.add(prev_tplaza);
        pageset_tplaza.add(recharge_tplaza);
        pageset_tplaza.add(next_tplaza);

        JPanel pageset_plaza = new JPanel();
        pageset_plaza.add(prev_plaza);
        pageset_plaza.add(recharge_plaza);
        pageset_plaza.add(next_plaza);

        JPanel pageset_emp = new JPanel();
        pageset_emp.add(prev_emp);
        pageset_emp.add(recharge_emp);
        pageset_emp.add(next_emp);

        JPanel pageset_nom = new JPanel();
        pageset_nom.add(prev_nom);
        pageset_nom.add(recharge_nom);
        pageset_nom.add(next_nom);

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
        tiposplaza.add(pageset_tplaza, BorderLayout.SOUTH);
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

        // bucle pa ir creando los textfield
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
        plaza.add(pageset_plaza, BorderLayout.SOUTH);
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

        // bucle pa ir creando los textfield
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
        empleados.add(pageset_emp, BorderLayout.SOUTH);
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

        // bucle pa ir creando los textfield
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
        nominas.add(pageset_nom, BorderLayout.SOUTH);
        tabs.addTab("Nominas", new JScrollPane(nominas));

        // añadimos
        frame.add(tabs, BorderLayout.CENTER);
        bd.create(); // metodo para crear la base de datos si no esta creada

        // cargamos los datos iniciales de la base de datos en los textfields
        tplazaobj.select(campos_tplaza_2d, page_tplaza[0], num_filas);
        plazaobj.select(campos_plaza_2d, page_plaza[0], num_filas);
        empleadosobj.select(campos_emp_2d, page_emp[0], num_filas);
        nominaobj.select(campos_nom_2d, page_nom[0], num_filas);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
