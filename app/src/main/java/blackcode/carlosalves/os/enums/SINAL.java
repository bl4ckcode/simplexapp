package blackcode.carlosalves.os.enums;

/**
 * Created by carlos.alves on 04/10/17.
 */

public enum SINAL {
    MENOR_IGUAL(0), MAIOR_IGUAL(1);

    private int id;

    SINAL(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SINAL valueOf(int id) {
        for(SINAL sinal : SINAL.values()) {
            if(id == sinal.getId())
                return sinal;
        }

        return null;
    }
}
