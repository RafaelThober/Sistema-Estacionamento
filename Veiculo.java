
import java.time.Duration;
import java.time.LocalDateTime;

class Veiculo {

    private String placa;
    private String modelo;
    private String tamanho;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    public Veiculo(String placa, String modelo, String tamanho) {
        this.placa = placa;
        this.modelo = modelo;
        this.tamanho = normalizarTamanho(tamanho);
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void registrarEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void registrarSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public long calcularTempoPermanencia() {
        return Duration.between(horaEntrada, horaSaida).toMinutes();
    }

    public double calcularValorPago() {
        long minutos = calcularTempoPermanencia();
        if (minutos <= 60) {
            return 5.0;
        } else if (minutos <= 180) {
            return 10.0;
        } else {
            return 15.0;
        }
    }

    private String normalizarTamanho(String tamanho) {
        switch (tamanho.toLowerCase()) {
            case "médio":
            case "média":
                return "médio";
            case "pequeno":
            case "pequena":
                return "pequeno";
            case "grande":
                return "grande";
            default:
                return tamanho.toLowerCase();
        }
    }
}
