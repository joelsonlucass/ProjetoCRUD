package com.ems.bdsqlitefull.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.MainActivity;
import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Pedidos;
import com.ems.bdsqlitefull.utils.Message;

import java.util.ArrayList;

public class ListAll extends AppCompatActivity {
    ListView listViewPedidos;
    ArrayList<Pedidos> pedidos = new ArrayList<>();
    ArrayAdapter<Pedidos> adaptador;
    ArrayList<Pedidos> pesquisa = new ArrayList<>();
    ArrayAdapter<Pedidos> adaptadorpesquisa;
    SQLiteDatabase db;
    Button btPesquisar;
    TextView nome;
    int count,position = 0;
    String cliente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD - Listagem Geral de Pedidos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btPesquisar = findViewById(R.id.btPesquisar);
        // Resgata o nome para pesquisar
        nome = findViewById(R.id.editNome);



        // Abre o banco de dados existente
        db = openOrCreateDatabase("db_pedidos", Context.MODE_PRIVATE, null);

        listViewPedidos = findViewById(R.id.listagem);

        // Carrega os registros em ordem alfabética no ArrayList para anexar ao adaptador
        pedidos.clear();
        Cursor c = db.rawQuery("SELECT * FROM pedido ORDER BY id ASC", null);
        while (c.moveToNext()) {
            pedidos.add(new Pedidos(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)));
        }
        // Configura o adaptador
        adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                pedidos);


        // Anexa o adaptador à ListView
        listViewPedidos.setAdapter(adaptador);

        // Ao clicar em um item, envia um objeto view detalhes
        listViewPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedidos pedidos = (Pedidos) listViewPedidos.getItemAtPosition(position);
                Intent itPedido = new Intent(getApplicationContext(), Details.class);
                itPedido.putExtra("objPedido", pedidos);
                startActivity(itPedido);
            }
        });

        // Ao clicar em editar, passa para outra tela os dados da tela atual serializado
        btPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = 0;
                cliente = nome.getText().toString();

                // Abre ou conecta ao banco
                db = openOrCreateDatabase("db_pedidos", Context.MODE_PRIVATE, null);

                // Executa a query com o cliente digitado
                Cursor d = db.rawQuery("SELECT * FROM pedido WHERE cliente='" +
                        cliente + "'", null);
                while (d.moveToNext()) {
                    pesquisa.add(new Pedidos(
                            d.getInt(0),
                            d.getString(1),
                            d.getString(2),
                            d.getString(3),
                            d.getString(4)));
                    count++;

                }

                // Configura o adaptador pesquisa
                adaptadorpesquisa = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        pesquisa);
                // Caso tenha retornado alguma pesquisa
                if(count > 0) {
                    Intent itPedido = new Intent(getApplicationContext(), Details.class);
                    itPedido.putExtra("objPedido", adaptadorpesquisa.getItem(position));
                    position++;
                    startActivity(itPedido);
                }
                // Caso de não retorno da pesquisa
                else {
                    // Cria uma caixa de mensagem e mostra se foi deletado
                    Message message = new Message(ListAll.this);
                    message.show(
                            "Nenhum dado foi encontrato",
                            "",
                            R.drawable.ic_remove);
                    ;
                }
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