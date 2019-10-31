package com.ems.bdsqlitefull.crud;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.MainActivity;
import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Pedidos;
import com.ems.bdsqlitefull.utils.Message;

public class Details extends AppCompatActivity {
    Button btEditar,btDeletar;
    TextView id, cliente, data, descricao, valor;

    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD - Detalhes do Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Resgata os objetos da view
        id = findViewById(R.id.id);
        cliente = findViewById(R.id.cliente);
        data = findViewById(R.id.data);
        descricao = findViewById(R.id.descricao);
        valor = findViewById(R.id.valor);
        btEditar = findViewById(R.id.btSalvar);
        btDeletar = findViewById(R.id.btDeletar);

        // cria um intent serializavel para pagina editar
        Intent itPedidos = getIntent();
        final Pedidos pedidos = (Pedidos) itPedidos.getExtras().getSerializable("objPedido");
        id.setText(String.valueOf(pedidos.getId()));
        cliente.setText(pedidos.getCliente());
        data.setText(pedidos.getValor());
        descricao.setText(pedidos.getData());
        valor.setText("R$ " + pedidos.getDescricao());

        // Ao clicar em editar, passa para outra tela os dados da tela atual serializado
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editar = new Intent(getApplicationContext(), EditRecord.class);
                editar.putExtra("objPedido", pedidos);
                startActivity(editar);
            }
        });

        // Ao clicar em deletar, deleta o item atual escolhido e envia o usuario a tela inicial
        btDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deleta os dados da tabela
                db = openOrCreateDatabase("db_pedidos", Context.MODE_PRIVATE, null);
                db.execSQL("DELETE FROM pedido WHERE id="
                        + pedidos.getId());

                // Cria uma caixa de mensagem e mostra se foi deletado
                Message message = new Message(Details.this);
                message.show(
                        "Dados Deletados com Sucesso!",
                        "",
                        R.drawable.ic_remove);
                ;
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });

    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}