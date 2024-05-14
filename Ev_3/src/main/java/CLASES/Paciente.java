package CLASES;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Paciente {

    public static void deletePaciente(Connection con, int ID_paciente){
        String sql = "DELETE FROM paciente WHERE ID_paciente = " + ID_paciente;
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se eliminó el paciente con ID: " + ID_paciente);
            } else {
                System.out.println("No se encontró el paciente con ID: " + ID_paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar eliminar el paciente", e);
        }
    }

    public static void insertarPaciente(Connection con, String nombre, String apellido, int edad, int altura, int peso){
        String sql = "INSERT INTO paciente (Nombre, Apellido, Edad, Altura, Peso) VALUES ('" +
                nombre + "', '" + apellido + "', " + edad + ", " + altura + ", " + peso + ")";
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se insertó un nuevo paciente");
            } else {
                System.out.println("No se pudo insertar el paciente");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar insertar el paciente", e);
        }
    }

    public static void consultarPacientes(Connection con) {
        String sql = "SELECT * FROM paciente";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_paciente");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                int edad = rs.getInt("Edad");
                int altura = rs.getInt("Altura");
                int peso = rs.getInt("Peso");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido +
                        ", Edad: " + edad + ", Altura: " + altura + ", Peso: " + peso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los pacientes", e);
        }
    }
}