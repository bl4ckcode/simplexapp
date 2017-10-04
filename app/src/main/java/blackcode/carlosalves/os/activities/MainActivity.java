package blackcode.carlosalves.os.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.adapters.VarDecisaoAdapter;
import blackcode.carlosalves.os.models.VariavelDecisao;

public class MainActivity extends AppCompatActivity {
    public static final String CONST_VAR_DECISAO = "varDecisao";
    public static final String CONST_MAX_MIN = "max_min";

    ArrayList<VariavelDecisao> variaveisDecisao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(getString(R.string.simplex_app));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);

        final RadioGroup radioGroup_max_min = (RadioGroup) findViewById(R.id.radioGroup_max_min);
        ListView listViewVarDecioes = (ListView) findViewById(R.id.listView_variveis_decisao);
        listViewVarDecioes.setRecyclerListener(scrollFocusListener);

        variaveisDecisao.add(new VariavelDecisao(0.0, 0));
        listViewVarDecioes.setAdapter(new VarDecisaoAdapter(this, R.layout.adapter_decisao, variaveisDecisao));

        RelativeLayout rlBotaoRestricao = (RelativeLayout) findViewById(R.id.rl_button_restricoes);
        rlBotaoRestricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = radioGroup_max_min.getCheckedRadioButtonId();
                if (checkedId == -1)
                    Toast.makeText(MainActivity.this, "Marque a opção de maximizar ou minimizar!", Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(MainActivity.this, RestricoesActivity.class);
                    intent.putParcelableArrayListExtra(CONST_VAR_DECISAO, variaveisDecisao);
                    intent.putExtra(CONST_MAX_MIN, checkedId);
                    startActivity(intent);
                }
            }
        });
    }

    AbsListView.RecyclerListener scrollFocusListener = new AbsListView.RecyclerListener() {
        @Override
        public void onMovedToScrapHeap(View view) {
            if (view.hasFocus()) {
                view.clearFocus();

                if (view instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager)
                            view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    };
}
