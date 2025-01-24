package app.vistas;

import app.core.Router;
import app.modelos.Jugador;
import app.modelos.Partida;
import app.modelos.VideoJuego;
import app.utilidades.Validador;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VistaConsolaRouter {
    private final Router router;

    public VistaConsolaRouter(Router router) {
        this.router = router;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        int opcionn;
        do {

            do {
                System.out.println("\n=== Menú Principal con Router ===");
                System.out.println("1. Agregar Jugador");
                System.out.println("2. Listar todos los Jugadores");
                System.out.println("3. Buscar jugador por ID");
                System.out.println("4. Buscar jugador por Nombre");
                System.out.println("5. Eliminar Jugador");
                System.out.println("6. Listar Videojuegos a los que ha jugado X jugador");
                System.out.println("7. Agregar Videojuego");
                System.out.println("8. Listar Videojuegos");
                System.out.println("9. Listar Videojuegos de mas caro a mas barato");
                System.out.println("10. Listar Videojuegos por genero");
                System.out.println("11. Buscar videojuego por ID");
                System.out.println("12. Eliminar Videojuego");
                System.out.println("13. Agregar Partida");
                System.out.println("14. Listar Partidas");
                System.out.println("15. Listar partida por ID");
                System.out.println("16. Eliminar Partida");

                System.out.println("17. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextLine();

                if (!Validador.esNumero(opcion)){
                    System.out.println("La opción debe ser un número.");
                }else{
                    opcionn = Integer.parseInt(opcion);
                    break;
                }

            }while(true);

            switch (opcionn) {
                case 1 -> agregarJugador(scanner);
                case 2 -> listarJugadores();
                case 3 -> listarJugadorPorId(scanner);
                case 4 -> buscarJugadorPorNombre(scanner);
                case 5 -> eliminarJugador(scanner);
                case 6 -> listarVidejuegosJugadoPorXJugador(scanner);
                case 7 -> agregarVideojuego(scanner);
                case 8 -> listarVideojuegos();
                case 9 -> listarVideojuegosOrdenadoCaroBarato();
                case 10 -> listarVideojuegoPorGenero(scanner);
                case 11 -> listarVideojuegoPorId(scanner);
                case 12 -> eliminarVideojuego(scanner);
                case 13 -> agregarPartida(scanner);
                case 14 -> listarPartidas(scanner);
                case 15 -> listarPartidaPorId(scanner);
                case 16 -> eliminarPartida(scanner);
                case 17 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcionn != 17);
    }

    private void listarVidejuegosJugadoPorXJugador(Scanner scanner) {
        System.out.println("Escribe el id del jugador: ");
        String id = scanner.nextLine();

        do {
            if (Validador.esNumero(id)){
                break;
            }else{
                System.out.println("El id debe ser un numero.");
                id = scanner.nextLine();
            }
        }while(true);

        int idd = Integer.parseInt(id);

        Jugador jugador = (Jugador) router.ejecutarAccion("jugadores", "buscarJugadorPorId", idd);

        if (jugador == null) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        List<VideoJuego> videoJuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarVideojuegosDeJugadorID", idd);

        if (!videoJuegos.isEmpty()) {
            for (VideoJuego videoJuego : videoJuegos) {
                System.out.println(videoJuego);
            }
        } else {
            System.out.println("No se encontraron videojuegos para el jugador con ID " + idd);
        }
    }
    private void listarVideojuegosOrdenadoCaroBarato() {
        List<VideoJuego> videoJuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarDeMasCaroAMasBarato");

        for (VideoJuego videoJuego : videoJuegos){
            System.out.println(videoJuego);
        }
    }

    private void listarVideojuegoPorGenero(Scanner scanner) {
        System.out.println("Ingrese el genero: ");
        String genero = scanner.nextLine();

        do {
            if (!Validador.esStringVacio(genero)){
                break;
            }else{
                System.out.println("El genero no es valido.");
                System.out.println("Ingrese el genero: ");
                genero = scanner.nextLine();
            }
        }while(true);

        List<VideoJuego> videoJuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarPorGenero", genero);

        if (videoJuegos.isEmpty()) {
            System.out.println("No se encontraron videojuegos para el genero " + genero);
            return;
        }

        for (VideoJuego videoJuego : videoJuegos){
            System.out.println(videoJuego);
        }
    }

    private void buscarJugadorPorNombre(Scanner scanner) {
        System.out.println("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine();

        do {
            if (!Validador.esStringVacio(nombre)){
                break;
            }else{
                System.out.println("El nombre no es valido.");
                nombre = scanner.nextLine();
            }
        }while(true);


        Jugador jugador = (Jugador) router.ejecutarAccion("jugadores", "buscarJugadorPorNombre", nombre);

        if (jugador!=null){
            System.out.println(jugador);
        }else{
            System.out.println("No se ha encontrado el jugador");
        }
    }

    private void eliminarPartida(Scanner scanner) {
        System.out.println("Ingrese el ID de la partida a eliminar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                 f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        boolean resultado = (boolean)router.ejecutarAccion("partidas", "eliminarPartida", id);
        mensajeBorrado(resultado);
    }

    private void listarPartidaPorId(Scanner scanner) {
        System.out.println("Ingrese el ID de la partida a buscar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        Partida obj = (Partida)router.ejecutarAccion("partidas", "buscarPartidaPorId", id);

        if(obj!=null){
            System.out.println(obj.toString());

        }else{
            System.out.println("No se ha encontrado la partida");
        }

    }

    private void listarPartidas(Scanner scanner) {
        List<Partida> listapartidas = (List<Partida>) router.ejecutarAccion("partidas", "listarPartidas");

        for(Partida partida : listapartidas) {
            System.out.println(partida);
        }
    }

    private void agregarPartida(Scanner scanner) {

        Object videojuegoEncontrado = null;
        Object jugadorEncontrado = null;

        System.out.println("Ingrese el ID del jugador: ");
        int idJugador;

        while (true) {
            idJugador = scanner.nextInt();

            jugadorEncontrado = router.ejecutarAccion("jugadores", "buscarJugadorPorId", idJugador);
            if (jugadorEncontrado != null) {
                break; // Sale del bucle si se encuentra el jugador
            }else{
                System.out.println("No existe el jugador");
            }

            System.out.println("Jugador no encontrado. Por favor, ingrese un ID válido: ");
        }

        System.out.println("Ingrese el ID del videojuego: ");
        int idVideojuego;

        while (true) {
            idVideojuego = scanner.nextInt();

            videojuegoEncontrado = router.ejecutarAccion("videojuegos", "buscarVideojuegoPorId", idVideojuego);
            if (videojuegoEncontrado == null) {
                System.out.println("No existe el videojuego");
            }else {
                break;
            }

            System.out.println("Videojuego no encontrado. Por favor, ingrese un ID válido: ");
        }

        boolean obj = (boolean)router.ejecutarAccion("partidas", "agregarPartida", idJugador, idVideojuego, generarTiempoAleatorio());

        mensajeRegistro(obj);

    }

    private Object generarTiempoAleatorio() {
        Random r = new Random();
        return r.nextInt(1,3600);
    }

    private void eliminarVideojuego(Scanner scanner) {
        System.out.println("Ingrese el ID del videojuego a eliminar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        boolean resultado = (boolean)router.ejecutarAccion("videojuegos", "eliminarVideojuego", id);

        mensajeBorrado(resultado);

    }

    private void listarVideojuegoPorId(Scanner scanner) {
        System.out.println("Ingrese el ID del videojuego a buscar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        VideoJuego videoJuego = (VideoJuego) router.ejecutarAccion("videojuegos", "buscarVideojuegoPorId", id);

        if (videoJuego != null) {
            System.out.println(videoJuego);
        } else {
            System.out.println("Videojuego no encontrado.");
        }
    }

    private void eliminarJugador(Scanner scanner) {
        System.out.println("Ingrese el ID del jugador a eliminar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        boolean resultado = (boolean)router.ejecutarAccion("jugadores", "eliminarJugador", id);
        mensajeBorrado(resultado);
    }

    private void listarJugadorPorId(Scanner scanner) {
        System.out.println("Ingrese el ID del jugador a buscar: ");
        boolean f;
        String idd="";
        do{
            idd = scanner.nextLine();
            if(Validador.esNumero(idd)){
                f=false;
            }else{
                f=true;
                System.out.println("Introduce un ID valido");
            }

        }while(f);
        int id=Integer.parseInt(idd);

        Jugador jugador = (Jugador) router.ejecutarAccion("jugadores", "buscarJugadorPorId", id);

        if (jugador != null) {
            System.out.println(jugador);
        } else {
            System.out.println("Jugador no encontrado.");
        }
    }

    private void agregarJugador(Scanner scanner) {
        System.out.print("Ingrese nombre del jugador: ");
        String nombre = scanner.nextLine();

        do{
            if(Validador.esStringVacio(nombre)){
                System.out.println("El nombre no puede estar vacio.");
                System.out.print("Ingrese nombre del jugador: ");
                nombre = scanner.nextLine();
            }else{
                break;
            }
        }while(true);


        System.out.print("Ingrese nivel del jugador: ");
        String nivell = scanner.nextLine();

        do{
            if(Validador.esNumero(nivell)){
                break;
            }else{
                System.out.println("El nivel debe ser un numero.");
                System.out.print("Ingrese nivel del jugador: ");
                nivell = scanner.nextLine();
            }
        }while(true);
        int nivel = Integer.parseInt(nivell);


        System.out.print("Ingrese la puntuación del jugador: ");
        String puntuac = scanner.nextLine();

        do{
            if(Validador.esNumero(puntuac)){
                break;
            }else{
                System.out.println("La puntuación debe ser un numero.");
                System.out.print("Ingrese la puntuación del jugador: ");
                puntuac = scanner.nextLine();
            }
        }while(true);
        int puntuacion = Integer.parseInt(puntuac);


        boolean resultado = (boolean)router.ejecutarAccion("jugadores", "agregarJugador", nombre, nivel, puntuacion);
        mensajeRegistro(resultado);
    }

    private void listarJugadores() {
        List<Jugador> listaJugadores = (List<Jugador>) router.ejecutarAccion("jugadores", "listarJugadores");

        for(Jugador jugador : listaJugadores) {
            System.out.println(jugador);
        }

    }

    private void agregarVideojuego(Scanner scanner) {
        System.out.print("Ingrese el título del videojuego: ");
        String titulo = scanner.nextLine();

        do{
            if(Validador.esStringVacio(titulo)){
                System.out.println("El titulo no puede estar vacio.");
                System.out.print("Ingrese el título del videojuego: ");
                titulo = scanner.nextLine();
            }else{
                break;
            }
        }while(true);


        System.out.print("Ingrese el genero del videojuego: ");
        String genero = scanner.nextLine();

        do{
            if(Validador.esStringVacio(genero)){
                System.out.println("El genero no puede estar vacio.");
                System.out.print("Ingrese el genero del videojuego: ");
                genero = scanner.nextLine();
            }else{
                break;
            }
        }while(true);


        System.out.print("Ingrese el precio del videojuego: ");
        String prec = scanner.nextLine();

        do{
            if(Validador.esDouble(prec)){
                break;
            }else{
                System.out.println("El precio debe ser un numero.");
                System.out.print("Ingrese el precio del videojuego: ");
                prec = scanner.nextLine();
            }
        }while(true);
        double precio = Double.parseDouble(prec);

        boolean resultado = (boolean)router.ejecutarAccion("videojuegos", "agregarVideojuego", titulo, genero, precio);
        mensajeRegistro(resultado);
    }

    private void listarVideojuegos() {
        List<VideoJuego> listaVideojuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarVideojuegos");

        for(VideoJuego videoJuego: listaVideojuegos) {
            System.out.println(videoJuego);
        }

    }

    private void mensajeRegistro(boolean resultado){
        if (resultado){
            System.out.println("Ha sido agregado correctamente");
        }else{
            System.out.println("No se ha podido agregar");
        }

    }
    private void mensajeBorrado(boolean resultado){
        if (resultado){
            System.out.println("Ha sido eliminado correctamente");
        }else{
            System.out.println("No se ha podido eliminar");
        }

    }


}

