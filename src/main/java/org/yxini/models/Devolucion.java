package org.yxini.models;

import java.sql.Date;
import java.sql.SQLException;

public class Devolucion extends Prestamo{
    Date fechaDevolucion;

    public Devolucion(String dniAlumno, int codigoLibro, Date fechaPrestamo, Date fechaDevolucion ) throws SQLException {
        super(dniAlumno,codigoLibro,fechaPrestamo);
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
