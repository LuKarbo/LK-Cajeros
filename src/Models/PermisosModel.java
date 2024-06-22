package Models;

public class PermisosModel {
    private int id_permisos;
    private String name;

    public PermisosModel(int id_permisos, String name) {
        this.id_permisos = id_permisos;
        this.name = name;
    }

    public int getId_permisos() {
        return id_permisos;
    }

    public String getName() {
        return name;
    }
}
