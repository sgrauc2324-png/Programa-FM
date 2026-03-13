import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;
import classes.Equipo;
import classes.Jugador;
import classes.Entrenador;

/**
 * Clase principal que gestiona la aplicacion Politecnics Football Manager.
 * Contiene las listas globales de equipos y personal en el mercado de fichajes.
 */
public class Main {
    private static List<Equipo> equipos = new ArrayList<>();
    private static List<Jugador> mercadoJugadores = new ArrayList<>();
    private static List<Entrenador> mercadoEntrenadores = new ArrayList<>();

    /**
     * Punto de entrada principal de la aplicacion.
     * @param args Argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        crearDirectorioTxtSiNoExiste();
        int usertype = login();
        menus(usertype);
    }

    /**
     * Verifica la existencia del directorio de guardado de archivos y lo crea si no existe.
     */
    private static void crearDirectorioTxtSiNoExiste() {
        File dir = new File("src/txt");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Redirige al usuario al menu correspondiente segun su nivel de acceso.
     * @param usertype Nivel de acceso del usuario (1 para Admin, 2 para Manager).
     */
    private static void menus(int usertype) {
        if (usertype == 1) {
            menuAdmin();
        } else if (usertype == 2) {
            menuManager();
        }
    }

    /**
     * Muestra y gestiona las opciones del menu para el rol de Administrador.
     */
    public static void menuAdmin() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\nBienvenido a Politecnics Football Manager:");
            System.out.println("1- Ver clasificacion liga actual\n2- Dar de alta equipo\n3- Dar de alta jugador/a o entrenador/a");
            System.out.println("4- Consultar datos equipo\n5- Consultar datos jugador/a equipo\n6- Disputar nueva lliga");
            System.out.println("7- Realizar sesion entrenamiento (del mercado fichajes)\n8- Guardar datos equipos\n0- Salir");
            opcion = leerEntero(sc, "Selecciona una opcion: ");

            switch (opcion) {
                case 2: darAltaEquipo(sc); break;
                case 3: darAltaPersonaMercado(sc); break;
                case 7: realizarEntrenamientoMercado(); break;
                case 8: guardarEquipos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    /**
     * Muestra y gestiona las opciones del menu para el rol de Gestor de Equipo (Manager).
     */
    public static void menuManager() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\nBienvenido a Politecnics Football Manager:");
            System.out.println("1- Ver clasificacion liga actual\n2- Gestionar mi equipo\n3- Consultar datos equipo");
            System.out.println("4- Consultar datos jugador/a equipo\n5- Transferir jugador/a\n6- Guardar datos equipos\n0- Salir");
            opcion = leerEntero(sc, "Selecciona una opcion: ");

            switch (opcion) {
                case 2:
                    String nombreBuscar = leerTexto(sc, "Introduce el nombre del equipo a gestionar:");
                    Equipo eq = null;
                    for (Equipo e : equipos) {
                        if (e.getNombre().equalsIgnoreCase(nombreBuscar)) {
                            eq = e; break;
                        }
                    }
                    if (eq == null) System.out.println("Error: El equipo no se encuentra en el sistema.");
                    else subMenuGestionarEquipo(sc, eq);
                    break;
                case 5: transferirJugador(sc); break;
                case 6: guardarEquipos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    /**
     * Solicita los datos necesarios por consola para registrar un nuevo equipo en el sistema.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     */
    public static void darAltaEquipo(Scanner sc) {
        String nombre;
        boolean existe;
        do {
            existe = false;
            nombre = leerTexto(sc, "Introduce el nombre del nuevo equipo:");
            for (Equipo e : equipos) {
                if (e.getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("Error: El equipo ya existe.");
                    existe = true; break;
                }
            }
        } while (existe);

        int año = leerEntero(sc, "Introduce el año de fundacion:");
        String ciudad = leerTexto(sc, "Introduce la ciudad:");

        System.out.println("¿Quieres introducir el nombre del estadio? (si/no)");
        String estadio = sc.nextLine().trim().equalsIgnoreCase("si") ? leerTexto(sc, "Introduce el nombre del estadio:") : "No asignado";

        System.out.println("¿Quieres introducir el nombre del presidente/a? (si/no)");
        String presidente = sc.nextLine().trim().equalsIgnoreCase("si") ? leerTexto(sc, "Introduce el nombre del presidente/a:") : "No asignado";

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
            System.out.println("Error al crear el archivo.");
        }
    }

    /**
     * Exporta la informacion de todos los equipos y sus plantillas a un archivo CSV.
     */
    public static void guardarEquipos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/txt/Equipos.csv"))) {
            for (Equipo e : equipos) {
                bw.write("EQUIPO," + e.getNombre() + "," + e.getAñoFundacion() + "," + e.getCiudad() + "," + e.getEstadio() + "," + e.getPresidente());
                bw.newLine();
                bw.write("PLANTILLA_JUGADORES:");
                bw.newLine();
                for (Jugador j : e.getJugadores()) {
                    bw.write(j.getNombre() + "," + j.getApellido() + "," + j.getDorsal() + "," + j.getPosicion() + "," + j.getCalidad());
                    bw.newLine();
                }
            }
            System.out.println("Archivo global actualizado.");
        } catch (IOException e) {
            System.out.println("Error al guardar.");
        }
    }

    /**
     * Permite crear un nuevo jugador o entrenador y añadirlo directamente al mercado de fichajes.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     */
    public static void darAltaPersonaMercado(Scanner sc) {
        int tipo;
        do {
            tipo = leerEntero(sc, "¿Quieres dar de alta un (1) Jugador/a o (2) Entrenador/a?");
        } while (tipo != 1 && tipo != 2);

        String nombre = leerTexto(sc, "Nombre:");
        String apellido = leerTexto(sc, "Apellido:");
        LocalDate fecha = leerFecha(sc, "Fecha nacimiento (AAAA-MM-DD):");
        double sueldo = leerDouble(sc, "Sueldo anual:");

        if (tipo == 1) {
            int dorsal = leerEntero(sc, "Dorsal:");
            int posIndice = -1;
            do {
                System.out.println("Selecciona posicion:");
                for (int i = 0; i < Jugador.Posicion.values().length; i++) {
                    System.out.println(i + "- " + Jugador.Posicion.values()[i]);
                }
                posIndice = leerEntero(sc, "");
                if (posIndice < 0 || posIndice >= Jugador.Posicion.values().length) {
                    posIndice = -1;
                    System.out.println("Error: Opcion fuera de rango.");
                }
            } while (posIndice == -1);

            mercadoJugadores.add(new Jugador(nombre, apellido, fecha, sueldo, dorsal, Jugador.Posicion.values()[posIndice]));
            System.out.println("Jugador/a añadido al mercado con exito.");
        } else {
            int torneos = leerEntero(sc, "Torneos ganados:");
            boolean sel = false;
            while (true) {
                String inputSel = leerTexto(sc, "¿Seleccionador? (true/false):").toLowerCase();
                if (inputSel.equals("true") || inputSel.equals("false")) {
                    sel = Boolean.parseBoolean(inputSel);
                    break;
                }
                System.out.println("Error: Escribe 'true' o 'false'.");
            }
            mercadoEntrenadores.add(new Entrenador(nombre, apellido, fecha, sueldo, torneos, sel));
            System.out.println("Entrenador/a añadido al mercado con exito.");
        }
    }

    /**
     * Aplica las rutinas de entrenamiento e incremento de atributos a todo el personal libre en el mercado.
     */
    public static void realizarEntrenamientoMercado() {
        for (Jugador j : mercadoJugadores) {
            j.entrenament();
            j.canviDePosicio();
        }
        for (Entrenador e : mercadoEntrenadores) {
            e.entrenament();
            e.incrementarSou();
        }
        System.out.println("Sesion de entrenamiento en el mercado de fichajes finalizada.");
    }

    /**
     * Gestiona el traspaso de un jugador de un equipo a otro, validando dorsales y existencias.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     */
    public static void transferirJugador(Scanner sc) {
        String nombreOrigen = leerTexto(sc, "Introduce el nombre del equipo de origen:");
        Equipo equipoOrigen = null;
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombreOrigen)) {
                equipoOrigen = e;
                break;
            }
        }
        if (equipoOrigen == null) {
            System.out.println("Error: El equipo de origen no existe.");
            return;
        }

        String nombreDestino = leerTexto(sc, "Introduce el nombre del equipo de destino:");
        Equipo equipoDestino = null;
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombreDestino)) {
                equipoDestino = e;
                break;
            }
        }
        if (equipoDestino == null) {
            System.out.println("Error: El equipo de destino no existe.");
            return;
        }

        String nombreJugador = leerTexto(sc, "Introduce el nombre del jugador a transferir:");
        int dorsalJugador = leerEntero(sc, "Introduce el dorsal del jugador:");

        Jugador jugadorATransferir = null;
        for (Jugador j : equipoOrigen.getJugadores()) {
            if (j.getNombre().equalsIgnoreCase(nombreJugador) && j.getDorsal() == dorsalJugador) {
                jugadorATransferir = j;
                break;
            }
        }

        if (jugadorATransferir == null) {
            System.out.println("Error: El jugador no se encuentra en el equipo de origen.");
            return;
        }

        boolean dorsalOcupado;
        int nuevoDorsal = dorsalJugador;
        do {
            dorsalOcupado = false;
            for (Jugador j : equipoDestino.getJugadores()) {
                if (j.getDorsal() == nuevoDorsal) {
                    dorsalOcupado = true;
                    nuevoDorsal = leerEntero(sc, "Error: Dorsal ocupado. Introduce un nuevo dorsal para el jugador:");
                    break;
                }
            }
        } while (dorsalOcupado);

        jugadorATransferir.setDorsal(nuevoDorsal);
        equipoOrigen.getJugadores().remove(jugadorATransferir);
        equipoDestino.getJugadores().add(jugadorATransferir);

        System.out.println("Jugador transferido exitosamente.");
    }

    /**
     * Muestra un menu especifico con opciones de gestion para un equipo concreto.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param equipo El objeto Equipo que se va a gestionar.
     */
    public static void subMenuGestionarEquipo(Scanner sc, Equipo equipo) {
        int opcion;
        do {
            System.out.println("\nTeam Manager: " + equipo.getNombre());
            System.out.println("1- Dar de baja al equipo\n2- Modificar presidente/a\n3- Destituir entrenador/a");
            System.out.println("4- Fichar jugador/a o entrenador/a\n0- Salir");
            opcion = leerEntero(sc, "Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    if (leerTexto(sc, "¿Estas seguro de dar de baja el equipo? (si/no):").equalsIgnoreCase("si")) {
                        equipos.remove(equipo);
                        System.out.println("Equipo eliminado.");
                        return;
                    }
                    break;
                case 2:
                    if (equipo.getPresidente().equals("No asignado")) System.out.println("Aviso: El equipo no tiene presidente.");
                    String nuevoPresi = leerTexto(sc, "Introduce el nombre del nuevo presidente/a:");
                    if (equipo.getPresidente().equalsIgnoreCase(nuevoPresi)) System.out.println("Aviso: Es la misma persona.");
                    equipo.setPresidente(nuevoPresi);
                    System.out.println("Presidente/a actualizado.");
                    break;
                case 3:
                    if (equipo.getEntrenador() == null) System.out.println("Error: El equipo no tiene entrenador/a.");
                    else if (leerTexto(sc, "¿Destituir al entrenador/a? (si/no):").equalsIgnoreCase("si")) {
                        mercadoEntrenadores.add(equipo.getEntrenador());
                        equipo.setEntrenador(null);
                        System.out.println("Entrenador/a destituido.");
                    }
                    break;
                case 4: ficharPersonal(sc, equipo); break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    /**
     * Muestra el mercado disponible y permite incorporar a un jugador o entrenador al equipo activo.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param equipo El objeto Equipo al que se unira el nuevo fichaje.
     */
    public static void ficharPersonal(Scanner sc, Equipo equipo) {
        int tipo = leerEntero(sc, "¿Que deseas fichar? (1) Jugador/a o (2) Entrenador/a:");
        if (tipo == 1) {
            if (mercadoJugadores.isEmpty()) {
                System.out.println("No hay jugadores/as disponibles."); return;
            }
            for (int i = 0; i < mercadoJugadores.size(); i++) {
                System.out.println(i + "- " + mercadoJugadores.get(i).getNombre() + " " + mercadoJugadores.get(i).getApellido());
            }
            int indice = leerEntero(sc, "Introduce el numero a fichar:");
            if (indice >= 0 && indice < mercadoJugadores.size()) {
                equipo.getJugadores().add(mercadoJugadores.remove(indice));
                System.out.println("Fichaje realizado.");
            } else System.out.println("Error: Seleccion no valida.");
        } else if (tipo == 2) {
            if (mercadoEntrenadores.isEmpty()) {
                System.out.println("No hay entrenadores/as disponibles."); return;
            }
            for (int i = 0; i < mercadoEntrenadores.size(); i++) {
                System.out.println(i + "- " + mercadoEntrenadores.get(i).getNombre() + " " + mercadoEntrenadores.get(i).getApellido());
            }
            int indice = leerEntero(sc, "Introduce el numero a fichar:");
            if (indice >= 0 && indice < mercadoEntrenadores.size()) {
                if (equipo.getEntrenador() != null) mercadoEntrenadores.add(equipo.getEntrenador());
                equipo.setEntrenador(mercadoEntrenadores.remove(indice));
                System.out.println("Fichaje realizado.");
            } else System.out.println("Error: Seleccion no valida.");
        } else System.out.println("Opcion no valida.");
    }

    /**
     * Gestiona el flujo de inicio de sesion o registro de usuarios al arrancar el programa.
     * @return El tipo de usuario logueado (1 para Admin, 2 para Manager).
     */
    public static int login() {
        List<String[]> lista = cargarUsuarios();
        int usertype = iniciarSesion(lista);
        return usertype == 0 ? crearCuenta() : usertype;
    }

    /**
     * Solicita credenciales para registrar un nuevo usuario y las guarda encriptadas.
     * @return El tipo de usuario de la cuenta recien creada.
     */
    private static int crearCuenta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Quieres crear una cuenta nueva? (Si/No)");
        if (sc.nextLine().trim().equalsIgnoreCase("si")) {
            String user = leerTexto(sc, "Introduce usuario:");
            String pass = leerTexto(sc, "Introduce password:");
            int type = leerEntero(sc, "Tipo (1-Admin, 2-Manager):");
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

    /**
     * Comprueba las credenciales introducidas contra la lista de usuarios registrados.
     * @param lista Lista de arrays de String que contiene los datos de los usuarios.
     * @return El tipo de usuario si el login es correcto, o 0 si falla.
     */
    public static int iniciarSesion(List<String[]> lista) {
        Scanner sc = new Scanner(System.in);
        String user = leerTexto(sc, "Usuario:");
        for (String[] fila : lista) {
            if (fila[0].equals(user)) {
                String pass = leerTexto(sc, "Password:");
                if (BCrypt.checkpw(pass, fila[1])) return Integer.parseInt(fila[2].trim());
            }
        }
        return 0;
    }

    /**
     * Lee el archivo de logins y carga los usuarios registrados en memoria.
     * @return Una lista con los datos de cada usuario.
     */
    public static List<String[]> cargarUsuarios() {
        List<String[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/txt/Login.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) lista.add(linea.split(","));
        } catch (IOException e) {}
        return lista;
    }

    /**
     * Solicita y valida la entrada de una cadena de texto, evitando que se deje en blanco.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param msj Mensaje a mostrar al usuario solicitando el dato.
     * @return La cadena de texto introducida y validada.
     */
    private static String leerTexto(Scanner sc, String msj) {
        String input;
        do {
            System.out.println(msj);
            input = sc.nextLine().trim();
            if (input.isEmpty()) System.out.println("Error: El campo no puede estar en blanco.");
        } while (input.isEmpty());
        return input;
    }

    /**
     * Solicita y valida la entrada de un numero entero, manejando posibles excepciones de formato.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param msj Mensaje a mostrar al usuario solicitando el dato.
     * @return El numero entero validado.
     */
    private static int leerEntero(Scanner sc, String msj) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(sc, msj));
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un numero valido.");
            }
        }
    }

    /**
     * Solicita y valida la entrada de un numero decimal, manejando posibles excepciones de formato.
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param msj Mensaje a mostrar al usuario solicitando el dato.
     * @return El numero decimal validado.
     */
    private static double leerDouble(Scanner sc, String msj) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(sc, msj));
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un numero valido.");
            }
        }
    }

    /**
     * Solicita y valida la entrada de una fecha con el formato correcto (AAAA-MM-DD).
     * @param sc Objeto Scanner para la lectura de datos por teclado.
     * @param msj Mensaje a mostrar al usuario solicitando el dato.
     * @return Objeto LocalDate con la fecha validada.
     */
    private static LocalDate leerFecha(Scanner sc, String msj) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(sc, msj));
            } catch (Exception e) {
                System.out.println("Error: Formato de fecha incorrecto.");
            }
        }
    }
}