package Controllers;

import DB.DBCajero;
import Facade.CajeroFacade;
import Models.CajeroModel;
import Models.Registro_MovimientoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CajerosEditController {
    private Connection db;

    public CajerosEditController() throws SQLException {
        this.db = DBCajero.getDB();
    }

    public ArrayList<CajeroModel> getAllCajeros(){
        return CajeroFacade.getAllCajeros(db);
    }

    public void saveChange(int id, String status, double monto){
        boolean statusBool = false;
        if(status == "Activo"){
            statusBool = true;
        }

        String sql = "UPDATE cajero SET status = ?, cantidad_dinero = ? WHERE id_cajero = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setBoolean(1, statusBool);
            stmt.setDouble(2, monto);
            stmt.setInt(3, id);

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                db.commit();
                System.out.println("Good");
            } else {
                System.out.println("Bad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void create(){
        String sql = "INSERT INTO cajero (status, cantidad_dinero) VALUES (?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setBoolean(1, false);
            stmt.setDouble(2, 0);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                db.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


