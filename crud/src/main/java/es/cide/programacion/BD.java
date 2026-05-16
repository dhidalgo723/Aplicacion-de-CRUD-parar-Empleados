package es.cide.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {

    // ruta del archivo SQLite
    private static final String URL = "jdbc:sqlite:MakuPlazas.db";

    // abre y devuelve una conexion a la base de datos
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public BD() {

    }

    // crea la tabla en la base de datos si no existe
    public static void create() {
        try (Connection con = DriverManager.getConnection(URL); Statement stmt = con.createStatement()) {

            // tabla de tipos de plaza
            String sql = "CREATE TABLE IF NOT EXISTS TIPUS_PLACA (\r\n"
                    + "    NOM VARCHAR(25) PRIMARY KEY,\r\n"
                    + "    FUNCIO VARCHAR(200) NOT NULL\r\n"
                    + ");\r\n";
            stmt.executeUpdate(sql);

            // tabla de plazas
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

            // tabla de empleados
            sql = "CREATE TABLE IF NOT EXISTS EMPLEAT (\r\n"
                    + "    NSS INTEGER PRIMARY KEY,\r\n"
                    + "    NOM VARCHAR(25) NOT NULL,\r\n"
                    + "    LLINATGES VARCHAR(25) NOT NULL,\r\n"
                    + "    EMAIL VARCHAR(25),\r\n"
                    + "    IBAN VARCHAR(25) UNIQUE NOT NULL CHECK (IBAN LIKE 'ES%')\r\n"
                    + ");";
            stmt.executeUpdate(sql);

            // tabla de ocupaciones
            sql = "CREATE TABLE IF NOT EXISTS OCUPA (\r\n"
                    + "    NSS_EMPLEAT INTEGER NOT NULL,\r\n"
                    + "    CODI_PLACA INTEGER NOT NULL,\r\n"
                    + "    DATA_INICI DATE NOT NULL,\r\n"
                    + "    DATA_FI DATE,\r\n"
                    + "    PRIMARY KEY (NSS_EMPLEAT, CODI_PLACA),\r\n"
                    + "    FOREIGN KEY (NSS_EMPLEAT) REFERENCES EMPLEAT (NSS),\r\n"
                    + "    FOREIGN KEY (CODI_PLACA) REFERENCES PLACA (CODI)\r\n"
                    + ");\r\n";
            stmt.executeUpdate(sql);

            // tabla de nominas
            sql = "CREATE TABLE IF NOT EXISTS NOMINA (\r\n"
                    + "    ID_NOMINA INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
                    + "    IBAN_PAGAMENT VARCHAR(25) UNIQUE NOT NULL CHECK (IBAN LIKE 'ES%') NOT NULL,\r\n"
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
}
