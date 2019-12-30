package com.example.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Users.PendingOders;
import com.example.pharmacy.Users.PendingOrdersFinish;
import com.example.pharmacy.Users.oder_adapter;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    RecyclerView Orders;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_orders , container , false);
        Orders = view.findViewById(R.id.OrderRecyclerView);
        PendingOders pendingOders = new PendingOders();
        pendingOders.GetPending(new PendingOrdersFinish() {
            @Override
            public void GetPending(ArrayList<PendingOders> PendingOrders) {
                Orders.setLayoutManager(new LinearLayoutManager(view.getContext()));
                oder_adapter OrderAdapter = new oder_adapter(PendingOrders , view.getContext());
                Orders.setAdapter(OrderAdapter);
            }

            @Override
            public void GetPendingByID(PendingOders pendingOders) {

            }
        });
        return view;
    }
}
