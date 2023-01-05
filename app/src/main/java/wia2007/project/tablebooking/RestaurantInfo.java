package com.example.invitable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.invitable.entity.Restaurant;
import com.example.invitable.dao.RestaurantDAO;
import com.example.invitable.database.TableBookingDatabase;

import java.util.List;

public class RestaurantInfo extends AppCompatActivity {

    EditText name, address, contact, averagePrice, description;
    Spinner cuisineType;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        name = findViewById(R.id.FillName);
        address = findViewById(R.id.fillAdd);
        contact = findViewById(R.id.fillContact);
        averagePrice = findViewById(R.id.FillAverageP);
        cuisineType = findViewById(R.id.fillCuisineT);
        description = findViewById(R.id.fillDes);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);

        //dropdown menu for cuisine type
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.cuisine, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cuisineType.setAdapter(adapter);

        //save data
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        //String resname = sharedPref.getString("user", null);

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        RestaurantDAO restaurantDAO = database.restaurantDAO();
        //List<Restaurant> restaurantList = restaurantDAO.getRestaurantByRestaurantUserName(resname);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME = name.getText().toString();
                String ADDRESS = address.getText().toString();
                String  CONTACT = contact.getText().toString();
                float  PRICE = Float.parseFloat(averagePrice.getText().toString());
                String DESCRIPTION = description.getText().toString();
                int CUISINETYPE=0;
                switch(cuisineType.getAdapter().toString()){
                    case "MALAYSIAN":
                        CUISINETYPE = 1;
                        break;
                    case "Dinner Buffet":
                        CUISINETYPE = 2;
                        break;
                    case "Japanese":
                        CUISINETYPE = 3;
                        break;
                    case "Indian":
                        CUISINETYPE = 4;
                        break;
                    case "High Tea Buffet":
                        CUISINETYPE = 5;
                        break;
                    case "Chinese":
                        CUISINETYPE = 6;
                        break;
                    case "Western":
                        CUISINETYPE = 7;
                        break;
                    case "Thai":
                        CUISINETYPE = 8;
                        break;
                }

                Restaurant restaurant = null;

                /*if(restaurantList.size() == 0){
                    throw new RuntimeException("No Restaurant Register");
                }
                int size = restaurantList.size();
                while(size != 0) {
                    if (restaurantList.get(size).getRestaurant_name().equals(resname)) {
                        restaurant = restaurantList.get(size);
                    }
                    size--;
                }*/

                restaurant.setRestaurant_name(NAME);
                restaurant.setAddress(ADDRESS);
                restaurant.setContact_number(CONTACT);
                restaurant.setAverage_price(PRICE);
                restaurant.setCuisine_type(CUISINETYPE);
                restaurant.setDescription(DESCRIPTION);
                restaurantDAO.updateRestaurants(restaurant);

                Intent intent = new Intent(RestaurantInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}