package Interface;

import Views.Home;

import java.sql.SQLException;

public interface IActualizar {
    void actualizar() throws SQLException;

    void actualizar(Home home);
}
