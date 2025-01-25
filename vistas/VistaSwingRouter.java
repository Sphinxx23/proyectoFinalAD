package app.vistas;

import app.core.Router;
import app.modelos.Partida;
import app.modelos.VideoJuego;
import app.modelos.Jugador;
import app.utilidades.Validador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class VistaSwingRouter {
    private static Router router; // Asume que el router ya está inicializado
    private static JFrame frame;

    public VistaSwingRouter(Router rout){
        this.router = rout;
    }

    /**
     * Muestra el menú principal de la aplicación en una ventana.
     * La ventana contiene botones organizados en categorías: Videojuegos, Jugadores y Partidas.
     * Cada categoría tiene un panel con opciones específicas para su gestión.
     *
     * La ventana se ajusta para que el texto se vea completamente y se centra en la pantalla.
     *
     * Este método no recibe parámetros ni devuelve valores.
     *
     * Detalles:
     * - Crea y configura la ventana principal.
     * - Añade un panel principal con un borde y espaciado agradable.
     * - Organiza botones de opciones en paneles con un diseño atractivo.
     * - Añade los paneles a la ventana y hace visible la ventana.
     */
    public void mostrarMenu() {
        // Crear la ventana principal
        frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700); // Tamaño ajustado para que el texto se vea completo

        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Crear el panel principal con un borde y espaciado agradable
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear paneles para cada categoría con borde
        JPanel panelVideojuegos = new JPanel(new GridLayout(0, 1, 5, 5));
        panelVideojuegos.setBorder(BorderFactory.createTitledBorder("Videojuegos"));
        JPanel panelJugadores = new JPanel(new GridLayout(0, 1, 5, 5));
        panelJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));
        JPanel panelPartidas = new JPanel(new GridLayout(0, 1, 5, 5));
        panelPartidas.setBorder(BorderFactory.createTitledBorder("Partidas"));

        // Crear botones para cada opción del menú con un diseño atractivo
        String[] videojuegos = {
                "Agregar Videojuego",
                "Listar Videojuegos",
                "Editar Videojuego",
                "Listar Videojuegos de más caro a más barato",
                "Listar Videojuegos por género",
                "Buscar videojuego por ID",
                "Estadistica videojuegos mas horas",
                "Estadistica videojuegos mas jugadores",
                "Eliminar Videojuego"
        };

        String[] jugadores = {
                "Agregar Jugador",
                "Listar todos los Jugadores",
                "Editar Jugador",
                "Buscar jugador por ID",
                "Buscar jugador por Nombre",
                "Estadistica jugadores mas horas",
                "Estadistica jugadores mas puntuación",
                "Eliminar Jugador",
                "Listar Videojuegos a los que ha jugado X jugador"
        };

        String[] partidas = {
                "Agregar Partida",
                "Listar Partidas",
                "Buscar partida por ID",
                "Eliminar Partida",
                "Salir"
        };

        agregarBotones(panelVideojuegos, videojuegos);
        agregarBotones(panelJugadores, jugadores);
        agregarBotones(panelPartidas, partidas);

        // Añadir los paneles con separaciones visibles
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(panelVideojuegos, gbc);
        gbc.gridx = 1;
        panel.add(panelJugadores, gbc);
        gbc.gridx = 2;
        panel.add(panelPartidas, gbc);

        // Agregar el panel principal a la ventana
        frame.add(panel);

        // Hacer la ventana visible
        frame.setVisible(true);
    }

    /**
     * Agrega una serie de botones a un panel dado.
     * Los botones son creados basados en un array de opciones, cada uno con un estilo visual específico.
     *
     * @param panel el panel al que se agregarán los botones
     * @param opciones un array de cadenas que representa las opciones de los botones
     *
     * Detalles:
     * - Establece la fuente, el color de fondo, el color de texto y el borde del botón.
     * - Asigna un ActionListener personalizado a cada botón.
     * - Añade cada botón al panel proporcionado.
     */
    private void agregarBotones(JPanel panel, String[] opciones) {
        for (String opcion : opciones) {
            JButton button = new JButton(opcion);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 25, 112), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
            button.addActionListener(new MenuActionListener(opcion));
            panel.add(button);
        }
    }

    // Clase para manejar los eventos de los botones
    static class MenuActionListener implements ActionListener {
        private final String opcion;

        public MenuActionListener(String opcion) {
            this.opcion = opcion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (opcion) {
                case "Agregar Jugador":
                    agregarJugador();
                    break;
                case "Listar todos los Jugadores":
                    listarJugadores();
                    break;
                case "Editar Jugador":
                    editarJugador();
                    break;
                case "Buscar jugador por ID":
                    buscarJugadorPorId();
                    break;
                case "Buscar jugador por Nombre":
                    buscarJugadorPorNombre();
                    break;
                case "Estadistica jugadores mas horas":
                    listarEstadisticaJugadoresMasHoras();
                    break;
                case "Estadistica jugadores mas puntuación":
                    listarEstadisticaJugadoresMayorPuntuacion();
                    break;
                case "Eliminar Jugador":
                    eliminarJugador();
                    break;
                case "Listar Videojuegos a los que ha jugado X jugador":
                    listarVideojuegosDeJugador();
                    break;
                case "Agregar Videojuego":
                    agregarVideojuego();
                    break;
                case "Listar Videojuegos":
                    listarVideojuegos();
                    break;
                case "Editar Videojuego":
                    editarVideojuego();
                    break;
                case "Listar Videojuegos de más caro a más barato":
                    listarVideojuegosCaroBarato();
                    break;
                case "Listar Videojuegos por género":
                    listarVideojuegosPorGenero();
                    break;
                case "Buscar videojuego por ID":
                    buscarVideojuegoPorId();
                    break;
                case "Eliminar Videojuego":
                    eliminarVideojuego();
                    break;
                case "Agregar Partida":
                    agregarPartida();
                    break;
                case "Listar Partidas":
                    listarPartidas();
                    break;
                case "Listar partida por ID":
                    listarPartidaPorId();
                    break;
                case "Eliminar Partida":
                    eliminarPartida();
                    break;
                case "Estadistica videojuegos mas horas":
                    listarEstadisticasVideojuegosHoras();
                    break;
                case "Estadistica videojuegos mas jugadores":
                    listarEstadisticasVideojuegosTotalJugadores();
                    break;
                case "Salir":
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no reconocida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Método que solicita al usuario el ID de un videojuego y los nuevos detalles del mismo (título, género y precio),
         * edita el videojuego a través del router y muestra el resultado en un cuadro de diálogo.
         *
         * Utiliza JOptionPane para solicitar el ID y los nuevos detalles del videojuego, y para mostrar el resultado.
         * La edición se realiza llamando al método 'ejecutarAccion' del router.
         *
         * @throws NumberFormatException si el ID o el precio ingresados no son números válidos.
         */
        private void editarVideojuego() {
            String idVideojuego = JOptionPane.showInputDialog("Ingrese el ID del videojuego a editar:");
            String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo título del videojuego:");
            String nuevoGenero = JOptionPane.showInputDialog("Ingrese el nuevo género del videojuego:");
            String nuevoPrecio = JOptionPane.showInputDialog("Ingrese el nuevo precio del videojuego:");

            boolean resultado = (boolean) router.ejecutarAccion(
                    "videojuegos",
                    "editarVideojuego",
                    Integer.parseInt(idVideojuego),
                    nuevoTitulo,
                    nuevoGenero,
                    Double.parseDouble(nuevoPrecio)
            );

            mostrarResultado(resultado);
        }

        /**
         * Permite al usuario editar los detalles de un videojuego existente.
         * Se solicita al usuario que ingrese el ID del videojuego a editar y los nuevos detalles a actualizar.
         * Luego, se llama a una acción para realizar la edición y se muestra el resultado.
         *
         * Detalles:
         * - Solicita al usuario ingresar el ID, título, género y precio del videojuego.
         * - Llama a la acción "editarVideojuego" en el router con los datos proporcionados.
         * - Muestra el resultado de la edición al usuario.
         */
        private void editarJugador() {
            String idJugador = JOptionPane.showInputDialog("Ingrese el ID del jugador a editar:");
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del jugador:");
            String nuevoNivel = JOptionPane.showInputDialog("Ingrese el nuevo nivel del jugador:");
            String nuevaPuntuacion = JOptionPane.showInputDialog("Ingrese la nueva puntuación del jugador:");

            boolean resultado = (boolean) router.ejecutarAccion(
                    "jugadores",
                    "editarJugador",
                    Integer.parseInt(idJugador),
                    nuevoNombre,
                    Integer.parseInt(nuevoNivel),
                    Integer.parseInt(nuevaPuntuacion)
            );

            mostrarResultado(resultado);
        }

        /**
         * Permite al usuario editar los detalles de un jugador existente.
         * Se solicita al usuario que ingrese el ID del jugador a editar y los nuevos detalles a actualizar.
         * Luego, se llama a una acción para realizar la edición y se muestra el resultado.
         *
         * Detalles:
         * - Solicita al usuario ingresar el ID, nombre, nivel y puntuación del jugador.
         * - Llama a la acción "editarJugador" en el router con los datos proporcionados.
         * - Muestra el resultado de la edición al usuario.
         */
        private void agregarJugador() {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del jugador:");
            String nivel = JOptionPane.showInputDialog("Ingrese nivel del jugador:");
            String puntuacion = JOptionPane.showInputDialog("Ingrese puntuación del jugador:");

            boolean resultado = (boolean) router.ejecutarAccion("jugadores", "agregarJugador", nombre, Integer.parseInt(nivel), Integer.parseInt(puntuacion));
            mostrarResultado(resultado);
        }

        /**
         * Lista todos los jugadores y muestra los detalles en una tabla.
         * Se obtienen los datos de los jugadores y se muestran en una ventana con una tabla.
         *
         * Detalles:
         * - Recupera una lista de jugadores desde el router.
         * - Crea una tabla con las columnas "ID", "Nombre" y "Nivel" para mostrar los datos de los jugadores.
         * - Añade la tabla a un JScrollPane y lo muestra en una ventana independiente.
         */
        private void listarJugadores() {
            ArrayList<Jugador> listaJugadores = (ArrayList<Jugador>) router.ejecutarAccion("jugadores", "listarJugadores");

            String[][] data = new String[listaJugadores.size()][3];  // Suponiendo que el Jugador tiene ID, Nombre y Nivel

            for (int i = 0; i < listaJugadores.size(); i++) {
                Jugador jugador = listaJugadores.get(i);
                data[i][0] = String.valueOf(jugador.getId());    // ID del jugador
                data[i][1] = jugador.getNombre();                // Nombre del jugador
                data[i][2] = String.valueOf(jugador.getNivel()); // Nivel del jugador
            }

            String[] columnas = {"ID", "Nombre", "Nivel"};

            JTable tablaJugadores = new JTable(data, columnas);
            tablaJugadores.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(tablaJugadores);
            JFrame tablaFrame = new JFrame("Lista de Jugadores");
            tablaFrame.setSize(500, 300);
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Lista todos los videojuegos y muestra los detalles en una tabla.
         * Se obtienen los datos de los videojuegos y se muestran en una ventana con una tabla.
         *
         * Detalles:
         * - Recupera una lista de videojuegos desde el router.
         * - Crea una tabla con las columnas "ID", "Título", "Género" y "Precio" para mostrar los datos de los videojuegos.
         * - Añade la tabla a un JScrollPane y lo muestra en una ventana independiente.
         */
        private void listarVideojuegos() {
            ArrayList<VideoJuego> listaVideojuegos = (ArrayList<VideoJuego>) router.ejecutarAccion("videojuegos", "listarVideojuegos");

            String[][] data = new String[listaVideojuegos.size()][4];  // Suponiendo que el VideoJuego tiene ID, Título, Género y Precio

            for (int i = 0; i < listaVideojuegos.size(); i++) {
                VideoJuego videojuego = listaVideojuegos.get(i);
                data[i][0] = String.valueOf(videojuego.getId());   // ID del videojuego
                data[i][1] = videojuego.getTitulo();                // Título del videojuego
                data[i][2] = videojuego.getGenero();                // Género del videojuego
                data[i][3] = String.valueOf(videojuego.getPrecio()); // Precio del videojuego
            }

            String[] columnas = {"ID", "Título", "Género", "Precio"};

            JTable tablaVideojuegos = new JTable(data, columnas);
            tablaVideojuegos.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(tablaVideojuegos);
            JFrame tablaFrame = new JFrame("Lista de Videojuegos");
            tablaFrame.setSize(600, 300);
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Lista todas las partidas y muestra los detalles en una tabla.
         * Se obtienen los datos de las partidas y se muestran en una ventana con una tabla.
         *
         * Detalles:
         * - Recupera una lista de partidas desde el router.
         * - Crea una tabla con las columnas "ID", "ID Jugador", "ID Videojuego" y "Fecha" para mostrar los datos de las partidas.
         * - Añade la tabla a un JScrollPane y lo muestra en una ventana independiente.
         */
        private void listarPartidas() {
            ArrayList<Partida> listaPartidas = (ArrayList<Partida>) router.ejecutarAccion("partidas", "listarPartidas");

            String[][] data = new String[listaPartidas.size()][4];  // Suponiendo que la Partida tiene ID, ID Jugador, ID Videojuego y Fecha

            for (int i = 0; i < listaPartidas.size(); i++) {
                Partida partida = listaPartidas.get(i);
                data[i][0] = String.valueOf(partida.getId());        // ID de la partida
                data[i][1] = String.valueOf(partida.getId_jugador()); // ID del jugador
                data[i][2] = String.valueOf(partida.getId_videojuego()); // ID del videojuego
                data[i][3] = partida.getFecha().toString();          // Fecha de la partida
            }

            String[] columnas = {"ID", "ID Jugador", "ID Videojuego", "Fecha"};

            JTable tablaPartidas = new JTable(data, columnas);
            tablaPartidas.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(tablaPartidas);
            JFrame tablaFrame = new JFrame("Lista de Partidas");
            tablaFrame.setSize(600, 300);
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Muestra el resultado de una operación en un cuadro de diálogo.
         * Si la operación se completó con éxito, muestra un mensaje de éxito.
         * Si hubo un error al realizar la operación, muestra un mensaje de error.
         *
         * @param resultado un booleano que indica si la operación se completó con éxito (true) o no (false)
         *
         * Detalles:
         * - Muestra un cuadro de diálogo informativo con el mensaje correspondiente al resultado de la operación.
         * - Utiliza JOptionPane para mostrar los cuadros de diálogo.
         */
        private void mostrarResultado(boolean resultado) {
            if (resultado) {
                JOptionPane.showMessageDialog(frame, "La operación se completó con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Hubo un error al realizar la operación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Busca un jugador por su ID y muestra el resultado en un cuadro de diálogo.
         * Solicita al usuario que ingrese el ID del jugador a buscar.
         * Llama a la acción "buscarJugadorPorId" en el router y muestra el resultado.
         *
         * Detalles:
         * - Solicita al usuario ingresar el ID del jugador.
         * - Llama a la acción "buscarJugadorPorId" en el router con el ID proporcionado.
         * - Muestra el resultado del jugador en un cuadro de diálogo.
         */
        private void buscarJugadorPorId() {
            String id = JOptionPane.showInputDialog("Ingrese el ID del jugador:");
            Object jugador = router.ejecutarAccion("jugadores", "buscarJugadorPorId", Integer.parseInt(id));
            JOptionPane.showMessageDialog(null, jugador, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        /**
         * Busca un jugador por su nombre y muestra el resultado en un cuadro de diálogo.
         * Solicita al usuario que ingrese el nombre del jugador a buscar.
         * Llama a la acción "buscarJugadorPorNombre" en el router y muestra el resultado.
         *
         * Detalles:
         * - Solicita al usuario ingresar el nombre del jugador.
         * - Llama a la acción "buscarJugadorPorNombre" en el router con el nombre proporcionado.
         * - Muestra el resultado del jugador en un cuadro de diálogo.
         */
        private void buscarJugadorPorNombre() {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
            Object jugador = router.ejecutarAccion("jugadores", "buscarJugadorPorNombre", nombre);
            JOptionPane.showMessageDialog(null, jugador, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        /**
         * Elimina un jugador por su ID y muestra el resultado de la operación.
         * Solicita al usuario que ingrese el ID del jugador a eliminar.
         * Llama a la acción "eliminarJugador" en el router y muestra el resultado.
         *
         * Detalles:
         * - Solicita al usuario ingresar el ID del jugador.
         * - Llama a la acción "eliminarJugador" en el router con el ID proporcionado.
         * - Muestra el resultado de la eliminación utilizando el método mostrarResultado.
         */
        private void eliminarJugador() {
            String id = JOptionPane.showInputDialog("Ingrese el ID del jugador a eliminar:");
            boolean resultado = (boolean) router.ejecutarAccion("jugadores", "eliminarJugador", Integer.parseInt(id));
            mostrarResultado(resultado);
        }

        private void listarVideojuegosDeJugador() {
            String idJugador = JOptionPane.showInputDialog("Ingrese el ID del jugador:");

            if (idJugador != null && Validador.esNumero(idJugador)) {
                ArrayList<VideoJuego> listaVideojuegos = (ArrayList<VideoJuego>) router.ejecutarAccion("videojuegos", "listarVideojuegosDeJugadorID", Integer.parseInt(idJugador));

                if (listaVideojuegos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El jugador con ID " + idJugador + " no tiene videojuegos registrados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String[][] data = new String[listaVideojuegos.size()][4];  // ID, Título, Género, Precio

                for (int i = 0; i < listaVideojuegos.size(); i++) {
                    VideoJuego videojuego = listaVideojuegos.get(i);
                    data[i][0] = String.valueOf(videojuego.getId());
                    data[i][1] = videojuego.getTitulo();
                    data[i][2] = videojuego.getGenero();
                    data[i][3] = String.valueOf(videojuego.getPrecio());
                }

                String[] columnas = {"ID", "Título", "Género", "Precio"};

                JTable tablaVideojuegos = new JTable(data, columnas);
                tablaVideojuegos.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(tablaVideojuegos);
                JFrame tablaFrame = new JFrame("Lista de Videojuegos del Jugador con ID: " + idJugador);
                tablaFrame.setSize(600, 300);
                tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tablaFrame.add(scrollPane);
                tablaFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Lista todos los videojuegos de un jugador específico y muestra los detalles en una tabla.
         * Solicita al usuario que ingrese el ID del jugador.
         * Verifica si el ID es válido y recupera la lista de videojuegos del jugador.
         * Si el jugador no tiene videojuegos registrados, muestra un mensaje informativo.
         * Si el ID es inválido, muestra un mensaje de error.
         *
         * Detalles:
         * - Solicita al usuario ingresar el ID del jugador.
         * - Verifica si el ID es válido utilizando el validador.
         * - Llama a la acción "listarVideojuegosDeJugadorID" en el router con el ID del jugador proporcionado.
         * - Si el jugador no tiene videojuegos registrados, muestra un mensaje informativo.
         * - Si el jugador tiene videojuegos, crea una tabla con las columnas "ID", "Título", "Género" y "Precio" para mostrar los datos de los videojuegos.
         * - Añade la tabla a un JScrollPane y lo muestra en una ventana independiente.
         * - Si el ID es inválido, muestra un mensaje de error.
         */
        private void agregarVideojuego() {
            String titulo = JOptionPane.showInputDialog("Ingrese el título del videojuego:");
            String genero = JOptionPane.showInputDialog("Ingrese el género del videojuego:");
            String precio = JOptionPane.showInputDialog("Ingrese el precio del videojuego:");

            boolean resultado = (boolean) router.ejecutarAccion("videojuegos", "agregarVideojuego", titulo, genero, Double.parseDouble(precio));
            mostrarResultado(resultado);
        }

        private void listarVideojuegosCaroBarato() {
            ArrayList<VideoJuego> listaVideojuegos = (ArrayList<VideoJuego>) router.ejecutarAccion("videojuegos", "listarDeMasCaroAMasBarato");

            String[][] data = new String[listaVideojuegos.size()][4];  // ID, Título, Género y Precio

            for (int i = 0; i < listaVideojuegos.size(); i++) {
                VideoJuego videojuego = listaVideojuegos.get(i);
                data[i][0] = String.valueOf(videojuego.getId());
                data[i][1] = videojuego.getTitulo();
                data[i][2] = videojuego.getGenero();
                data[i][3] = String.valueOf(videojuego.getPrecio());
            }

            String[] columnas = {"ID", "Título", "Género", "Precio"};

            JTable tablaVideojuegos = new JTable(data, columnas);
            tablaVideojuegos.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(tablaVideojuegos);
            JFrame tablaFrame = new JFrame("Lista de Videojuegos (De Más Caro a Más Barato)");
            tablaFrame.setSize(600, 300);
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Lista todos los videojuegos ordenados de más caro a más barato y muestra los detalles en una tabla.
         * Se obtienen los datos de los videojuegos y se muestran en una ventana con una tabla.
         *
         * Detalles:
         * - Recupera una lista de videojuegos desde el router, ordenados de más caro a más barato.
         * - Crea una tabla con las columnas "ID", "Título", "Género" y "Precio" para mostrar los datos de los videojuegos.
         * - Añade la tabla a un JScrollPane y lo muestra en una ventana independiente.
         */
        private void listarVideojuegosPorGenero() {
            String genero = JOptionPane.showInputDialog("Ingrese el género:");

            ArrayList<VideoJuego> listaVideojuegos = (ArrayList<VideoJuego>) router.ejecutarAccion("videojuegos", "listarPorGenero", genero);

            if (listaVideojuegos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron videojuegos del género: " + genero, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[][] data = new String[listaVideojuegos.size()][4];  // ID, Título, Género y Precio

            for (int i = 0; i < listaVideojuegos.size(); i++) {
                VideoJuego videojuego = listaVideojuegos.get(i);
                data[i][0] = String.valueOf(videojuego.getId());
                data[i][1] = videojuego.getTitulo();
                data[i][2] = videojuego.getGenero();
                data[i][3] = String.valueOf(videojuego.getPrecio());
            }

            String[] columnas = {"ID", "Título", "Género", "Precio"};

            JTable tablaVideojuegos = new JTable(data, columnas);
            tablaVideojuegos.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(tablaVideojuegos);
            JFrame tablaFrame = new JFrame("Lista de Videojuegos por Género: " + genero);
            tablaFrame.setSize(600, 300);
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Método que solicita al usuario un ID de videojuego, lo busca a través del router
         * y muestra el resultado en un cuadro de diálogo.
         *
         * Utiliza JOptionPane para solicitar el ID y mostrar el resultado.
         * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
         */
        private void buscarVideojuegoPorId() {
            String id = JOptionPane.showInputDialog("Ingrese el ID del videojuego:");
            Object videojuego = router.ejecutarAccion("videojuegos", "buscarVideojuegoPorId", Integer.parseInt(id));
            JOptionPane.showMessageDialog(null, videojuego, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        /**
         * Método que solicita al usuario los ID del jugador y del videojuego,
         * agrega una nueva partida a través del router, y muestra el resultado en un cuadro de diálogo.
         *
         * Utiliza JOptionPane para solicitar los IDs y mostrar el resultado.
         * La acción de agregar la partida se realiza llamando al método 'ejecutarAccion' del router.
         *
         * @throws NumberFormatException si los IDs ingresados no son números válidos.
         */
        private void agregarPartida() {
            String idJugador = JOptionPane.showInputDialog("Ingrese el ID del jugador:");
            String idVideojuego = JOptionPane.showInputDialog("Ingrese el ID del videojuego:");

            boolean resultado = (boolean) router.ejecutarAccion("partidas", "agregarPartida", Integer.parseInt(idJugador), Integer.parseInt(idVideojuego), generarTiempoAleatorio());
            mostrarResultado(resultado);
        }

        /**
         * Método que genera un tiempo aleatorio en segundos, dentro del rango de 1 a 3600.
         *
         * Utiliza la clase Random para generar un número aleatorio.
         *
         * @return un valor entero que representa un tiempo aleatorio en segundos.
         */
        private int generarTiempoAleatorio() {
            Random r = new Random();
            return r.nextInt(1,3600);
        }

        private void listarPartidaPorId() {
            String id = JOptionPane.showInputDialog(frame, "Ingrese el ID de la partida a buscar:");
            if (id != null && Validador.esNumero(id)) {
                int idInt = Integer.parseInt(id);
                Partida partida = (Partida) router.ejecutarAccion("partidas", "buscarPartidaPorId", idInt);

                JOptionPane.showMessageDialog(frame,
                        partida != null ? partida.toString() : "No se encontró una partida con el ID proporcionado.");
            } else {
                JOptionPane.showMessageDialog(frame, "ID inválido. Por favor ingrese un número válido.");
            }
        }

        /**
         * Método que solicita al usuario el ID de una partida, la busca a través del router
         * y muestra el resultado en un cuadro de diálogo.
         *
         * Utiliza JOptionPane para solicitar el ID y mostrar el resultado.
         * La búsqueda se realiza llamando al método 'ejecutarAccion' del router.
         * Si el ID ingresado no es un número válido, muestra un mensaje de error.
         *
         * @throws NumberFormatException si el ID ingresado no es un número válido.
         */
        private void eliminarPartida() {
            String id = JOptionPane.showInputDialog(frame, "Ingrese el ID de la partida a eliminar:");
            if (id != null && Validador.esNumero(id)) {
                int idInt = Integer.parseInt(id);
                boolean resultado = (boolean) router.ejecutarAccion("partidas", "eliminarPartida", idInt);

                JOptionPane.showMessageDialog(frame,
                        resultado ? "Partida eliminada correctamente." : "No se pudo eliminar la partida.");
            } else {
                JOptionPane.showMessageDialog(frame, "ID inválido. Por favor ingrese un número válido.");
            }
        }

        private void eliminarVideojuego() {
            String id = JOptionPane.showInputDialog(frame, "Ingrese el ID del videojuego a eliminar:");
            if (id != null && Validador.esNumero(id)) {
                int idInt = Integer.parseInt(id);
                boolean resultado = (boolean) router.ejecutarAccion("videojuegos", "eliminarVideojuego", idInt);

                JOptionPane.showMessageDialog(frame,
                        resultado ? "Videojuego eliminado correctamente." : "No se pudo eliminar el videojuego.");
            } else {
                JOptionPane.showMessageDialog(frame, "ID inválido. Por favor ingrese un número válido.");
            }
        }

        /**
         * Método que solicita al usuario el ID de un videojuego, lo elimina a través del router
         * y muestra el resultado en un cuadro de diálogo.
         *
         * Utiliza JOptionPane para solicitar el ID y mostrar el resultado.
         * La eliminación se realiza llamando al método 'ejecutarAccion' del router.
         * Si el ID ingresado no es un número válido, muestra un mensaje de error.
         *
         * @throws NumberFormatException si el ID ingresado no es un número válido.
         */
        private void listarEstadisticasVideojuegosHoras() {
            // Simulamos los datos obtenidos de la base de datos
            ArrayList<Object[]> listaEstadisticas = (ArrayList<Object[]>) router.ejecutarAccion("videojuegos", "listarEstadisticasVideojuegosHoras");

            // Preparamos los datos para la tabla
            String[][] data = new String[listaEstadisticas.size()][3]; // 3 columnas: ID, Título, Tiempo Total, Jugadores Totales

            for (int i = 0; i < listaEstadisticas.size(); i++) {
                Object[] estadistica = listaEstadisticas.get(i);
                data[i][0] = String.valueOf(estadistica[0]); // ID del videojuego
                data[i][1] = String.valueOf(estadistica[1]); // Título del videojuego
                data[i][2] = String.valueOf(estadistica[2]); // Tiempo total jugado
                 }

            // Definimos las columnas
            String[] columnas = {"ID", "Título", "Tiempo Total (Segundos)"};

            // Creamos la tabla
            JTable tablaEstadisticas = new JTable(data, columnas);
            tablaEstadisticas.setFillsViewportHeight(true);

            // Envolvemos la tabla en un JScrollPane
            JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);

            // Creamos el marco para mostrar la tabla
            JFrame tablaFrame = new JFrame("Estadísticas de Videojuegos");
            tablaFrame.setSize(800, 400); // Ajustamos el tamaño de la ventana
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        private void listarEstadisticasVideojuegosTotalJugadores() {
            // Simulamos los datos obtenidos de la base de datos
            ArrayList<Object[]> listaEstadisticas = (ArrayList<Object[]>) router.ejecutarAccion("videojuegos", "listarEstadisticasVideojuegosJugadoresTotales");

            // Preparamos los datos para la tabla
            String[][] data = new String[listaEstadisticas.size()][3]; // 3 columnas: ID, Título, Tiempo Total, Jugadores Totales

            for (int i = 0; i < listaEstadisticas.size(); i++) {
                Object[] estadistica = listaEstadisticas.get(i);
                data[i][0] = String.valueOf(estadistica[0]); // ID del videojuego
                data[i][1] = String.valueOf(estadistica[1]); // Título del videojuego
                data[i][2] = String.valueOf(estadistica[2]); // Tiempo total jugado
            }

            // Definimos las columnas
            String[] columnas = {"ID", "Título", "Total de jugadores"};

            // Creamos la tabla
            JTable tablaEstadisticas = new JTable(data, columnas);
            tablaEstadisticas.setFillsViewportHeight(true);

            // Envolvemos la tabla en un JScrollPane
            JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);

            // Creamos el marco para mostrar la tabla
            JFrame tablaFrame = new JFrame("Estadísticas de Videojuegos");
            tablaFrame.setSize(800, 400); // Ajustamos el tamaño de la ventana
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Método que obtiene estadísticas de videojuegos y las muestra en una tabla dentro de un cuadro de diálogo.
         *
         * Simula la obtención de datos de la base de datos a través del router.
         * Prepara los datos para mostrarlos en una tabla.
         * Crea un JTable para visualizar las estadísticas y lo envuelve en un JScrollPane.
         *
         * La tabla muestra el ID del videojuego, el título y el número total de jugadores.
         * La ventana de la tabla se ajusta a un tamaño de 800x400 y se cierra al hacer clic en el botón de cerrar.
         */
        private void listarEstadisticaJugadoresMasHoras() {
            // Simulamos los datos obtenidos de la base de datos
            ArrayList<Object[]> listaEstadisticas = (ArrayList<Object[]>) router.ejecutarAccion("jugadores", "listarJugadoresMasHoras");

            // Preparamos los datos para la tabla
            String[][] data = new String[listaEstadisticas.size()][3]; // 3 columnas: ID, Nombre, Horas Jugadas

            for (int i = 0; i < listaEstadisticas.size(); i++) {
                Object[] estadistica = listaEstadisticas.get(i);
                data[i][0] = String.valueOf(estadistica[0]); // ID del jugador
                data[i][1] = String.valueOf(estadistica[1]); // Nombre del jugador
                data[i][2] = String.valueOf(estadistica[2]); // Horas totales jugadas
            }

            // Definimos las columnas
            String[] columnas = {"ID", "Nombre", "Horas Jugadas"};

            // Creamos la tabla
            JTable tablaEstadisticas = new JTable(data, columnas);
            tablaEstadisticas.setFillsViewportHeight(true);

            // Envolvemos la tabla en un JScrollPane
            JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);

            // Creamos el marco para mostrar la tabla
            JFrame tablaFrame = new JFrame("Jugadores con Más Horas de Juego");
            tablaFrame.setSize(800, 400); // Ajustamos el tamaño de la ventana
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

        /**
         * Método que obtiene las estadísticas de los jugadores con mayor puntuación y las muestra en una tabla dentro de un cuadro de diálogo.
         *
         * Simula la obtención de datos de la base de datos a través del router.
         * Prepara los datos para mostrarlos en una tabla.
         * Crea un JTable para visualizar las estadísticas y lo envuelve en un JScrollPane.
         *
         * La tabla muestra el ID del jugador, el nombre y la puntuación.
         * La ventana de la tabla se ajusta a un tamaño de 800x400 y se cierra al hacer clic en el botón de cerrar.
         */
        private void listarEstadisticaJugadoresMayorPuntuacion() {
            // Simulamos los datos obtenidos de la base de datos
            ArrayList<Object[]> listaEstadisticas = (ArrayList<Object[]>) router.ejecutarAccion("jugadores", "listarJugadoresMayorPuntuacion");

            // Preparamos los datos para la tabla
            String[][] data = new String[listaEstadisticas.size()][3]; // 3 columnas: ID, Nombre, Puntuación

            for (int i = 0; i < listaEstadisticas.size(); i++) {
                Object[] estadistica = listaEstadisticas.get(i);
                data[i][0] = String.valueOf(estadistica[0]); // ID del jugador
                data[i][1] = String.valueOf(estadistica[1]); // Nombre del jugador
                data[i][2] = String.valueOf(estadistica[2]); // Puntuación
            }

            // Definimos las columnas
            String[] columnas = {"ID", "Nombre", "Puntuación"};

            // Creamos la tabla
            JTable tablaEstadisticas = new JTable(data, columnas);
            tablaEstadisticas.setFillsViewportHeight(true);

            // Envolvemos la tabla en un JScrollPane
            JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);

            // Creamos el marco para mostrar la tabla
            JFrame tablaFrame = new JFrame("Jugadores con Mayor Puntuación");
            tablaFrame.setSize(800, 400); // Ajustamos el tamaño de la ventana
            tablaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tablaFrame.add(scrollPane);
            tablaFrame.setVisible(true);
        }

    }
}
