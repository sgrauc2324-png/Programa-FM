package com.politecnics.footballmanager.model;

import java.time.LocalDate;

public abstract class Persona {

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    protected double motivacion;
    protected double sueldo;

    public Persona(String nombre, String apellido, LocalDate fechaNacimiento, double motivacion, double sueldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.motivacion = motivacion;
        this.sueldo = sueldo;
    }

    public void entrenament() {
        this.motivacion += 0.2;
        if (this.motivacion > 10) {
            this.motivacion = 10;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public double getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(double motivacion) {
        this.motivacion = motivacion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
}