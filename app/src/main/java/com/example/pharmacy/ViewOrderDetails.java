package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacy.Users.CartFirebaseFinish;
import com.example.pharmacy.Users.CartItems;
import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.PendingOders;
import com.example.pharmacy.Users.PendingOrdersFinish;
import com.example.pharmacy.Users.Person;
import com.example.pharmacy.Users.cart_adapter;

import java.util.ArrayList;

public class ViewOrderDetails extends AppCompatActivity {
    TextView TotalPrice;
    TextView Username;
    TextView Phone;
    TextView Address;
    RecyclerView Items;
    Button Confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        TotalPrice = findViewById(R.id.AdminTotalPrice);
        String OrderID = (String) getIntent().getExtras().get("OrderID");
        Username = findViewById(R.id.AdminUserName);
        Phone = findViewById(R.id.AdminMobile);
        Address = findViewById(R.id.AdminAddress);
        Items = findViewById(R.id.AdminRecyclerView);
        Confirm = findViewById(R.id.AdminCofirmOrder);
        PendingOders pendingOders = new PendingOders();
        pendingOders.GetPendingByID(OrderID, new PendingOrdersFinish() {
            @Override
            public void GetPending(ArrayList<PendingOders> PendingOrders) {

            }

            @Override
            public void GetPendingByID(final PendingOders pendingOders) {
                TotalPrice.setText("Total Price: "+pendingOders.getTotalPrice());
                Person person = new Person();
                person.GetUserWithID(pendingOders.getUserID(), ViewOrderDetails.this, new FirebaseFinish() {
                    @Override
                    public void onGetAllUsers(ArrayList<Person> personArrayList) {

                    }

                    @Override
                    public void onGetUserWithID(Person person) {
                        Username.setText("Name: "+person.getName());
                        Phone.setText("Phone: "+person.getPhone());
                        Address.setText("Address: "+person.getAddress());
                    }

                    @Override
                    public void GetUserPrivilege(long Privilege) {

                    }
                });

                person.GetCartItemsByUserID(pendingOders.getUserID(), pendingOders.getCartID(), new CartFirebaseFinish() {
                    @Override
                    public void GetCartID(String ID) {

                    }

                    @Override
                    public void AddNewCart(String CartID) {

                    }

                    @Override
                    public void GetTotalPrice(double Price) {

                    }

                    @Override
                    public void GetAllCartItems(ArrayList<CartItems> CartItems, String ID) {
                        cart_adapter cart_adapter = new cart_adapter(CartItems , ViewOrderDetails.this);
                        Items.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        Items.setAdapter(cart_adapter);
                    }
                });

                Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pendingOders.CofirmOrder(pendingOders.getCartID() , pendingOders.getUserID() , pendingOders.getID());
                        Toast.makeText(getApplicationContext() , "Order Confirmed" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
