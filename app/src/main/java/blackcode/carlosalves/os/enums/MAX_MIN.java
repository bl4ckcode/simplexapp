package blackcode.carlosalves.os.enums;

/**
 * Created by carlos.alves on 04/10/17.
 */

public enum MAX_MIN {
    MAX(0), MIN(1);

    private int id;

    MAX_MIN(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MAX_MIN valueOf(int id) {
        for(MAX_MIN max_min : MAX_MIN.values()) {
            if(id == max_min.getId())
                return max_min;
        }

        return null;
    }
}
