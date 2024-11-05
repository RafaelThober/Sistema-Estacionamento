import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estacionamento {

    private List<Vaga> vagas;
    private List<Veiculo> historico;
    private DateTimeFormatter formatter;

    public Estacionamento() {
        vagas = new ArrayList<>();
        historico = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void cadastrarVaga(int numero, String tamanho) {
        if (vagaExiste(numero)) {
            System.out.println("\nVaga com este número já está cadastrada.\n");
            return;
        }
        vagas.add(new Vaga(numero, tamanho));
        System.out.println("\nVaga cadastrada com sucesso.\n");
    }

    public void registrarEntradaVeiculo(String placa, String modelo, String tamanho, String horaEntradaStr) {
        if (vagas.isEmpty()) {
            System.out.println("\nNenhuma vaga cadastrada no sistema. Por favor, cadastre uma vaga primeiro.\n");
            return;
        }
        LocalTime horaEntradaTime = LocalTime.parse(horaEntradaStr, formatter);
        LocalDateTime horaEntrada = LocalDateTime.of(LocalDate.now(), horaEntradaTime);
        Veiculo veiculo = new Veiculo(placa, modelo, tamanho);
        veiculo.registrarEntrada(horaEntrada);
        for (Vaga vaga : vagas) {
            if (vaga.isDisponivel() && vaga.getTamanho().equalsIgnoreCase(veiculo.getTamanho())) {
                vaga.ocupar(veiculo);
                System.out.println("\nVeículo registrado na vaga " + vaga.getNumero() + ".\n");
                return;
            }
        }
        System.out.println("\nNenhuma vaga disponível para o tamanho do veículo.\n");
    }

    public void registrarSaidaVeiculo(String placa, String horaSaidaStr) {
        LocalTime horaSaidaTime = LocalTime.parse(horaSaidaStr, formatter);
        LocalDateTime horaSaida = LocalDateTime.of(LocalDate.now(), horaSaidaTime);
        for (Vaga vaga : vagas) {
            if (!vaga.isDisponivel() && vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                Veiculo veiculo = vaga.getVeiculo();
                veiculo.registrarSaida(horaSaida);
                double valorPago = veiculo.calcularValorPago();
                System.out.println("\nVeículo removido da vaga " + vaga.getNumero() + ". Valor pago: R$" + valorPago + "\n");
                historico.add(veiculo);
                vaga.liberar();
                return;
            }
        }
        System.out.println("\nVeículo não encontrado.\n");
    }

    public void gerarRelatorioVagasOcupadas() {
        System.out.println("\nVagas ocupadas no momento:\n");
        boolean vagasOcupadas = false;
        for (Vaga vaga : vagas) {
            if (!vaga.isDisponivel()) {
                System.out.println("Vaga: " + vaga.getNumero() + ", Tamanho: " + vaga.getTamanho() + ", Placa do veículo: " + vaga.getVeiculo().getPlaca());
                vagasOcupadas = true;
            }
        }
        if (!vagasOcupadas) {
            System.out.println("Nenhuma vaga ocupada no momento.\n");
        }
    }

    public void gerarHistoricoVeiculos() {
        System.out.println("\nHistórico de permanência de veículos:\n");
        if (historico.isEmpty()) {
            System.out.println("Nenhum veículo registrado no histórico.\n");
            return;
        }
        for (Veiculo veiculo : historico) {
            System.out.println("Placa: " + veiculo.getPlaca() + ", Tempo de permanência: " + veiculo.calcularTempoPermanencia() + " minutos, Valor pago: R$" + veiculo.calcularValorPago());
        }
        System.out.println();
    }

    private boolean vagaExiste(int numero) {
        for (Vaga vaga : vagas) {
            if (vaga.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Estacionamento sistema = new Estacionamento();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=================================");
            System.out.println("  GERENCIADOR DE ESTACIONAMENTO  ");
            System.out.println("=================================");
            System.out.println("1. Cadastrar Vaga");
            System.out.println("2. Registrar entrada de veículo");
            System.out.println("3. Registrar saída de veículo");
            System.out.println("4. Gerar relatório de vagas ocupadas");
            System.out.println("5. Gerar histórico de veículos");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    System.out.println("Cadastrar Vaga");
                    System.out.print("Digite o número da vaga: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o tamanho da vaga (Pequena, Média, Grande): ");
                    String tamanhoVaga = scanner.nextLine();
                    sistema.cadastrarVaga(numero, tamanhoVaga);
                    break;
                case 2:
                    System.out.println("Registrar Entrada de Veículo");
                    System.out.print("Digite a placa do veículo: ");
                    String placa = scanner.nextLine();
                    System.out.print("Digite o modelo do veículo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Digite o tamanho do veículo (Pequeno, Médio, Grande): ");
                    String tamanho = scanner.nextLine();
                    System.out.print("Digite a hora de entrada (HH:mm): ");
                    String horaEntradaStr = scanner.nextLine();
                    sistema.registrarEntradaVeiculo(placa, modelo, tamanho, horaEntradaStr);
                    break;
                case 3:
                    System.out.println("Registrar Saída de Veículo");
                    System.out.print("Digite a placa do veículo: ");
                    placa = scanner.nextLine();
                    System.out.print("Digite a hora de saída (HH:mm): ");
                    String horaSaidaStr = scanner.nextLine();
                    sistema.registrarSaidaVeiculo(placa, horaSaidaStr);
                    break;
                case 4:
                    System.out.println("Relatório de Vagas Ocupadas");
                    sistema.gerarRelatorioVagasOcupadas();
                    break;
                case 5:
                    System.out.println("Histórico de Veículos");
                    sistema.gerarHistoricoVeiculos();
                    break;
                case 6:
                    System.out.println("Obrigado por utilizar o Gerenciador!\n");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.\n");
                    break;
            }
        }
    }
}
