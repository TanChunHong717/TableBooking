package wia2007.project.tablebooking.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import wia2007.project.tablebooking.AdminBookingList;
import wia2007.project.tablebooking.BookingAdapter;
import wia2007.project.tablebooking.R;
import wia2007.project.tablebooking.dao.BookingDAO;
import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {
    Integer customer_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
        View view =  inflater.inflate(R.layout.fragment_booking, container, false);
        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity().getApplicationContext());
        BookingDAO bookingDAO = database.bookingDAO();
        ListView listView = view.findViewById(R.id.LVBooking);
        BookingAdapter adapter = new BookingAdapter(getContext(), bookingDAO.getBookingRestaurantByCustomer(customer_id), false);
        listView.setAdapter(adapter);

        ((Spinner) view.findViewById(R.id.SPBookingSort)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item2 = adapterView.getSelectedItem();
                String item = item2.toString();
                if (item.equals("Sort by date DESC")) {
                    adapter.changeCursor(bookingDAO.getBookingRestaurantByCustomer(customer_id));
                }else {
                    adapter.changeCursor(bookingDAO.getBookingRestaurantByCustomerOrderByName(customer_id));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.TVMainAct);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Bookings");

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getActivity().getApplicationContext());
        BookingDAO bookingDAO = database.bookingDAO();
        CustomerDAO customerDAO = database.customerDAO();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPref.getString("user", null);
        if (username == null)
            return;
        List<Customer> customerList = customerDAO.getCustomerByUsername(username);
        if (customerList.size() != 1)
            throw new RuntimeException("More than one user with the same username found");
        customer_id = customerList.get(0).getCustomer_id();
    }


}