package CLASES;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Agendar_citas {

    public static void deleteCita(Connection con, int ID_cita){
        String sql = "DELETE FROM agendar_citas WHERE ID_cita = " + ID_cita;
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se eliminó la cita con ID: " + ID_cita);
            } else {
                System.out.println("No se encontró la cita con ID: " + ID_cita);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar eliminar la cita", e);
        }
    }

    public static void insertarCita(Connection con, int ID_paciente, String fecha, String hora, int ID_doctor, String motivo){
        String sql = "INSERT INTO agendar_citas (ID_paciente, Fecha_cita, Hora_cita, ID_doctor, Motivo) VALUES (" +
                ID_paciente + ", '" + fecha + "', '" + hora + "', " + ID_doctor + ", '" + motivo + "')";
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result == 1){
                System.out.println("Se agendó una nueva cita");
            } else {
                System.out.println("No se pudo agendar la cita");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar agendar la cita", e);
        }
    }

    public static void consultarCitas(Connection con) {
        String sql = "SELECT * FROM agendar_citas";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_cita");
                int id_paciente = rs.getInt("ID_paciente");
                String fecha = rs.getString("Fecha_cita");
                String hora = rs.getString("Hora_cita");
                int id_doctor = rs.getInt("ID_doctor");
                String motivo = rs.getString("Motivo");
                System.out.println("ID: " + id + ", ID Paciente: " + id_paciente + ", Fecha: " + fecha +
                        ", Hora: " + hora + ", ID Doctor: " + id_doctor + ", Motivo: " + motivo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las citas", e);
        }
    }
}