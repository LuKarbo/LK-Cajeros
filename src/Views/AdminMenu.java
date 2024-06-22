package Views;

import Controllers.AdminMenuController;
import Controllers.CajerosEditController;
import Facade.TableFacade;
import Facade.Type_Data_Facade;
import Interface.IActualizar;
import Models.CajeroModel;
import Models.Registro_MovimientoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminMenu extends JFrame implements IActualizar {
    private JPanel mainPanel;
    private JPanel cajerosPanel;
    private JScrollPane dataPanel;
    private JTable dataTable;
    private JButton transferenciaButton;
    private JButton depositosBTN;
    private JButton retirosBTN;
    private JLabel filtrosLabel;
    private JTable cajerosTable;
    private JLabel cajerosLabel;
    private JButton editarButton;
    private JButton allButton;
    private JButton agregarButton;

    AdminMenuController amd = new AdminMenuController();

    public AdminMenu() throws SQLException {

        // Data/Movimientos
        String[] columnas = {"ID", "Tipo", "Monto", "Codigo Terceros","N Cajero", "Fecha"};
        ArrayList<Registro_MovimientoModel> dataCrud = amd.getAllMovimientos();
        Object[][] datos = amd.getDataFromList(dataCrud);
        dataTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datos));
        dataTable.getTableHeader().setReorderingAllowed(false);// para que no se puedan mover

        // Filtros
        retirosBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Retiro Funciona");
                Object[][] datosFilt =  amd.getDataFromList(TableFacade.tableFilt(1,dataCrud));

                dataTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datosFilt));
                dataTable.getTableHeader().setReorderingAllowed(false);
            }
        });
        depositosBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Deposito Funciona");
                Object[][] datosFilt =  amd.getDataFromList(TableFacade.tableFilt(2,dataCrud));

                dataTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datosFilt));
                dataTable.getTableHeader().setReorderingAllowed(false);
            }
        });
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Transferencia Funciona");
                Object[][] datosFilt =  amd.getDataFromList(TableFacade.tableFilt(3,dataCrud));

                dataTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datosFilt));
                dataTable.getTableHeader().setReorderingAllowed(false);
            }
        });
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] datos = amd.getDataFromList(dataCrud);

                dataTable.setModel(Type_Data_Facade.generarModeloTabla(columnas,datos));
                dataTable.getTableHeader().setReorderingAllowed(false);
            }
        });

        // Cajeros
        cajerosTable.setModel(getCajeroTableModel());
        cajerosTable.getTableHeader().setReorderingAllowed(false);// para que no se puedan mover

        // Funcionalidad Cajero
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Editar Cajero Funciona");
                try {
                    CajerosEdit cajeroEdit = new CajerosEdit(AdminMenu.this);
                    cajeroEdit.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CajerosEditController cec = new CajerosEditController();
                    cec.create();
                    actualizar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        setTitle("Admin Menu");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    public DefaultTableModel getCajeroTableModel(){

        String[] columnas_Cajeros = {"ID", "Dinero Total", "Estado"};

        ArrayList<CajeroModel> cajerosDataCrud = amd.getAllCajeros();
        Object[][] datos_cajero = amd.getDataFromList_Cajero(cajerosDataCrud);

        DefaultTableModel cajeroModel = new DefaultTableModel();
        for (String columna : columnas_Cajeros) {
            cajeroModel.addColumn(columna);
        }
        for (Object[] fila : datos_cajero) {
            cajeroModel.addRow(fila);
        }

        return cajeroModel;
    }

    @Override
    public void actualizar() {
        // recargo la tabla cajero
        cajerosTable.setModel(getCajeroTableModel());
        cajerosTable.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void actualizar(Home home){

    }
}
