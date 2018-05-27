package com.ocimarabarcellos.mbaimobiliaria.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;
import com.ocimarabarcellos.mbaimobiliaria.R;

import java.util.List;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.MyViewHolder> {

    private List<Imovel> ListaImovel;

    public ImovelAdapter(List<Imovel> lista) {
        this.ListaImovel = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemImovel = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.activity_item_imovel, parent, false);


        return new MyViewHolder(itemImovel);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Imovel imovel = ListaImovel.get(position);
        holder.dsImovel.setText(imovel.getDsImovel());
        holder.dsEndereco.setText(imovel.getDsEndereco());
        holder.dsCidadeUF.setText(imovel.getDsCidade() + "-" + imovel.getDsUF());
    }

    @Override
    public int getItemCount() {
        return this.ListaImovel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dsImovel;
        TextView dsEndereco;
        TextView dsCidadeUF;


        public MyViewHolder(View itemView) {
            super(itemView);

            dsImovel = itemView.findViewById(R.id.tvDesc);
            dsEndereco = itemView.findViewById(R.id.tvEndereco);
            dsCidadeUF = itemView.findViewById(R.id.tvCidadeUf);

        }
    }


}
