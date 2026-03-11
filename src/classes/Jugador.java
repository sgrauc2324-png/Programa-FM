package classes;

import java.time.LocalDate;
import java.util.Random;

public class Jugador extends Persona {
    public enum Posicion {
        POR, DEF, MIG, DAV
    }

    private int dorsal;
    private Posicion posicion;
    private double calidad;

    public Jugador(String nombre, String apellido, LocalDate fechaNacimiento, double sueldo, int dorsal, Posicion posicion) {
        super(nombre, apellido, fechaNacimiento, 5.0, sueldo);
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.calidad = generarCalidadAleatoria();
    }

    private double generarCalidadAleatoria() {
        return 30 + (Math.random() * (100 - 30));
    }

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

    @Override
    public String toString() {
        return "JUGADOR," + getNombre() + "," + getApellido() + "," + getFechaNacimiento() + "," + getSueldo() + "," + dorsal + "," + posicion + "," + calidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dorsal == jugador.dorsal && getNombre().equalsIgnoreCase(jugador.getNombre());
    }

    @Override
    public int hashCode() {
        int result = getNombre().toLowerCase().hashCode();
        result = 31 * result + dorsal;
        return result;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public double getCalidad() {
        return calidad;
    }

    public void setCalidad(double calidad) {
        this.calidad = calidad;
    }
}