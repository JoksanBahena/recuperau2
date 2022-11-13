package com.example.recuperau2.model;

public class BeanEstudiante {
    Long id;
    BeanProfesor curpProfesor;
    String nombre;
    String apellido;
    String fechaNac;
    String curp;
    String matricula;

    public BeanEstudiante() {
    }

    public BeanEstudiante(Long id, BeanProfesor curpProfesor, String nombre, String apellido, String fechaNac, String curp, String matricula) {
        this.id = id;
        this.curpProfesor = curpProfesor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.curp = curp;
        this.matricula = matricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BeanProfesor getCurpProfesor() {
        return curpProfesor;
    }

    public void setCurpProfesor(BeanProfesor curpProfesor) {
        this.curpProfesor = curpProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
