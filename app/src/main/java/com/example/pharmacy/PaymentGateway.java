package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacy.Users.Person;

public class PaymentGateway extends AppCompatActivity {
    TextView TotalPrice;
    Button PlaceOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        Bundle bundle = getIntent().getExtras();
        final double doubleTotalPrice = (double) bundle.get("TotalPrice");
        final String CartID = (String) bundle.get("CartID");

        TotalPrice = findViewById(R.id.Payment_Total_Price);
        PlaceOrder = findViewById(R.id.Place_Order);
        TotalPrice.setText(TotalPrice.getText()+" : "+doubleTotalPrice);

        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.PlaceOrder(CartID , doubleTotalPrice , PaymentGateway.this);
            }
        });


    }
}
