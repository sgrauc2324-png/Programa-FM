package classes;

import java.time.LocalDate;

public class Entrenador extends Persona {

    private int torneosGanados;
    private boolean esSeleccionador;

    public Entrenador(String nombre, String apellido, LocalDate fechaNacimiento, double sueldo, int torneosGanados, boolean esSeleccionador) {
        super(nombre, apellido, fechaNacimiento, 5.0, sueldo);
        this.torneosGanados = torneosGanados;
        this.esSeleccionador = esSeleccionador;
    }

    public void incrementarSou() {
        this.sueldo += this.sueldo * 0.005;
    }

    @Override
    public void entrenament() {
        if (this.esSeleccionador) {
            this.motivacion += 0.3;
        } else {
            this.motivacion += 0.15;
        }

        if (this.motivacion > 10) {
            this.motivacion = 10;
        }
    }

    public int getTorneosGanados() {
        return torneosGanados;
    }

    public void setTorneosGanados(int torneosGanados) {
        this.torneosGanados = torneosGanados;
    }

    public boolean isEsSeleccionador() {
        return esSeleccionador;
    }

    public void setEsSeleccionador(boolean esSeleccionador) {
        this.esSeleccionador = esSeleccionador;
    }
}