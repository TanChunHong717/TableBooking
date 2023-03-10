package wia2007.project.tablebooking;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Date;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.PasswordChecker;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.TBSignUpAct);
        setSupportActionBar(toolbar);
        // add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.sign_up);

        EditText ETSignUpUsername = findViewById(R.id.ETSignUpUsername);
        EditText ETSignUpEmail = findViewById(R.id.ETSignUpEmail);
        CustomerDAO customerDAO = TableBookingDatabase.getDatabase(getApplicationContext()).customerDAO();
        ETSignUpUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String user = editable.toString();
                List<Customer> customerList = customerDAO.getCustomerByUsername(user);
                TextView TVUsernameUsed = findViewById(R.id.TVSignUpUsernameExist);
                if (customerList.size() != 0) {
                    TVUsernameUsed.setVisibility(View.VISIBLE);
                } else {
                    TVUsernameUsed.setVisibility(View.INVISIBLE);
                }
            }
        });

        ETSignUpEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();
                List<Customer> customerList = customerDAO.getCustomerByEmail(email);
                TextView TVEmailUsed = findViewById(R.id.TVSignUpEmailExist);
                if (customerList.size() != 0) {
                    TVEmailUsed.setVisibility(View.VISIBLE);
                } else
                    TVEmailUsed.setVisibility(View.INVISIBLE);
            }
        });

        Button btnSignUp = findViewById(R.id.BtnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.ETSignUpUsername)).getText().toString();
                String email = ((EditText) findViewById(R.id.ETSignUpEmail)).getText().toString();
                String name = ((EditText) findViewById(R.id.ETSignUpName)).getText().toString();
                String phone = ((EditText) findViewById(R.id.ETSignUpPhone)).getText().toString();
                String birthDate = ((EditText) findViewById(R.id.ETSignUpBirthDate)).getText().toString();
                String password = ((EditText) findViewById(R.id.ETSignUpPassword)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.ETSignUpConfirmPassword)).getText().toString();
                int gender = (((RadioButton) findViewById(R.id.RBtnMale)).isChecked()) ? Customer.GENDER_MALE : (((RadioButton) findViewById(R.id.RBtnFemale)).isChecked()) ? Customer.GENDER_FEMALE : -1;
                if (!password.equals(confirmPassword) || !PasswordChecker.validPassword(password)) {
                    ((TextView) findViewById(R.id.TVSignUpPasswordExplain)).setTypeface(((TextView) findViewById(R.id.TVSignUpPasswordExplain)).getTypeface(), Typeface.BOLD);
                    return;
                } else if (username.equals("") || email.equals("") || name.equals("") ||
                        phone.equals("") || birthDate.equals("") || gender == -1) {
                    Toast.makeText(getApplicationContext(),"Please fill in all data to proceed",Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Date date = Date.valueOf(birthDate);
                    TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
                    CustomerDAO customerDAO = database.customerDAO();
                    List<Customer> customerList = customerDAO.getCustomerByUsername(username);
                    if (customerList.size() != 0) {
                        Toast.makeText(getApplicationContext(), "Username Must be unique", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    customerList = customerDAO.getCustomerByEmail(email);
                    if (customerList.size() != 0) {
                        Toast.makeText(getApplicationContext(), "Email Must be unique", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    customerDAO.insertCustomers(new Customer(username, password, name, phone, email, gender, date));

                    if (getIntent().getBooleanExtra("BackRest", false)) {
                        Intent replyIntent = new Intent();
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    } else {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (IllegalArgumentException e) {
                    TypedValue typedValue = new TypedValue();
                    getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
                    int color = typedValue.data;
                    ((TextView) findViewById(R.id.TVSignUpDateFormatExplain)).setTextColor(color);
                }


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // map toolbar back button same as system back button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}