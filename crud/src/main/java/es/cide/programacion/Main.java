package es.cide.programacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

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

    // inicializo las clases y objetos
    static BD bd = new BD();
    static TPlaza tplazaobj = new TPlaza();
    static Nomina nominaobj = new Nomina();
    static Empleados empleadosobj = new Empleados();
    static Plaza plazaobj = new Plaza();
    static Ocupa ocupaobj = new Ocupa();

    // filas q se muestran a la vez en la tabla de cada pestaña
    static int num_filas = 6;

    public static void main(String[] args) {
        // look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // frame
        JFrame frame = new JFrame("Maku-SQL");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // pestañas para cada tabla de la base de datos
        JTabbedPane tabs = new JTabbedPane();

        // tipus plaza
        JPanel panel_tiposplaza = new JPanel(new GridBagLayout());
        panel_tiposplaza.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listartplaza = new JPanel(new GridBagLayout());
        panel_listartplaza.setBorder(BorderFactory.createTitledBorder("Listar"));

        // botones para tipos de plaza
        JButton add_tiposplaza = new JButton("Crear Tipo de Plaza");
        JButton remove_tiposplaza = new JButton("Eliminar Tipo de Plaza");
        JButton update_tiposplaza = new JButton("Actualizar Tipo de Plaza");

        // dimension de los botones
        add_tiposplaza.setPreferredSize(new Dimension(add_tiposplaza.getPreferredSize().width, 50));
        remove_tiposplaza.setPreferredSize(new Dimension(remove_tiposplaza.getPreferredSize().width, 50));
        update_tiposplaza.setPreferredSize(new Dimension(update_tiposplaza.getPreferredSize().width, 50));

        // arraylist para meter la informacion
        ArrayList<JTextField> campos_nom_tplaza = new ArrayList<>();
        ArrayList<JTextField> campos_fun_tplaza = new ArrayList<>();

        // le añadimos las funciones a los botones
        add_tiposplaza.addActionListener(e -> tplazaobj.insertar());
        remove_tiposplaza.addActionListener(e -> tplazaobj.delete());
        update_tiposplaza.addActionListener(e -> tplazaobj.update());

        // hacemos lo mismo con la tabla de plaza
        JPanel panel_plaza = new JPanel(new GridBagLayout());

        JPanel panel_botonesplaza = new JPanel(new GridBagLayout());
        panel_botonesplaza.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarplaza = new JPanel(new GridBagLayout());
        panel_listarplaza.setBorder(BorderFactory.createTitledBorder("Listar"));

        JButton add_plaza = new JButton("Crear Plaza");
        JButton remove_plaza = new JButton("Eliminar Plaza");
        JButton update_plaza = new JButton("Actualizar Plaza");

        add_plaza.setPreferredSize(new Dimension(add_plaza.getPreferredSize().width, 50));
        remove_plaza.setPreferredSize(new Dimension(remove_plaza.getPreferredSize().width, 50));
        update_plaza.setPreferredSize(new Dimension(update_plaza.getPreferredSize().width, 50));

        ArrayList<JTextField> campos_codi_pla = new ArrayList<>();
        ArrayList<JTextField> campos_nom_pla = new ArrayList<>();
        ArrayList<JTextField> campos_salari_pla = new ArrayList<>();
        ArrayList<JTextField> campos_info_pla = new ArrayList<>();
        ArrayList<JTextField> campos_codiplaza_pla = new ArrayList<>();
        ArrayList<JTextField> campos_nomplaza_pla = new ArrayList<>();

        add_plaza.addActionListener(e -> plazaobj.insertar());
        remove_plaza.addActionListener(e -> plazaobj.delete());
        update_plaza.addActionListener(e -> plazaobj.update());

        // lo mismo para empleados
        JPanel panel_empleados = new JPanel(new GridBagLayout());

        JPanel panel_botonesempleados = new JPanel(new GridBagLayout());
        panel_botonesempleados.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarempleados = new JPanel(new GridBagLayout());
        panel_listarempleados.setBorder(BorderFactory.createTitledBorder("Listar"));

        JButton add_empleado = new JButton("Crear Empleado");
        JButton remove_empleado = new JButton("Eliminar Empleado");
        JButton update_empleado = new JButton("Actualizar Empleado");

        add_empleado.setPreferredSize(new Dimension(add_empleado.getPreferredSize().width, 50));
        remove_empleado.setPreferredSize(new Dimension(remove_empleado.getPreferredSize().width, 50));
        update_empleado.setPreferredSize(new Dimension(update_empleado.getPreferredSize().width, 50));

        ArrayList<JTextField> campos_nss_emp = new ArrayList<>();
        ArrayList<JTextField> campos_nom_emp = new ArrayList<>();
        ArrayList<JTextField> campos_llin_emp = new ArrayList<>();
        ArrayList<JTextField> campos_email_emp = new ArrayList<>();
        ArrayList<JTextField> campos_iban_emp = new ArrayList<>();

        add_empleado.addActionListener(e -> empleadosobj.insertar());
        remove_empleado.addActionListener(e -> empleadosobj.delete());
        update_empleado.addActionListener(e -> empleadosobj.update());

        // lo mismo pa nominas
        JPanel panel_nominas = new JPanel(new GridBagLayout());
        JPanel panel_botonesnominas = new JPanel(new GridBagLayout());
        panel_botonesnominas.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarnominas = new JPanel(new GridBagLayout());
        panel_listarnominas.setBorder(BorderFactory.createTitledBorder("Listar"));

        JButton add_nomina = new JButton("Crear Nomina");
        JButton remove_nomina = new JButton("Eliminar Nomina");
        JButton update_nomina = new JButton("Actualizar Nomina");

        add_nomina.setPreferredSize(new Dimension(add_nomina.getPreferredSize().width, 50));
        remove_nomina.setPreferredSize(new Dimension(remove_nomina.getPreferredSize().width, 50));
        update_nomina.setPreferredSize(new Dimension(update_nomina.getPreferredSize().width, 50));

        ArrayList<JTextField> campos_id_nom = new ArrayList<>();
        ArrayList<JTextField> campos_iban_nom = new ArrayList<>();
        ArrayList<JTextField> campos_import_nom = new ArrayList<>();
        ArrayList<JTextField> campos_nss_nom = new ArrayList<>();
        ArrayList<JTextField> campos_codiplaza_nom = new ArrayList<>();

        add_nomina.addActionListener(e -> nominaobj.insertar());
        remove_nomina.addActionListener(e -> nominaobj.delete());
        update_nomina.addActionListener(e -> nominaobj.update());

        // lo mismo pa la tabla de ocupa
        JPanel panel_ocupa = new JPanel(new GridBagLayout());
        JPanel panel_botonesocupa = new JPanel(new GridBagLayout());
        panel_botonesocupa.setBorder(BorderFactory.createTitledBorder("Botones"));

        JPanel panel_listarocupa = new JPanel(new GridBagLayout());
        panel_listarocupa.setBorder(BorderFactory.createTitledBorder("Listar"));

        JButton add_ocupa = new JButton("Crear Ocupa");
        JButton remove_ocupa = new JButton("Eliminar Ocupa");
        JButton update_ocupa = new JButton("Actualizar Ocupa");

        add_ocupa.setPreferredSize(new Dimension(add_ocupa.getPreferredSize().width, 50));
        remove_ocupa.setPreferredSize(new Dimension(remove_ocupa.getPreferredSize().width, 50));
        update_ocupa.setPreferredSize(new Dimension(update_ocupa.getPreferredSize().width, 50));

        ArrayList<JTextField> campos_nss_ocupa = new ArrayList<>();
        ArrayList<JTextField> campos_codiplaza_ocupa = new ArrayList<>();
        ArrayList<JTextField> campos_inicio_ocupa = new ArrayList<>();
        ArrayList<JTextField> campos_fin_ocupa = new ArrayList<>();

        add_ocupa.addActionListener(e -> ocupaobj.insertar());
        remove_ocupa.addActionListener(e -> ocupaobj.delete());
        update_ocupa.addActionListener(e -> ocupaobj.update());

        // botones de abajo
        // cada pestaña tiene su propio contador de pagina en un array de 1 elemento
        // un array de ints pq las lambdas solo pueden capturar variables efectivamente finales
        int[] page_tplaza = {0};
        int[] page_plaza = {0};
        int[] page_emp = {0};
        int[] page_nom = {0};
        int[] page_ocupa = {0};

        // los botones de cada tabla
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

        JButton prev_ocupa = new JButton("⬅️");
        JButton recharge_ocupa = new JButton("🔁");
        JButton next_ocupa = new JButton("➡️");

        // los diccionarios con los campos para saber donde poner cada cosa del select 
        LinkedHashMap<String, ArrayList<JTextField>> campos_tplaza = new LinkedHashMap<>();
        campos_tplaza.put("NOM", campos_nom_tplaza);
        campos_tplaza.put("FUNCIO", campos_fun_tplaza);
        tplazaobj.setCampos(campos_tplaza);

        // lo mismo pero con los treemaps
        TreeMap<String, ArrayList<JTextField>> campos_plaza = new TreeMap<>();
        campos_plaza.put("CODI", campos_codi_pla);
        campos_plaza.put("NOM", campos_nom_pla);
        campos_plaza.put("SALARI", campos_salari_pla);
        campos_plaza.put("INFORME_SUPERVISIO", campos_info_pla);
        campos_plaza.put("CODI_PLACA_SUPERVISORA", campos_codiplaza_pla);
        campos_plaza.put("NOM_TIPUS_PLACA", campos_nomplaza_pla);
        plazaobj.setCampos(campos_plaza);

        TreeMap<String, ArrayList<JTextField>> campos_emp = new TreeMap<>();
        campos_emp.put("NSS", campos_nss_emp);
        campos_emp.put("NOM", campos_nom_emp);
        campos_emp.put("LLINATGES", campos_llin_emp);
        campos_emp.put("EMAIL", campos_email_emp);
        campos_emp.put("IBAN", campos_iban_emp);
        empleadosobj.setCampos(campos_emp);

        TreeMap<String, ArrayList<JTextField>> campos_nom = new TreeMap<>();
        campos_nom.put("ID_NOMINA", campos_id_nom);
        campos_nom.put("IBAN_PAGAMENT", campos_iban_nom);
        campos_nom.put("IMPORT", campos_import_nom);
        campos_nom.put("NSS_EMPLEAT", campos_nss_nom);
        campos_nom.put("CODI_PLACA", campos_codiplaza_nom);
        nominaobj.setCampos(campos_nom);

        TreeMap<String, ArrayList<JTextField>> campos_ocupa = new TreeMap<>();
        campos_ocupa.put("NSS_EMPLEAT", campos_nss_ocupa);
        campos_ocupa.put("CODI_PLACA", campos_codiplaza_ocupa);
        campos_ocupa.put("DATA_INICI", campos_inicio_ocupa);
        campos_ocupa.put("DATA_FI", campos_fin_ocupa);
        ocupaobj.setCampos(campos_ocupa);

        // añadimos la funcion select con sus respectivas cosas en los parametros
        recharge_tplaza.addActionListener(e -> {
            page_tplaza[0] = 0;
            tplazaobj.select(page_tplaza[0], num_filas);
        });
        next_tplaza.addActionListener(e -> {
            page_tplaza[0]++;
            tplazaobj.select(page_tplaza[0], num_filas);
        });
        prev_tplaza.addActionListener(e -> {
            if (page_tplaza[0] > 0) {
                page_tplaza[0]--;
                tplazaobj.select(page_tplaza[0], num_filas);
            }
        });

        recharge_plaza.addActionListener(e -> {
            page_plaza[0] = 0;
            plazaobj.select(page_plaza[0], num_filas);
        });
        next_plaza.addActionListener(e -> {
            page_plaza[0]++;
            plazaobj.select(page_plaza[0], num_filas);
        });
        prev_plaza.addActionListener(e -> {
            if (page_plaza[0] > 0) {
                page_plaza[0]--;
                plazaobj.select(page_plaza[0], num_filas);
            }
        });

        recharge_emp.addActionListener(e -> {
            page_emp[0] = 0;
            empleadosobj.select(page_emp[0], num_filas);
        });
        next_emp.addActionListener(e -> {
            page_emp[0]++;
            empleadosobj.select(page_emp[0], num_filas);
        });
        prev_emp.addActionListener(e -> {
            if (page_emp[0] > 0) {
                page_emp[0]--;
                empleadosobj.select(page_emp[0], num_filas);
            }
        });

        recharge_nom.addActionListener(e -> {
            page_nom[0] = 0;
            nominaobj.select(page_nom[0], num_filas);
        });
        next_nom.addActionListener(e -> {
            page_nom[0]++;
            nominaobj.select(page_nom[0], num_filas);
        });
        prev_nom.addActionListener(e -> {
            if (page_nom[0] > 0) {
                page_nom[0]--;
                nominaobj.select(page_nom[0], num_filas);
            }
        });

        recharge_ocupa.addActionListener(e -> {
            page_ocupa[0] = 0;
            ocupaobj.select(page_ocupa[0], num_filas);
        });
        next_ocupa.addActionListener(e -> {
            page_ocupa[0]++;
            ocupaobj.select(page_ocupa[0], num_filas);
        });
        prev_ocupa.addActionListener(e -> {
            if (page_ocupa[0] > 0) {
                page_ocupa[0]--;
                ocupaobj.select(page_ocupa[0], num_filas);
            }
        });

        // panel de abajo para poner los botones de abajo
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

        JPanel pageset_ocupa = new JPanel();
        pageset_ocupa.add(prev_ocupa);
        pageset_ocupa.add(recharge_ocupa);
        pageset_ocupa.add(next_ocupa);

        // apartado gráfico
        // grafico de tipus plaza
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1.0;

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
            campos_nom_tplaza.add(new JTextField());
            campos_fun_tplaza.add(new JTextField());
            campos_fun_tplaza.get(i).setColumns(20);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listartplaza.add(campos_nom_tplaza.get(i), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listartplaza.add(campos_fun_tplaza.get(i), gbc);
        }

        JPanel tiposplaza = new JPanel(new BorderLayout());
        tiposplaza.add(panel_tiposplaza, BorderLayout.NORTH);
        tiposplaza.add(panel_listartplaza, BorderLayout.CENTER);
        tiposplaza.add(pageset_tplaza, BorderLayout.SOUTH);
        tabs.addTab("Tipos de Plaza", new JScrollPane(tiposplaza));

        // grafico plaza
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
            campos_codi_pla.add(new JTextField());
            campos_codi_pla.get(i).setColumns(5);
            campos_nom_pla.add(new JTextField());
            campos_nom_pla.get(i).setColumns(15);
            campos_salari_pla.add(new JTextField());
            campos_salari_pla.get(i).setColumns(6);
            campos_info_pla.add(new JTextField());
            campos_info_pla.get(i).setColumns(25);
            campos_codiplaza_pla.add(new JTextField());
            campos_codiplaza_pla.get(i).setColumns(5);
            campos_nomplaza_pla.add(new JTextField());
            campos_nomplaza_pla.get(i).setColumns(15);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_codi_pla.get(i), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_nom_pla.get(i), gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_salari_pla.get(i), gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_info_pla.get(i), gbc);
            gbc.gridx = 4;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_codiplaza_pla.get(i), gbc);
            gbc.gridx = 5;
            gbc.gridy = 3 + i;
            panel_listarplaza.add(campos_nomplaza_pla.get(i), gbc);
        }

        JPanel plaza = new JPanel(new BorderLayout());
        plaza.add(panel_botonesplaza, BorderLayout.NORTH);
        plaza.add(panel_listarplaza, BorderLayout.CENTER);
        plaza.add(pageset_plaza, BorderLayout.SOUTH);
        tabs.addTab("Plazas", new JScrollPane(plaza));

        // grafico de empleados
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
            campos_nss_emp.add(new JTextField());
            campos_nss_emp.get(i).setColumns(8);
            campos_nom_emp.add(new JTextField());
            campos_nom_emp.get(i).setColumns(12);
            campos_llin_emp.add(new JTextField());
            campos_llin_emp.get(i).setColumns(15);
            campos_email_emp.add(new JTextField());
            campos_email_emp.get(i).setColumns(18);
            campos_iban_emp.add(new JTextField());
            campos_iban_emp.get(i).setColumns(18);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_nss_emp.get(i), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_nom_emp.get(i), gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_llin_emp.get(i), gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_email_emp.get(i), gbc);
            gbc.gridx = 4;
            gbc.gridy = 3 + i;
            panel_listarempleados.add(campos_iban_emp.get(i), gbc);
        }

        JPanel empleados = new JPanel(new BorderLayout());
        empleados.add(panel_botonesempleados, BorderLayout.NORTH);
        empleados.add(panel_listarempleados, BorderLayout.CENTER);
        empleados.add(pageset_emp, BorderLayout.SOUTH);
        tabs.addTab("Empleados", new JScrollPane(empleados));

        // grafico nominas
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
        panel_listarnominas.add(new JLabel("IBAN"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("Importe"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("NSS"), gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        panel_listarnominas.add(new JLabel("Plaza"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_id_nom.add(new JTextField());
            campos_id_nom.get(i).setColumns(5);
            campos_iban_nom.add(new JTextField());
            campos_iban_nom.get(i).setColumns(18);
            campos_import_nom.add(new JTextField());
            campos_import_nom.get(i).setColumns(6);
            campos_nss_nom.add(new JTextField());
            campos_nss_nom.get(i).setColumns(8);
            campos_codiplaza_nom.add(new JTextField());
            campos_codiplaza_nom.get(i).setColumns(5);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_id_nom.get(i), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_iban_nom.get(i), gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_import_nom.get(i), gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_nss_nom.get(i), gbc);
            gbc.gridx = 4;
            gbc.gridy = 3 + i;
            panel_listarnominas.add(campos_codiplaza_nom.get(i), gbc);
        }

        JPanel nominas = new JPanel(new BorderLayout());
        nominas.add(panel_botonesnominas, BorderLayout.NORTH);
        nominas.add(panel_listarnominas, BorderLayout.CENTER);
        nominas.add(pageset_nom, BorderLayout.SOUTH);
        tabs.addTab("Nominas", new JScrollPane(nominas));

        // grafico nomina
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_botonesocupa.add(add_ocupa, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_botonesocupa.add(remove_ocupa, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_botonesocupa.add(update_ocupa, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_listarocupa.add(new JLabel("NSS"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_listarocupa.add(new JLabel("Plaza"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_listarocupa.add(new JLabel("Inicio"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_listarocupa.add(new JLabel("Fin"), gbc);

        for (int i = 0; i < num_filas; i++) {
            campos_nss_ocupa.add(new JTextField());
            campos_nss_ocupa.get(i).setColumns(8);
            campos_codiplaza_ocupa.add(new JTextField());
            campos_codiplaza_ocupa.get(i).setColumns(5);
            campos_inicio_ocupa.add(new JTextField());
            campos_inicio_ocupa.get(i).setColumns(10);
            campos_fin_ocupa.add(new JTextField());
            campos_fin_ocupa.get(i).setColumns(10);

            gbc.gridx = 0;
            gbc.gridy = 3 + i;
            panel_listarocupa.add(campos_nss_ocupa.get(i), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3 + i;
            panel_listarocupa.add(campos_codiplaza_ocupa.get(i), gbc);
            gbc.gridx = 2;
            gbc.gridy = 3 + i;
            panel_listarocupa.add(campos_inicio_ocupa.get(i), gbc);
            gbc.gridx = 3;
            gbc.gridy = 3 + i;
            panel_listarocupa.add(campos_fin_ocupa.get(i), gbc);
        }

        JPanel ocupa = new JPanel(new BorderLayout());
        ocupa.add(panel_botonesocupa, BorderLayout.NORTH);
        ocupa.add(panel_listarocupa, BorderLayout.CENTER);
        ocupa.add(pageset_ocupa, BorderLayout.SOUTH);
        tabs.addTab("Ocupa", new JScrollPane(ocupa));

        frame.add(tabs, BorderLayout.CENTER);
        bd.create();

        tplazaobj.select(page_tplaza[0], num_filas);
        plazaobj.select(page_plaza[0], num_filas);
        empleadosobj.select(page_emp[0], num_filas);
        nominaobj.select(page_nom[0], num_filas);
        ocupaobj.select(page_ocupa[0], num_filas);

        frame.setLocationRelativeTo(null); // centra la ventana
        frame.setVisible(true); // lo hacce visible
    }
}
