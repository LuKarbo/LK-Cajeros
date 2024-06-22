package Models;
import java.util.Date;
public class TransaccionModel {
    private int id_transaccion;
    private int id_Cajero;
    private int id_Cliente;
    private String clientCode_receptor;
    private Date fecha_y_hora;
    private int monto;

    public TransaccionModel(int id_transaccion, int id_Cajero, int id_Cliente, String clientCode_receptor, Date fecha_y_hora, int monto) {
        this.id_transaccion = id_transaccion;
        this.id_Cajero = id_Cajero;
        this.id_Cliente = id_Cliente;
        this.clientCode_receptor = clientCode_receptor;
        this.fecha_y_hora = fecha_y_hora;
        this.monto = monto;
    }

    public int getId_transaccion() {
        return id_transaccion;
    }

    public int getId_Cajero() {
        return id_Cajero;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public String getClientCode_receptor() {
        return clientCode_receptor;
    }

    public Date getFecha_y_hora() {
        return fecha_y_hora;
    }

    public int getMonto() {
        return monto;
    }
}
