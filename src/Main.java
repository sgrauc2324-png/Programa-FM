import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;
import classes.Equipo;
import classes.Jugador;
import classes.Entrenador;

public class Main {
    private static List<Equipo> equipos = new ArrayList<>();
    private static List<Jugador> mercadoJugadores = new ArrayList<>();
    private static List<Entrenador> mercadoEntrenadores = new ArrayList<>();

    public static void main(String[] args) {
        int usertype = login();
        menus(usertype);
    }

    private static void menus(int usertype) {
        if (usertype == 1) {
            menuAdmin();
        } else if (usertype == 2) {
            menuManager();
        }
    }

    public static void menuAdmin() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nBienvenido a Politecnics Football Manager:");
            System.out.println("1- Ver clasificacion liga actual");
            System.out.println("2- Dar de alta equipo");
            System.out.println("3- Dar de alta jugador/a o entrenador/a");
            System.out.println("4- Consultar datos equipo");
            System.out.println("5- Consultar datos jugador/a equipo");
            System.out.println("6- Disputar nueva lliga");
            System.out.println("7- Realizar sesion entrenamiento (del mercado fichajes)");
            System.out.println("8- Guardar datos equipos");
            System.out.println("0- Salir");
            System.out.print("Selecciona una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 2: darAltaEquipo(sc); break;
                case 3: darAltaPersonaMercado(sc); break;
                case 8: guardarEquipos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public static void menuManager() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nBienvenido a Politecnics Football Manager:");
            System.out.println("1- Ver clasificacion liga actual");
            System.out.println("2- Gestionar mi equipo");
            System.out.println("3- Consultar datos equipo");
            System.out.println("4- Consultar datos jugador/a equipo");
            System.out.println("5- Transferir jugador/a");
            System.out.println("6- Guardar datos equipos");
            System.out.println("0- Salir");
            System.out.print("Selecciona una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 2: subMenuGestionarEquipo(); break;
                case 6: guardarEquipos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public static void darAltaEquipo(Scanner sc) {
        String nombre;
        boolean existe;
        do {
            existe = false;
            System.out.println("Introduce el nombre del nuevo equipo:");
            nombre = sc.nextLine();
            for (Equipo e : equipos) {
                if (e.getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("Error: El equipo ya existe.");
                    existe = true;
                    break;
                }
            }
        } while (existe);

        System.out.println("Introduce el año de fundacion:");
        int año = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la ciudad:");
        String ciudad = sc.nextLine();

        String estadio = "No asignado";
        System.out.println("¿Quieres introducir el nombre del estadio? (si/no)");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            System.out.println("Introduce el nombre del estadio:");
            estadio = sc.nextLine();
        }

        String presidente = "No asignado";
        System.out.println("¿Quieres introducir el nombre del presidente/a? (si/no)");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            System.out.println("Introduce el nombre del presidente/a:");
            presidente = sc.nextLine();
        }

        Equipo nuevoEquipo = new Equipo(nombre, año, ciudad, estadio, presidente);
        equipos.add(nuevoEquipo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/txt/" + nombre + ".txt"))) {
            bw.write("DATOS_EQUIPO;" + nombre + ";" + año + ";" + ciudad + ";" + estadio + ";" + presidente);
            bw.newLine();
            bw.write("PLANTILLA_JUGADORES:");
            bw.newLine();
            for (Jugador j : nuevoEquipo.getJugadores()) {
                bw.write(j.getNombre() + "," + j.getApellido() + "," + j.getDorsal() + "," + j.getPosicion() + "," + j.getCalidad());
                bw.newLine();
            }
            System.out.println("Equipo dado de alta y archivo " + nombre + ".txt generado con exito.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo del equipo.");
        }
    }

    public static void guardarEquipos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/txt/Equipos_Global.txt"))) {
            for (Equipo e : equipos) {
                bw.write("EQUIPO," + e.getNombre() + "," + e.getAñoFundacion() + "," + e.getCiudad() + "," + e.getEstadio() + "," + e.getPresidente());
                bw.newLine();
            }
            System.out.println("Archivo global actualizado.");
        } catch (IOException e) {
            System.out.println("Error al guardar.");
        }
    }

    public static void darAltaPersonaMercado(Scanner sc) {
        System.out.println("¿Quieres dar de alta un (1) Jugador/a o (2) Entrenador/a?");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido:");
        String apellido = sc.nextLine();
        System.out.println("Fecha nacimiento (AAAA-MM-DD):");
        LocalDate fecha = LocalDate.parse(sc.nextLine());
        System.out.println("Sueldo anual:");
        double sueldo = sc.nextDouble();
        sc.nextLine();

        if (tipo == 1) {
            System.out.println("Dorsal:");
            int dorsal = sc.nextInt();
            sc.nextLine();
            System.out.println("Selecciona posicion:");
            for (int i = 0; i < Jugador.Posicion.values().length; i++) {
                System.out.println(i + "- " + Jugador.Posicion.values()[i]);
            }
            int posIndice = sc.nextInt();
            sc.nextLine();
            mercadoJugadores.add(new Jugador(nombre, apellido, fecha, sueldo, dorsal, Jugador.Posicion.values()[posIndice]));
        } else if (tipo == 2) {
            System.out.println("Torneos ganados:");
            int torneos = sc.nextInt();
            sc.nextLine();
            System.out.println("¿Seleccionador? (true/false):");
            boolean sel = sc.nextBoolean();
            sc.nextLine();
            mercadoEntrenadores.add(new Entrenador(nombre, apellido, fecha, sueldo, torneos, sel));
        }
    }

    public static void subMenuGestionarEquipo() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\nTeam Manager:");
            System.out.println("1- Dar de baja al equipo");
            System.out.println("2- Modificar presidente/a");
            System.out.println("3- Destituir entrenador/a");
            System.out.println("4- Fichar jugador/a o entrenador/a");
            System.out.println("0- Salir");
            opcion = sc.nextInt();
            sc.nextLine();
        } while (opcion != 0);
    }

    public static int login() {
        List<String[]> listaDeFilas = cargarUsuarios();
        int usertype = iniciarSesion(listaDeFilas);
        if (usertype == 0) usertype = crearCuenta();
        return usertype;
    }

    private static int crearCuenta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Quieres crear una cuenta nueva? (Si/No)");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            System.out.println("Introduce usuario:");
            String user = sc.nextLine();
            System.out.println("Introduce password:");
            String pass = sc.nextLine();
            System.out.println("Tipo (1-Admin, 2-Manager):");
            int type = sc.nextInt();
            sc.nextLine();
            String hashed = BCrypt.hashpw(pass, BCrypt.gensalt(12));
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/txt/Login.csv", true))) {
                bw.write(user + "," + hashed + "," + type);
                bw.newLine();
            } catch (IOException e) {}
            return type;
        }
        System.exit(0);
        return 0;
    }

    public static int iniciarSesion(List<String[]> listaDeFilas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Usuario:");
        String user = sc.nextLine();
        for (String[] fila : listaDeFilas) {
            if (fila[0].equals(user)) {
                System.out.println("Password:");
                String pass = sc.nextLine();
                if (BCrypt.checkpw(pass, fila[1])) return Integer.parseInt(fila[2].trim());
            }
        }
        return 0;
    }

    public static List<String[]> cargarUsuarios() {
        List<String[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/txt/Login.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) lista.add(linea.split(","));
        } catch (IOException e) {}
        return lista;
    }
}