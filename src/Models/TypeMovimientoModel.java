package Models;

public class TypeMovimientoModel {
    private int id_typeMovimiento;
    private String typeName;

    public TypeMovimientoModel(int id, String name){
        this.id_typeMovimiento = id;
        this.typeName = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getId_typeMovimiento() {
        return id_typeMovimiento;
    }
}
