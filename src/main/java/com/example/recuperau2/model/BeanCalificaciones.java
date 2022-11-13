package com.example.recuperau2.model;

public class BeanCalificaciones {

    String materia;
    int calificacion;

    BeanEstudiante estudiante;

    public BeanCalificaciones(String materia, int calificacion, BeanEstudiante estudiante) {
        this.materia = materia;
        this.calificacion = calificacion;
        this.estudiante = estudiante;
    }

    public BeanCalificaciones() {

    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public BeanEstudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(BeanEstudiante estudiante) {
        this.estudiante = estudiante;
    }
}