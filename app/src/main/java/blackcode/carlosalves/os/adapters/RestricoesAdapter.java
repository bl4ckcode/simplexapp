package blackcode.carlosalves.os.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.enums.SINAL;
import blackcode.carlosalves.os.models.Restricao;

/**
 * Created by carlos.alves on 04/10/17.
 */

public class RestricoesAdapter extends RecyclerView.Adapter<RestricoesAdapter.RestricoesViewholder> {
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
        ListView listViewVariaveis;

        RestricoesViewholder(View itemView) {
            super(itemView);

            txtViewRestricao = itemView.findViewById(R.id.txtView_restricao);
            imViewRestricao = itemView.findViewById(R.id.imView_add_restricao);
            listViewVariaveis = itemView.findViewById(R.id.listView_variaveis_restricao);
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
        holder.imViewRestricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restricoes.add(new Restricao(restricao.getProximaVariavel(), size, SINAL.MAIOR_IGUAL.getId(), 0.0));
                notifyItemInserted(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return restricoes.size();
    }
}
