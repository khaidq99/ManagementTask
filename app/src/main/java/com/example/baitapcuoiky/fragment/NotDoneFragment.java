package com.example.baitapcuoiky.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitapcuoiky.DBOpenHelper;
import com.example.baitapcuoiky.DetailActivity;
import com.example.baitapcuoiky.EditActivity;
import com.example.baitapcuoiky.LoginActivity;
import com.example.baitapcuoiky.MainActivity;
import com.example.baitapcuoiky.R;
import com.example.baitapcuoiky.TaskAdapter;
import com.example.baitapcuoiky.model.Task;
import com.example.baitapcuoiky.model.User;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotDoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotDoneFragment extends Fragment implements TaskAdapter.OnTaskListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBOpenHelper db;
    private RecyclerView rvNotDone;
    private List<Task> listTask;
    private SharedPreferences sp;


    public NotDoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotDoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotDoneFragment newInstance(String param1, String param2) {
        NotDoneFragment fragment = new NotDoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_done, container, false);
        sp = getActivity().getApplicationContext().getSharedPreferences("dataUser", MODE_PRIVATE);

        int userId = sp.getInt("userId", 0);
        db = new DBOpenHelper(getContext());
        rvNotDone = view.findViewById(R.id.rvNotDone);
        rvNotDone.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listTask = db.getListTaskNotDone(userId);
        TaskAdapter adapter = new TaskAdapter(getContext(), this);
        adapter.setData(listTask);
        rvNotDone.setAdapter(adapter);

        return view;
    }

    @Override
    public void onTaskClick(int position) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        Task task = listTask.get(position);
        intent.putExtra("task", task);
        startActivity(intent);
    }
}