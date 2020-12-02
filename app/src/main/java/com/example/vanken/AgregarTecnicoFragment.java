package com.example.vanken;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.ui.tecnicos.TecnicosFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarTecnicoFragment extends Fragment {
    Button button;
    EditText nombre, apellidos, telefono, email, password, passwordConf, rango;
    VolleySingleton volleySingleton;
    ImageView imageView;
    ImageButton imageButton;
    Boolean bandera=false, editar=false;
    String imagenString;
    Uri imageUri;
    int id;
    private static final int PICK_IMAGE = 10;

    public AgregarTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarTecnicoFragment newInstance(String param1, String param2) {
        AgregarTecnicoFragment fragment = new AgregarTecnicoFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id=getArguments().getInt("id", 0);
            editar=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_agregar_tecnico, container, false);

        button = (Button) view.findViewById(R.id.btnAgregarTecnico);
        nombre = (EditText) view.findViewById(R.id.editTextNombreAgregarTecnico);
        apellidos = (EditText) view.findViewById(R.id.editTextApellidosAgregarTecnico);
        email = (EditText) view.findViewById(R.id.editTextUsernameAgregarTecnico);
        password = (EditText) view.findViewById(R.id.editTextPasswordAgregarTecnico);
        passwordConf = (EditText) view.findViewById(R.id.editTextConfirmarPasswordAgregarTecnico);
        telefono = (EditText) view.findViewById(R.id.editTextTelefonoAgregarTecnico);
        rango = (EditText) view.findViewById(R.id.editTextRangoAgregarTecnico);
        imageView=(ImageView)view.findViewById(R.id.imageViewAgregarTecnico);
        imageButton=(ImageButton)view.findViewById(R.id.imgBtnAgregarTecnico);
        final Map map = new HashMap<>();
        if(editar){
            button.setText("Guardar cambios");
            password.setVisibility(View.GONE);
            passwordConf.setVisibility(View.GONE);
            map.clear();
            map.put("funcion", "detallesTecnico");
            map.put("id", Integer.toString(id));
            llamarWebService(map, 2);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editar){
                    if (!nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty()
                            && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() &&
                            !passwordConf.getText().toString().isEmpty()) {
                        if (password.getText().toString().equals(passwordConf.getText().toString())) {
                            llenarMapa(map);
                            map.put("funcion", "registro");
                            map.put("tipo","Tecnico");
                            llamarWebService(map,0);
                        }else{
                            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //
                    if (!nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty()
                            && !email.getText().toString().isEmpty() ) {
                        llenarMapa(map);
                        map.put("funcion", "editar");
                        map.put("id",Integer.toString(id));
                        map.put("tipo","Tecnico");
                        llamarWebService(map,1);
                    }else {
                        Toast.makeText(getContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
                bandera=true;
            }
        });
        return view;
    }
    public void  setDetalles(JSONObject jsonObject){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("lista");
            nombre.setText(jsonArray.getJSONObject(0).getString("nombre"));
            apellidos.setText(jsonArray.getJSONObject(0).getString("apellidos"));
            telefono.setText(jsonArray.getJSONObject(0).getString("telefono"));
            email.setText(jsonArray.getJSONObject(0).getString("user"));
            rango.setText(jsonArray.getJSONObject(0).getString("rango"));
            imagenString=jsonArray.getJSONObject(0).getString("imagen");
           // String base64Image = base64String;

            byte[] decodedString = Base64.decode(imagenString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            imageView.setImageBitmap(decodedByte);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void llenarMapa(Map map){
        map.clear();
        map.put("nombre", nombre.getText().toString());
        map.put("apellidos", apellidos.getText().toString());

        map.put("telefono", telefono.getText().toString());
        map.put("user", email.getText().toString());

        map.put("rango", rango.getText().toString());

        if(bandera)
            map.put("imagen", imagenString);
        else
            map.put("imagen", "");
        if(!editar){
            map.put("tipo", "Tecnico");
            map.put("pass", password.getText().toString());
        }else {
            map.put("pass", "");
        }
    }
    public void llamarWebService(Map<String, String> map, final int t) {
        String url = "https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        JSONObject jo = new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    //Toast.makeText(getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                    switch (t){
                                        case 0:
                                            if (jsonObject.getBoolean("respuesta")) {
                                                Toast.makeText(getContext(), "Técnico agregado correctamente", Toast.LENGTH_SHORT).show();
                                                NavHostFragment.findNavController(AgregarTecnicoFragment.this).navigate(R.id.nav_tecnicos);
                                            }else
                                                Toast.makeText(getContext(), "Hubo un error al agregar al técnico", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:

                                            if (jsonObject.getBoolean("respuesta")) {
                                                Toast.makeText(getContext(), "Técnico modificado correctamente", Toast.LENGTH_SHORT).show();
                                                NavHostFragment.findNavController(AgregarTecnicoFragment.this).navigate(R.id.nav_tecnicos);

                                            }else
                                                Toast.makeText(getContext(), "Hubo un error al editar al técnico", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 2:
                                            if (jsonObject.getBoolean("respuesta")) {
                                               setDetalles(jsonObject);
                                            }
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause = error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                            }
                        })
        );
    }
    private String convertirImagenString(Bitmap bitmap){
        String s="";
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte[] imagenByte=byteArrayOutputStream.toByteArray();
        s= Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return s;
    }
    public void cargarImagen(){
        /*Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione"),10);*/


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                10);
    }



    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        //Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case 10:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == 10) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContext().getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista

                            imageView.setImageBitmap(bmp);

                            imagenString=convertirImagenString(bmp);
                        }
                    }
                }
                break;
        }
    }


}