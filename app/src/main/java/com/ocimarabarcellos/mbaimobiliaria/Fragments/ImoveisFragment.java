package com.ocimarabarcellos.mbaimobiliaria.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ocimarabarcellos.mbaimobiliaria.Activitys.CadImovelActivity;
import com.ocimarabarcellos.mbaimobiliaria.Adapter.ImovelAdapter;
import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;
import com.ocimarabarcellos.mbaimobiliaria.DAO.ImovelDAO;
import com.ocimarabarcellos.mbaimobiliaria.R;
import com.ocimarabarcellos.mbaimobiliaria.helper.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImoveisFragment extends Fragment {

    private RecyclerView recyclerLstImovel;
    private ImovelAdapter imovelAdapter;
    private List<Imovel> lstImoveis = new ArrayList<>();
    private Imovel imovSelecionado;


    public ImoveisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imoveis, container, false);

        //Configurar Recycler
        recyclerLstImovel = view.findViewById(R.id.recyclerListaImovel);



        //Adicionar evento de clicker na recyclerView
        recyclerLstImovel.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity().getApplicationContext(),
                        recyclerLstImovel,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recupera os dados para edição
                                Imovel imovSel = lstImoveis.get(position);

                                //Enviar imovel para activity CadImovel
                                Intent intent = new Intent(view.getContext(), CadImovelActivity.class);
                                intent.putExtra("ImovelSelecionado", imovSel);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Recuperar o Imovel que o usuario quer deletar
                                imovSelecionado = lstImoveis.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                                //Configurar Titulo e Mensagem
                                dialog.setTitle(getString(R.string.msg_conf_exclusao));
                                dialog.setMessage(getString(R.string.msg_excluir_imovel) + " " + imovSelecionado.getDsCidade() + " - " + imovSelecionado.getDsUF() + " ?");
                                dialog.setPositiveButton(getString(R.string.msg_retorno_positivo), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ImovelDAO imovelDAO = new ImovelDAO(getActivity().getApplicationContext());
                                        if (imovelDAO.deletar(imovSelecionado)){
                                            carregarImoveis();
                                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.sucesso_excluir_imovel), Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_excluir_imovel), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });
                                dialog.setNegativeButton(getString(R.string.msg_retorno_negativo), null);
                                dialog.create();
                                dialog.show();


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );



        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.btnAddImovel);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(view.getContext(), CadImovelActivity.class));
            }
        });

        return view;



    }

    public void carregarImoveis(){

        //Listar Imoveis
        ImovelDAO imovelDAO = new ImovelDAO(getActivity().getApplicationContext());
        lstImoveis = imovelDAO.listar();


        //Configurar o adapter
        imovelAdapter = new ImovelAdapter( lstImoveis);


        //configurar o recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerLstImovel.setLayoutManager(layoutManager);
        recyclerLstImovel.setHasFixedSize(true);
        recyclerLstImovel.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayout.VERTICAL));
        recyclerLstImovel.setAdapter(imovelAdapter);
    }

    @Override
    public void onStart() {
        carregarImoveis();
        super.onStart();
    }
}
