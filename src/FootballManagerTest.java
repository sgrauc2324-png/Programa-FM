import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import classes.Equipo;
import classes.Jugador;

public class FootballManagerTest {

    private List<Equipo> equipos;
    private List<Jugador> mercadoJugadores;

    @BeforeEach
    void setUp() {
        equipos = new ArrayList<>();
        mercadoJugadores = new ArrayList<>();
    }

    @Test
    void testAltaEquipoDuplicado() {
        Equipo e1 = new Equipo("FC Barcelona", 1899, "Barcelona", "Camp Nou", "Joan Laporta");
        equipos.add(e1);

        String nombreNuevo = "FC Barcelona";
        boolean existe = false;
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombreNuevo)) {
                existe = true;
                break;
            }
        }

        assertTrue(existe);
    }

    @Test
    void testTransferenciaJugadorEntreEquipos() {
        Equipo origen = new Equipo("Origen CF", 2000, "Ciudad A", "Estadio A", "Presi A");
        Equipo destino = new Equipo("Destino CF", 2005, "Ciudad B", "Estadio B", "Presi B");
        Jugador j1 = new Jugador("Leo", "Messi", LocalDate.of(1987, 6, 24), 50000000, 10, Jugador.Posicion.DAV);

        origen.getJugadores().add(j1);

        Jugador aTransferir = origen.getJugadores().get(0);
        origen.getJugadores().remove(aTransferir);
        destino.getJugadores().add(aTransferir);

        assertAll("Transferencia",
                () -> assertEquals(0, origen.getJugadores().size()),
                () -> assertEquals(1, destino.getJugadores().size()),
                () -> assertEquals("Leo", destino.getJugadores().get(0).getNombre())
        );
    }

    @Test
    void testFichajeDesdeMercado() {
        Equipo equipo = new Equipo("Team Test", 2020, "VLC", "Mestalla", "Peter");
        Jugador libre = new Jugador("Libre", "Player", LocalDate.of(1995, 1, 1), 1000, 7, Jugador.Posicion.MIG);
        mercadoJugadores.add(libre);

        Jugador fichado = mercadoJugadores.remove(0);
        equipo.getJugadores().add(fichado);

        assertNotNull(equipo.getJugadores().get(0));
        assertTrue(mercadoJugadores.isEmpty());
    }

    @Test
    void testValidacionDorsalOcupado() {
        Equipo equipo = new Equipo("Dorsal Team", 2010, "Madrid", "Estadio", "Presi");
        equipo.getJugadores().add(new Jugador("P1", "A1", LocalDate.now(), 1000, 10, Jugador.Posicion.POR));

        int nuevoDorsal = 10;
        boolean ocupado = false;
        for (Jugador j : equipo.getJugadores()) {
            if (j.getDorsal() == nuevoDorsal) {
                ocupado = true;
                break;
            }
        }

        assertEquals(true, ocupado);
    }
}