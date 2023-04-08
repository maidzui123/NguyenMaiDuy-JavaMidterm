package giuaki.application.controller;

import giuaki.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;

@Controller
public class OrderSneakerController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/getCart")
    @ResponseBody
    public HashMap<String, Object> getCart(@RequestParam("status") String status){
        return orderService.getSneakerByStatus(status);
    }
    @PutMapping("/addNewCart")
    @ResponseBody
    public HashMap<String, Object> addNewCart(@RequestParam HashMap<String, String> newCart){
        return orderService.addNewCart(newCart);
    }
    @PutMapping("/addOldCart")
    @ResponseBody
    public HashMap<String, Object> addOldCart(@RequestParam HashMap<String, String> orderDetailInfo){
        return orderService.addSneakerToCart(orderDetailInfo);
    }
    @DeleteMapping("/deleteOrderDetail")
    @ResponseBody
    public HashMap<String, Object> deleteCartDetail(@RequestParam HashMap<String, String> deleteData){
        return orderService.deleteOrderItem(deleteData);
    }
    @PutMapping("/purchaseSneaker")
    @ResponseBody
    public HashMap<String, Object> purchaseCart(){
        return orderService.purchaseSneaker();
    }
}
