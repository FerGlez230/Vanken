package com.example.vanken.ui.estadisticas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vanken.R;

public class EstadisticasFragment extends Fragment {

    private EstadisticasViewModel estadisticasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        estadisticasViewModel =
                ViewModelProviders.of(this).get(EstadisticasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        estadisticasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}