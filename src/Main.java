import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main() {
        int usertype = login();

        System.out.println("Sesión iniciada con tipo: " + usertype);
    }

    public static int login() {
        List<String[]> listaDeFilas;
        int usertype;

        listaDeFilas = cargarUsuarios();
        usertype = iniciarSesion(listaDeFilas);
        if (usertype == 0) {
            usertype = crearCuenta();
        }

        return usertype;
    }

    private static int crearCuenta() {
        Scanner sc = new Scanner(System.in);

        String username = "";
        int usertype = 0;
        boolean usernameValido = false;
        boolean usertypeCorrecto = false;
        boolean passwordIgual = false;
        String confirmarPassword;
        String nuevoPassword;

        System.out.println("¿Quieres crear una cuenta nueva? (Si/No)");
        String cuentaNueva = sc.nextLine();
        if (cuentaNueva.equalsIgnoreCase("si")) {
            do {
                System.out.println("Introduce tu nuevo usuario.");
                username = sc.nextLine();

                if (!username.isEmpty()) {
                    usernameValido = true;
                }
            } while(!usernameValido);

            do {
                System.out.println("Introduce tu contraseña nueva.");
                nuevoPassword = sc.nextLine();
                System.out.println("Confirma la contraseña.");
                confirmarPassword = sc.nextLine();

                if (confirmarPassword.equals(nuevoPassword)) {
                    do {
                        System.out.println("¿Qué tipo de usuario es? 1- Admin, 2- Manager");
                        if (sc.hasNextInt()) {
                            usertype = sc.nextInt();
                            sc.nextLine();
                            if (usertype == 1 || usertype == 2) {
                                usertypeCorrecto = true;
                            }
                        } else {
                            sc.nextLine();
                        }
                    } while (!usertypeCorrecto);
                    passwordIgual = true;
                }
            } while (!passwordIgual);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/txt/Login.txt", true))) {
                bw.write(username + "," + nuevoPassword + "," + usertype);
                bw.newLine();
            } catch (IOException e) {
                System.out.println("Error al guardar.");
            }
        } else {
            System.exit(0);
        }

        return usertype;
    }

    public static int iniciarSesion(List<String[]> listaDeFilas) {
        Scanner sc = new Scanner(System.in);

        boolean salir = false;
        String username;
        String password;
        int usertype = 0;
        boolean passwordCorrecto = false;

        do {
            System.out.println("Introduce tu nombre de usuario:");
            username = sc.nextLine();
            if (!username.isEmpty()) {
                salir = true;
            }
        } while (!salir);


        for (String[] fila : listaDeFilas) {
            if (fila.length > 0 && fila[0].equals(username)) {
                System.out.println("Introduce tu contraseña.");
                do {
                    password = sc.nextLine();
                    if (fila[1].equals(password)) {
                        passwordCorrecto = true;
                        usertype = Integer.parseInt(fila[2].trim());
                    } else {
                        System.out.println("Contraseña incorrecta, reintenta.");
                    }
                } while (!passwordCorrecto);
                break;
            }
        }

        return usertype;
    }

    public static List<String[]> cargarUsuarios() {
        List<String[]> listaDeFilas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/txt/Login.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                listaDeFilas.add(valores);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo.");
        }
        return listaDeFilas;
    }
}