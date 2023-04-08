package giuaki.application.controller;

import giuaki.application.entity.Product;
import giuaki.application.service.OrderService;
import giuaki.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/")
    public String getAll(Model model){
        Iterable<Product> products = productService.getAllProducts();
        model.addAttribute("count", orderService.getCountOfOrder());
        model.addAttribute("products",products);
        return "index";
    }
    @GetMapping("/cart.html")
    public String getCart(Model model) {
        return "cart";
    }
    @GetMapping("/searchSneaker")
    @ResponseBody
    public Iterable<Product> getSneakerByName(@RequestParam("name") String name)
    {
        if(name == "" && name == null) return null;
        return productService.getSneakerByName(name);
    }
    @GetMapping("/idSneaker/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id)
    {
        return productService.getProduct(id);
    }

    @GetMapping("/filterSneaker")
    @ResponseBody
    public Iterable<Product> filterSneaker(@RequestParam Map<String, String> data) {
        return productService.filterSneaker(data);
    }
}
