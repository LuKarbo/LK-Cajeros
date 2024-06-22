package Controllers;

import DB.DBCajero;
import Facade.ClienteFacade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ClientActionController {
    private Connection db;

    public ClientActionController() throws SQLException {
        this.db = DBCajero.getDB();
    }

    public boolean DepRet(int id_cajero, int id_typeMove, int id_cliente, double monto, double cajeroMonto,double monto_movi) throws SQLException {

        ClienteFacade cf = new ClienteFacade();
        CajerosEditController cec = new CajerosEditController();

        // actualizo saldo de usuario
        if(cf.actuCliForId(id_cliente,monto)){
            // actualizo saldod de cajero
            cec.saveChange(id_cajero,"Activo",cajeroMonto);
            // creo el registro de movimiento
            if(cf.crearMovimiento(id_cajero,id_typeMove,null,id_cliente,monto_movi)){
                return true;
            }
        }
        return false;
    }

    public boolean Trans(int id_cajero, int id_cliente, String cli_code, double monto, double monto_movi) throws SQLException {
        ClienteFacade cf = new ClienteFacade();
        // actualizo saldo del tercero
        if(cf.actuCliForCode(cli_code,monto_movi)){
            // actualizo saldo de usuario
            if(cf.actuCliForId(id_cliente,monto)){
                // creo el registro de trans
                if(cf.crearTrans(id_cajero,id_cliente,cli_code,monto_movi)){
                    return true;
                }
            }
        }

        return false;
    }

}
