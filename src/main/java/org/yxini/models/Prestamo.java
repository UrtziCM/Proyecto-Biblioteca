package org.yxini.models;

import org.yxini.controllers.db.DBManager;

import java.sql.Date;
import java.sql.SQLException;

public class Prestamo {
    private int idPrestamo;
    private String dniAlumno;
    private int codigoLibro;
    private String tituloLibro;
    private Date fechaPrestamo;


    public Prestamo(String dniAlumno, int codigoLibro, Date fechaPrestamo) throws SQLException {
        this.dniAlumno = dniAlumno;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        tituloLibro = new DBManager().getLibroByCode(codigoLibro).getTitulo();
    }
    public Prestamo(int idPrestamo,String dniAlumno, int codigoLibro, Date fechaPrestamo) throws SQLException {
        this.idPrestamo = idPrestamo;
        this.dniAlumno = dniAlumno;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        tituloLibro = new DBManager().getLibroByCode(codigoLibro).getTitulo();
    }

    public void updateCode(String tituloLibro) throws SQLException {
        DBManager gestor = new DBManager();
        Libro l = gestor.getLibroByName(tituloLibro);
        this.codigoLibro = l.getCodigo();
        this.tituloLibro = l.getTitulo();
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public int getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }
}
