package es.cide.programacion;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BDArrayList {

    public List<Alumne> obtenirAlumnes() {
        List<Alumne> llista = new ArrayList<>();
        String sql = "SELECT id, nom FROM alumnes";

        try (Connection con = DriveManager.getConnection("jdbc.sqlite:escola.db"); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Alumne a = new Alumne(rs.getInt("id"), rs.getString("nom"));
                llista.add(a);
            }
        } catch (SQLClientInfoException e) {
            e.printStackTrace();
        }

        return llista;
    }

    public void guardarAlumnes(List<Alumne> llista) {
        String sql = "INSERT INTO alumnes (id, nom) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc.sqlite:escola.db"); PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (Alumne a : llista) {
                pstmt.setInt(1, a.getId());
                pstmt.setString(2, a.getNom());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


