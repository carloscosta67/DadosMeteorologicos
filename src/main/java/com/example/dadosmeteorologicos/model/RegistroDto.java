package com.example.dadosmeteorologicos.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistroDto {
    private UUID id;
    private String cidade;
    private Integer estacao;
    private LocalDate data;
    private LocalTime hora;
    private Double temperaturaMedia;
    private Double umidadeMedia;
    private Double velVento;
    private Double dirVento;
    private Double chuva;
    private boolean temperaturaSuspeita = false;
    private boolean umidadeSuspeita = false;
    private boolean velocidadeVentoSuspeita = false;
    private boolean direcaoVentoSuspeita = false;
    private boolean chuvaSuspeita = false;
    

    public RegistroDto(String cidade, Integer estacao, LocalDate data, LocalTime hora, Double temperaturaMedia,
    Double umidadeMedia, Double velVento, Double dirVento, Double chuva, boolean temperaturaSuspeita, boolean umidadeSuspeita, boolean velocidadeVentoSuspeita, boolean direcaoVentoSuspeita, boolean chuvaSuspeita) {
    this.id = UUID.randomUUID();
    this.cidade = cidade;
    this.estacao = estacao;
    this.data = data;
    this.hora = hora;
    this.temperaturaMedia = temperaturaMedia;
    this.umidadeMedia = umidadeMedia;
    this.velVento = velVento;
    this.dirVento = dirVento;
    this.chuva = chuva;
    this.temperaturaSuspeita = temperaturaSuspeita;
    this.umidadeSuspeita = umidadeSuspeita;
    this.velocidadeVentoSuspeita = velocidadeVentoSuspeita;
    this.direcaoVentoSuspeita = direcaoVentoSuspeita;
    this.chuvaSuspeita = chuvaSuspeita;
}

public RegistroDto(UUID id, String cidade, Integer estacao, LocalDate data, LocalTime hora, Double temperaturaMedia,
    Double umidadeMedia, Double velVento, Double dirVento, Double chuva, boolean temperaturaSuspeita, boolean umidadeSuspeita, boolean velocidadeVentoSuspeita, boolean direcaoVentoSuspeita, boolean chuvaSuspeita) {
    this.id = id;
    this.cidade = cidade;
    this.estacao = estacao;
    this.data = data;
    this.hora = hora;
    this.temperaturaMedia = temperaturaMedia;
    this.umidadeMedia = umidadeMedia;
    this.velVento = velVento;
    this.dirVento = dirVento;
    this.chuva = chuva;
    this.temperaturaSuspeita = temperaturaSuspeita;
    this.umidadeSuspeita = umidadeSuspeita;
    this.velocidadeVentoSuspeita = velocidadeVentoSuspeita;
    this.direcaoVentoSuspeita = direcaoVentoSuspeita;
    this.chuvaSuspeita = chuvaSuspeita;
}

    

}
