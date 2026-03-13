package classes;

import java.time.LocalDate;

/**
 * Representa a un entrenador de fútbol dentro del sistema, hereda de la clase Persona.
 */
public class Entrenador extends Persona {

    private int torneosGanados;
    private boolean esSeleccionador;

    /**
     * Constructor para inicializar los datos de un nuevo entrenador.
     * @param nombre Nombre del entrenador.
     * @param apellido Apellido del entrenador.
     * @param fechaNacimiento Fecha de nacimiento del entrenador.
     * @param sueldo Sueldo anual asignado.
     * @param torneosGanados Cantidad de torneos ganados en su carrera.
     * @param esSeleccionador Indica si el entrenador es también seleccionador nacional.
     */
    public Entrenador(String nombre, String apellido, LocalDate fechaNacimiento, double sueldo, int torneosGanados, boolean esSeleccionador) {
        super(nombre, apellido, fechaNacimiento, 5.0, sueldo);
        this.torneosGanados = torneosGanados;
        this.esSeleccionador = esSeleccionador;
    }

    /**
     * Aumenta el sueldo actual del entrenador en un 0.5%.
     */
    public void incrementarSou() {
        this.sueldo += this.sueldo * 0.005;
    }

    /**
     * Incrementa la motivación del entrenador.
     * El incremento es de 0.3 si es seleccionador, o de 0.15 si no lo es, hasta un límite máximo de 10.0.
     */
    @Override
    public void entrenament() {
        if (this.esSeleccionador) {
            this.motivacion += 0.3;
        } else {
            this.motivacion += 0.15;
        }

        if (this.motivacion > 10.0) {
            this.motivacion = 10.0;
        }
    }

    /**
     * Devuelve una representación en formato de cadena de texto de los datos del entrenador.
     * @return Cadena con los datos separados por comas (formato CSV).
     */
    @Override
    public String toString() {
        return "ENTRENADOR," + getNombre() + "," + getApellido() + "," + getFechaNacimiento() + "," + getSueldo() + "," + torneosGanados + "," + esSeleccionador;
    }

    /**
     * Obtiene la cantidad de torneos ganados por el entrenador.
     * @return Número de torneos ganados.
     */
    public int getTorneosGanados() {
        return torneosGanados;
    }

    /**
     * Establece una nueva cantidad de torneos ganados.
     * @param torneosGanados Nuevo número de torneos ganados.
     */
    public void setTorneosGanados(int torneosGanados) {
        this.torneosGanados = torneosGanados;
    }

    /**
     * Indica si el entrenador ejerce también como seleccionador.
     * @return true si es seleccionador, false en caso contrario.
     */
    public boolean isEsSeleccionador() {
        return esSeleccionador;
    }

    /**
     * Establece si el entrenador ejerce como seleccionador nacional.
     * @param esSeleccionador true para asignarlo como seleccionador, false en caso contrario.
     */
    public void setEsSeleccionador(boolean esSeleccionador) {
        this.esSeleccionador = esSeleccionador;
    }
}