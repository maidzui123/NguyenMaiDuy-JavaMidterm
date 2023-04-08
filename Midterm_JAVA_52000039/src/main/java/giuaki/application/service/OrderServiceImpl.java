package giuaki.application.service;

import giuaki.application.entity.OrderSneaker;
import giuaki.application.entity.OrderSneakerDetail;
import giuaki.application.entity.Product;
import giuaki.application.repository.OrderSneakerDetailRepository;
import giuaki.application.repository.OrderSneakerRepository;
import giuaki.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderSneakerRepository orderSneakerRepository;
    @Autowired
    private OrderSneakerDetailRepository orderSneakerDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    public HashMap<String, Object> addNewCart(HashMap<String, String> data) {
        HashMap<String, Object> res = new HashMap<>();
        if(data.get("sneakerId") == null || data.get("quantity") == null || data.get("size") == null){
            res.put("mess", "error");
            return res;
        }
        OrderSneaker orderSneaker = new OrderSneaker();
        Product product = productRepository.findSneakerById(Long.parseLong(data.get("sneakerId")));
        Long price = product.getPrice() * Long.parseLong(data.get("quantity"));
        orderSneaker.setPrice(price);
        orderSneaker.setStatus(0);
        orderSneakerRepository.save(orderSneaker);
        OrderSneakerDetail detail = new OrderSneakerDetail();
        detail.setProduct(product);
        detail.setSize(Integer.parseInt(data.get("size")));
        detail.setQuantity(Integer.parseInt(data.get("quantity")));
        detail.setOrderSneaker(orderSneaker);
        orderSneakerDetailRepository.save(detail);
        res.put("mess", "success");
        return res;
    }
    public HashMap<String, Object> addSneakerToCart(HashMap<String, String> data){
        HashMap<String, Object> res = new HashMap<>();
        if(data.get("sneakerId") == null || data.get("quantity") == null || data.get("orderId") == null || data.get("size") == null){
            res.put("mess", "error");
            return res;
        }
        OrderSneaker orderSneaker = orderSneakerRepository.findOrderById(Long.parseLong(data.get("orderId")));
        Product sneaker = productRepository.findSneakerById(Long.parseLong(data.get("sneakerId")));
        List<OrderSneakerDetail> orderSneakerDetailList = orderSneaker.getOrderSneakerDetails();
        for(OrderSneakerDetail item : orderSneakerDetailList){
            if(item.getProduct().getId().equals(sneaker.getId())){
                res.put("mes", "Cant's buy same sneaker");
                return res;
            }
        }
        Long price = orderSneaker.getPrice() + sneaker.getPrice() * Long.parseLong(data.get("quantity"));
        orderSneaker.setPrice(price);
        OrderSneakerDetail detail = new OrderSneakerDetail();
        detail.setOrderSneaker(orderSneaker);
        detail.setSize(Integer.parseInt(data.get("size")));
        detail.setQuantity(Integer.parseInt(data.get("quantity")));
        detail.setProduct(sneaker);
        orderSneakerDetailRepository.save(detail);
        orderSneakerRepository.save(orderSneaker);
        return getSneakerByStatus("0");
    }
    public HashMap<String, Object> getSneakerByStatus(String statusData) {
        HashMap<String, Object> res = new HashMap<>();
        if(statusData == null){
            res.put("mess", "error");
            return res;
        }
        List<OrderSneaker> orderSneakers;
        Integer status = Integer.valueOf(statusData);
        if(status == 0){
            orderSneakers = orderSneakerRepository.findOrderSneakerByStatus(0);
            res.put("data", orderSneakers);
            if(orderSneakers.isEmpty()) {
                res.put("count",0);
            }
            else if (orderSneakers.get(0).getOrderSneakerDetails().isEmpty()) {
                res.put("count",0);
            }
            else {
                res.put("count",orderSneakers.get(0).getOrderSneakerDetails().size());
            }
            return res;
        }else if(status == 1){
            orderSneakers = orderSneakerRepository.findOrderSneakerByStatus(1);
            res.put("data", orderSneakers);
            return res;
        }
        return null;
    }
    @Override
    public Integer getCountOfOrder() {
        List<OrderSneaker> orderSneakerList = orderSneakerRepository.findOrderSneakerByStatus(0);
        if(orderSneakerList.isEmpty())
        {
            return 0;
        }
        else {
            List<OrderSneakerDetail> orderSneakerDetails = orderSneakerDetailRepository.findOrderDetailsByOrderSneaker(orderSneakerList.get(0));
            if (orderSneakerDetails.isEmpty()) return 0;
            return orderSneakerDetails.size();
        }
    }
    @Override
    public HashMap<String, Object> deleteOrderItem(HashMap<String, String> data){
        HashMap<String, Object> res = new HashMap<>();
        if(data.get("orderId") == null || data.get("sneakerId") == null){
            res.put("mess", "error");
            return res;
        }
        OrderSneaker orderSneaker = orderSneakerRepository.findOrderById(Long.parseLong(data.get("orderId")));
        Product sneaker = productRepository.findSneakerById(Long.parseLong(data.get("sneakerId")));
        OrderSneakerDetail detail = orderSneakerDetailRepository.findOrderDetailByOrderSneakerAndProduct(orderSneaker, sneaker);
        orderSneaker.setPrice(orderSneaker.getPrice() - detail.getProduct().getPrice()*detail.getQuantity());
        if (detail == null) {
            res.put("mess", orderSneaker);
            return res;
        }
        orderSneaker.removeOrderSneakerDetail(detail);
        orderSneakerRepository.save(orderSneaker);
        return getSneakerByStatus("0");
    }

    @Override
    public HashMap<String, Object> purchaseSneaker(){
        HashMap<String, Object> res = new HashMap<>();
        List<OrderSneaker> orderSneakers = orderSneakerRepository.findOrderSneakerByStatus(0);
        orderSneakers.get(0).setStatus(1);
        orderSneakerRepository.save(orderSneakers.get(0));
        res.put("mess", "Purchase success");
        return res;
    }
}
