package br.com.senac.projectsolutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.senac.projectsolutions.Model.Produto;
import br.com.senac.projectsolutions.R;
import br.com.senac.projectsolutions.View.CarrinhoActivity;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoHolder> {
    private Context context;
    private ArrayList<Produto> produtosAdd;

    public CarrinhoAdapter(Context context){
        this.context = context;
        produtosAdd = new ArrayList<>();
        for (int i = 0; i < 7 ; i ++){
            Produto prod = new Produto();
            prod.setNome("Produto " + i);
            prod.setValor(120.00);
            produtosAdd.add(prod);
            ((CarrinhoActivity)context).atualizaValorCompra(produtosAdd);
        }
    }

    @NonNull
    @Override
    public CarrinhoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarrinhoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto_carrinho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CarrinhoHolder holder, final int position) {
        holder.nomeProduto.setText(produtosAdd.get(position).getNome());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtosAdd.get(position).setValor(quantidadeManager(true, holder.quantidadeProdutos, holder.precoProduto));
                ((CarrinhoActivity)context).atualizaValorCompra(produtosAdd);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtosAdd.get(position).setValor(quantidadeManager(false, holder.quantidadeProdutos, holder.precoProduto));
                ((CarrinhoActivity)context).atualizaValorCompra(produtosAdd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtosAdd.size();
    }

    private Double quantidadeManager(boolean adicionando, TextView quantidade, TextView preco) {
        int qtdAtual = Integer.parseInt(quantidade.getText().toString());
        double precoAtual = Double.parseDouble(preco.getText().toString().replace("R$", "").replace(",", "."));
        double precoUnitatario = precoAtual / qtdAtual;
        double newPreco = 0.00;

        if (adicionando) {
            if (qtdAtual == 1){
                newPreco = precoAtual * (qtdAtual + 1);
            }else{
                newPreco = precoUnitatario * (qtdAtual + 1);
            }

            quantidade.setText(String.valueOf(qtdAtual + 1));
            preco.setText("R$".concat(String.format(Locale.US, "%.2f", newPreco).replace(".", ",")));
        } else {
            if (qtdAtual > 1) {
                newPreco = precoUnitatario * (qtdAtual - 1);
                quantidade.setText(String.valueOf(qtdAtual - 1));
                preco.setText("R$".concat(String.format(Locale.US, "%.2f", newPreco).replace(".", ",")));
            }
        }
        return newPreco;
    }
}
