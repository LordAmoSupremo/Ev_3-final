package CLASES;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Doctor {

    public static void deleteDoctor(Connection con, int ID_doctor){
        String sql = "DELETE FROM doctor WHERE ID_doctor = " + ID_doctor;
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se eliminó el doctor con ID: " + ID_doctor);
            } else {
                System.out.println("No se encontró el doctor con ID: " + ID_doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar eliminar el doctor", e);
        }
    }

    public static void insertarDoctor(Connection con, String nombre, String apellido, String especialidad){
        String sql = "INSERT INTO doctor (Nombre, Apellido, Especialidad) VALUES ('" +
                nombre + "', '" + apellido + "', '" + especialidad + "')";
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se insertó un nuevo doctor");
            } else {
                System.out.println("No se pudo insertar el doctor");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar insertar el doctor", e);
        }
    }

    public static void consultarDoctores(Connection con) {
        String sql = "SELECT * FROM doctor";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_doctor");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String especialidad = rs.getString("Especialidad");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido +
                        ", Especialidad: " + especialidad);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los doctores", e);
        }
    }
}
