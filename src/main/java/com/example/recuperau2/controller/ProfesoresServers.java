package com.example.recuperau2.controller;

import com.example.recuperau2.model.BeanProfesor;
import com.example.recuperau2.model.DaoProfesor;
import com.example.recuperau2.utils.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/profesores")
public class ProfesoresServers {
    Map<String, Object> response = new HashMap<>();

    @GET
    @Path("/")
    @Produces(value = {"application/json"})
    public List<BeanProfesor> getAll() {
        return new DaoProfesor().findAll();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> save(BeanProfesor profesor){
        System.out.println(profesor.getNombre());
        Response<BeanProfesor> result = new DaoProfesor().save(profesor);
        response.put("result", result);
        return response;
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> update(BeanProfesor profesor){
        System.out.println(profesor.getNombre());
        Response<BeanProfesor> result = new DaoProfesor().update(profesor);
        response.put("result", result);
        return response;
    }
}