package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo de futbol con su informacion basica y su plantilla de jugadores.
 */
public class Equipo {

    private String nombre;
    private int añoFundacion;
    private String ciudad;
    private String estadio;
    private String presidente;
    private Entrenador entrenador;
    private List<Jugador> jugadores;

    /**
     * Constructor para inicializar un equipo con los datos minimos.
     * @param nombre Nombre del equipo.
     * @param añoFundacion Año de fundacion del equipo.
     * @param ciudad Ciudad a la que pertenece el equipo.
     */
    public Equipo(String nombre, int añoFundacion, String ciudad) {
        this.nombre = nombre;
        this.añoFundacion = añoFundacion;
        this.ciudad = ciudad;
        this.jugadores = new ArrayList<>();
        this.estadio = "No asignado";
        this.presidente = "No asignado";
    }

    /**
     * Constructor para inicializar un equipo con todos sus datos.
     * @param nombre Nombre del equipo.
     * @param añoFundacion Año de fundacion del equipo.
     * @param ciudad Ciudad a la que pertenece el equipo.
     * @param estadio Nombre del estadio del equipo.
     * @param presidente Nombre del presidente o presidenta del equipo.
     */
    public Equipo(String nombre, int añoFundacion, String ciudad, String estadio, String presidente) {
        this(nombre, añoFundacion, ciudad);
        this.estadio = (estadio == null || estadio.isEmpty()) ? "No asignado" : estadio;
        this.presidente = (presidente == null || presidente.isEmpty()) ? "No asignado" : presidente;
    }

    /**
     * Calcula la calidad media de los jugadores que componen la plantilla del equipo.
     * @return La calidad media calculada, o 0.0 si el equipo no tiene jugadores.
     */
    public double calcularCalidadMedia() {
        if (jugadores == null || jugadores.isEmpty()) {
            return 0.0;
        }
        double sumaCalidad = 0;
        for (Jugador j : jugadores) {
            sumaCalidad += j.getCalidad();
        }
        return sumaCalidad / jugadores.size();
    }

    /**
     * Genera una cadena de texto con los datos del equipo en formato estructurado.
     * @return Cadena de texto con los datos del equipo separados por punto y coma.
     */
    @Override
    public String toString() {
        return "DATOS_EQUIPO;" + nombre + ";" + añoFundacion + ";" + ciudad + ";" + estadio + ";" + presidente;
    }

    /**
     * Obtiene el nombre del equipo.
     * @return Nombre del equipo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el equipo.
     * @param nombre El nuevo nombre del equipo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el año de fundacion del equipo.
     * @return Año de fundacion.
     */
    public int getAñoFundacion() {
        return añoFundacion;
    }

    /**
     * Obtiene la ciudad del equipo.
     * @return Nombre de la ciudad.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Obtiene el nombre del estadio del equipo.
     * @return Nombre del estadio.
     */
    public String getEstadio() {
        return estadio;
    }

    /**
     * Establece un nuevo estadio para el equipo.
     * @param estadio El nuevo nombre del estadio.
     */
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    /**
     * Obtiene el nombre del presidente o presidenta del equipo.
     * @return Nombre del presidente.
     */
    public String getPresidente() {
        return presidente;
    }

    /**
     * Establece un nuevo presidente o presidenta para el equipo.
     * @param presidente El nuevo nombre del presidente.
     */
    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    /**
     * Obtiene el entrenador actual del equipo.
     * @return Objeto Entrenador asignado al equipo, o null si no tiene.
     */
    public Entrenador getEntrenador() {
        return entrenador;
    }

    /**
     * Asigna un nuevo entrenador al equipo.
     * @param entrenador El objeto Entrenador a asignar.
     */
    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    /**
     * Obtiene la lista de jugadores que componen la plantilla del equipo.
     * @return Lista de objetos Jugador.
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Establece una nueva plantilla de jugadores para el equipo.
     * @param jugadores La nueva lista de jugadores.
     */
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}