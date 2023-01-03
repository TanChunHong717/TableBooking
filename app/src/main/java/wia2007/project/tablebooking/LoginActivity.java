package wia2007.project.tablebooking;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.Restaurant;
import wia2007.project.tablebooking.entity.UserStatus;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.BtnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ETUsername = findViewById(R.id.ETUsername);
                String username = ETUsername.getText().toString();
                EditText ETPassword = findViewById(R.id.ETPassword);
                String password = ETPassword.getText().toString();
                Spinner SPStatus = findViewById(R.id.SPStatus);

                if (SPStatus.getSelectedItem().toString().equals("Customer")) {
                    TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
                    CustomerDAO customerDAO = database.customerDAO();
                    List<Customer> customerList = customerDAO.getCustomerByUsername(username);
                    if(customerList.size() > 0 && password.equals(customerList.get(0).getPassword())){
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user", username); // false on logout
                        editor.putBoolean(UserStatus.IS_ADMIN, false);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        findViewById(R.id.TVIncorrect).setVisibility(View.VISIBLE);
                } else {
                    TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
                    RestaurantDAO restaurantDAO = database.restaurantDAO();
                    List<Restaurant> restaurantList = restaurantDAO.getRestaurantByRestaurantUserName(username);

                    if(restaurantList.size() > 0 && password.equals(restaurantList.get(0).getPassword())){
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user", username); // false on logout
                        editor.putBoolean(UserStatus.IS_ADMIN, true);
                        editor.apply();

                        //ToDo: Change activity to main page of admin
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        findViewById(R.id.TVIncorrect).setVisibility(View.VISIBLE);
                }
            }
        });

        Button btnGoSignUp = findViewById(R.id.BtnGoSignUp);
        btnGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}