package org.yxini.models;

public class Libro {
    private int codigoLibro;
    private String titulo;
    private String autor;
    private String editorial;
    private String estado;
    private boolean deBaja;

    public Libro(String titulo, String autor, String editorial, String estado, boolean deBaja) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.deBaja = deBaja;
    }
    public Libro(int codigoLibro, String titulo, String autor, String editorial, String estado, boolean deBaja) {
        this.codigoLibro = codigoLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.deBaja = deBaja;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDeBaja() {
        return deBaja;
    }

    public void setDeBaja(boolean deBaja) {
        this.deBaja = deBaja;
    }

    @Override
    public String toString() {
        return titulo + " escrito por " + autor + " editado por " + editorial;
    }

    public int getCodigo() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }
}
