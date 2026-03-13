package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Gestiona la competicion entre una lista de equipos y su clasificacion.
 */
public class Liga {

    private String nombre;
    private int cantidadEquipos;
    private List<Equipo> equipos;
    private List<EstadisticaEquipo> clasificacion;

    /**
     * Constructor para crear una nueva liga.
     * @param nombre Nombre de la liga.
     * @param cantidadEquipos Numero maximo de equipos que participaran.
     */
    public Liga(String nombre, int cantidadEquipos) {
        this.nombre = nombre;
        this.cantidadEquipos = cantidadEquipos;
        this.equipos = new ArrayList<>();
        this.clasificacion = new ArrayList<>();
    }

    /**
     * Inscribe a un equipo en la liga si hay espacio y no esta ya registrado.
     * @param equipo Objeto Equipo a inscribir.
     */
    public void añadirEquipo(Equipo equipo) {
        if (equipos.size() < cantidadEquipos && !equipos.contains(equipo)) {
            equipos.add(equipo);
            clasificacion.add(new EstadisticaEquipo(equipo));
        }
    }

    /**
     * Simula todos los partidos posibles entre los equipos registrados en la liga.
     */
    public void disputarPartidos() {
        Random rand = new Random();
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                jugarPartido(clasificacion.get(i), clasificacion.get(j), rand);
            }
        }
    }

    /**
     * Simula un partido individual entre dos equipos calculando los goles en funcion de sus calidades medias y la motivacion de sus entrenadores.
     * @param local Estadisticas del equipo local.
     * @param visitante Estadisticas del equipo visitante.
     * @param rand Objeto Random para la generacion de probabilidades.
     */
    private void jugarPartido(EstadisticaEquipo local, EstadisticaEquipo visitante, Random rand) {
        double motivacionLocal = (local.getEquipo().getEntrenador() != null) ? local.getEquipo().getEntrenador().getMotivacion() : 5.0;
        double motivacionVisitante = (visitante.getEquipo().getEntrenador() != null) ? visitante.getEquipo().getEntrenador().getMotivacion() : 5.0;

        double calidadLocal = local.getEquipo().calcularCalidadMedia() + (motivacionLocal * 0.5);
        double calidadVisitante = visitante.getEquipo().calcularCalidadMedia() + (motivacionVisitante * 0.5);

        int golesLocal = rand.nextInt((int) (calidadLocal / 10) + 1);
        int golesVisitante = rand.nextInt((int) (calidadVisitante / 10) + 1);

        local.registrarPartido(golesLocal, golesVisitante);
        visitante.registrarPartido(golesVisitante, golesLocal);
    }

    /**
     * Ordena la lista de equipos por puntos y diferencia de goles, mostrando la tabla resultante por consola.
     */
    public void mostrarClasificacion() {
        clasificacion.sort(Comparator.comparingInt(EstadisticaEquipo::getPuntos)
                .thenComparingInt(e -> e.getGolesFavor() - e.getGolesContra())
                .reversed());

        System.out.println("\n--- Clasificacion: " + nombre + " ---");
        for (EstadisticaEquipo e : clasificacion) {
            System.out.println(String.format("%s | Puntos: %d | PJ: %d | GF: %d | GC: %d",
                    e.getEquipo().getNombre(), e.getPuntos(), e.getPartidosJugados(), e.getGolesFavor(), e.getGolesContra()));
        }
    }

    /**
     * Clase interna privada que almacena y gestiona las estadisticas de un equipo dentro de la liga.
     */
    private class EstadisticaEquipo {
        private Equipo equipo;
        private int puntos, partidosJugados, golesFavor, golesContra;

        /**
         * Constructor para inicializar las estadisticas de un equipo a cero.
         * @param equipo El equipo al que pertenecen estas estadisticas.
         */
        public EstadisticaEquipo(Equipo equipo) {
            this.equipo = equipo;
        }

        /**
         * Actualiza los datos estadisticos tras disputar un partido.
         * @param favor Goles marcados por el equipo.
         * @param contra Goles recibidos por el equipo.
         */
        public void registrarPartido(int favor, int contra) {
            this.partidosJugados++;
            this.golesFavor += favor;
            this.golesContra += contra;
            if (favor > contra) this.puntos += 3;
            else if (favor == contra) this.puntos += 1;
        }

        public Equipo getEquipo() { return equipo; }
        public int getPuntos() { return puntos; }
        public int getGolesFavor() { return golesFavor; }
        public int getGolesContra() { return golesContra; }
        public int getPartidosJugados() { return partidosJugados; }
    }
}