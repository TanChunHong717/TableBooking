package wia2007.project.tablebooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderMenuPopUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderMenuPopUp extends Fragment {
//    DatabaseHelper myDb;

    int BookingID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderMenuPopUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderMenuPopUp.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderMenuPopUp newInstance(String param1, String param2) {
        OrderMenuPopUp fragment = new OrderMenuPopUp();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_menu_pop_up, container, false);
//        myDb = new DatabaseHelper(this.getContext());
        BackGroundTaskFood backGroundTaskFood = new BackGroundTaskFood(this.getContext());
        backGroundTaskFood.execute("get_food_order");


        Button btnFoodOrderOK = view.findViewById(R.id.BtnFoodOrderOK);
        btnFoodOrderOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }


}