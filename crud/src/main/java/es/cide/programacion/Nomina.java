package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Nomina extends BD {

    // variables para la base de datos
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // para tener la conexion en la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    //variables
    private String tabla = "NOMINA";
    private String[] columnas = {"ID_NOMINA", "IBAN_PAGAMENT", "IMPORT", "NSS_EMPLEAT", "CODI_PLACA"};
    private String[] registros = {"ID de la nómina", "IBAN de pago", "Importe", "NSS del empleado", "Código de la plaza"};
    private String pk = "ID_NOMINA";

    public Nomina(String tabla, String[] columnas, String[] registros) {
        super(tabla, columnas, registros);
        this.tabla = tabla;
        this.columnas = columnas;
        this.registros = registros;
    }

    public Nomina() {

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
