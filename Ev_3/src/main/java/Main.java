import java.sql.*;
import static CLASES.Paciente.*;
import static CLASES.Doctor.*;
import static CLASES.Agendar_citas.*;
import java.util.Scanner;
public class Main {

    public static Connection Conectar_bd(String bd) {
        Connection connection = null;
        String host = "jdbc:mysql://localhost:3306/" + bd;
        String user = "root";
        String password = "Idraptors#3%";
        System.out.println("Conectando...");
        try {
            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Conexion exitosa!!!");
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void Desconexion(Connection con) {
        try{
            con.close();
            System.out.println("Desconectado!!!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new  RuntimeException(e);
        }
    }
       public static void main(String[] args) {
        Connection bd = Conectar_bd("clinica");
        Scanner scanner = new Scanner(System.in);


           int intentos = 3;
           boolean autenticado = false;

           do {
               System.out.print("Nombre de usuario: ");
               String nombreUsuario = scanner.nextLine();
               System.out.print("Contraseña: ");
               String contrasena = scanner.nextLine();

               autenticado = autenticarUsuario(bd, nombreUsuario, contrasena);
               if (!autenticado) {
                   System.out.println("Nombre de usuario o contraseña incorrectos. Intentos restantes: " + (--intentos));
               }
           } while (!autenticado && intentos > 0);

           if (autenticado) {
               System.out.println("Inicio de sesión exitoso. ¡Bienvenido!");
               ejecutarMenu(bd, scanner);
           } else {
               System.out.println("Se agotaron los intentos de inicio de sesión. Cerrando el programa...");
           }

           Desconexion(bd);
           scanner.close();
       }

    public static boolean autenticarUsuario(Connection con, String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasena);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Si hay al menos un resultado, el usuario es válido
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar usuario", e);
        }
    }

    public static void ejecutarMenu(Connection bd, Scanner scanner) {

        int opcion;
           do {
               System.out.println("\nMenú:");
               System.out.println("1. Gestionar pacientes");
               System.out.println("2. Gestionar doctores");
               System.out.println("3. Gestionar citas");
               System.out.println("0. Salir");
               System.out.print("Seleccione una opción: ");
               opcion = scanner.nextInt();

               switch (opcion) {
                   case 1:
                       menuPacientes(bd, scanner);
                       break;
                   case 2:
                       menuDoctores(bd, scanner);
                       break;
                   case 3:
                       menuCitas(bd, scanner);
                       break;
                   case 0:
                       System.out.println("Saliendo del programa...");
                       break;
                   default:
                       System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
               }
           } while (opcion != 0);

           Desconexion(bd);
           scanner.close();
       }

    public static void menuPacientes(Connection bd, Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nMenú Pacientes:");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Consultar pacientes");
            System.out.println("3. Eliminar paciente");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agregarPaciente(bd, scanner);
                    break;
                case 2:
                    consultarPacientes(bd);
                    break;
                case 3:
                    eliminarPaciente(bd, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 0);
    }

    public static void agregarPaciente(Connection bd, Scanner scanner) {
        System.out.println("\nIngrese los datos del nuevo paciente:");
        System.out.print("Nombre: ");
        String nombre = scanner.next();
        System.out.print("Apellido: ");
        String apellido = scanner.next();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        System.out.print("Altura: ");
        int altura = scanner.nextInt();
        System.out.print("Peso: ");
        int peso = scanner.nextInt();

        insertarPaciente(bd, nombre, apellido, edad, altura, peso);
    }

    public static void eliminarPaciente(Connection bd, Scanner scanner) {
        System.out.print("\nIngrese el ID del paciente a eliminar: ");
        int paciente_id = scanner.nextInt();

        deletePaciente(bd, paciente_id);
    }
    public static void menuDoctores(Connection bd, Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nMenú Doctores:");
            System.out.println("1. Agregar doctor");
            System.out.println("2. Consultar doctores");
            System.out.println("3. Eliminar doctor");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agregarDoctor(bd, scanner);
                    break;
                case 2:
                    consultarDoctores(bd);
                    break;
                case 3:
                    eliminarDoctor(bd, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 0);
    }

    public static void agregarDoctor(Connection bd, Scanner scanner) {
        System.out.println("\nIngrese los datos del nuevo doctor:");
        System.out.print("Nombre: ");
        String nombre = scanner.next();
        System.out.print("Apellido: ");
        String apellido = scanner.next();
        System.out.print("Especialidad: ");
        String especialidad = scanner.next();

        insertarDoctor(bd, nombre, apellido, especialidad);
    }

    public static void eliminarDoctor(Connection bd, Scanner scanner) {
        System.out.print("\nIngrese el ID del doctor a eliminar: ");
        int doctor_id = scanner.nextInt();

        deleteDoctor(bd, doctor_id);
    }
    public static void menuCitas(Connection bd, Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nMenú Citas:");
            System.out.println("1. Agendar nueva cita");
            System.out.println("2. Consultar citas agendadas");
            System.out.println("3. Eliminar cita");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agendarNuevaCita(bd, scanner);
                    break;
                case 2:
                    consultarCitas(bd);
                    break;
                case 3:
                    eliminarCita(bd, scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 0);
    }

    public static void agendarNuevaCita(Connection bd, Scanner scanner) {
        System.out.println("\nIngrese los datos de la nueva cita:");
        System.out.print("ID del paciente: ");
        int id_paciente = scanner.nextInt();
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.next();
        System.out.print("Hora (HH:MM): ");
        String hora = scanner.next();
        System.out.print("ID del doctor: ");
        int id_doctor = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();

        insertarCita(bd, id_paciente, fecha, hora, id_doctor, motivo);
    }

    public static void eliminarCita(Connection bd, Scanner scanner) {
        System.out.print("\nIngrese el ID de la cita a eliminar: ");
        int cita_id = scanner.nextInt();

        deleteCita(bd, cita_id);
    }
}
