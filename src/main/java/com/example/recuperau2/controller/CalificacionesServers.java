package com.example.recuperau2.controller;

import com.example.recuperau2.model.BeanCalificaciones;
import com.example.recuperau2.model.DaoProfesor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/calificaciones")
public class CalificacionesServers {
    Map<String, Object> response = new HashMap<>();

    @GET
    @Path("/")
    @Produces(value = {"application/json"})
    public List<BeanCalificaciones> getAll() {
        return new DaoProfesor().getCalificaciones();
    }
}
