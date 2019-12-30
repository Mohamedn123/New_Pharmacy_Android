package com.example.pharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Users.CartFirebaseFinish;
import com.example.pharmacy.Users.CartItems;
import com.example.pharmacy.Users.Person;
import com.example.pharmacy.Users.cart_adapter;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView cartRecyclerView;
    TextView ItemsCount;
    TextView TotalPrice;
    Button ProceedToPayment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart , container , false);
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        ItemsCount = view.findViewById(R.id.ItemsCount);
        TotalPrice = view.findViewById(R.id.Cart_Total_Price);
        ProceedToPayment = view.findViewById(R.id.OrderConfirm);
        Person person = new Person();
        person.GetAllCartItems(new CartFirebaseFinish() {
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
            public void GetAllCartItems(ArrayList<CartItems> CartItems, final String ID) {
                if (CartItems == null || CartItems.size() == 0){
                    cartRecyclerView.setVisibility(View.INVISIBLE);
                    ItemsCount.setText("It Looks Lonely Here Please add Some Products");
                    TotalPrice.setVisibility(View.INVISIBLE);
                    ProceedToPayment.setVisibility(View.INVISIBLE);
                }
                else{
                    ItemsCount.setText("Total Items in Your Cart: "+CartItems.size());
                    cartRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    cart_adapter adapter = new cart_adapter(CartItems , view.getContext());
                    cartRecyclerView.setAdapter(adapter);
                    double cartTotalPrice = 0;
                    for (int x=0;x<CartItems.size();x++){
                        cartTotalPrice+=CartItems.get(x).getTotalPrice();
                    }
                    TotalPrice.setText("Total Price: " +cartTotalPrice);
                    final double finalCartTotalPrice = cartTotalPrice;
                    ProceedToPayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(view.getContext() , PaymentGateway.class);
                            intent.putExtra("CartID" , ID);
                            intent.putExtra("TotalPrice" , finalCartTotalPrice);
                            startActivity(intent);
                        }
                    });
                }


            }

        });
        return view;
    }
}
