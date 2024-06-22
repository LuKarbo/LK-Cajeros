package Models;

import java.util.Date;

public class Registro_MovimientoModel {
    private int id_registro_movimiento;
    private int id_cajero;
    private int id_typeMovimiento;
    private Integer id_transaccion;
    private int id_Cliente;
    private Integer monto;
    private Date date;

    public Registro_MovimientoModel(int id_registro_movimiento,int id_cajero, int id_typeMovimiento, Integer id_transaccion, int id_Cliente, Integer monto, Date date) {
        this.id_registro_movimiento = id_registro_movimiento;
        this.id_cajero = id_cajero;
        this.id_typeMovimiento = id_typeMovimiento;
        this.id_transaccion = id_transaccion;
        this.id_Cliente = id_Cliente;
        this.monto = monto;
        this.date = date;
    }

    public int getId_registro_movimiento() {
        return id_registro_movimiento;
    }

    public int getId_cajero() {
        return id_cajero;
    }

    public int getId_typeMovimiento() {
        return id_typeMovimiento;
    }

    public Integer getId_transaccion() {
        return id_transaccion;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public Integer getMonto() {
        return monto;
    }

    public Date getDate() {
        return date;
    }
}
