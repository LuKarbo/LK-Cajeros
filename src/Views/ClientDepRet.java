package Views;

import Controllers.CajerosEditController;
import Controllers.ClientActionController;
import Controllers.ClientController;
import Interface.IActualizar;
import Models.CajeroModel;
import Models.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDepRet extends JFrame implements IActualizar {
    private JPanel mainPanel;
    private JLabel cajeroLabel;
    private JComboBox cajeroSelector;
    private JLabel statusLabel;
    private JLabel statusValue;
    private JLabel montoMaxLabel;
    private JLabel montoMaxValue;
    private JTextField montoField;
    private JLabel montoLabel;
    private JButton depositarButton;
    private JButton retirarButton;
    private JLabel saldoValue;
    private JLabel saldoLabel;
    private JLabel cajeroDesactLabel;

    private boolean funcional;
    private Cliente user;
    private Home home;

    public ClientDepRet(Cliente c, Home h) throws SQLException {
        // agrego los cajeros
        cargarCajerosYuser(c);
        this.home = h;
        // acciones
        cargarAccionesBTN(user);
        setTitle("Deposito o Retiro");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    public void cargarCajerosYuser(Cliente c) throws SQLException {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        CajerosEditController cec = new CajerosEditController();
        ArrayList<CajeroModel> cajeros = cec.getAllCajeros();
        for (CajeroModel cajero : cajeros) {
            comboBoxModel.addElement(String.valueOf(cajero.getId_cajero()));
        }
        cajeroSelector.setModel(comboBoxModel);
        // Opcion por defecto (es el primer cajero)
        if (!cajeros.isEmpty()) {
            cajeroSelector.setSelectedItem(String.valueOf(cajeros.get(0).getId_cajero()));
            if(cajeros.get(0).getStatus()){
                statusValue.setText("Activo");
                funcional = true;
                cajeroDesactLabel.setVisible(false);
            }
            else{
                statusValue.setText("Desactivado");
                funcional = false;
                cajeroDesactLabel.setText("Cajero Desactivado, seleccionar otro");
                cajeroDesactLabel.setVisible(true);
            }
            montoMaxValue.setText(String.valueOf(cajeros.get(0).getCantidad_dinero()));


        }
        // Funcion del selector
        cajeroSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtengo el valor seleccionado
                int selected = cajeroSelector.getSelectedIndex();
                if (selected != -1) {
                    // Busco el cajero que concuerda con él
                    CajeroModel cajeroSeleccionado = cajeros.get(selected);
                    // Actualizo el Status y Monto
                    if (cajeroSeleccionado.getStatus()) {
                        statusValue.setText("Activo");
                        funcional = true;
                        cajeroDesactLabel.setVisible(false);
                    }
                    else{
                        statusValue.setText("Desactivado");
                        funcional = false;
                        cajeroDesactLabel.setText("Cajero Desactivado, seleccionar otro");
                        cajeroDesactLabel.setVisible(true);
                    }
                    montoMaxValue.setText(String.valueOf(cajeroSeleccionado.getCantidad_dinero()));
                }
            }
        });

        ClientController cc = new ClientController();
        Cliente u = cc.getForID(c.getId_Cliente());
        this.user = u;
        saldoValue.setText(String.valueOf(u.getSaldo()));
    }

    public void cargarAccionesBTN(Cliente c){
        if(funcional){
            depositarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(save(2,c.getId_Cliente())){
                        cajeroDesactLabel.setText("Deposito realizado con Exito");
                        cajeroDesactLabel.setVisible(true);
                        try {
                            actualizar();
                            actualizar(home);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else{
                        cajeroDesactLabel.setText("Error al realizar Deposito");
                        cajeroDesactLabel.setVisible(true);
                    }
                }
            });
            retirarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double monto = 0;
                    try{
                        monto = Double.parseDouble(montoField.getText());
                    }
                    catch (NumberFormatException nfe){
                        cajeroDesactLabel.setText("Ingresar solo numeros positivos");
                        cajeroDesactLabel.setVisible(true);
                    }
                    if(monto > Double.parseDouble(montoMaxValue.getText())){
                        cajeroDesactLabel.setText("EL Cajero no tiene esa cantidad de dinero disponible");
                        cajeroDesactLabel.setVisible(true);

                    }
                    else if(monto > c.getSaldo()){
                        cajeroDesactLabel.setText("Usted no dispone de esa cantidad de Dinero");
                        cajeroDesactLabel.setVisible(true);
                    }
                    else{
                        if(save(1,c.getId_Cliente())){
                            cajeroDesactLabel.setText("Retiro realizado con Exito");
                            cajeroDesactLabel.setVisible(true);
                            try {
                                actualizar();
                                actualizar(home);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        else{
                            cajeroDesactLabel.setText("Error al realizar Retiro");
                            cajeroDesactLabel.setVisible(true);
                        }
                    }
                }
            });
        }
    }

    public boolean save(int type_id, int client_id){
        try {
            ClientActionController cac = new ClientActionController();
            CajerosEditController cec = new CajerosEditController();
            ArrayList<CajeroModel> cajeros = cec.getAllCajeros();
            double monto = 0;
            try{
                monto = Double.parseDouble(montoField.getText());
            }
            catch (NumberFormatException e){
                cajeroDesactLabel.setText("Ingresar solo numeros positivos");
                cajeroDesactLabel.setVisible(true);
            }
            int selected = cajeroSelector.getSelectedIndex();
            CajeroModel cajeroSeleccionado = cajeros.get(selected);

            if(type_id == 1 && monto >= 1){
                //retiro
                if(cac.DepRet(cajeroSeleccionado.getId_cajero(),type_id,client_id,user.getSaldo() - monto,(double)cajeroSeleccionado.getCantidad_dinero()-monto,monto)){
                    return true;
                }
                return false;
            }
            else if(type_id == 2 && monto >= 1){
                // deposito
                if(cac.DepRet(cajeroSeleccionado.getId_cajero(),type_id,client_id,user.getSaldo() + monto,(double)cajeroSeleccionado.getCantidad_dinero()+monto,monto)){
                    return true;
                }
                return false;
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actualizar() throws SQLException {
        cargarCajerosYuser(this.user);
        System.out.println("Retiro DEp");
    }

    @Override
    public void actualizar(Home home){
        home.actualizar(this.user);
        System.out.println("Actualizo Home");
    }
}
