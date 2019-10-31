package com.ems.bdsqlitefull.crud;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.MainActivity;
import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Pedidos;
import com.ems.bdsqlitefull.utils.Message;

public class EditRecord extends AppCompatActivity {

    TextView id;
    EditText cliente, data, descricao, valor;
    Button btSalvar;

    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD - Editar Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Resgata so objetos da view
        id = findViewById(R.id.id);
        cliente = findViewById(R.id.cliente);
        data = findViewById(R.id.data);
        descricao = findViewById(R.id.descricao);
        valor = findViewById(R.id.valor);
        btSalvar = findViewById(R.id.btSalvar);

        // Resgata o objeto serializavel para mostrar na tela
        final Intent itPedidos = getIntent();
        final Pedidos pedidos = (Pedidos) itPedidos.getExtras().getSerializable("objPedido");
        id.setText(String.valueOf(pedidos.getId()));
        cliente.setText(pedidos.getCliente());
        data.setText(pedidos.getValor());
        descricao.setText(pedidos.getData());
        valor.setText(pedidos.getDescricao());

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Coleta os dados digitados nos campos
                ContentValues values = new ContentValues();
                values.put("cliente", cliente.getText().toString());
                values.put("data", data.getText().toString());
                values.put("descricao", descricao.getText().toString());
                values.put("valor", valor.getText().toString());

                // Seta os novos dados
                Pedidos novosDados = new Pedidos();
                novosDados.setCliente(cliente.getText().toString());
                novosDados.setValor(data.getText().toString());
                novosDados.setData(descricao.getText().toString());
                novosDados.setDescricao(valor.getText().toString());

                // Atualiza os dados na tabela
                db = openOrCreateDatabase("db_pedidos", Context.MODE_PRIVATE, null);
                db.execSQL("UPDATE pedido SET " +
                        "cliente='" + novosDados.getCliente() + "'," +
                        "datapedido='" + novosDados.getValor() + "'," +
                        "descricao='" + novosDados.getData() + "'," +
                        "valor='" + novosDados.getDescricao() + "' " +
                        "WHERE id=" + pedidos.getId()
                );

                // Cria uma caixa de mensagem e mostra os dados atualizados
                Message message = new Message(EditRecord.this);
                message.show(
                        "Dados Atualizados com Sucesso!",
                        novosDados.getDados(),
                        R.drawable.ic_add);
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