package Models;

import Controllers.ClientController;
import DB.DBCajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class Cliente {
    private int id_Cliente;
    private String name;
    private double saldo;
    private String clientCode;
    private int id_permisos;
    private Connection db;

    public Cliente() throws SQLException {
        this.db = DBCajero.getDB();
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public String getname() {
        return name;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getClientCode() {
        return clientCode;
    }

    public int getId_permisos() {
        return id_permisos;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public void setId_permisos(int id_permisos) {
        this.id_permisos = id_permisos;
    }

    public boolean login(String userName, String userPassword) throws SQLException {
        try{
            Cliente user = new Cliente();
            ClientController cc = new ClientController();
            user = cc.login(userName,userPassword);
            if (user != null){
                this.setId_Cliente(user.getId_Cliente());
                this.setName(user.getname());
                this.setSaldo(user.getSaldo());
                this.setClientCode(user.getClientCode());
                this.setId_permisos(user.getId_permisos());
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean register(String userName, String userPassword) throws SQLException {
        try{
            ClientController cc = new ClientController();
            return cc.register(userName,userPassword);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}
