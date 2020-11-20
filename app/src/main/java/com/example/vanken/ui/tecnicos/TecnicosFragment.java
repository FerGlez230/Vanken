package com.example.vanken.ui.tecnicos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TecnicosFragment extends Fragment {

    private TecnicosViewModel tecnicosViewModel;
    private RecyclerView recyclerViewTecnicos;
    private ArrayList<String> names;//Solo para probar
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tecnicosViewModel =
                ViewModelProviders.of(this).get(TecnicosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tecnicos, container, false);
        root.findViewById(R.id.recyclerListaTecnicos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.two_line_list_item, names);
        tecnicosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}