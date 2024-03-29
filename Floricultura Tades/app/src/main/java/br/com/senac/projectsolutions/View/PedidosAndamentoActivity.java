package br.com.senac.projectsolutions.View;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.senac.projectsolutions.Adapter.PedidosAdapter;
import br.com.senac.projectsolutions.Controller.PerfilController;
import br.com.senac.projectsolutions.Model.Venda;
import br.com.senac.projectsolutions.R;

public class PedidosAndamentoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView pedidosAndamento;
    private LinearLayout loadingScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_andamento);
        findViewsById();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PerfilController controller = new PerfilController();
        controller.getPedidosAndamento(PedidosAndamentoActivity.this);
    }

    public void onServidorResponse(boolean status, ArrayList<Venda> pedidosAndamento, String msg){
        LinearLayout linearRecycler = findViewById(R.id.linear_recycler);
        LinearLayout linearEmpty = findViewById(R.id.linear_empty);
        loadingScreen.setVisibility(View.GONE);

        if(status){
            if (pedidosAndamento.size() > 0){
                setRecyclerView(pedidosAndamento);
                linearRecycler.setVisibility(View.VISIBLE);
                linearEmpty.setVisibility(View.GONE);
            }else{
                linearRecycler.setVisibility(View.GONE);
                linearEmpty.setVisibility(View.VISIBLE);
            }
        }else{
            linearRecycler.setVisibility(View.GONE);
            linearEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void findViewsById(){
        toolbar = findViewById(R.id.toolbar_andamento);
        pedidosAndamento = findViewById(R.id.recycler_andamento);
        loadingScreen = findViewById(R.id.linear_loading);
    }

    private void setRecyclerView(ArrayList<Venda> pedidoAndamento){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        pedidosAndamento.setLayoutManager(linearLayoutManager);
        pedidosAndamento.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));
        PedidosAdapter adapter = new PedidosAdapter(pedidoAndamento);
        pedidosAndamento.setAdapter(adapter);
    }
}
