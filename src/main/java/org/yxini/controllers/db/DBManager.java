package org.yxini.controllers.db;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.yxini.models.*;

/**
 * DAO De la aplicacion para gestion de biblioteca.
 */
public class DBManager {
    private ConnectionDB conexion;

    /**
     * Método que devuelve una lista con todos los Alumnos de la base de datos.
     * @return una lista con todos los Alumnos de la base de datos.
     */
    public ObservableList<Alumno> cargarAlumnos() {
        ObservableList<Alumno> comidas = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM alumno";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                Alumno c = new Alumno(dni, nombre, apellido, apellido2);
                comidas.add(c);
            }

            rs.close();
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return comidas;
    }


    /**
     * Método que devuelve una lista con todos los Libros de la base de datos.
     * @return una lista con todos los Libros de la base de datos.
     */
    public ObservableList<Libro> cargarLibros() {
        ObservableList<Libro> libros = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM libro";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String estado = rs.getString("estado");
                boolean baja = rs.getBoolean("baja");
                Libro c = new Libro(titulo, autor, editorial, estado, baja);
                libros.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return libros;
    }

    /**
     * Método que devuelve una lista con todos los Libros de la base de datos que no estan dados de baja.
     * @return una lista con todos los Libros de la base de datos sin dar de baja.
     */
    public ObservableList<Libro> cargarLibrosSinBaja() {
        ObservableList<Libro> libros = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM libro WHERE baja = 0";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String estado = rs.getString("estado");
                boolean baja = rs.getBoolean("baja");
                Libro c = new Libro(titulo, autor, editorial, estado, baja);
                libros.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return libros;
    }

    public ObservableList<Prestamo> cargarPrestamo() {
        ObservableList<Prestamo> pretamos = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM `prestamo` WHERE (SELECT baja FROM libro WHERE codigo_libro = codigo) = 0";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni_alumno");
                int codigoLibro = rs.getInt("codigo_libro");
                String tituloLibro=getLibroByCode(codigoLibro).getTitulo();
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Prestamo c = new Prestamo(dni, codigoLibro, fechaPrestamo);
                c.updateCode(tituloLibro);
                pretamos.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return pretamos;
    }

    /**
     * Método que devuelve una lista con todos los Prestamos de la base de datos que no tienen devolucion.
     * @return una lista con todos los prestamos de la base de datos.
     */
    public ObservableList<Prestamo> cargarPrestamosActivos() {
        ObservableList<Prestamo> pretamos = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM `prestamo` WHERE (SELECT baja FROM libro WHERE codigo_libro = codigo) = 1";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idPrestamo = rs.getInt("id_prestamo");
                String dni = rs.getString("dni_alumno");
                int codigoLibro = rs.getInt("codigo_libro");
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Prestamo c = new Prestamo(idPrestamo,dni, codigoLibro, fechaPrestamo);
                pretamos.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return pretamos;
    }


    /**
     * Método que devuelve una lista con todos los prestamos devueltos de la base de datos.
     * @return una lista con todos los prestamos devueltos de la base de datos.
     */
    public ObservableList<Devolucion> cargarHistorico() {
        ObservableList<Devolucion> devoluciones = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM historico_prestamo";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni_alumno");
                int codigoLibro = rs.getInt("codigo_libro");
                String tituloLibro=getLibroByCode(codigoLibro).getTitulo();
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Date fechaDevolucion = rs.getDate("fecha_devolucion");
                Devolucion c = new Devolucion(dni, codigoLibro, fechaPrestamo,fechaDevolucion);
                devoluciones.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return devoluciones;
    }

    /**
     * Añade el alumno pasado como parametro a la base de datos.
     * @param alumno alumno a añdir a la base de datos.
     */
    public void addAlumno(Alumno alumno) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo;
            sqlAddEquipo = "INSERT INTO alumno VALUES(?,?,?,?)";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setString(1, alumno.getDni());
            pstm.setString(2, alumno.getNombre());
            pstm.setString(3, alumno.getApellido1());
            pstm.setString(4, alumno.getApellido2());
            pstm.execute();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Añade el libro pasado como parametro a la base de datos.
     * @param libro libro a añdir a la base de datos.
     */
    public void addLibro(Libro libro) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo;
            sqlAddEquipo = "INSERT INTO libro (titulo,autor,editorial,estado,baja) VALUES(?,?,?,?,?)";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setString(1, libro.getTitulo());
            pstm.setString(2, libro.getAutor());
            pstm.setString(3, libro.getEditorial());
            pstm.setString(4, libro.getEstado());
            pstm.setInt(5, (libro.isDeBaja())?1:0);
            pstm.execute();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve un alumno de la base de datos con el dni pasado como parametro
     * @param dni DNI del alumno
     * @return Alumno con dni igual al dni pasado como parametro
     * @throws SQLException si no se ha encontrado el alumno
     */
    public Alumno getAlumnoByID(String dni) throws SQLException {
        conexion = new ConnectionDB();
        Alumno equ = null;
        System.out.println(dni);
        String sql = "SELECT * FROM alumno WHERE dni = ?";
        PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
        pstmt.setString(1, dni);
        pstmt.executeQuery();
        ResultSet rs = pstmt.getResultSet();
        if (rs.next()) {
            equ = new Alumno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"));
        }
        System.out.println(equ);
        conexion.closeConexion();
        return equ;
    }

    /**
     * Devuelve un libro de la base de datos con codigo igual al codigo pasado como parametro.
     * @param codigo Codigo a buscar en la BD
     * @return Libro con codigo igual a 'codigo'
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public Libro getLibroByCode(int codigo) throws SQLException {
        conexion = new ConnectionDB();
        Libro lib = null;
        String sql = String.format("SELECT * FROM libro WHERE codigo = ?");
        PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
        pstmt.setInt(1, codigo);
        pstmt.executeQuery();
        ResultSet rs = pstmt.getResultSet();
        while (rs.next()) {
            int codigoLibro = rs.getInt("codigo");
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            String editorial = rs.getString("editorial");
            String estado = rs.getString("estado");
            boolean baja = rs.getBoolean("baja");
            lib = new Libro(codigoLibro,titulo, autor, editorial, estado, baja);
        }

        conexion.closeConexion();
        return lib;
    }

    /**
     * Devuelve el primer libro con el titulo que se le da como parametro.
     * @param tituloLibro titulo del libro a buscar
     * @return primer libro con ese titulo de la base de datos.
     * @throws SQLException Si hay problemas con la BBDD
     */
    public Libro getLibroByName(String tituloLibro) throws SQLException {
        conexion = new ConnectionDB();
        Libro lib = null;
        String sql = String.format("SELECT * FROM libro WHERE titulo = ?");
        PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
        pstmt.setString(1, tituloLibro);
        pstmt.executeQuery();
        ResultSet rs = pstmt.getResultSet();
        if (rs.next()) {
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            String editorial = rs.getString("editorial");
            String estado = rs.getString("estado");
            boolean baja = rs.getBoolean("baja");
            lib = new Libro(titulo, autor, editorial, estado, baja);
            lib.setCodigoLibro(rs.getInt("codigo"));
        }
        conexion.closeConexion();
        return lib;
    }

    /**
     * Cambia los datos de un alumno a los datos nuevos.
     * @param oldAlumno datos viejos.
     * @param alumno datos nuevos.
     */
    public void actualizarAlumno(Alumno oldAlumno,Alumno alumno) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo = "UPDATE alumno SET dni = ?, nombre = ?, apellido1 = ?, apellido2 = ? WHERE ? = ?";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setString(1, alumno.getDni());
            pstm.setString(2, alumno.getNombre());
            pstm.setString(3, alumno.getApellido1());
            pstm.setString(4, alumno.getApellido2());
            pstm.setString(5, oldAlumno.getDni());
            pstm.setString(6, alumno.getDni());

            pstm.executeUpdate();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un alumno de la base de datos.
     * @param alumno Alumno para borrar.
     * @throws SQLException Si hay problemas de conexion con la BBDD
     */
    public void borrarAlumno(Alumno alumno) throws SQLException {
        conexion = new ConnectionDB();
        String sql = "DELETE FROM alumno WHERE dni=?";
        PreparedStatement stmt = conexion.getConexion().prepareStatement(sql);
        stmt.setString(1, alumno.getDni());
        stmt.executeUpdate();
        conexion.closeConexion();
    }

    /**
     * Devuelve una lista de String's con todos los DNIs de la base de datos.
     * @return un ObservableArrayList de Strings con los dnis
     * @see String,ObservableList
     */
    public ObservableList<String> getDNIs() {
        ObservableList<String> dnis = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT dni FROM alumno";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                dnis.add(dni);
            }

            rs.close();
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return dnis;
    }

    /**
     * Añade un prestamo a la base de datos.
     * @param prestamo Prestamo para añadir a la base de datos.
     */
    public void addPrestamo(Prestamo prestamo) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo;
            sqlAddEquipo = "INSERT INTO prestamo (dni_alumno,codigo_libro,fecha_prestamo) VALUES(?,?,?)";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setString(1, prestamo.getDniAlumno());
            pstm.setInt(2, prestamo.getCodigoLibro());
            pstm.setDate(3, prestamo.getFechaPrestamo());
            pstm.execute();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un prestamo al histórico de la base de datos.
     * @param devolucion Nuevo prestamo.
     */
    public void addFinPrestamo(Devolucion devolucion) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo;
            sqlAddEquipo = "INSERT INTO historico_prestamo (dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion) VALUES(?,?,?,?)";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setString(1, devolucion.getDniAlumno());
            pstm.setInt(2, devolucion.getCodigoLibro());
            pstm.setDate(3, devolucion.getFechaPrestamo());
            pstm.setDate(4, devolucion.getFechaDevolucion());
            pstm.execute();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con todos los prestamos con libros en el estado seleccionado.
     * @param state estado seleccionado.
     * @return Lista de prestamos.
     * @see Libro
     */
    public ObservableList<Devolucion> getPrestamosByState(String state) {
        ObservableList<Devolucion> devoluciones = FXCollections.observableArrayList();
        try {
            conexion = new ConnectionDB();
            String consulta = "SELECT * FROM historico_prestamo WHERE codigo_libro IN (SELECT codigo FROM libro WHERE estado = ?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1,state);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni_alumno");
                int codigoLibro = rs.getInt("codigo_libro");
                String tituloLibro=getLibroByCode(codigoLibro).getTitulo();
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Date fechaDevolucion = rs.getDate("fecha_devolucion");
                Devolucion c = new Devolucion(dni, codigoLibro, fechaPrestamo,fechaDevolucion);
                c.updateCode(tituloLibro);
                devoluciones.add(c);
            }
            rs.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("Error en la carga de productos desde la base de datos");
            e.printStackTrace();
        }
        return devoluciones;
    }

    /**
     * Modifica los datos de un libro con los datos pasados como parametro.
     * @param oldLibro Libro para cambiar
     * @param newLibro Libro con datos nuevos
     * @see Libro
     */
    public void modificarLibro(Libro oldLibro, Libro newLibro) {
        try {
            conexion = new ConnectionDB();
            String sqlAddEquipo;
            sqlAddEquipo = "UPDATE libro SET codigo=?,titulo=?,autor=?,editorial=?,estado=?,baja=? WHERE codigo = ?";
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sqlAddEquipo);
            pstm.setInt(1,newLibro.getCodigo());
            pstm.setString(2,newLibro.getTitulo());
            pstm.setString(3,newLibro.getAutor());
            pstm.setString(4,newLibro.getEditorial());
            pstm.setString(5,newLibro.getEstado());
            pstm.setInt(6,newLibro.isDeBaja()?1:0);
            pstm.setInt(7,oldLibro.getCodigo());
            pstm.execute();
            conexion.closeConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un prestamo de la base de datos.
     * @param prestamoParaBorrar el prestamo a borrar.
     */
    public void borrarPrestamo(Prestamo prestamoParaBorrar) {
        try {
            conexion = new ConnectionDB();
            String sql = "DELETE FROM prestamo WHERE id_prestamo=?";
            try (PreparedStatement stmt = conexion.getConexion().prepareStatement(sql)) {
                stmt.setInt(1, prestamoParaBorrar.getIdPrestamo());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.closeConexion();
        }
    }

    /**
     * Comprueba se el DNI existe en la base de datos y devuelve un Boolean con el resultado.
     * @param alumno Alumno con el DNI a comprobar.
     * @return true si existe false si no existe el dni.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public boolean existsAlumno(Alumno alumno) throws SQLException {
        conexion = new ConnectionDB();
        String sql = String.format("SELECT dni FROM alumno WHERE dni = ?");
        PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
        pstmt.setString(1, alumno.getDni());
        pstmt.executeQuery();
        boolean existsAlumno = pstmt.getResultSet().next();
        conexion.closeConexion();
        return existsAlumno;
    }

    /**
     * Borra un libro de la base de datos.
     * @param libroABorrar Libro para borrar de la base de datos.
     */
    public void borrarLibro(Libro libroABorrar) {
        try {
            conexion = new ConnectionDB();
            String sql = "DELETE FROM libro WHERE codigo=?";
            try (PreparedStatement stmt = conexion.getConexion().prepareStatement(sql)) {
                stmt.setInt(1, libroABorrar.getCodigo());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.closeConexion();
        }
    }
}