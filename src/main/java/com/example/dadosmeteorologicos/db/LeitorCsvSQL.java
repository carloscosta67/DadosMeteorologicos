package com.example.dadosmeteorologicos.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.example.dadosmeteorologicos.model.RegistroDto;

public class LeitorCsvSQL extends IniciaBanco{
    private Connection conn;

    public LeitorCsvSQL() {
        this.conn = super.conectarBanco();
    }

    public void salvarRegistro(List<RegistroDto> listaRegistroDto) {
   
        try {
            if (conn != null) {
                conn.setAutoCommit(false); // Inicia uma transação
    
                String sql = "INSERT INTO Registro" + 
                "(id, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita)"+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (RegistroDto registro : listaRegistroDto) {
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
    
                        pstmt.addBatch();
                    }
    
                    pstmt.executeBatch();
                    conn.commit(); // Finaliza a transação
                } catch (SQLException e) {
                    conn.rollback(); // Desfaz as alterações se ocorrer um erro
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    private void setDoubleOrNull(PreparedStatement pstmt, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            pstmt.setDouble(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, java.sql.Types.DOUBLE);
        }
    }

}
