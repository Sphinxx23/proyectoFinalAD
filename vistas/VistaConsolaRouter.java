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
                System.out.println("2. Editar Jugador");
                System.out.println("3. Listar todos los Jugadores");
                System.out.println("4. Buscar jugador por ID");
                System.out.println("5. Buscar jugador por Nombre");
                System.out.println("6. Eliminar Jugador");
                System.out.println("7. Listar Videojuegos a los que ha jugado X jugador");
                System.out.println("8. Agregar Videojuego");
                System.out.println("9. Editar Videojuego");
                System.out.println("10. Listar Videojuegos");
                System.out.println("11. Listar Videojuegos de mas caro a mas barato");
                System.out.println("12. Listar Videojuegos por genero");
                System.out.println("13. Buscar videojuego por ID");
                System.out.println("14. Eliminar Videojuego");
                System.out.println("15. Agregar Partida");
                System.out.println("16. Listar Partidas");
                System.out.println("17. Listar partida por ID");
                System.out.println("18. Eliminar Partida");
                System.out.println("19. Videojuegos con mas tiempo jugado");
                System.out.println("20. Videojuegos con mas jugadores");
                System.out.println("21. Jugadores con mas tiempo jugado");
                System.out.println("22. Jugadores con mas puntuación");

                System.out.println("23. Salir");
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
                case 2 -> editarJugador(scanner);
                case 3 -> listarJugadores();
                case 4 -> listarJugadorPorId(scanner);
                case 5 -> buscarJugadorPorNombre(scanner);
                case 6 -> eliminarJugador(scanner);
                case 7 -> listarVidejuegosJugadoPorXJugador(scanner);
                case 8 -> agregarVideojuego(scanner);
                case 9 -> editarVideojuego(scanner);
                case 10 -> listarVideojuegos();
                case 11-> listarVideojuegosOrdenadoCaroBarato();
                case 12 -> listarVideojuegoPorGenero(scanner);
                case 13 -> listarVideojuegoPorId(scanner);
                case 14 -> eliminarVideojuego(scanner);
                case 15 -> agregarPartida(scanner);
                case 16 -> listarPartidas(scanner);
                case 17 -> listarPartidaPorId(scanner);
                case 18 -> eliminarPartida(scanner);
                case 19 ->estadisticaVideojuegoHoras();
                case 20 ->estadisticaVideojuegoJugador();
                case 21 ->estadisticaJugadorHoras();
                case 22 ->estadisticaJugadorPuntuacion();
                case 23 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcionn != 23);
    }

    /**
     * Método que edita la información de un videojuego solicitando su ID, título, género y precio.
     *
     * Utiliza un Scanner para recibir las entradas del usuario y valida cada entrada
     * para asegurarse de que los datos ingresados sean válidos.
     * La acción de editar el videojuego se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

    private void editarVideojuego(Scanner scanner) {

        System.out.print("Ingrese ID del videojuego: ");
        String idd = scanner.nextLine();

        do{
            if(Validador.esNumero(idd)){
                break;
            }else{
                System.out.println("El ID debe ser un numero.");
                System.out.print("Ingrese ID del videojuego: ");
                idd = scanner.nextLine();
            }
        }while(true);
        int id = Integer.parseInt(idd);

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


        boolean b = (boolean)router.ejecutarAccion("videojuegos", "editarVideojuego", id,titulo, genero, precio);
        if(b){
            System.out.println("Videojuego editado correctamente");
        }else{
            System.out.println("Error al editar");
        }

    }

    /**
     * Método que edita la información de un jugador solicitando su ID, nombre, nivel y puntuación.
     *
     * Utiliza un Scanner para recibir las entradas del usuario y valida cada entrada
     * para asegurarse de que los datos ingresados sean válidos.
     * La acción de editar el jugador se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

    private void editarJugador(Scanner scanner) {

        System.out.print("Ingrese ID del jugador: ");
        String idd = scanner.nextLine();

        do{
            if(Validador.esNumero(idd)){
                break;
            }else{
                System.out.println("El ID debe ser un numero.");
                System.out.print("Ingrese ID del jugador: ");
                idd = scanner.nextLine();
            }
        }while(true);
        int id = Integer.parseInt(idd);

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

        boolean b = (boolean)router.ejecutarAccion("jugadores", "editarJugador", id,nombre, nivel, puntuacion);
           if(b){
               System.out.println("Jugador editado correctamente");
           }else{
               System.out.println("Error al editar");
           }
    }

    /**
     * Método que obtiene las estadísticas de los jugadores y las imprime en la consola.
     *
     * La acción de listar estadísticas de jugadores se realiza llamando al método 'ejecutarAccion' del router.
     */
    private void estadisticaJugadorPuntuacion() {
        List<Object[]> videoJuegos = (List<Object[]>) router.ejecutarAccion("jugadores", "listarJugadoresMayorPuntuacion");

        for (Object[] videoJuego : videoJuegos){
            System.out.println("ID: " +  videoJuego[0] +" Nombre: "+  videoJuego[1] + " Puntuacion: " + videoJuego[2]);
        }
    }

    /**
     * Método que obtiene las estadísticas de los jugadores y las imprime en la consola.
     *
     * La acción de listar estadísticas de jugadores se realiza llamando al método 'ejecutarAccion' del router.
     */
    private void estadisticaJugadorHoras() {
        List<Object[]> videoJuegos = (List<Object[]>) router.ejecutarAccion("jugadores", "listarJugadoresMasHoras");

        for (Object[] videoJuego : videoJuegos){
            System.out.println("ID: " +  videoJuego[0] +" Nombre: "+  videoJuego[1] + " Segundos jugados: " + videoJuego[2]);
        }

    }

    /**
     * Método que obtiene las estadísticas de los videojuegos y las imprime en la consola.
     *
     * La acción de listar estadísticas de videojuegos se realiza llamando al método 'ejecutarAccion' del router.
     */
    private void estadisticaVideojuegoJugador() {
        List<Object[]> videoJuegos = (List<Object[]>) router.ejecutarAccion("videojuegos", "listarEstadisticasVideojuegosJugadoresTotales");

        for (Object[] videoJuego : videoJuegos){
            System.out.println("ID: " +  videoJuego[0] +" Titulo: "+  videoJuego[1] + " Total Jugadores: " + videoJuego[2]);
        }

    }

    /**
     * Método que obtiene las estadísticas de los videojuegos y las imprime en la consola.
     *
     * La acción de listar estadísticas de videojuegos se realiza llamando al método 'ejecutarAccion' del router.
     */
    private void estadisticaVideojuegoHoras() {
        List<Object[]> videoJuegos = (List<Object[]>) router.ejecutarAccion("videojuegos", "listarEstadisticasVideojuegosHoras");

        for (Object[] videoJuego : videoJuegos){
            System.out.println("ID: " +  videoJuego[0] +" Titulo: "+  videoJuego[1] + " Segundos jugados : " + videoJuego[2]);
        }
    }

    /**
     * Método que solicita al usuario el ID de un jugador y lista los videojuegos jugados por dicho jugador.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * Busca el jugador a través del router y, si existe, lista los videojuegos jugados por el jugador.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que obtiene una lista de videojuegos ordenada de más caro a más barato a través del router y la imprime en la consola.
     *
     * La acción de listar videojuegos se realiza llamando al método 'ejecutarAccion' del router.
     */

    private void listarVideojuegosOrdenadoCaroBarato() {
        List<VideoJuego> videoJuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarDeMasCaroAMasBarato");

        for (VideoJuego videoJuego : videoJuegos){
            System.out.println(videoJuego);
        }
    }

    /**
     * Método que solicita al usuario un género de videojuego, busca los videojuegos de ese género a través del router
     * y los muestra en la consola.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el género ingresado no esté vacío.
     * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que solicita al usuario el nombre de un jugador, lo busca a través del router
     * y muestra la información del jugador en la consola.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el nombre ingresado no esté vacío.
     * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que solicita al usuario el ID de una partida para eliminarla, lo valida y la elimina a través del router.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La acción de eliminar la partida se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

    /**
     * Método que solicita al usuario el ID de una partida, lo busca a través del router
     * y muestra la información de la partida en la consola.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

    /**
     * Método que obtiene una lista de partidas a través del router y la imprime en la consola.
     *
     * La acción de listar partidas se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
    private void listarPartidas(Scanner scanner) {
        List<Partida> listapartidas = (List<Partida>) router.ejecutarAccion("partidas", "listarPartidas");

        for(Partida partida : listapartidas) {
            System.out.println(partida);
        }
    }

    /**
     * Método que solicita al usuario los ID del jugador y del videojuego, valida su existencia,
     * agrega una nueva partida a través del router y muestra el resultado.
     *
     * Utiliza un Scanner para recibir las entradas del usuario y valida cada entrada
     * para asegurarse de que los datos ingresados sean válidos.
     * La acción de agregar la partida se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

        scanner.nextLine();

    }

    /**
     * Método que genera un tiempo aleatorio en segundos.
     *
     * Utiliza la clase Random para generar un número aleatorio entre 1 y 3600 segundos.
     *
     * @return un número aleatorio entre 1 y 3600.
     */
    private Object generarTiempoAleatorio() {
        Random r = new Random();
        return r.nextInt(1,3600);
    }

    /**
     * Método que solicita al usuario el ID de un videojuego para eliminarlo, lo valida y lo elimina a través del router.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La acción de eliminar el videojuego se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

    /**
     * Método que solicita al usuario el ID de un videojuego, lo busca a través del router
     * y muestra la información del videojuego en la consola.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

    /**
     * Método que solicita al usuario el ID de un jugador para eliminarlo, lo valida y lo elimina a través del router.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La acción de eliminar el jugador se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que solicita al usuario el ID de un jugador, lo busca a través del router
     * y muestra la información del jugador en la consola.
     *
     * Utiliza un Scanner para recibir la entrada del usuario y valida que el ID ingresado sea un número válido.
     * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que agrega un nuevo jugador solicitando su nombre, nivel y puntuación.
     *
     * Utiliza un Scanner para recibir las entradas del usuario y valida cada entrada
     * para asegurarse de que los datos ingresados sean válidos.
     * La acción de agregar el jugador se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */
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

    /**
     * Método que obtiene una lista de jugadores a través del router y la imprime en la consola.
     *
     * La acción de listar jugadores se realiza llamando al método 'ejecutarAccion' del router.
     */
    private void listarJugadores() {
        List<Jugador> listaJugadores = (List<Jugador>) router.ejecutarAccion("jugadores", "listarJugadores");

        for(Jugador jugador : listaJugadores) {
            System.out.println(jugador);
        }

    }

    /**
     * Método que agrega un nuevo videojuego solicitando su título, género y precio.
     *
     * Utiliza un Scanner para recibir las entradas del usuario y valida cada entrada
     * para asegurarse de que los datos ingresados sean válidos.
     * La acción de agregar el videojuego se realiza llamando al método 'ejecutarAccion' del router.
     *
     * @param scanner el objeto Scanner utilizado para recibir las entradas del usuario.
     */

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

    /**
     * Método que obtiene una lista de videojuegos a través del router y la imprime en la consola.
     *
     * La acción de listar videojuegos se realiza llamando al método 'ejecutarAccion' del router.
     */

    private void listarVideojuegos() {
        List<VideoJuego> listaVideojuegos = (List<VideoJuego>) router.ejecutarAccion("videojuegos", "listarVideojuegos");

        for(VideoJuego videoJuego: listaVideojuegos) {
            System.out.println(videoJuego);
        }

    }

    /**
     * Método que muestra un mensaje en la consola indicando si una operación de registro ha sido exitosa o no.
     *
     * @param resultado el resultado de la operación de registro.
     */
    private void mensajeRegistro(boolean resultado){
        if (resultado){
            System.out.println("Ha sido agregado correctamente");
        }else{
            System.out.println("No se ha podido agregar");
        }

    }

    /**
     * Método que muestra un mensaje en la consola indicando si una operación de borrado ha sido exitosa o no.
     *
     * @param resultado el resultado de la operación de borrado.
     */
    private void mensajeBorrado(boolean resultado){
        if (resultado){
            System.out.println("Ha sido eliminado correctamente");
        }else{
            System.out.println("No se ha podido eliminar");
        }

    }


}

