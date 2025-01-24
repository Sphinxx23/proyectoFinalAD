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

public class VistaSwingRouter {
    private static Router router; // Asume que el router ya está inicializado
    private static JFrame frame;

    public VistaSwingRouter(Router rout){
        this.router = rout;
    }

    public void mostrarMenu() {
        // Crear la ventana principal
        frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(17, 1, 10, 10)); // 17 opciones, espaciadas

        // Crear botones para cada opción del menú
        String[] opciones = {
                "Agregar Jugador",
                "Listar todos los Jugadores",
                "Buscar jugador por ID",
                "Buscar jugador por Nombre",
                "Eliminar Jugador",
                "Listar Videojuegos a los que ha jugado X jugador",
                "Agregar Videojuego",
                "Listar Videojuegos",
                "Listar Videojuegos de más caro a más barato",
                "Listar Videojuegos por género",
                "Buscar videojuego por ID",
                "Eliminar Videojuego",
                "Agregar Partida",
                "Listar Partidas",
                "Listar partida por ID",
                "Eliminar Partida",
                "Salir"
        };

        for (String opcion : opciones) {
            JButton button = new JButton(opcion);
            button.addActionListener(new MenuActionListener(opcion));
            panel.add(button);
        }

        // Agregar el panel a la ventana principal
        frame.add(panel);

        // Hacer la ventana visible
        frame.setVisible(true);
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
                case "Buscar jugador por ID":
                    buscarJugadorPorId();
                    break;
                case "Buscar jugador por Nombre":
                    buscarJugadorPorNombre();
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
                case "Salir":
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no reconocida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void agregarJugador() {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del jugador:");
            String nivel = JOptionPane.showInputDialog("Ingrese nivel del jugador:");
            String puntuacion = JOptionPane.showInputDialog("Ingrese puntuación del jugador:");

            boolean resultado = (boolean) router.ejecutarAccion("jugadores", "agregarJugador", nombre, Integer.parseInt(nivel), Integer.parseInt(puntuacion));
            mostrarResultado(resultado);
        }

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

        private void mostrarResultado(boolean resultado) {
            if (resultado) {
                JOptionPane.showMessageDialog(frame, "La operación se completó con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Hubo un error al realizar la operación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void buscarJugadorPorId() {
            String id = JOptionPane.showInputDialog("Ingrese el ID del jugador:");
            Object jugador = router.ejecutarAccion("jugadores", "buscarJugadorPorId", Integer.parseInt(id));
            JOptionPane.showMessageDialog(null, jugador, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        private void buscarJugadorPorNombre() {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
            Object jugador = router.ejecutarAccion("jugadores", "buscarPorNombre", nombre);
            JOptionPane.showMessageDialog(null, jugador, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

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


        private void agregarVideojuego() {
            String titulo = JOptionPane.showInputDialog("Ingrese el título del videojuego:");
            String genero = JOptionPane.showInputDialog("Ingrese el género del videojuego:");
            String precio = JOptionPane.showInputDialog("Ingrese el precio del videojuego:");

            boolean resultado = (boolean) router.ejecutarAccion("videojuegos", "agregarVideojuego", titulo, genero, Integer.parseInt(precio));
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


        private void buscarVideojuegoPorId() {
            String id = JOptionPane.showInputDialog("Ingrese el ID del videojuego:");
            Object videojuego = router.ejecutarAccion("videojuegos", "buscarVideojuegoPorId", Integer.parseInt(id));
            JOptionPane.showMessageDialog(null, videojuego, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        private void agregarPartida() {
            String idJugador = JOptionPane.showInputDialog("Ingrese el ID del jugador:");
            String idVideojuego = JOptionPane.showInputDialog("Ingrese el ID del videojuego:");

            boolean resultado = (boolean) router.ejecutarAccion("partidas", "agregarPartida", Integer.parseInt(idJugador), Integer.parseInt(idVideojuego));
            mostrarResultado(resultado);
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
    }
}
