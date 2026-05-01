package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Plaza extends BD {

    // variables para la base de datos
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // para tener la conexion en la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private String tabla = "PLACA";
    private String[] columnas = {"CODI", "NOM", "SALARI", "INFORME_SUPERVISIO", "CODI_PLACA_SUPERVISORA", "NOM_TIPUS_PLACA"};
    private String[] registros = {"Código de la plaza:", "Nombre de la plaza:", "Salario de la plaza:", "Información de la plaza:", "Código de la plaza supervisora:", "Tipo de plaza:"};
    private String pk = "CODI";

    public Plaza(String tabla, String[] columnas, String[] registros) {
        super(tabla, columnas, registros);
        this.tabla = tabla;
        this.columnas = columnas;
        this.registros = registros;
    }

    public Plaza() {

    }

    public String getTabla() { return tabla; }
    public void setTabla(String tabla) { this.tabla = tabla; }

    public String[] getColumnas() { return columnas; }
    public void setColumnas(String[] columnas) { this.columnas = columnas; }

    public String[] getRegistros() { return registros; }
    public void setRegistros(String[] registros) { this.registros = registros; }

    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    public void insertar() {
        BD.insertar(tabla, columnas, registros);
    }

    public void delete() {
        BD.delete(tabla, pk);
    }

    @Override
    public void update() {

    }

    @Override
    public void select(JTextField[][] campos, int page, int numFilas) {
        BD.select(tabla, columnas, campos, page, numFilas);
    }

}
