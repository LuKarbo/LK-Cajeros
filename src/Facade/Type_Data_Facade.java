package Facade;

import Models.Registro_MovimientoModel;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Type_Data_Facade {
    public static String getType(int id, Connection db){
        String sql = "Select nombre From typemovimiento Where id_typeMovimiento = ?";
        String name = "";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static Object[][] getDataFromList(ArrayList<Registro_MovimientoModel> lista,Connection db){
        Object[][] datos = new Object[lista.size()][7];

        for (int i = 0; i < lista.size(); i++) {
            Registro_MovimientoModel rmm = lista.get(i);
            datos[i][0] = rmm.getId_registro_movimiento();
            datos[i][1] = getType(rmm.getId_typeMovimiento(),db);
            datos[i][2] = rmm.getMonto();
            if(rmm.getId_transaccion() != null && rmm.getId_transaccion() >= 1){
                datos[i][3] = getTransClientCode(rmm.getId_transaccion(),db);
            }
            else{
                datos[i][3] = "";
            }
            datos[i][4] = rmm.getId_cajero();
            datos[i][5] = rmm.getDate();

        }

        return datos;
    }

    public static String getTransClientCode(int id,Connection db){
        String sql = "Select clientCode_receptor From transaccion Where id_transaccion = ?";
        String code = "";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("clientCode_receptor");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static DefaultTableModel generarModeloTabla(String[] columnas, Object[][] data){
        DefaultTableModel modelo = new DefaultTableModel();
        for (String columna : columnas) {
            modelo.addColumn(columna);
        }
        for (Object[] fila : data) {
            modelo.addRow(fila);
        }

        return modelo;
    }


}
