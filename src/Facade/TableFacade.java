package Facade;

import Models.Registro_MovimientoModel;

import java.sql.Connection;
import java.util.ArrayList;

public class TableFacade {

    public static ArrayList<Registro_MovimientoModel> tableFilt(int typeId, ArrayList<Registro_MovimientoModel> value){
        ArrayList<Registro_MovimientoModel> newList = new ArrayList<>();
        if(typeId >= 1 ){
            for (Registro_MovimientoModel rmm : value) {
                if(rmm.getId_typeMovimiento() == typeId){
                    newList.add(rmm);
                }
            }
        }

        return newList;
    }

}
