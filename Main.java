import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Random;

public class Main {
    public static int conteoDisparos;
    public static int sangre = 0;
    public static Tanque[][] tablero = new Tanque[2][2];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        configurarTablero();
        mostrarTablero();
        while (true) {
            mostrarMenu();
            acciones(sc);
            if (todosMuertos()) {
                System.out.println("Han muerto todos los tanques. Fin del juego.");
                break;
            }
        }
        sc.close();
    }

    public static boolean todosMuertos() {
        boolean check = true;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != null && tablero[i][j].getSalud() > 0)
                    check = false;
            }
        }
        return check;
    }

    public static void configurarTablero() {
        int tanques = (int) ((Math.random() * (5 - 1)) + 1);
        for (int i = 0; i < tanques; ++i) {
            int fila = i / 2;
            int columna = i % 2;
            int tipo = (int) ((Math.random() * (3 - 1)) + 1);
            if (tipo == 1) {
                tablero[fila][columna] = new TanqueNormal();
            } else {
                tablero[fila][columna] = new TanqueAlien();
            }
        }
    }

    public static void mostrarTablero() {
        System.out.println("-------------");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print("|");
                Tanque tanquesito = tablero[i][j];

                if (tanquesito != null) {
                    if (tanquesito instanceof TanqueNormal) {
                        System.out.print("BN-" + tanquesito.getSalud());
                    } else if (tanquesito instanceof TanqueAlien) {
                        System.out.print("BA-" + tanquesito.getSalud());
                    }
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println("|");
            System.out.println("-------------");
        }
        System.out.println();
        System.out.println();
    }

    public static void mostrarMenu() {
        System.out.println("*Presione 1 para disparar una bala");
        System.out.println("*Presione 2 para lanzar una bomba atómica que matará el tanque ");
        System.out.println("*Presione 3 para activar tanque mutante");
        System.out.println("*Presione 4 para mostrar la frase de la abuela");
        System.out.println("*Presione 5 para mostrar el conteo de disparos");
        System.out.println("*Presione 6 para leer la cantidad de sangre");
        System.out.println("*Presione 7 para guardar en archivo txt");
        System.out.println("la cantidad de sangre de los tanques");
    }

    public static void acciones(Scanner sc) {
        int usuario = sc.nextInt();
        if (usuario == 1) {
            conteoDisparos++;
            disparar(sc);
            mostrarTablero();
        }
        else if (usuario == 2) {
            lanzarBomba();
            mostrarTablero();
        }
        else if (usuario == 3) {
            activarTanque();
            mostrarTablero();
        }
        else if (usuario == 4) {
            mostrarFrase();
        }
        else if (usuario == 5) {
            mostarConteo();
        }
        else if (usuario == 6) {
            leerSangre();
        }
        else if (usuario == 7) {
            guardarArchivo();
        } else {
            System.out.println("Elija una opción válida");
            System.out.println();
            System.out.println();
        }
    }

    public static void disparar(Scanner sc) {
        System.out.println("Elija la fila y columna para dispararla, se le ");
        System.out.println("bajará 5 de vida al tanque de la posición elegida");
        int fila = sc.nextInt();
        int columna = sc.nextInt();
        if (fila > 2 && fila < 0 && columna > 2 && columna < 0) {
            System.out.println("Ingrese un numero entre 0 y 1");
        } else {
            if (tablero[fila][columna].getSalud() == 0) {
                System.out.println("ya tienes 0 de salud en este tanque");
                System.out.println();
                System.out.println();
            } else {
                if (tablero[fila][columna] != null) {
                    tablero[fila][columna].setSalud(tablero[fila][columna].getSalud() - 5);
                } else {
                    System.out.println("La posicion es nula");
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    public static void lanzarBomba() {
        Random generador = new Random();
        int filaRandom = generador.nextInt(2);
        int columnaRandom = generador.nextInt(2);
        if (tablero[filaRandom][columnaRandom] != null) {
            tablero[filaRandom][columnaRandom].setSalud(
                    tablero[filaRandom][columnaRandom].getSalud() - tablero[filaRandom][columnaRandom].getSalud());
        } else {
            System.out.println("La posición generada es nula");
        }
    }

    public static void activarTanque() {
        int mini = 9999999;
        int fila = -1, columna = -1;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                Tanque tanque = tablero[i][j];
                if (tanque != null && tanque.getSalud() > 0 && tanque.getSalud() < mini) {
                    mini = tanque.getSalud();
                    fila = i;
                    columna = j;
                }
            }
        }
        tablero[fila][columna].setSalud(tablero[fila][columna].getSalud() * 2);
    }

    public static void mostrarFrase() {
        System.out.println("Mijo, yo a tu edad jugaba bien.");
      System.out.println();
      System.out.println();
    }

    public static void mostarConteo() {
        System.out.println("La cantidad de disparos realizados es " + conteoDisparos);
        System.out.println();
        System.out.println();
    }

    public static void leerSangre() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                Tanque tanque = tablero[i][j];
                if (tanque != null) {
                    System.out.println("La cantidad de sangre del tanque en la posición " + i + " " + j + " es " +
                            tanque.getSalud());
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    public static void guardarArchivo() {
        File archivo = new File("vida.txt");
        try {
            FileWriter escritor = new FileWriter(archivo);
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++) {
                    Tanque tanque = tablero[i][j];
                    if (tanque != null) {
                        escritor.write("La cantidad de sangre del tanque en la posición " + i + " " + j + " es " +
                                tanque.getSalud());
                        escritor.write("\n");
                    }
                }
            }
            System.out.println("El archivo se guardó en " + archivo.getAbsolutePath());
            escritor.close();
        } catch (Exception e) {
            System.out.println("No se pudo escribir el archivo.");
        }
    }

}