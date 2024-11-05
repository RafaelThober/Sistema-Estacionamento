
class Vaga {

    private int numero;
    private String tamanho;
    private boolean disponivel;
    private Veiculo veiculo;

    public Vaga(int numero, String tamanho) {
        this.numero = numero;
        this.tamanho = normalizarTamanho(tamanho);
        this.disponivel = true;
        this.veiculo = null;
    }

    public int getNumero() {
        return numero;
    }

    public String getTamanho() {
        return tamanho;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void ocupar(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.disponivel = false;
    }

    public void liberar() {
        this.veiculo = null;
        this.disponivel = true;
    }

    public Veiculo getVeiculo() {
        return veiculo;
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
