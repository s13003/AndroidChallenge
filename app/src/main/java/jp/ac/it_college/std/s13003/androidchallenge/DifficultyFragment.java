package jp.ac.it_college.std.s13003.androidchallenge;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

public class DifficultyFragment extends Fragment implements View.OnClickListener {
    public static DifficultyFragment newInstance() {
        DifficultyFragment fragment = new DifficultyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_difficulty, container, false);
        rootView.findViewById(R.id.button1).setOnClickListener(this);
        rootView.findViewById(R.id.button2).setOnClickListener(this);
        rootView.findViewById(R.id.button3).setOnClickListener(this);
        return rootView;
    }

    public void difficultySelected(String difficulty) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("Difficulty", difficulty);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                difficultySelected("EASY");
                break;


            case R.id.button2:
                difficultySelected("NORMAL");
                break;

            case R.id.button3:
                difficultySelected("HARD");
                break;
        }
    }
}
