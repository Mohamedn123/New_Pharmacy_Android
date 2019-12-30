package com.example.pharmacy.Users;

import java.util.ArrayList;

public interface PendingOrdersFinish {
    void GetPending(ArrayList<PendingOders> PendingOrders);
    void GetPendingByID(PendingOders pendingOders);
}
