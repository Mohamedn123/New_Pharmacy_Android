package com.example.pharmacy.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.OrdersFragment;
import com.example.pharmacy.R;
import com.example.pharmacy.ViewOrderDetails;
import com.example.pharmacy.ViewUserInformation;

import java.util.ArrayList;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class oder_adapter extends RecyclerView.Adapter<oder_adapter.ViewHolder> {
    ArrayList<PendingOders> PendingOrders = new ArrayList<PendingOders>();
    Context context;

    public oder_adapter(ArrayList<PendingOders> PendingOrder, Context context) {
        this.PendingOrders = PendingOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.TotalPrice.setText(PendingOrders.get(position).getTotalPrice() + "");
        Person person = new Person();
        person.GetUserWithID(PendingOrders.get(position).getUserID(), context, new FirebaseFinish() {
            @Override
            public void onGetAllUsers(ArrayList<Person> personArrayList) {

            }

            @Override
            public void onGetUserWithID(final Person person) {
                holder.Username.setText(person.getName());
                holder.Call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + person.getPhone()));


                        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                        }
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void GetUserPrivilege(long Privilege) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return this.PendingOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Username;
        TextView TotalPrice;
        Button Call;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            Username = itemView.findViewById(R.id.OrderUserName);
            TotalPrice = itemView.findViewById(R.id.OrderTotalPrice);
            Call = itemView.findViewById(R.id.OrderCall);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ViewOrderDetails.class);
                    intent.putExtra("OrderID" , PendingOrders.get(getAdapterPosition()).getID());
                    context.startActivity(intent);
                }
            });
        }
    }
}
