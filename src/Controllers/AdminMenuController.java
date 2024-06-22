package Controllers;

import DB.DBCajero;
import Facade.CajeroFacade;
import Facade.Type_Data_Facade;
import Models.CajeroModel;
import Models.Registro_MovimientoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdminMenuController {
    private Connection db;

    public AdminMenuController() throws SQLException {
        this.db = DBCajero.getDB();
    }

    public ArrayList<Registro_MovimientoModel> getAllMovimientos() {
        ArrayList<Registro_MovimientoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_movimiento";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
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

    public ArrayList<CajeroModel> getAllCajeros(){
        return CajeroFacade.getAllCajeros(db);
    }

    public Object[][] getDataFromList_Cajero(ArrayList<CajeroModel> lista){
        Object[][] datos = new Object[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            CajeroModel cm = lista.get(i);
            datos[i][0] = cm.getId_cajero();
            datos[i][1] = cm.getCantidad_dinero();
            datos[i][2] = cm.getStatus();
        }

        return datos;
    }

}
