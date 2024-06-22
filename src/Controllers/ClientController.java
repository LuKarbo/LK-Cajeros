package Controllers;

import DB.DBCajero;
import Models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientController {
    private Connection db;
    protected Cliente user;

    public ClientController() throws SQLException {
        this.user = new Cliente();
        this.db = DBCajero.getDB();
    }

    public Cliente login(String userName, String userPassword){
        String sql = "SELECT * FROM cliente WHERE nombre = ? AND password = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, userPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Si el ResultSet tiene al menos una fila, significa que las credenciales son correctas
                    user.setId_Cliente(rs.getInt("id_cliente"));
                    user.setName(rs.getString("nombre"));
                    user.setSaldo(rs.getInt("saldo"));
                    user.setClientCode(rs.getString("clientCode"));
                    user.setId_permisos(rs.getInt("id_permisos"));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String userName, String userPassword){
        if(findForName(userName)){
            return false;
        }
        else{
            // comprobar que no haya nadie con ese nombre
            // generar clientCode random
            // permisos iniciales de Cliente 1;
            // permisos iniciales de Admin 5;
            UUID uuid = UUID.randomUUID();
            String clientCode = uuid.toString().replace("-", "").substring(0, 10);

            String sql = "INSERT INTO cliente (nombre, password, saldo, clientCode, id_permisos) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, userName);
                stmt.setString(2, userPassword);
                stmt.setInt(3, 0);
                stmt.setString(4, clientCode);
                stmt.setInt(5, 1); // id_permisos inicial para clientes

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    db.commit();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean findForName(String userName){
        boolean encontrado = false;
        String sql = "SELECT * FROM cliente WHERE nombre = ? ";

        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    encontrado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public Cliente getForID(int userId){
        String sql = "SELECT * FROM cliente WHERE id_cliente = ? ";

        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user.setId_Cliente(rs.getInt("id_cliente"));
                    user.setName(rs.getString("nombre"));
                    user.setSaldo(rs.getInt("saldo"));
                    user.setClientCode(rs.getString("clientCode"));
                    user.setId_permisos(rs.getInt("id_permisos"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean findForCode(String code){
        boolean encontrado = false;
        String sql = "SELECT * FROM cliente WHERE clientCode = ? ";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    encontrado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

}
