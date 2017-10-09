package blackcode.carlosalves.os.enums;

/**
 * Created by carlos.alves on 04/10/17.
 */

public enum MAX_MIN {
    MAX(0, "MAX"), MIN(1, "MIN");

    private int id;
    private String max_min;

    MAX_MIN(int id, String max_min) {
        this.id = id;
        this.max_min = max_min;
    }

    public int getId() {
        return id;
    }

    public String getMax_min() {
        return this.max_min;
    }

    public static MAX_MIN valueOf(int id) {
        for(MAX_MIN max_min : MAX_MIN.values()) {
            if(id == max_min.getId())
                return max_min;
        }

        return null;
    }
}
