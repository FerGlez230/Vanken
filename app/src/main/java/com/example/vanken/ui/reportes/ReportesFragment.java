package com.example.vanken.ui.reportes;

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

public class ReportesFragment extends Fragment {

    private ReportesViewModel reportesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportesViewModel =
                ViewModelProviders.of(this).get(ReportesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reportes, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        reportesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}