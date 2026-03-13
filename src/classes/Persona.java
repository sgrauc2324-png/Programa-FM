package classes;

import java.time.LocalDate;

/**
 * Clase abstracta que representa a una persona genérica dentro del sistema.
 * Contiene los atributos y métodos comunes para jugadores y entrenadores.
 */
public abstract class Persona {

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    protected double motivacion;
    protected double sueldo;

    /**
     * Constructor para inicializar los datos básicos de una persona.
     * @param nombre Nombre de la persona.
     * @param apellido Apellido de la persona.
     * @param fechaNacimiento Fecha de nacimiento de la persona.
     * @param motivacion Nivel de motivacion inicial.
     * @param sueldo Sueldo anual asignado a la persona.
     */
    public Persona(String nombre, String apellido, LocalDate fechaNacimiento, double motivacion, double sueldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.motivacion = motivacion;
        this.sueldo = sueldo;
    }

    /**
     * Incrementa la motivacion de la persona tras realizar una sesion de entrenamiento.
     * El nivel de motivacion tiene un limite maximo establecido en 10.
     */
    public void entrenament() {
        this.motivacion += 0.2;
        if (this.motivacion > 10) {
            this.motivacion = 10;
        }
    }

    /**
     * Obtiene el nombre de la persona.
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido de la persona.
     * @return El apellido de la persona.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     * @return Objeto LocalDate con la fecha de nacimiento.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Obtiene el nivel de motivacion actual de la persona.
     * @return El valor numerico de la motivacion.
     */
    public double getMotivacion() {
        return motivacion;
    }

    /**
     * Establece un nuevo nivel de motivacion para la persona.
     * @param motivacion El nuevo valor de motivacion a asignar.
     */
    public void setMotivacion(double motivacion) {
        this.motivacion = motivacion;
    }

    /**
     * Obtiene el sueldo anual asignado a la persona.
     * @return El sueldo anual.
     */
    public double getSueldo() {
        return sueldo;
    }

    /**
     * Establece un nuevo sueldo anual para la persona.
     * @param sueldo El nuevo valor del sueldo anual.
     */
    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
}