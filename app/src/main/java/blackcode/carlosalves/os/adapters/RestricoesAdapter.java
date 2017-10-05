package blackcode.carlosalves.os.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.enums.SINAL;
import blackcode.carlosalves.os.models.Restricao;

/**
 * Created by carlos.alves on 04/10/17.
 */

public class RestricoesAdapter extends RecyclerView.Adapter<RestricoesAdapter.RestricoesViewholder>
        implements VarRestricaoAdapter.PassValuesInterface {
    private ArrayList<Restricao> restricoes;
    private Activity activity;
    private int size;

    public RestricoesAdapter(Activity activity, ArrayList<Restricao> restricoes, int size) {
        this.activity = activity;
        this.restricoes = restricoes;
        this.size = size;
    }

    class RestricoesViewholder extends RecyclerView.ViewHolder {
        TextView txtViewRestricao;
        ImageView imViewRestricao;
        RecyclerView recViewVariaveis;
        RadioGroup radioGroupDemanda;
        EditText edtTxtDemanda;

        RestricoesViewholder(View itemView) {
            super(itemView);

            txtViewRestricao = itemView.findViewById(R.id.txtView_restricao);
            imViewRestricao = itemView.findViewById(R.id.imView_add_restricao);
            recViewVariaveis = itemView.findViewById(R.id.listView_variaveis_restricao);
            radioGroupDemanda = itemView.findViewById(R.id.radioGroup_demanda);
            edtTxtDemanda = itemView.findViewById(R.id.edtTxt_demanda);
        }
    }

    @Override
    public RestricoesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restricao, parent, false);
        return new RestricoesViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final RestricoesViewholder holder, int position) {
        final Restricao restricao = restricoes.get(position);
        String descRestricao = activity.getString(R.string.restricao) + " " + restricao.getIdVarivel();
        holder.txtViewRestricao.setText(descRestricao);
        holder.recViewVariaveis.setLayoutManager(new LinearLayoutManager(activity));
        holder.recViewVariaveis.setAdapter(new VarRestricaoAdapter(this, restricao.getVariaveis(), position));

        holder.imViewRestricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imViewRestricao.setVisibility(View.INVISIBLE);
                restricoes.add(new Restricao(restricao.getProximaVariavel(), size, SINAL.MAIOR_IGUAL.getId(), 0.0));
                notifyItemInserted(restricoes.size() - 1);
            }
        });

        holder.edtTxtDemanda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String number = holder.edtTxtDemanda.getText().toString();

                if (!number.equals("")) {
                    double valor = Double.parseDouble(number);
                    int value = holder.radioGroupDemanda.getCheckedRadioButtonId();
                    Restricao rest = restricoes.get(holder.getAdapterPosition());

                    rest.setDemanda(valor);

                    if (value == -1)
                        holder.radioGroupDemanda.check(rest.getSinal());
                    else
                        rest.setSinal(SINAL.valueOf(holder.radioGroupDemanda.getCheckedRadioButtonId()).getId());

                    holder.edtTxtDemanda.setText("" + valor);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restricoes.size();
    }

    @Override
    public void passValues(int index, ArrayList<Double> variaveis) {
        restricoes.get(index).setVariaveis(variaveis);
    }
}
