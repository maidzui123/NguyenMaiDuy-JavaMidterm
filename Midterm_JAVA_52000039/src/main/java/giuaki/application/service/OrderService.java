package giuaki.application.service;

import java.util.HashMap;

public interface OrderService {
    public HashMap<String, Object> addNewCart(HashMap<String, String> data);
    public HashMap<String, Object> addSneakerToCart(HashMap<String, String> data);
    HashMap<String, Object> getSneakerByStatus(String statusData);

    public Integer getCountOfOrder();
    public HashMap<String, Object> deleteOrderItem(HashMap<String, String> data);

    HashMap<String, Object> purchaseSneaker();
}
