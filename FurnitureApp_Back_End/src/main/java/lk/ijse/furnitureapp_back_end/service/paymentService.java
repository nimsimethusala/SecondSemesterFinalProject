package lk.ijse.furnitureapp_back_end.service;

public interface paymentService {
    void savePayment(String orderId, String paymentId, double amount);
}
