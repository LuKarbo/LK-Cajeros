package Facade;

import DB.DBCajero;

import java.sql.*;

public class ClienteFacade {

    private Connection db;
    public ClienteFacade() throws SQLException {
        db = DBCajero.getDB();
    }

    public boolean actuCliForId(int id, double saldo){
        String sql = "UPDATE Cliente SET saldo = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setDouble(1, saldo);
            stmt.setInt(2, id);

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                db.commit();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actuCliForCode(String code, double saldo){
        String sql = "UPDATE Cliente SET saldo = saldo + ?  WHERE clientCode = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setDouble(1, saldo);
            stmt.setString(2, code);

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                db.commit();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearTrans(int id_cajero, int id_cliente, String clientCode_receptor, double monto){
        String sql = "INSERT INTO transaccion (id_cajero, id_cliente, clientCode_receptor, fecha_y_hora, monto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_cajero);
            stmt.setInt(2, id_cliente);
            stmt.setString(3, clientCode_receptor);
            stmt.setDate(4, getCurrentDate());
            stmt.setDouble(5, monto);

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                db.commit();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // una vez insertado, creo el registro de movimiento correspondiente
                    crearMovimiento(id_cajero,3,generatedKeys.getInt(1),id_cliente,monto);
                    return true;
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para la transacción.");
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearMovimiento(int id_cajero, int type,Integer id_trans, int id_cliente, double monto) {

        if(id_trans != null){
            // Definimos la consulta SQL para insertar el movimiento
            String sql = "INSERT INTO Registro_movimiento (id_cajero, id_typeMovimiento,id_transaccion, id_cliente, monto, fecha_y_hora) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";


            try (PreparedStatement pstmt = db.prepareStatement(sql)){

                pstmt.setInt(1, id_cajero);
                pstmt.setInt(2, type);
                pstmt.setInt(3, id_trans);
                pstmt.setInt(4, id_cliente);
                pstmt.setDouble(5, monto);
                pstmt.setDate(6, getCurrentDate());

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    db.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error al crear el registro de movimiento: " + e.getMessage());
                return false;
            }
        }
        else{
            // Definimos la consulta SQL para insertar el movimiento
            String sql = "INSERT INTO Registro_movimiento (id_cajero, id_typeMovimiento, id_cliente, monto, fecha_y_hora) " +
                    "VALUES (?, ?, ?, ?, ?)";


            try (PreparedStatement pstmt = db.prepareStatement(sql)){

                pstmt.setInt(1, id_cajero);
                pstmt.setInt(2, type);
                pstmt.setInt(3, id_cliente);
                pstmt.setDouble(4, monto);
                pstmt.setDate(5, getCurrentDate());

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    db.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error al crear el registro de movimiento: " + e.getMessage());
                return false;
            }
        }
    }

    // Método para obtener la fecha actual como un Date
    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

}
