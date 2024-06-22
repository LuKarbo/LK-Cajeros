package Test;

import Controllers.ClientActionController;
import Controllers.ClientController;
import Models.CajeroModel;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import Models.CajeroModel;
import java.util.ArrayList;
import Models.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestController {

    // retiro 1, dep 2, trans 3
    @Test
    public void testRetiro() throws SQLException {
        ClientActionController cac = new ClientActionController();
        // Retiro de cuenta Admin en el cajero 1, debería de retornar true
        assertEquals(true,cac.DepRet(1,1,1,2924,32197,20));
    }
    @Test
    public void testDeposito() throws SQLException{
        ClientActionController cac = new ClientActionController();
        // Deposito de cuenta Admin en el cajero 1, debería de retornar true
        assertEquals(true,cac.DepRet(1,2,1,2944,32217,20));
    }
    @Test
    public void testTransaccion() throws SQLException{
        ClientActionController cac = new ClientActionController();
        // Transacción de Admin a Test2, deberia de dar true
        assertEquals(true,cac.Trans(1,1,"d957d31cc1",2844,100));
    }

    @Test
    public void testRegister() throws SQLException {
        ClientController cc = new ClientController();
        // deberia fallar, ya que hay una cuenta con ese nombre
        assertEquals(false,cc.register("Admin","5434535"));
    }

    @Test
    public void testFindForName() throws SQLException {
        ClientController cc = new ClientController();
        // deberia fallar, ya que no hay ningun usuario con ese nombre
        assertEquals(false,cc.findForName("pepito"));
    }

    @Test
    public void testFindForCode() throws SQLException {
        ClientController cc = new ClientController();
        // deberia ser true, ya que ese codigo es el de Test3
        assertEquals(true,cc.findForCode("d1c4b5f638"));
    }
}
