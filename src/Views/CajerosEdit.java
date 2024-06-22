package Views;

import Controllers.CajerosEditController;
import DB.DBCajero;
import Facade.CajeroFacade;
import Models.CajeroModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CajerosEdit extends JFrame {
    private JPanel mainPanel;
    private JLabel seleccionarLabel;
    private JComboBox cajeroSelector;
    private JComboBox statusSelector;
    private JLabel statusLabel;
    private JTextField montoField;
    private JLabel montoLabel;
    private JButton confirmarButton;
    private JButton cancelButton;

    public CajerosEdit(AdminMenu am) throws SQLException {

        // Creo el selector de cajero
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        CajerosEditController cec = new CajerosEditController();
        ArrayList<CajeroModel> cajeros = cec.getAllCajeros();
        for (CajeroModel cajero : cajeros) {
            comboBoxModel.addElement(String.valueOf(cajero.getId_cajero()));
        }
        cajeroSelector.setModel(comboBoxModel);

        // creo selector de status
        statusSelector.addItem("Activo");
        statusSelector.addItem("Desactivado");

        // Opcion por defecto (es el primer cajero)
        if (!cajeros.isEmpty()) {
            cajeroSelector.setSelectedItem(String.valueOf(cajeros.get(0).getId_cajero()));
            if(cajeros.get(0).getStatus()){
                statusSelector.setSelectedIndex(0);
            }
            else{
                statusSelector.setSelectedIndex(1);
            }
            montoField.setText(String.valueOf(cajeros.get(0).getCantidad_dinero()));

        }

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
                        statusSelector.setSelectedIndex(0);
                    } else {
                        statusSelector.setSelectedIndex(1);
                    }
                    montoField.setText(String.valueOf(cajeroSeleccionado.getCantidad_dinero()));
                }
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cajero_id = cajeroSelector.getSelectedIndex();
                String status = (String) statusSelector.getSelectedItem();
                String monto = montoField.getText();

                cec.saveChange(cajeros.get(cajero_id).getId_cajero(),status,Double.parseDouble(monto));

                am.actualizar(); // actualizo la tabla con el cambio
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setTitle("Cajero Edit");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

}
