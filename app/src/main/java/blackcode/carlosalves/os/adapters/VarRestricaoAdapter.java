package blackcode.carlosalves.os.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;

/**
 * Created by carlos.alves on 05/10/17.
 */

class VarRestricaoAdapter extends RecyclerView.Adapter<VarRestricaoAdapter.VarRestricaoViewholder> {
    interface PassValuesInterface {
        void passValues(int index, ArrayList<Double> variaveis);
    }

    private ArrayList<Double> variaveis = new ArrayList<>();
    private int restId;
    private PassValuesInterface listener;

    VarRestricaoAdapter(PassValuesInterface listener, ArrayList<Double> variaveis, int restId) {
        this.variaveis = variaveis;
        this.restId = restId;
        this.listener = listener;
    }

    class VarRestricaoViewholder extends RecyclerView.ViewHolder {
        TextView txtViewVarDecisao;
        EditText edtTxtVarDecisao;
        RadioGroup radioGroupDemanda;
        EditText edtTxtDemanda;

        VarRestricaoViewholder(View itemView) {
            super(itemView);

            txtViewVarDecisao = itemView.findViewById(R.id.txtView_variavel);
            edtTxtVarDecisao = itemView.findViewById(R.id.edt_restricao);
            radioGroupDemanda = itemView.findViewById(R.id.radioGroup_demanda);
            edtTxtDemanda = itemView.findViewById(R.id.edtTxt_demanda);
        }
    }

    @Override
    public VarRestricaoViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_var_restricao, parent, false);
        return new VarRestricaoViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final VarRestricaoViewholder holder, int position) {
        String text = "x" + position;
        double valor = variaveis.get(position);

        holder.txtViewVarDecisao.setText(text);
        holder.edtTxtVarDecisao.setText("" + (valor == 0.0 ? "" : valor));
        holder.edtTxtVarDecisao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String number = holder.edtTxtVarDecisao.getText().toString();

                if (!number.equals("")) {
                    double valor = Double.parseDouble(number);
                    variaveis.set(holder.getAdapterPosition(), valor);
                    listener.passValues(restId, variaveis);
                    holder.edtTxtVarDecisao.setText("" + valor);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return variaveis.size();
    }
}
