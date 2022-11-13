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

public class DaoEstudiante implements Repository<BeanEstudiante>{

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    MySQL client = new MySQL();

    @Override
    public List<BeanEstudiante> findAll() {
        List<BeanEstudiante> estudiantes = new ArrayList<>();
        BeanEstudiante estudiante = null;
        BeanProfesor profesor = null;

        try {
            conn = client.getConnection();
            String query = "SELECT es.*, pr.curp AS profesorCurp, pr.nombre AS profesorNombre, pr.numEmpleado AS profesorNum FROM estudiantes es INNER JOIN profesores pr ON es.curpProfesor = pr.curp;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                estudiante = new BeanEstudiante();
                profesor = new BeanProfesor();
                profesor.setCurp(rs.getString("profesorCurp"));
                profesor.setNombre(rs.getString("profesorNombre"));
                profesor.setNumEmpleado(rs.getInt("profesorNum"));
                estudiante.setCurpProfesor(profesor);
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setFechaNac(rs.getString("fechaNac"));
                estudiante.setCurp(rs.getString("curp"));
                estudiante.setMatricula(rs.getString("matricula"));
                estudiantes.add(estudiante);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoEstudiante.class.getName())
                    .log(Level.SEVERE, "Error -> findAll" + e.getMessage());
        }finally {
            client.close(conn,ps,rs);
        }
        return estudiantes;
    }

    @Override
    public BeanEstudiante findById(Long id) {
        return null;
    }

    @Override
    public Response<BeanEstudiante> save(BeanEstudiante estudiante) {
        try{
            if (validarCurp(estudiante.getCurp())){
                return new Response<>(false, "La curp ya existe", null);
            }
            if (validarMatricula(estudiante.getMatricula())){
                return new Response<>(false, "La matricula ya existe", null);
            }

            conn = client.getConnection();
            String query = "INSERT INTO estudiantes (curpProfesor, nombre, apellido, fechaNac, curp, matricula) VALUES(?,?,?,?,?,?);";
            ps = conn.prepareStatement(query);

            ps.setString(1, estudiante.getCurpProfesor().getCurp());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getApellido());
            ps.setString(4, estudiante.getFechaNac());
            ps.setString(5, estudiante.getCurp());
            ps.setString(6, estudiante.getMatricula());

            if (ps.executeUpdate() == 1){
                return new Response<>(200,"Registrado correctamente", estudiante, false);
            }else{
                return new Response<>(200,"Error al registrar", estudiante,true);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoEstudiante.class.getName()).log(Level.SEVERE, "Error -> save: "+e.getMessage());
            return new Response<>(400, "Error con el servidor", null, true);

        }finally {
            client.close(conn,ps,rs);
        }
    }

    @Override
    public Response<BeanEstudiante> update(BeanEstudiante estudiante) {
        try{
            conn = client.getConnection();
            String query = "UPDATE estudiantes set nombre=?, apellido=?, fechaNac=?, matricula=? WHERE curp=?;";
            ps = conn.prepareStatement(query);

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getFechaNac());
            ps.setString(4, estudiante.getMatricula());
            ps.setString(5, estudiante.getCurp());

            if (ps.executeUpdate() == 1){
                return new Response<>(200,"Actualizado correctamente", estudiante, false);
            }else{
                return new Response<>(400,"Error al registrar", estudiante,true);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoEstudiante.class.getName()).log(Level.SEVERE, "Error -> save: "+e.getMessage());
            return new Response<>(400, "Error con el servidor", null, true);

        }finally {
            client.close(conn,ps,rs);
        }
    }

    @Override
    public Response<BeanEstudiante> remove(Long id) {
        return null;
    }

    public boolean validarCurp(String curp){
        try{
            conn = client.getConnection();
            String query = "SELECT * FROM estudiantes WHERE curp = ?;";
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

    public boolean validarMatricula(String matricula){

        try{

            conn = client.getConnection();
            String query = "SELECT * FROM estudiantes WHERE matricula = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, matricula);
            rs = ps.executeQuery();

            if(rs.next()){
                return true;

            }

        }catch (Exception e){
            System.out.println("Error -> validarMatricula"+ e.getMessage());

        } finally {
            client.close(conn,ps,rs);

        }

        return false;
    }
}
