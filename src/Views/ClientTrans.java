package Views;

import Controllers.CajerosEditController;
import Controllers.ClientActionController;
import Controllers.ClientController;
import Models.CajeroModel;
import Models.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientTrans extends JFrame {
    private JLabel cajeroLabel;
    private JComboBox cajeroSelector;
    private JLabel saldoLabel;
    private JLabel saldoDispValue;
    private JLabel montoLabel;
    private JTextField montoField;
    private JLabel transLabel;
    private JTextField transField;
    private JButton transferirButton;
    private JPanel mainPanel;
    private JLabel alertLabel;

    private Cliente user;
    private Home home;

    public ClientTrans(Cliente c, Home h) throws SQLException {
        alertLabel.setVisible(false);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        CajerosEditController cec = new CajerosEditController();
        ArrayList<CajeroModel> cajeros = cec.getAllCajeros();
        for (CajeroModel cajero : cajeros) {
            comboBoxModel.addElement(String.valueOf(cajero.getId_cajero()));
        }
        cajeroSelector.setModel(comboBoxModel);

        ClientController cc = new ClientController();
        Cliente u = cc.getForID(c.getId_Cliente());
        this.user = u;
        saldoDispValue.setText(String.valueOf(u.getSaldo()));

        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double monto = 0;
                try{
                    monto = Double.parseDouble(montoField.getText());
                }
                catch (NumberFormatException nfe){
                    alertLabel.setText("Ingresar solo numeros positivos");
                    alertLabel.setVisible(true);
                }
                // verifico si ese codigo ingresado existe
                try {
                    ClientController cc = new ClientController();
                    if(cc.findForCode(transField.getText())){
                        if(monto > c.getSaldo()){
                            alertLabel.setText("Usted no dispone de esa cantidad de Dinero");
                            alertLabel.setVisible(true);
                        }
                        else{
                            try {
                                ClientActionController cac = new ClientActionController();
                                int selected = cajeroSelector.getSelectedIndex();
                                CajeroModel cajeroSeleccionado = cajeros.get(selected);
                                if(cac.Trans(cajeroSeleccionado.getId_cajero(),u.getId_Cliente(),transField.getText(),u.getSaldo()-monto,monto)) {
                                    Cliente cli = cc.getForID(c.getId_Cliente());
                                    h.actualizar(cli);
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    else{
                        alertLabel.setText("Codigo de Usuario destinatario Incorrecto");
                        alertLabel.setVisible(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        setTitle("Transferencia a usuario por Codigo");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
