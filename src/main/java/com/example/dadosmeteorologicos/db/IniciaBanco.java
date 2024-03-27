package com.example.dadosmeteorologicos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.dadosmeteorologicos.model.RegistroDto;

public class IniciaBanco {

    private String url = "jdbc:postgresql://localhost/ApiFatec";
    private String user = "postgres";
    private String password = "root";
    private Connection conn;

    public IniciaBanco() {
        this.conn = conectarBanco();
    }

    public Connection conectarBanco() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void fecharConexao() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }

    public void iniciarBanco(){
        try {
            if (conn != null) {
                System.out.println("Banco Iniciado");
    
                String sql = "CREATE TABLE IF NOT EXISTS Registro (" +
                    "id UUID PRIMARY KEY," +
                    "cidade VARCHAR(255)," +
                    "estacao INT," +
                    "data DATE," +
                    "hora TIME," +
                    "temperaturaMedia DECIMAL(5,2)," +
                    "umidadeMedia DECIMAL(5,2)," +
                    "velVento DECIMAL(5,2)," +
                    "dirVento DECIMAL(5,2)," +
                    "chuva DECIMAL(5,2)," +
                    "temperaturaSuspeita BOOLEAN," +
                    "umidadeSuspeita BOOLEAN," +
                    "velocidadeVentoSuspeita BOOLEAN," +
                    "direcaoVentoSuspeita BOOLEAN," +
                    "chuvaSuspeita BOOLEAN" +
                    ")";
    
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
    
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RegistroDto> selecionarTodosRegistros() {
        List<RegistroDto> registros = new ArrayList<>();

        try {
            if (conn != null) {
                String sql = "SELECT * FROM Registro";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String cidade = rs.getString("cidade");
                    Integer estacao = rs.getInt("estacao");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    Double temperaturaMedia = rs.getDouble("temperaturaMedia");
                    Double umidadeMedia = rs.getDouble("umidadeMedia");
                    Double velVento = rs.getDouble("velVento");
                    Double dirVento = rs.getDouble("dirVento");
                    Double chuva = rs.getDouble("chuva");
                    boolean temperaturaSuspeita = rs.getBoolean("temperaturaSuspeita");
                    boolean umidadeSuspeita = rs.getBoolean("umidadeSuspeita");
                    boolean velocidadeVentoSuspeita = rs.getBoolean("velocidadeVentoSuspeita");
                    boolean direcaoVentoSuspeita = rs.getBoolean("direcaoVentoSuspeita");
                    boolean chuvaSuspeita = rs.getBoolean("chuvaSuspeita");

                    RegistroDto registro = new RegistroDto(id, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita);
                    registros.add(registro);
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return registros;
    }   

    public List<RegistroDto> selecionarTodosRegistrosSuspeitos(){
        List<RegistroDto> registros = new ArrayList<>();
        try {
            if (conn != null) {
                String sql = "SELECT * FROM Registro WHERE temperaturaSuspeita = true OR umidadeSuspeita = true OR velocidadeVentoSuspeita = true OR direcaoVentoSuspeita = true OR chuvaSuspeita = true";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String cidade = rs.getString("cidade");
                    Integer estacao = rs.getInt("estacao");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    Double temperaturaMedia = rs.getDouble("temperaturaMedia");
                    Double umidadeMedia = rs.getDouble("umidadeMedia");
                    Double velVento = rs.getDouble("velVento");
                    Double dirVento = rs.getDouble("dirVento");
                    Double chuva = rs.getDouble("chuva");
                    Boolean temperaturaSuspeita = rs.getBoolean("temperaturaSuspeita");
                    Boolean umidadeSuspeita = rs.getBoolean("umidadeSuspeita");
                    Boolean velocidadeVentoSuspeita = rs.getBoolean("velocidadeVentoSuspeita");
                    Boolean direcaoVentoSuspeita = rs.getBoolean("direcaoVentoSuspeita");
                    Boolean chuvaSuspeita = rs.getBoolean("chuvaSuspeita");

                    RegistroDto registro = new RegistroDto(id, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita);
                    System.out.println(registro);
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return registros;
    }
   
}