package com.example.dadosmeteorologicos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.dadosmeteorologicos.model.Cidade;
import com.example.dadosmeteorologicos.model.RegistroDto;

public class IniciaBanco {

    private String url = "jdbc:postgresql://localhost/ApiFatec";
    private String user = "postgres";
    private String password = "root";


    public void iniciarBanco(){
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database!");
    
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

    public void salvarRegistro(List<RegistroDto> listaRegistroDto) {
        String url = "jdbc:postgresql://localhost/ApiFatec";
        String user = "postgres";
        String password = "root";
        for (RegistroDto registro : listaRegistroDto) {
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                if (conn != null) {
                    String sql = "INSERT INTO Registro (id, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setObject(1, registro.getId());
                    pstmt.setString(2, registro.getCidade());
                    pstmt.setInt(3, registro.getEstacao());
                    pstmt.setDate(4, java.sql.Date.valueOf(registro.getData()));
                    pstmt.setTime(5, java.sql.Time.valueOf(registro.getHora()));
                    setDoubleOrNull(pstmt, 6, registro.getTemperaturaMedia());
                    setDoubleOrNull(pstmt, 7, registro.getUmidadeMedia());
                    setDoubleOrNull(pstmt, 8, registro.getVelVento());
                    setDoubleOrNull(pstmt, 9, registro.getDirVento());
                    setDoubleOrNull(pstmt, 10, registro.getChuva());
                    pstmt.setBoolean(11, registro.isTemperaturaSuspeita());
                    pstmt.setBoolean(12, registro.isUmidadeSuspeita());
                    pstmt.setBoolean(13, registro.isVelocidadeVentoSuspeita());
                    pstmt.setBoolean(14, registro.isDirecaoVentoSuspeita());
                    pstmt.setBoolean(15, registro.isChuvaSuspeita());
        
                    pstmt.executeUpdate();
                }
                
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }   
    }

    private void setDoubleOrNull(PreparedStatement pstmt, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            pstmt.setDouble(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, java.sql.Types.DOUBLE);
        }
    }

        public List<Cidade> selecionarTodasCidades() {
            List<Cidade> cidades = new ArrayList<>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                if (conn != null) {
                    String sql = "SELECT * FROM Registro";

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        UUID id = UUID.fromString(rs.getString("id"));
                        String nome = rs.getString("nome");
                        String codigo = rs.getString("codigo");

                        Cidade cidade = new Cidade(id, nome, codigo);
                        cidades.add(cidade);
                    }
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }  
        return cidades;
    }

    public List<RegistroDto> selecionarTodosRegistros() {
        List<RegistroDto> registros = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
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
}
