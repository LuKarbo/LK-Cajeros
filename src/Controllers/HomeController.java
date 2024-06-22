package Controllers;

import DB.DBCajero;
import Facade.Type_Data_Facade;
import Models.Registro_MovimientoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HomeController {
    private Connection db;

    public HomeController() throws SQLException {
        this.db = DBCajero.getDB();
    }

    public ArrayList<Registro_MovimientoModel> getMovimientosByUSER(int id_user) {
        ArrayList<Registro_MovimientoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_movimiento WHERE id_cliente = ? ";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, id_user);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registro_MovimientoModel rmm = new Registro_MovimientoModel(
                            rs.getInt("id_registro_movimiento"),
                            rs.getInt("id_cajero"),
                            rs.getInt("id_typeMovimiento"),
                            rs.getInt("id_transaccion"),
                            rs.getInt("id_cliente"),
                            rs.getInt("monto"),
                            rs.getDate("fecha_y_hora")
                    );
                    lista.add(rmm);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Object[][] getDataFromList(ArrayList<Registro_MovimientoModel> lista){
        return Type_Data_Facade.getDataFromList(lista,db);
    }

    public String getType(int id){
        return Type_Data_Facade.getType(id,db);
    }

}
