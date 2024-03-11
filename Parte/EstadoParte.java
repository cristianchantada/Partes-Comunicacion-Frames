package Parte;
public enum EstadoParte {
    EN_PROCESO("en proceso"),
    TERMINADO("terminado");

    private final String estadoParte;

    EstadoParte(String estadoParte){
        this.estadoParte = estadoParte;
    }

}
