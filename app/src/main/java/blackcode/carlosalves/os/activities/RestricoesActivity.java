package blackcode.carlosalves.os.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import blackcode.carlosalves.os.R;
import blackcode.carlosalves.os.adapters.RestricoesAdapter;
import blackcode.carlosalves.os.enums.MAX_MIN;
import blackcode.carlosalves.os.enums.SINAL;
import blackcode.carlosalves.os.models.Restricao;
import blackcode.carlosalves.os.models.VariavelDecisao;

public class RestricoesActivity extends AppCompatActivity {
    private ArrayList<Restricao> restricoes = new ArrayList<>();
    private RestricoesAdapter restricoesAdapter;

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
        final MAX_MIN max_min = MAX_MIN.valueOf(bundle.getInt(MainActivity.CONST_MAX_MIN));
        final ArrayList<VariavelDecisao> listaVarDecisao = bundle.getParcelableArrayList(MainActivity.CONST_VAR_DECISAO);
        assert listaVarDecisao != null;

        int tamanho = listaVarDecisao.size();
        restricoes.add(new Restricao(0, tamanho, SINAL.MAIOR_IGUAL.getId(), "0.0"));

        RecyclerView recViewRestricoes = (RecyclerView) findViewById(R.id.recView_restricoes);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        restricoesAdapter = new RestricoesAdapter(this, restricoes, tamanho);
        recViewRestricoes.setLayoutManager(linearManager);
        recViewRestricoes.setAdapter(restricoesAdapter);

        RelativeLayout rlButtonCalcSimplex = (RelativeLayout) findViewById(R.id.rl_button_calcular_simplex);
        rlButtonCalcSimplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    restricoes = restricoesAdapter.getRestricoes();

                    RequestQueue queue = Volley.newRequestQueue(RestricoesActivity.this);
                    String url = "https://pls69.herokuapp.com/submit";

                    JSONObject jsonObjectMaxMin = new JSONObject();
                    jsonObjectMaxMin.put("maxmin", max_min.getMax_min());

                    JSONArray jsonArrayZ = new JSONArray();
                    for (VariavelDecisao variavelDecisao : listaVarDecisao) {
                        jsonArrayZ.put(variavelDecisao.getValor() + "");
                    }

                    jsonObjectMaxMin.put("Z", jsonArrayZ);
                    jsonObjectMaxMin.put("rests", restricoes.size() + "");

                    for (Restricao restricao : restricoes) {
                        JSONArray jsonArray = new JSONArray();
                        for (Double valor : restricao.getVariaveis()) {
                            jsonArray.put(valor + "");
                        }
                        jsonArray.put(SINAL.valueOf(restricao.getSinal()).getSinal());
                        jsonArray.put(Double.parseDouble(restricao.getDemanda()) + "");
                        jsonObjectMaxMin.put("R" + restricao.getIdVarivel(), jsonArray);
                    }

                    final String transform = jsonObjectMaxMin.toString().replaceAll("\"", "'");

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RestricoesActivity.this);
                                    builder.setTitle("Resposta");
                                    builder.setMessage(response);
                                    builder.setPositiveButton("OK", null);
                                    builder.create().show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return transform == null ? null : transform.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        transform, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String parsed;

                            try {
                                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            } catch (UnsupportedEncodingException e) {
                                parsed = new String(response.data);
                            }

                            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    queue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
