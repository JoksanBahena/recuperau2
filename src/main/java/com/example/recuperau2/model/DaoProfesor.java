package com.example.recuperau2.model;

import com.example.recuperau2.utils.MySQL;
import com.example.recuperau2.utils.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoProfesor implements Repository<BeanProfesor>{

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    MySQL client = new MySQL();

    @Override
    public List<BeanProfesor> findAll() {

        List<BeanProfesor> profesores = new ArrayList<>();
        BeanProfesor profesor = null;

        try {
            conn = client.getConnection();
            String query = "SELECT * FROM profesores;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                profesor = new BeanProfesor();
                profesor.setNombre(rs.getString("nombre"));
                profesor.setApellido(rs.getString("apellido"));
                profesor.setFechaNac(rs.getString("fechaNac"));
                profesor.setCurp(rs.getString("curp"));
                profesor.setNumEmpleado(rs.getInt("numEmpleado"));
                profesores.add(profesor);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoProfesor.class.getName())
                    .log(Level.SEVERE, "Error -> findAll" + e.getMessage());
        }finally {
            client.close(conn,ps,rs);
        }
        return profesores;
    }

    @Override
    public BeanProfesor findById(Long id) {
        return null;
    }

    @Override
    public Response<BeanProfesor> save(BeanProfesor profesor) {

        try{
            if (validarCurp(profesor.getCurp())){
                return new Response<>(false, "La curp ya existe", null);
            }
            if (validarNumEmpleado(profesor.getNumEmpleado())){
                return new Response<>(false, "El numEmpleado ya existe", null);
            }

            conn = client.getConnection();
            String query = "INSERT INTO profesores (nombre, apellido, fechaNac, curp, numEmpleado) VALUES(?,?,?,?,?);";
            ps = conn.prepareStatement(query);

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setString(3, profesor.getFechaNac());
            ps.setString(4, profesor.getCurp());
            ps.setInt(5, profesor.getNumEmpleado());

            if (ps.executeUpdate() == 1){
                return new Response<>(200,"Registrado correctamente", profesor, false);
            }else{
                return new Response<>(200,"Error al registrar",profesor,true);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoProfesor.class.getName()).log(Level.SEVERE, "Error -> save: "+e.getMessage());
            return new Response<>(400, "Error con el servidor", null, true);

        }finally {
            client.close(conn,ps,rs);
        }
    }

    @Override
    public Response<BeanProfesor> update(BeanProfesor profesor) {
        try{
            conn = client.getConnection();
            String query = "UPDATE profesores set nombre=?, apellido=?, fechaNac=?, numEmpleado=? WHERE curp=?;";
            ps = conn.prepareStatement(query);

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setString(3, profesor.getFechaNac());
            ps.setInt(4, profesor.getNumEmpleado());
            ps.setString(5, profesor.getCurp());

            if (ps.executeUpdate() == 1){
                return new Response<>(200,"Actualizado correctamente", profesor, false);
            }else{
                return new Response<>(400,"Error al registrar", profesor,true);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoProfesor.class.getName()).log(Level.SEVERE, "Error -> save: "+e.getMessage());
            return new Response<>(400, "Error con el servidor", null, true);

        }finally {
            client.close(conn,ps,rs);
        }
    }

    @Override
    public Response<BeanProfesor> remove(Long id) {
        return null;
    }

    public List<BeanCalificaciones> getCalificaciones() {
        List<BeanCalificaciones> calificaciones = new ArrayList<>();
        BeanCalificaciones calificacion = null;
        BeanEstudiante estudiante = null;

        try {
            conn = client.getConnection();
            String query = "SELECT ca.*, es.nombre FROM calificaciones ca INNER JOIN estudiantes es ON ca.curpEstudiante = es.curp;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                calificacion = new BeanCalificaciones();
                estudiante = new BeanEstudiante();
                estudiante.setNombre(rs.getString("nombre"));
                calificacion.setEstudiante(estudiante);
                calificacion.setMateria(rs.getString("materia"));
                calificacion.setCalificacion(rs.getInt("calificacion"));
                calificaciones.add(calificacion);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoProfesor.class.getName())
                    .log(Level.SEVERE, "Error -> findAll" + e.getMessage());
        }finally {
            client.close(conn,ps,rs);
        }
        return calificaciones;
    }

    public List<BeanCalificaciones> promedio() {
        List<BeanCalificaciones> calificaciones = new ArrayList<>();
        BeanCalificaciones calificacion = null;

        try {
            conn = client.getConnection();
            String query = "SELECT AVG(calificacion) AS promedio FROM calificaciones";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                calificacion = new BeanCalificaciones();
                calificacion.setCalificacion(rs.getInt("promedio"));
                calificaciones.add(calificacion);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoProfesor.class.getName()).log(Level.SEVERE, "Error -> findAll" + e.getMessage());
        }finally {
            client.close(conn,ps,rs);
        }
        return calificaciones;

    }

    public boolean validarCurp(String curp){
        try{
            conn = client.getConnection();
            String query = "SELECT * FROM profesores WHERE curp = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, curp);
            rs = ps.executeQuery();

            if(rs.next()){
                return true;

            }
        }catch (Exception e){
            System.out.println("Error -> validarCurp"+ e.getMessage());

        } finally {
            client.close(conn,ps,rs);

        }

        return false;
    }

    public boolean validarNumEmpleado(int numero){

        try{

            conn = client.getConnection();
            String query = "SELECT * FROM profesores WHERE numEmpleado = ?;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, numero);
            rs = ps.executeQuery();

            if(rs.next()){
                return true;

            }

        }catch (Exception e){
            System.out.println("Error -> validarNumEmpleado"+ e.getMessage());

        } finally {
            client.close(conn,ps,rs);

        }

        return false;
    }
}