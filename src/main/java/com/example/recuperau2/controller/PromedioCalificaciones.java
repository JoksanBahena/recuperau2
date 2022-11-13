package com.example.recuperau2.controller;

import com.example.recuperau2.model.BeanCalificaciones;
import com.example.recuperau2.model.DaoProfesor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/api/promedio")
public class PromedioCalificaciones {

    @GET
    @Path("/")
    @Produces(value = {"application/json"})
    public List<BeanCalificaciones> getAll() {
        return new DaoProfesor().promedio();

    }
}
