package com.example.vanken.ui.estadisticas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.Persona;
import com.example.vanken.Modelos.Reporte;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class EstadisticasFragment extends Fragment {

    private EstadisticasViewModel estadisticasViewModel;
    private PieChartView pieChartView;
    private ColumnChartView columnChartView;
    private ArrayList<Persona> personas;//Solo para probar
    VolleySingleton volleySingleton;
    private Map map;
    private String[] colores={"#D73F51B5","#80CBC4","#BBDEFB","#64B5F6","#3f51b5","#4DB6AD","#00838f","#B2DFDB","#7986CB"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        estadisticasViewModel =
                ViewModelProviders.of(this).get(EstadisticasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        pieChartView= (PieChartView) root.findViewById(R.id.graficaTecnicos);
        columnChartView=(ColumnChartView) root.findViewById(R.id.graficaServicios);
        map=new HashMap();
        map.put("funcion", "estadisticasTecnico");
        llamarWebService(map, 0);
        map.clear();
        map.put("funcion", "estadisticasGanancias");
        llamarWebService(map,1);

       /* List list=new ArrayList<>();
        list.add(new SliceValue(15, Color.parseColor(colores[0])).setLabel("Primero"));
        list.add(new SliceValue(10, Color.parseColor(colores[1])).setLabel("Segundo"));
        list.add(new SliceValue(25, Color.parseColor(colores[2])).setLabel("Tercero"));*/



        //final TextView textView = root.findViewById(R.id.text_home);
        estadisticasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
    public void llenarGraficaTecnicos(JSONObject jsonObject) {
        JSONArray jsonArray;
        List list=new ArrayList();
        try {
            jsonArray = jsonObject.getJSONArray("lista");
            Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new SliceValue(Integer.parseInt(jsonArray.getJSONObject(i).getString("cantidad")),
                        Color.parseColor(colores[i])).setLabel(jsonArray.getJSONObject(i).getString("nombre")
                        .concat(jsonArray.getJSONObject(i).getString("apellidos"))));
            }
            PieChartData pieChartData=new PieChartData(list);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("Técnicos").setCenterText1FontSize(20);
            pieChartView.setPieChartData(pieChartData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void llenarGraficaGanancias(JSONObject jsonObject){
        JSONArray jsonArray;
        int numSubcolumns = 1;
        int numColumns = 7;
        try {
            jsonArray = jsonObject.getJSONArray("lista");
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(Float.parseFloat( jsonArray.getJSONObject(i).getString("cantidad")),
                            Color.parseColor(colores[i])));
                }
                Column column = new Column(values);
                column.setHasLabels(true);
                columns.add(column);
            }
            ColumnChartData data = new ColumnChartData(columns);

                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);

                axisX.setName("Día de la semana");
                axisY.setName("Ganancias");

                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
                columnChartView.setContentDescription("Ganancias semanales");
            columnChartView.setColumnChartData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void llamarWebService(Map<String, String> map, final  int t){
        String url="https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        JSONObject jo=new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                //Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                                try {
                                    if(jsonObject.getBoolean("respuesta")){
                                        switch (t){
                                            case 0: llenarGraficaTecnicos(jsonObject); break;
                                            case 1: llenarGraficaGanancias(jsonObject); break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause=error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                            }
                        })
        );
    }
}