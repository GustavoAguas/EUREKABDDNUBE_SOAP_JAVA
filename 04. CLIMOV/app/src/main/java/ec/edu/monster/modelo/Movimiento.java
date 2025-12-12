package ec.edu.monster.modelo;

public class Movimiento {
    private String cuenta;
    private int nromov;
    private String fecha;
    private String tipo;
    private String accion;
    private double importe;

    public Movimiento(String cuenta, int nroMov, String fecha, String tipo, String accion, double importe) {
        this.cuenta = cuenta;
        this.nromov = nroMov;
        this.fecha = fecha;
        this.tipo = tipo;
        this.accion = accion;
        this.importe = importe;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getNroMov() {
        return nromov;
    }

    public void setNroMov(int nroMov) {
        this.nromov = nroMov;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
