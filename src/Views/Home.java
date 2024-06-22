package Views;

import Facade.TableFacade;
import Facade.Type_Data_Facade;
import Interface.IActualizar;
import Models.Cliente;
import Models.Registro_MovimientoModel;
import Controllers.HomeController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Home extends JFrame implements IActualizar {
    private Cliente user;
    private JPanel mainPanel;
    private JLabel userNameValue;
    private JLabel userCode;
    private JLabel userCodeValue;
    private JScrollPane dataPanel;
    private JTable activitiesTable;
    private JButton TransferirBTN;
    private JButton retirarBTN;
    private JButton DepositarBTN;
    private JLabel userName;
    private JLabel userSaldo;
    private JLabel userSaldoValue;
    private JButton adminMenuBTN;
    private JButton depositoRetiroButton;
    private JButton transferenciaButton;
    private JLabel funcionesLabel;
    private JLabel filtroLabel;
    private JButton ActualizarBTN;
    private HomeController homeC = new HomeController();
    public Home(Cliente c) throws SQLException {
        this.user = c;
        userNameValue.setText(this.user.getname());
        userCodeValue.setText(this.user.getClientCode());
        userSaldoValue.setText(String.valueOf(this.user.getSaldo()));

        if(this.user.getId_permisos() >=2 ){
            adminMenuBTN.setVisible(true);
        }
        else{
            adminMenuBTN.setVisible(false);
        }

        generarTabla(0);// para que no se puedan mover

        // Filtros
        // 1
        retirarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTabla(1);
            }
        });
        // 2
        DepositarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTabla(2);
            }
        });
        // 3
        TransferirBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTabla(3);
            }
        });
        // 4
        ActualizarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTabla(0);
            }
        });

        // Admin BTN
        adminMenuBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin Menu FUnciona");
                try {
                    AdminMenu menu = new AdminMenu();
                    menu.setVisible(true);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        // Funciones
        depositoRetiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDepRet cdr = null;
                try {
                    cdr = new ClientDepRet(c,Home.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cdr.setVisible(true);
            }
        });
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientTrans ct = new ClientTrans(c, Home.this);
                    ct.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setTitle("Home");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public void generarTabla(int a){
        String[] columnas = {"ID", "Tipo", "Monto", "Codigo Terceros","N Cajero", "Fecha"};
        ArrayList<Registro_MovimientoModel> dataCrud = homeC.getMovimientosByUSER(this.user.getId_Cliente());

        if(a >= 1){
            Object[][] datosFilt =  homeC.getDataFromList(TableFacade.tableFilt(a,dataCrud));
            activitiesTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datosFilt));
            activitiesTable.getTableHeader().setReorderingAllowed(false);
        }
        else{
            Object[][] datos = homeC.getDataFromList(dataCrud);
            activitiesTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datos));
            activitiesTable.getTableHeader().setReorderingAllowed(false);
        }
    }

    public void actualizar(Cliente c) {
        generarTabla(0);
        userSaldoValue.setText(String.valueOf(c.getSaldo()));
    }

    @Override
    public void actualizar() throws SQLException {

    }

    @Override
    public void actualizar(Home home){

    }
}
