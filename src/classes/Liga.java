package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Liga {

    private String nombre;
    private int cantidadEquipos;
    private List<Equipo> equipos;
    private List<EstadisticaEquipo> clasificacion;

    public Liga(String nombre, int cantidadEquipos) {
        this.nombre = nombre;
        this.cantidadEquipos = cantidadEquipos;
        this.equipos = new ArrayList<>();
        this.clasificacion = new ArrayList<>();
    }

    public void añadirEquipo(Equipo equipo) {
        if (equipos.size() < cantidadEquipos && !equipos.contains(equipo)) {
            equipos.add(equipo);
            clasificacion.add(new EstadisticaEquipo(equipo));
        }
    }

    public void disputarPartidos() {
        Random rand = new Random();
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                jugarPartido(clasificacion.get(i), clasificacion.get(j), rand);
            }
        }
    }

    private void jugarPartido(EstadisticaEquipo local, EstadisticaEquipo visitante, Random rand) {
        double calidadLocal = local.getEquipo().calcularCalidadMedia() + (local.getEquipo().getEntrenador().getMotivacion() * 0.5);
        double calidadVisitante = visitante.getEquipo().calcularCalidadMedia() + (visitante.getEquipo().getEntrenador().getMotivacion() * 0.5);

        int golesLocal = rand.nextInt((int) (calidadLocal / 10) + 1);
        int golesVisitante = rand.nextInt((int) (calidadVisitante / 10) + 1);

        local.registrarPartido(golesLocal, golesVisitante);
        visitante.registrarPartido(golesVisitante, golesLocal);
    }

    public void mostrarClasificacion() {
        clasificacion.sort(Comparator.comparingInt(EstadisticaEquipo::getPuntos)
                .thenComparingInt(e -> e.getGolesFavor() - e.getGolesContra())
                .reversed());

        System.out.println("\n--- Clasificación: " + nombre + " ---");
        for (EstadisticaEquipo e : clasificacion) {
            System.out.println(String.format("%s | Puntos: %d | PJ: %d | GF: %d | GC: %d",
                    e.getEquipo().getNombre(), e.getPuntos(), e.getPartidosJugados(), e.getGolesFavor(), e.getGolesContra()));
        }
    }

    private class EstadisticaEquipo {
        private Equipo equipo;
        private int puntos, partidosJugados, golesFavor, golesContra;

        public EstadisticaEquipo(Equipo equipo) {
            this.equipo = equipo;
        }

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