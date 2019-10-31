package com.ems.bdsqlitefull.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Pedidos;
import com.ems.bdsqlitefull.utils.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Insert extends AppCompatActivity {
    EditText cliente, descricao, valor;
    Button btInserir;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Abertura ou criação do Banco de Dados
        db = openOrCreateDatabase("db_pedidos", Context.MODE_PRIVATE, null);

        // Cria a tabela se não existir, senão carrega a tabela para uso
        db.execSQL("CREATE TABLE IF NOT EXISTS pedido(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cliente VARCHAR NOT NULL, " +
                "descricao VARCHAR NOT NULL, " +
                "valor VARCHAR NOT NULL, " +
                "datapedido VARCHAR NOT NULL);");

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD - Inserir Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Resgata os objetos da view
        cliente = findViewById(R.id.editCliente);
        descricao = findViewById(R.id.editNome);
        valor = findViewById(R.id.editValor);
        btInserir = findViewById(R.id.btInserir);

        // Função click do botão inserir
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Cria um objeto Pedido para receber os dados
            Pedidos pedidos = new Pedidos();
            pedidos.setCliente(cliente.getText().toString());
            pedidos.setDescricao(descricao.getText().toString());
            pedidos.setValor(valor.getText().toString());
            // Resgata a data atual e insere no objeto
            Date todayDate = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            String todayString = formatter.format(todayDate);
            pedidos.setData(todayString);

            // Coleta os dados digitados nos campos
            ContentValues values = new ContentValues();
            values.put("cliente", pedidos.getCliente());
            values.put("descricao", pedidos.getDescricao());
            values.put("valor", pedidos.getValor());
            values.put("datapedido", pedidos.getData());

            // Insere os dados na tabela
            db.insert("pedido", null, values);

            // Cria uma caixa de mensagem e mostra os dados incluídos
            Message message = new Message(Insert.this);
            message.show(
                    "Dados incluídos com sucesso!",
                    pedidos.getDados(),
                    R.drawable.ic_add);

            // Limpa os campos de entrada
            clearText();
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

    /**
     * Limpa os campos de entrada e fecha o teclado
     */
    public void clearText() {
        cliente.setText("");
        descricao.setText("");
        valor.setText("");
        cliente.requestFocus();

        // fecha o teclado virtual
        ((InputMethodManager) Insert.this.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);
    }
}
