package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String nombre;
    private int añoFundacion;
    private String ciudad;
    private String estadio;
    private String presidente;
    private Entrenador entrenador;
    private List<Jugador> jugadores;

    public Equipo(String nombre, int añoFundacion, String ciudad) {
        this.nombre = nombre;
        this.añoFundacion = añoFundacion;
        this.ciudad = ciudad;
        this.jugadores = new ArrayList<>();
    }

    public Equipo(String nombre, int añoFundacion, String ciudad, String estadio, String presidente) {
        this(nombre, añoFundacion, ciudad);
        this.estadio = estadio;
        this.presidente = presidente;
    }

    public double calcularCalidadMedia() {
        if (jugadores.isEmpty()) {
            return 0;
        }
        double sumaCalidad = 0;
        for (Jugador j : jugadores) {
            sumaCalidad += j.getCalidad();
        }
        return sumaCalidad / jugadores.size();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAñoFundacion() {
        return añoFundacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}