package Facade;

import Models.CajeroModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CajeroFacade {
    public static ArrayList<CajeroModel> getAllCajeros(Connection db){
        ArrayList<CajeroModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM cajero";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CajeroModel rmm = new CajeroModel(
                            rs.getInt("id_cajero"),
                            rs.getInt("cantidad_dinero"),
                            rs.getBoolean("status")
                    );
                    lista.add(rmm);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
