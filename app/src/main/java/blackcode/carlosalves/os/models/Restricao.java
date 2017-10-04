package blackcode.carlosalves.os.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by carlos.alves on 04/10/17.
 */

public class Restricao implements Parcelable {
    private int idVarivel;
    private int sinal;
    private ArrayList<Double> variaveis = new ArrayList<>();
    private Double demanda;

    public Restricao(int idVarivel, int size, int sinal, Double demanda) {
        this.idVarivel = idVarivel;
        this.sinal = sinal;
        this.demanda = demanda;

        for(int i = 0; i < size; i++) {
            variaveis.add(0.0);
        }
    }

    private Restricao(Parcel in) {
        idVarivel = in.readInt();
        sinal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idVarivel);
        dest.writeInt(sinal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restricao> CREATOR = new Creator<Restricao>() {
        @Override
        public Restricao createFromParcel(Parcel in) {
            return new Restricao(in);
        }

        @Override
        public Restricao[] newArray(int size) {
            return new Restricao[size];
        }
    };

    public int getIdVarivel() {
        return idVarivel;
    }

    public ArrayList<Double> getVariaveis() {
        return variaveis;
    }

    public int getSinal() {
        return sinal;
    }

    public Double getDemanda() {
        return demanda;
    }

    public void setIdVarivel(int idVarivel) {
        this.idVarivel = idVarivel;
    }

    public void setVariaveis(ArrayList<Double> variaveis) {
        this.variaveis = variaveis;
    }

    public void setSinal(int sinal) {
        this.sinal = sinal;
    }

    public void setDemanda(Double demanda) {
        this.demanda = demanda;
    }

    public int getProximaVariavel() {
        return getIdVarivel() + 1;
    }
}
