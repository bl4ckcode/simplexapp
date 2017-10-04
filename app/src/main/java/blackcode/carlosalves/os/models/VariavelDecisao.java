package blackcode.carlosalves.os.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlos.alves on 20/09/17.
 */

public class VariavelDecisao implements Parcelable {
    private Double valor;
    private int numVariavel;

    public VariavelDecisao(Double valor, int numVariavel) {
        this.valor = valor;
        this.numVariavel = numVariavel;
    }


    private VariavelDecisao(Parcel in) {
        numVariavel = in.readInt();
        valor = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numVariavel);
        dest.writeDouble(valor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VariavelDecisao> CREATOR = new Creator<VariavelDecisao>() {
        @Override
        public VariavelDecisao createFromParcel(Parcel in) {
            return new VariavelDecisao(in);
        }

        @Override
        public VariavelDecisao[] newArray(int size) {
            return new VariavelDecisao[size];
        }
    };

    public Double getValor() {
        return valor;
    }

    public int getNumVariavel() {
        return numVariavel;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setNumVariavel(int numVariavel) {
        this.numVariavel = numVariavel;
    }

    public int getProximaVariavel() {
        return getNumVariavel() + 1;
    }
}
