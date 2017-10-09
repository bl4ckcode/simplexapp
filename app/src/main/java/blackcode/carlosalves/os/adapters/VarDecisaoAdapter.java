package blackcode.carlosalves.os.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.models.VariavelDecisao;

/**
 * Created by carlos.alves on 18/09/17.
 */
public class VarDecisaoAdapter extends ArrayAdapter<VarDecisaoAdapter.MainAdapterViewholder> {
    private Resources recursos;
    private ArrayList<VariavelDecisao> variaveisDecisao;

    public VarDecisaoAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<VariavelDecisao> variaveisDecisao) {
        super(context, resource);

        recursos = context.getResources();
        this.variaveisDecisao = variaveisDecisao;
    }

    static class MainAdapterViewholder {
        TextView txtViewVarDecisao;
        EditText edtTxtVarDecisao;
        ImageView imgViewVarDecisao;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MainAdapterViewholder holder;

        if (convertView == null) {
            holder = new MainAdapterViewholder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_var_decisao, parent, false);

            holder.txtViewVarDecisao = convertView.findViewById(R.id.txtView_variavel);
            holder.edtTxtVarDecisao = convertView.findViewById(R.id.edt_restricao);
            holder.imgViewVarDecisao = convertView.findViewById(R.id.imgView_icon);

            convertView.setTag(holder);
        } else {
            holder = (MainAdapterViewholder) convertView.getTag();
        }

        final VariavelDecisao variavelDecisao = variaveisDecisao.get(position);
        holder.txtViewVarDecisao.setText("x" + variavelDecisao.getNumVariavel());
        holder.edtTxtVarDecisao.setText("" + (variavelDecisao.getValor() == 0.0 ? "" : variavelDecisao.getValor()));

        holder.edtTxtVarDecisao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String number = holder.edtTxtVarDecisao.getText().toString();

                if (!number.equals("")) {
                    VariavelDecisao variavelDecisao = variaveisDecisao.get(position);

                    variavelDecisao.setValor(Double.parseDouble(holder.edtTxtVarDecisao.getText().toString()));
                    variaveisDecisao.set(position, variavelDecisao);
                }
            }
        });

        holder.imgViewVarDecisao.setImageDrawable(recursos.getDrawable(R.drawable.ic_add_circle_outline, null));
        holder.imgViewVarDecisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgViewVarDecisao.setVisibility(View.INVISIBLE);
                holder.imgViewVarDecisao.setTag(position);

                VariavelDecisao novaVariavel = new VariavelDecisao(0.0, variaveisDecisao.get(position).getProximaVariavel());
                variaveisDecisao.add(novaVariavel);

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return variaveisDecisao.size();
    }
}
