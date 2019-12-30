package com.example.pharmacy.Users;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PendingOders {

    String ID;
    String CartID;
    String UserID;
    double TotalPrice;

    public PendingOders(String ID, String cartID, String userID, double totalPrice) {
        this.ID = ID;
        CartID = cartID;
        UserID = userID;
        TotalPrice = totalPrice;
    }

    public PendingOders() {
    }

    public String getID() {
        return ID;
    }

    public String getCartID() {
        return CartID;
    }

    public String getUserID() {
        return UserID;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void GetPending(final PendingOrdersFinish finish){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("PendingOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            ArrayList<PendingOders> pendingOders = new ArrayList<PendingOders>();
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                    pendingOders.add(new PendingOders(queryDocumentSnapshot.getId() , (String) queryDocumentSnapshot.get("CartID") , (String) queryDocumentSnapshot.get("UserID") , (double) queryDocumentSnapshot.get("TotalPrice")));

                }
                finish.GetPending(pendingOders);
            }
        });
    }

    public void GetPendingByID (String ID , final PendingOrdersFinish finish){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("PendingOrder").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                finish.GetPendingByID(new PendingOders(documentSnapshot.getId() , (String) documentSnapshot.get("CartID") , (String) documentSnapshot.get("UserID") , (double) documentSnapshot.get("TotalPrice")));

            }
        });
    }

    public void CofirmOrder (String CartID,String UserID , String OrderID){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map <String , Object> map = new HashMap<>();
        map.put("Status" , 3);
        firebaseFirestore.collection("Carts").document(UserID).collection("CartsCollection").document(CartID).update(map);
        firebaseFirestore.collection("PendingOrder").document(OrderID).delete();

    }
}
