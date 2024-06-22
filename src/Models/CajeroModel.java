package Models;

import DB.DBCajero;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
public class CajeroModel {
    private int id_cajero;
    private boolean status;
    private int cantidad_dinero;

    public CajeroModel(int id, int dinero, boolean estado) throws SQLException {
        this.id_cajero = id;
        this.cantidad_dinero = dinero;
        this.status = estado;
    }

    public int getId_cajero() {
        return id_cajero;
    }

    public boolean getStatus() {
        return status;
    }

    public int getCantidad_dinero() {
        return cantidad_dinero;
    }

}
