//package repositorios;
//
//import entidades.Partida;
//import entidades.Jugador;
//import repositorios.excepciones.RepositorioPartidasException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
///**
// * Repositorio para gestionar partidas con mejoras de concurrencia y seguridad.
// */
//public class RepositorioPartidas {
//
//    // Implementación Thread-safe del patrón Singleton con inicialización eager
//    private static final RepositorioPartidas INSTANCE = new RepositorioPartidas();
//
//    // Estructura de datos concurrente para almacenar partidas
//    private final ConcurrentHashMap<String, Partida> partidas;
//
//    // Lock para operaciones críticas
//    private final ReentrantLock lock;
//
//    /**
//     * Constructor privado para prevenir instanciación directa.
//     */
//    private RepositorioPartidas() {
//        this.partidas = new ConcurrentHashMap<>();
//        this.lock = new ReentrantLock(true); // Fair lock para prevenir inanición
//    }
//
//    /**
//     * Obtiene la instancia única del repositorio.
//     *
//     * @return La instancia del repositorio.
//     */
//    public static RepositorioPartidas getInstance() {
//        return INSTANCE;
//    }
//
//    /**
//     * Crea una nueva partida asociada a una sala, añadiendo un anfitrión inicial.
//     *
//     * @param sala ID de la sala.
//     * @param anfitrion Jugador que será el anfitrión.
//     * @throws RepositorioPartidasException Si ya existe una partida para la sala.
//     */
//    public void crearPartida(String sala, Jugador anfitrion) throws RepositorioPartidasException {
//        lock.lock();
//        try {
//            if (partidas.containsKey(sala)) {
//                throw new RepositorioPartidasException(
//                        "Ya existe una partida para esta sala: " + sala
//                );
//            }
//
//            Partida nuevaPartida = new Partida(anfitrion);  // Crear la partida con el anfitrión
//            partidas.put(sala, nuevaPartida);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    /**
//     * Actualiza el puntaje de un usuario en una partida con validación.
//     *
//     * @param sala ID de la sala.
//     * @param usuario Nombre del usuario.
//     * @param puntaje Puntaje a agregar.
//     * @throws RepositorioPartidasException Si la partida no existe o no puede actualizarse.
//     */
//    public void actualizarPuntaje(String sala, String usuario, int puntaje)
//            throws RepositorioPartidasException {
//        Partida partida = obtenerPartida(sala);
//
////        if (partida.getEstado() != EstadoPartida.EN_CURSO) {
////            throw new RepositorioPartidasException(
////                    "No se puede actualizar puntaje. Partida no está en curso: " + sala
////            );
//        }
//
//        // Aquí, deberías agregar lógica para actualizar el puntaje del jugador.
//    }
//
//    /**
//     * Finaliza una partida de manera segura.
//     *
//     * @param sala ID de la sala.
//     * @throws RepositorioPartidasException Si la partida no existe.
//     */
//    public void finalizarPartida(String sala) throws RepositorioPartidasException {
//        Partida partida = obtenerPartida(sala);
//        partida.setEstado(EstadoPartida.FINALIZADA);
//    }
//
//    /**
//     * Obtiene una partida por su ID de sala.
//     *
//     * @param sala ID de la sala.
//     * @return La partida correspondiente.
//     * @throws RepositorioPartidasException Si la partida no existe.
//     */
//    public Partida obtenerPartida(String sala) throws RepositorioPartidasException {
//        return Optional.ofNullable(partidas.get(sala))
//                .orElseThrow(() -> new RepositorioPartidasException(
//                        "No existe partida para la sala: " + sala
//                ));
//    }
//
//    /**
//     * Elimina una partida del repositorio.
//     *
//     * @param sala ID de la sala.
//     * @throws RepositorioPartidasException Si la partida no existe.
//     */
//    public void eliminarPartida(String sala) throws RepositorioPartidasException {
//        lock.lock();
//        try {
//            Partida partida = partidas.remove(sala);
//
//            if (partida == null) {
//                throw new RepositorioPartidasException(
//                        "No se pudo eliminar. No existe partida para la sala: " + sala
//                );
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    /**
//     * Obtiene todas las partidas activas de manera segura e inmutable.
//     *
//     * @return Lista no modificable de partidas activas.
//     */
//    public List<Partida> getPartidasActivas() {
//        return Collections.unmodifiableList(
//                List.copyOf(partidas.values())
//        );
//    }
//
//    /**
//     * Obtiene las partidas activas que cumplan con un criterio específico.
//     *
//     * @param filtro Función de filtrado para las partidas.
//     * @return Lista de partidas que cumplen el criterio.
//     */
//    public List<Partida> getPartidasActivas(Predicate<Partida> filtro) {
//        return partidas.values().stream()
//                .filter(filtro)
//                .collect(Collectors.toUnmodifiableList());
//    }
//
//    /**
//     * Verifica si existe una partida para una sala específica.
//     *
//     * @param sala ID de la sala a verificar.
//     * @return true si la partida existe, false en caso contrario.
//     */
//    public boolean existePartida(String sala) {
//        return partidas.containsKey(sala);
//    }
//}
