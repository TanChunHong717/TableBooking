package wia2007.project.tablebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.sql.Date;
import java.util.List;

import wia2007.project.tablebooking.dao.CustomerDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Customer;
import wia2007.project.tablebooking.entity.PasswordChecker;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("user", null);
        if(username == null){
            throw new RuntimeException("User don't login");
        }

        TableBookingDatabase database = TableBookingDatabase.getDatabase(getApplicationContext());
        CustomerDAO customerDAO = database.customerDAO();
        List<Customer> customerList = customerDAO.getCustomerByUsername(username);
        if(customerList.size() != 1) {
            throw new RuntimeException("Incorrect username being store");
        }
        Customer customer = customerList.get(0);

        ((EditText) findViewById(R.id.ETEditProfileName)).setText(customer.getName());
        ((EditText) findViewById(R.id.ETEditProfilePhone)).setText(customer.getMobile_number());
        ((EditText) findViewById(R.id.ETEditProfileEmail)).setText(customer.getEmail());
        ((EditText) findViewById(R.id.ETEditProfileBirthDate)).setText(customer.getBirth_date().toString());
        if(customer.getGender() == Customer.GENDER_MALE)
            ((RadioButton) findViewById(R.id.RBtnEditProfileMale)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.RBtnEditProfileFemale)).setChecked(true);


        findViewById(R.id.BtnEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.ETEditProfileName)).getText().toString();
                String phone = ((EditText) findViewById(R.id.ETEditProfilePhone)).getText().toString();
                String email = ((EditText) findViewById(R.id.ETEditProfileEmail)).getText().toString();
                int gender = (((RadioButton) findViewById(R.id.RBtnEditProfileMale)).isChecked())? Customer.GENDER_MALE:(((RadioButton) findViewById(R.id.RBtnEditProfileFemale)).isChecked())?Customer.GENDER_FEMALE:-1;
                String birthDate = ((EditText) findViewById(R.id.ETEditProfileBirthDate)).getText().toString();
                String password = ((EditText) findViewById(R.id.ETEditProfilePassword)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.ETEditProfileConfirmPassword)).getText().toString();

                if (!password.equals(confirmPassword) || !PasswordChecker.validPassword(password) ||
                        email.equals("") || name.equals("") || phone.equals("") ||
                        birthDate.equals("") || gender == -1
                ) {
                    return;
                }

                customer.setName(name);
                customer.setMobile_number(phone);
                customer.setEmail(email);
                customer.setGender(gender);
                customer.setBirth_date(Date.valueOf(birthDate));
                customer.setPassword(password);

                customerDAO.updateCustomers(customer);

                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}