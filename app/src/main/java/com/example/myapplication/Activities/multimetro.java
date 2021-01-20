package com.example.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.materialCustomAdapter;
import com.example.myapplication.entidades.MaterialEnt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class multimetro extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    String pedido, evaluar;
    static ArrayList  <String> pedidoMultimetro = new ArrayList<>();
    ImageView image;
    private ListView listViewMat;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    final ArrayList<String> names = new ArrayList<>();
    final ArrayList<String> label = new ArrayList<>();
    MaterialEnt materialEnt = new MaterialEnt();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimetro);
        listViewMat =  findViewById(R.id.listMult);

        request = Volley.newRequestQueue(multimetro.this);

        cargarWebServices();


        listViewMat.setClickable(true);

        listViewMat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        pedido = names.get(0);
                        evaluar = label.get(0);
                        if(evaluar.equals("No disponible")){
                            Toast.makeText(multimetro.this,"Este material no se puede seleccionar", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(multimetro.this,"Seleccionaste el " + pedido, Toast.LENGTH_SHORT).show();
                            pedidoMultimetro.add(pedido);
                        }
                        break;

                    case 1:
                        pedido = names.get(1);
                        evaluar = label.get(1);
                        if(evaluar.equals("No disponible")){
                            Toast.makeText(multimetro.this,"Este material no se puede seleccionar", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(multimetro.this,"Seleccionaste el " + pedido, Toast.LENGTH_SHORT).show();
                            pedidoMultimetro.add(pedido);
                        }
                        break;
                    case 2:
                        pedido = names.get(2);
                        evaluar = label.get(2);
                        if(evaluar.equals("No disponible")){
                            Toast.makeText(multimetro.this,"Este material no se puede seleccionar", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(multimetro.this,"Seleccionaste el " + pedido, Toast.LENGTH_SHORT).show();
                            pedidoMultimetro.add(pedido);
                        }
                        break;
                    case 3:
                        pedido = names.get(3);
                        evaluar = label.get(3);
                        if(evaluar.equals("No disponible")){
                            Toast.makeText(multimetro.this,"Este material no se puede seleccionar", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(multimetro.this,"Seleccionaste el " + pedido, Toast.LENGTH_SHORT).show();
                            pedidoMultimetro.add(pedido);
                        }
                        break;
                }

            }
        });

        image = findViewById(R.id.backimageView);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(multimetro.this,MainActivity.class);
                startActivity(i);
                Animatoo.animateSlideRight(multimetro.this);
                finish();
            }
        });

    }

    private void cargarWebServices() {
        progress = new ProgressDialog(multimetro.this);
        progress.setMessage("Consultando...");

        String url = "http://189.223.106.193/proyectolab/consultarListaMult.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    public ArrayList<String> getMultimetros (){
        return pedidoMultimetro;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(multimetro.this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        System.out.println();
        Log.i("ERROR",error.toString());
        progress.hide();

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("usuario");

        try {

        for (int x = 0; x<json.length();x++){

            JSONObject jsonObject;
            jsonObject = json.getJSONObject(x);
            materialEnt.setEtiqueta(jsonObject.optString("etiqueta"));
            materialEnt.setEstado(jsonObject.optString("estado"));

            names.add(materialEnt.getEtiqueta());
            label.add(materialEnt.getEstado());

            materialCustomAdapter materialCustomAdapter = new materialCustomAdapter(this, R.layout.item_mult, names, label);
            listViewMat.setAdapter(materialCustomAdapter);

            }
        progress.hide();
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(multimetro.this,"Mensaje.." + response,Toast.LENGTH_SHORT).show();
            progress.hide();

        }

    }
}