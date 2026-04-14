package es.cide.programacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            // aplica el look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // frame principal
        JFrame frame = new JFrame("Calculadora");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel panel = new JPanel();

        // Usamos GridBagLayout que permite un control más preciso de la posición y
        // tamaño de los componentes
        panel.setLayout(new GridBagLayout());
        // GridBagConstraints define cómo se posicionará cada componente en el
        // GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // fill = HORIZONTAL hace que los componentes se estiren horizontalmente para
        // llenar el espacio
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Insets define los márgenes alrededor de cada componente (arriba, izquierda,
        // abajo, derecha)
        gbc.insets = new Insets(5, 10, 5, 10);

        // botones con las acciones
        JButton add_empleado = new JButton("Crear Empleado");
        JButton add_plaza = new JButton("Crear Plaza");
        JButton add_nomina = new JButton("Crear Nómina");

        JButton remove_empleado = new JButton("Eliminar Empleado");
        JButton remove_plaza = new JButton("Eliminar Plaza");
        JButton remove_nomina = new JButton("Eliminar Nómina");

        JButton read_empleado = new JButton("Listar Empleados");
        JButton read_plaza = new JButton("Listar Plazas");
        JButton read_nomina = new JButton("Listar Nóminas");

        JButton update_empleado = new JButton("Actualizar Empleado");
        JButton update_plaza = new JButton("Actualizar Plaza");
        JButton update_nomina = new JButton("Actualizar Nómina");

        ArrayList<String> list_empleado = new ArrayList<>();
        ArrayList<String> list_plaza = new ArrayList<>();
        ArrayList<String> list_nomina = new ArrayList<>();

        JTextArea textarea_empleados = new JTextArea();
        JScrollPane scroll_empleados = new JScrollPane(textarea_empleados);

        JTextArea textarea_plazas = new JTextArea();
        JScrollPane scroll_plazas = new JScrollPane(textarea_plazas);

        JTextArea textarea_nominas = new JTextArea();
        JScrollPane scroll_nominas = new JScrollPane(textarea_nominas);

        frame.add(panel);

        // fila 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(new Label("Empleados"), gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 1; // Fila 1
        gbc.gridwidth = 1; // Ocupa 1 columna
        gbc.weightx = 0.3; // 30% del espacio horizontal
        panel.add(add_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(remove_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(read_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(update_empleado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(scroll_empleados, gbc);

        // fila 1
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(new Label("Plazas"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(add_plaza, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(remove_plaza, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(read_plaza, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(update_plaza, gbc);

        // fila 2
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(new Label("Nóminas"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(add_nomina, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(remove_nomina, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(read_nomina, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(update_nomina, gbc);

        // lo centra en la pantalla
        frame.setLocationRelativeTo(null);
        // hace que se pueda ver
        frame.setVisible(true);
    }
}
