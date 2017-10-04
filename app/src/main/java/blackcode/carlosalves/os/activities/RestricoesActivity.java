package blackcode.carlosalves.os.activities;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.adapters.RestricoesAdapter;
import blackcode.carlosalves.os.enums.MAX_MIN;
import blackcode.carlosalves.os.enums.SINAL;
import blackcode.carlosalves.os.models.Restricao;
import blackcode.carlosalves.os.models.VariavelDecisao;

public class RestricoesActivity extends AppCompatActivity {
    private ArrayList<Restricao> restricoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restricoes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(getString(R.string.restricoes));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        MAX_MIN max_min = MAX_MIN.valueOf(bundle.getInt(MainActivity.CONST_MAX_MIN));
        ArrayList<VariavelDecisao> listaVarDecisao = bundle.getParcelableArrayList(MainActivity.CONST_VAR_DECISAO);
        assert listaVarDecisao != null;

        int tamanho = listaVarDecisao.size();
        restricoes.add(new Restricao(0, tamanho, SINAL.MAIOR_IGUAL.getId(), 0.0));

        RecyclerView recViewRestricoes = (RecyclerView) findViewById(R.id.recView_restricoes);
        recViewRestricoes.setAdapter(new RestricoesAdapter(this, restricoes, tamanho));

        RelativeLayout rlButtonCalcSimplex = (RelativeLayout) findViewById(R.id.rl_button_calcular_simplex);
        rlButtonCalcSimplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
