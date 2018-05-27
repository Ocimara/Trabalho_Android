package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;
import com.ocimarabarcellos.mbaimobiliaria.DAO.ImovelDAO;
import com.ocimarabarcellos.mbaimobiliaria.R;
import java.util.ArrayList;
import java.util.List;

public class CadImovelActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<String> siglasUf = new ArrayList<String>();
    private String uf;

    private EditText edtDsImovel;
    private EditText edtEndereco;
    private EditText edtCidade;

    private Imovel imovAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_imovel);

        edtDsImovel = findViewById(R.id.edtDsImovel);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtCidade = findViewById(R.id.edtCidade);
        spinner = (Spinner) findViewById(R.id.spnUF);
        loadEstados();

        //Recuperar a Imovel que foi passada
        imovAtual = (Imovel) getIntent().getSerializableExtra("ImovelSelecionado");

        if (imovAtual != null) {
            //Configurar as recuperaçoes
            edtDsImovel.setText(imovAtual.getDsImovel());
            edtEndereco.setText(imovAtual.getDsEndereco());
            edtCidade.setText(imovAtual.getDsCidade());
            setarValorUF(imovAtual.getDsUF());

        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_imovel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar :
                ImovelDAO imovelDAO = new ImovelDAO(getApplicationContext());
                Imovel imov = new Imovel();
                imov.setDsImovel(edtDsImovel.getText().toString());
                imov.setDsEndereco(edtEndereco.getText().toString());
                imov.setDsCidade(edtCidade.getText().toString());
                imov.setDsUF(uf);

                if (imovAtual != null) { //Edição
                    if (!edtDsImovel.getText().toString().isEmpty())
                        {
                            imov.setId(imovAtual.getId());

                            if (imovelDAO.atualizar(imov)) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Imóvel Salvo com sucesso!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Erro ao salvar Imóvel!", Toast.LENGTH_LONG).show();
                            }

                        }
                    } else {
                    //Edição Adição
                    //executa salvar para o item

                        if (!edtDsImovel.getText().toString().isEmpty()) {
                            if (imovelDAO.salvar(imov)) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Imóvel Salvo com sucesso!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Erro ao salvar Imóvel!", Toast.LENGTH_LONG).show();
                            }

                        }

                    }

                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public void loadEstados(){
        // Uma lista contendo os objetos
        siglasUf.add("Selecione");
        siglasUf.add("AC");
        siglasUf.add("AM");
        siglasUf.add("AP");
        siglasUf.add("BA");
        siglasUf.add("CE");
        siglasUf.add("DF");
        siglasUf.add("GO");
        siglasUf.add("MA");
        siglasUf.add("MG");
        siglasUf.add("MS");
        siglasUf.add("MT");
        siglasUf.add("PA");
        siglasUf.add("PB");
        siglasUf.add("PE");
        siglasUf.add("PI");
        siglasUf.add("RJ");
        siglasUf.add("RN");
        siglasUf.add("RO");
        siglasUf.add("RR");
        siglasUf.add("RS");
        siglasUf.add("SC");
        siglasUf.add("SE");
        siglasUf.add("SP");
        siglasUf.add("TO");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, siglasUf);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                uf = parent.getItemAtPosition(posicao).toString();

               // Toast.makeText(CadImovelActivity.this, "Nome Selecionado: " + uf, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void setarValorUF(String vlUF){

        int posicaoArray = 0;

        for(int i=0; (i <= siglasUf.size()-1) ; i++){

            if(siglasUf.get(i).equals(vlUF)){

                posicaoArray = i;
                break;
            }else{
                posicaoArray = 0;
            }
        }
        spinner.setSelection(posicaoArray);
    }

}
