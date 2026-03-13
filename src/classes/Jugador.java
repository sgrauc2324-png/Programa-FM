package classes;

import java.time.LocalDate;
import java.util.Random;

/**
 * Representa a un jugador de fútbol dentro del sistema, hereda de la clase Persona.
 */
public class Jugador extends Persona {

    /**
     * Enumeración que define las posiciones posibles de un jugador en el campo.
     */
    public enum Posicion {
        POR, DEF, MIG, DAV
    }

    private int dorsal;
    private Posicion posicion;
    private double calidad;

    /**
     * Constructor para crear y dar de alta a un nuevo jugador.
     * @param nombre Nombre del jugador.
     * @param apellido Apellido del jugador.
     * @param fechaNacimiento Fecha de nacimiento del jugador.
     * @param sueldo Sueldo anual del jugador.
     * @param dorsal Número de dorsal asignado.
     * @param posicion Posición en la que juega.
     */
    public Jugador(String nombre, String apellido, LocalDate fechaNacimiento, double sueldo, int dorsal, Posicion posicion) {
        super(nombre, apellido, fechaNacimiento, 5.0, sueldo);
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.calidad = generarCalidadAleatoria();
    }

    /**
     * Genera un valor de calidad aleatorio entre 30 y 100 para inicializar al jugador.
     * @return Un valor decimal representando la calidad inicial.
     */
    private double generarCalidadAleatoria() {
        return 30 + (Math.random() * (100 - 30));
    }

    /**
     * Ocasionalmente (5% de probabilidad) cambia la posición del jugador de forma aleatoria a una nueva,
     * y aumenta su calidad en 1 punto (hasta un máximo de 100).
     */
    public void canviDePosicio() {
        Random rand = new Random();
        if (rand.nextInt(100) < 5) {
            Posicion antiguaPosicion = this.posicion;
            Posicion[] posiciones = Posicion.values();
            Posicion nuevaPosicion;

            do {
                nuevaPosicion = posiciones[rand.nextInt(posiciones.length)];
            } while (nuevaPosicion == antiguaPosicion);

            this.posicion = nuevaPosicion;
            this.calidad += 1;
            if (this.calidad > 100) this.calidad = 100;

            System.out.println("Cambio de posicion! " + getNombre() + " pasa de " + antiguaPosicion + " a " + this.posicion + ". Calidad aumentada a: " + this.calidad);
        }
    }

    /**
     * Ejecuta la rutina de entrenamiento del jugador.
     * Llama al entrenamiento base para la motivación y aumenta la calidad del jugador de forma aleatoria.
     */
    @Override
    public void entrenament() {
        super.entrenament();
        Random rand = new Random();
        int probabilidad = rand.nextInt(100);
        double incremento;

        if (probabilidad < 70) {
            incremento = 0.1;
        } else if (probabilidad < 90) {
            incremento = 0.2;
        } else {
            incremento = 0.3;
        }

        this.calidad += incremento;
        if (this.calidad > 100) this.calidad = 100;

        System.out.println("Entrenamiento finalizado. Incremento de calidad: " + incremento);
    }

    /**
     * Devuelve una representación en formato de cadena de texto (CSV) de los datos del jugador.
     * @return Cadena con los datos del jugador separados por comas.
     */
    @Override
    public String toString() {
        return "JUGADOR," + getNombre() + "," + getApellido() + "," + getFechaNacimiento() + "," + getSueldo() + "," + dorsal + "," + posicion + "," + calidad;
    }

    /**
     * Compara si dos jugadores son iguales basándose en su nombre y dorsal.
     * @param o Objeto a comparar.
     * @return true si son el mismo jugador, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dorsal == jugador.dorsal && getNombre().equalsIgnoreCase(jugador.getNombre());
    }

    /**
     * Genera un código hash para el jugador basándose en su nombre y dorsal.
     * @return Código hash entero.
     */
    @Override
    public int hashCode() {
        int result = getNombre().toLowerCase().hashCode();
        result = 31 * result + dorsal;
        return result;
    }

    /**
     * Obtiene el número de dorsal del jugador.
     * @return El dorsal del jugador.
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Asigna un nuevo número de dorsal al jugador.
     * @param dorsal El nuevo dorsal.
     */
    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    /**
     * Obtiene la posición en el campo del jugador.
     * @return La posición actual.
     */
    public Posicion getPosicion() {
        return posicion;
    }

    /**
     * Asigna una nueva posición en el campo al jugador.
     * @param posicion La nueva posición.
     */
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    /**
     * Obtiene el nivel de calidad actual del jugador.
     * @return El valor de calidad.
     */
    public double getCalidad() {
        return calidad;
    }

    /**
     * Asigna un nuevo nivel de calidad al jugador.
     * @param calidad El nuevo valor de calidad.
     */
    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }
}