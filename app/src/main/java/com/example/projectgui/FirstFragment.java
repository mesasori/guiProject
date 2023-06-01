package com.example.projectgui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projectgui.databinding.FragmentFirstBinding;
import com.example.projectgui.recyclerViewWeekday.DataClass;
import com.example.projectgui.recyclerViewWeekday.Place;
import com.example.projectgui.recyclerViewWeekday.Underground;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private int[][] graph = {
            {0, 5, 0, 3, 0, 0, 0, 3, 0, 0},
            {5, 0, 5, 0, 0, 0, 4, 0, 0, 0},
            {0, 5, 0, 0, 3, 4, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 0, 3, 4},
            {0, 0, 3, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 0, 0, 0, 0, 0, 0}
    };
    private final ArrayList<String> paths = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        paths.addAll(Arrays.asList("monday.txt", "tuesday.txt", "wednesday.txt", "thursday.txt", "friday.txt", "saturday.txt", "sunday.txt"));
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] result = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                for (int position = 0; position < 7; position++) {
                    ArrayList<Place> ans = new ArrayList<>();
                    FileInputStream fis = null;
                    try {
                        fis = binding.getRoot().getContext().openFileInput(paths.get(position));
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = br.readLine()) != null) {
                            ans.add(parsePlace(line));
                        }
                        if (fis != null) fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (ans.size() != 0) {
                        int index1 = 0, index2 = 0;
                        if (ans.size() == 1) {
                            index1 = ans.get(0).getUnderground().getId();
                            index2 = index1;
                        }
                        if (ans.size() == 2) {
                            index1 = ans.get(0).getUnderground().getId();
                            index2 = ans.get(1).getUnderground().getId();
                        }
                        int[] calculated = new Calculate(graph).out(index1, index2);
                        for (int i = 0; i < calculated.length; i++) {
                            result[i] += calculated[i];
                        }
                    }
                }
                int index = 0, mn = Integer.MAX_VALUE;
                for (int i = 0; i < 10; i++) {
                    if (mn > result[i]) {
                        mn = result[i];
                        index = i;
                    }
                }

                String text = new DataClass().returnUndergroundById(index);
                binding.tvUnder.setText(text);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Place parsePlace(String line) {
        int spaces = 0;
        String underground = "", address = "";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '_') {
                underground = line.substring(0, i);
                address = line.substring(i + 1);
                break;
            }
        }
        return new Place(address, new Underground(underground));
    }

}