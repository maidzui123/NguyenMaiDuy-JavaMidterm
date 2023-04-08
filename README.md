<a name="readme-top"></a>
<div align="center">
  <a href="https://github.com/maidzui123/NguyenMaiDuy-JavaMidterm">
    <img src="logo.png" alt="Logo" width="200" height="200">
  </a>
</div>
<h3 align="center">ZuySneaker</h3>

# ZuySneaker
ZuySneaker is a convenient and easy-to-use website that allows users to choose and buy sneaker.

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#Summary">Summary</a></li>
    <li><a href="#Built-with">What I Use To Build This Website?</a></li>
    <li><a href="#Code-Explanation">Code Java & Explanation</a></li>
    <li><a href="#Deployment">How To Deploy?</a></li>
    <li><a href="#Website-screenshots">Website Screenshots</a></li>
    <li><a href="#Postman-snapshots">Postman Screenshots</a></li>
  </ol>
</details>
 
 
 ## What I Use To Build This Website?
- In this project, I have implemented various software development principles, patterns, and practices, including the use of the Lombok library for Java to reduce boilerplate code. Lombok provides annotations that automatically generate commonly used code like getters, setters, and constructors, making code more concise, readable, and maintainable.
- I have also utilized the Spring framework, which is a comprehensive open-source framework for building modern Java-based enterprise applications. Spring provides features such as inversion of control, dependency injection, and aspect-oriented programming to enable developers to focus on business logic rather than low-level infrastructure.
- To create dynamic web pages, I have used Thymeleaf, a Java-based template engine that integrates well with Spring.
- For data persistence and management, I have used the Java Persistence API (JPA), which is a framework for object-relational mapping. JPA allows us to map Java objects to relational database tables and vice versa, making it easier to work with data in a database. I have also used the MySQL Connector/J JDBC driver to connect to MySQL databases.
- Finally, I have secured our application using Spring Security, a powerful and highly customizable authentication and access-control framework for securing Spring-based Java web applications. With Spring Security, I have configured security rules, created custom authentication mechanisms, and integrated with other security providers to protect against common attacks like cross-site scripting and request forgery.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Code Java & Explanation
### Entity
- Class OrderSneaker

> This is a Java class representing an OrderSneaker entity, which is likely used to persist order data in a database in an e-commerce application.

>The class is annotated with @Entity and @Table, indicating that it maps to a database table named orderSneaker. 
>
>The class has several instance variables that map to columns in the orderSneaker table.
>
>id: A unique identifier for the order, which is generated automatically.
>
>status: An integer representing the status of the order.
>
>price: The total price of the order.
>
>createdAt: A timestamp representing when the order was created.
>
>orderSneakerDetails: A list of OrderSneakerDetail objects, which represent the individual items in the order.
>
>The class also has a @PrePersist method that sets the createdAt field to the current time before persisting the entity.
>
>Finally, the class has a removeOrderSneakerDetail method that removes an OrderSneakerDetail object from the list of associated details, and sets the OrderSneaker field of the removed object to null. 
>
>The @PrePersist annotation is used to specify a method that >should be called before the entity is persisted. It sets the createdAt property to the current date and time.

```java
@Entity
@Table(name = "orderSneaker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderSneaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "price")
    private Long price;
    @Column(name = "created_at", columnDefinition = "")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "orderSneaker",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderSneakerDetail> orderSneakerDetails;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    public void removeOrderSneakerDetail(OrderSneakerDetail detail) {
        orderSneakerDetails.remove(detail);
        detail.setOrderSneaker(null);
    }
}
```
 - Class OrderSneakerDetail

>This Java class represents the OrderSneakerDetail entity in a database, which contains details about a specific product within an order.
>
> id: is the primary key for the OrderSneakerDetail entity.
>
>quantity: an Integer variable that represents the quantity of the product associated with this order sneaker detail.
>
>size: an Integer variable that represents the size of the product associated with this order sneaker detail.
>
>orderSneaker: a ManyToOne association to the OrderSneaker entity, indicating that each order sneaker detail belongs to a single cart and a cart can have multiple order sneaker details.
>
>The fetch parameter of the @ManyToOne annotation specifies that the related OrderSneaker and Product entities should be loaded lazily, only when they are actually accessed.
>
>product: a ManyToOne association to the Product entity, indicating that each order sneaker detail is associated with a single product and a product can be associated with multiple order sneaker details.

```java
@Entity
@Table(name = "orderSneaker_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "orderSneaker"})
public class OrderSneakerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "size")
    private Integer size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    @JsonBackReference
    private OrderSneaker orderSneaker;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sneaker", referencedColumnName = "id")
    @JsonManagedReference
    private Product product;
}
```
 - Class Product

>The Product class has the following attributes: id (unique identifier), name (name of the product), price (price of the product), brand (brand of the product), image (URL of the product's image), and gender (gender of the product).
>
>The @Entity annotation indicates that this class is an entity, and the @Table annotation specifies the name of the database table that corresponds to this entity.
>
>The class also has various annotations such as @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor, @ToString, and @JsonIgnoreProperties for various purposes.

```java
@Entity
@Table(name = "product")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "brand")
    private String brand;
    @Column(name = "image")
    private String image;
    @Column(name = "gender")
    private String gender;
}
```

### Repository
> The repository folder in a Spring Boot project is used to store classes that handle data storage and retrieval, and is an essential component of building robust and scalable applications.
>
>Include 3 interface: OrderSneakerDetailRepository, OrderSneakerRepository, ProductRepository

### Controller
 - Class OrderSneakerController

 > getCart: This is a Java method that retrieves a shopping cart or order based on the given status by calling the getSneakerByStatus method of the file OrderService.java.
 > 
 > addNewCart: This is a Java method that adds a new sneaker to cart by calling the addNewCart method of the file OrderService.java.
 > 
 > addOldCart: This is a Java method that if in cart exist a sneaker then adds another sneaker to the cart by calling the addSneakerToCart method of the file OrderService.java.
 > 
 > deleteCartDetail: This is a Java method that deletes a sneaker from the cart by calling the deleteOrderItem method of the file OrderService.java.
 > 
 > purchaseCart: This is a Java method that purchases the cart by calling the purchaseSneaker method of the the file OrderService.java.
``` Java Code
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
```
 - Class ProductController

 > getAll: retrieves all products and adds them to the Model object, along with the count of orders. It returns the name of the HTML template that will be used to render the view.
 >
 > getCart: returns the name of the HTML template for rendering the shopping cart view.
 >
 > getSneakerByName: retrieves products by name and returns them as an iterable of Product objects (use to search).
 >
 > getProduct: retrieves a product by its ID and returns it as a Product object.
 > 
 > filterSneaker:  filters products based on the query parameters passed in the request and returns them as an iterable of Product objects. 

```java
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
```
- Class CustomErrorController

>ErrorManagement(HttpServletRequest request, Model model): the status code of the error is retrieved from the request object using the getAttribute() method. If the status code attribute is not present, a default value of 500 (Internal Server Error) is used. The error message is retrieved in a similar way, with a default value of "Internal Server Error" used if the error message attribute is not present. The error code and message are then added as attributes to the Model object using the addAttribute() method. The method then returns the name of the view that should be rendered, in this case, "error". This means that when an error occurs, the "error.html" template will be rendered with the error code and message provided as model attributes.


```java
@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping(value = "/error")
    public String ErrorManagement(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) == null ? 500 : request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) == null ? "Internal Server Error" : request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        model.addAttribute("errorMsg",errorMessage);
        model.addAttribute("errorCode",status);
        return "error";
    }
}
```

### Service
- Class OrderServiceImpl

>addNewCart(HashMap<String, String> params): This is a Java method that takes a HashMap object data as an argument and returns another HashMap object res. The method adds a new order to a cart system, and the input parameters (data) specify the details of the new order. The method first checks if the required fields (sneakerId, quantity, and size) are present in the input data HashMap. If any of these fields are missing, the method returns an error message in the res HashMap and exits. If all required fields are present, the method creates a new OrderSneaker object and initializes its price and status attributes. It then saves this new OrderSneaker object using a repository (orderSneakerRepository). Next, the method creates a new OrderSneakerDetail object and initializes its product, size, quantity, and orderSneaker attributes. The product attribute is set by looking up the Product object corresponding to the sneakerId field in the data HashMap, using a repository (productRepository). The method then saves this new OrderSneakerDetail object using another repository (orderSneakerDetailRepository). Finally, the method adds a success message to the res HashMap and returns it.
>
>addSneakerToCart(HashMap<String, String> params): The method first checks if the required fields (sneakerId, quantity, orderId, and size) are present in the input data HashMap. If any of these fields are missing, the method returns an error message in the res HashMap and exits. If all required fields are present, the method retrieves the OrderSneaker object corresponding to the orderId field in the data HashMap from a repository (orderSneakerRepository). It also retrieves the Product object corresponding to the sneakerId field from another repository (productRepository). Next, the method checks if the OrderSneaker object already contains an OrderSneakerDetail object with the same Product as the one being added. If such a detail already exists, the method returns an error message in the res HashMap and exits. If the OrderSneaker object does not already contain the Product, the method calculates the new price of the order by adding the price of the new sneaker to the existing price of the order. It then updates the OrderSneaker object with the new price and creates a new OrderSneakerDetail object with the OrderSneaker, Product, size, and quantity attributes. The new detail is saved to the repository (orderSneakerDetailRepository) and the updated OrderSneaker object is saved to the repository (orderSneakerRepository). Finally, the method returns the list of OrderSneaker objects that have a status of "0".
>
>getSneakerByStatus(String statusData): The method takes a String parameter statusData which is used to filter sneakers based on their status. If the statusData parameter is null, the method will return a HashMap with a "mess" key set to "error". If the statusData parameter is "0", the method will retrieve a list of all sneakers with status 0 (which means they are in the shopping cart). The method will then create a HashMap with a "data" key set to the list of sneakers, and a "count" key set to the number of items in the shopping cart. If the list of sneakers is empty or if the first sneaker in the list has no details, the count will be set to 0. If the statusData parameter is "1", the method will retrieve a list of all sneakers with status 1 (which means they have been ordered). The method will then create a HashMap with a "data" key set to the list of ordered sneakers. If the statusData parameter is neither "0" nor "1", the method will return null.
>
>getCountOfOrder(): This method getCountOfOrder() is used to get the total count of orders with status 0 (pending). Firstly, it retrieves all orders from the database with status 0 using the orderSneakerRepository.findOrderSneakerByStatus(0) method. If the returned list of orders is empty, it means there are no pending orders, so it returns 0. If the list of orders is not empty, it retrieves the order details for the first order in the list using the orderSneakerDetailRepository.findOrderDetailsByOrderSneaker(orderSneakerList.get(0)) method. If the returned list of order details is empty, it means there are no items in the pending orders, so it returns 0.
>
>deleteOrderItem(HashMap<String, String> data): First, it checks if orderId and sneakerId are not null in the input data. If either of them is null, it returns an error message. Then, it retrieves the OrderSneaker instance with the given orderId and the Product instance with the given sneakerId. It also retrieves the OrderSneakerDetail instance that corresponds to this OrderSneaker and Product instances using the findOrderDetailByOrderSneakerAndProduct method. If the OrderSneakerDetail instance is null, it means that the order item is not found in the order. In this case, it returns an error message containing the OrderSneaker instance. If the OrderSneakerDetail instance is not null, it updates the total price of the OrderSneaker by subtracting the price of the deleted order item. It then removes the OrderSneakerDetail instance from the OrderSneaker instance using the removeOrderSneakerDetail method, and saves the updated OrderSneaker instance.
>
>purchaseCart(String cartParams): The method first initializes a new empty HashMap called res. Then, it calls a method on a repository object named orderSneakerRepository to retrieve a list of OrderSneaker objects where the status is equal to 0. Next, the method sets the status of the first OrderSneaker object in the list to 1, indicating that it has been purchased. The updated OrderSneaker object is then saved back to the repository using the save() method.

```java
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
```
 - ProductServiceImpl

>getAllProducts() - This method returns all products in the product repository by calling the findAll() method of the productRepository.
>
>getProduct(Long id) - This method returns a product by its unique identifier (id) by calling the findById() method of the productRepository.
>
>deleteProduct(Long id) - This method deletes a product by its unique identifier (id). The implementation of this method is missing, so it does nothing.
>
>save(Product product) - This method saves a product in the product repository. The implementation of this method always returns null, indicating that the product was not saved. It is likely that this method is incomplete and needs to be implemented properly.
>
>getSneakerByName(String name) - This method returns all products that contain the given name (case-insensitive) in their name field. It calls the findProductsByNameContainingIgnoreCase() method of the productRepository.
>
>filterSneaker(Map<String,String> data) - This method filters products based on various criteria provided in a Map of key-value pairs. It extracts the necessary information from the Map and constructs a query to retrieve the matching products from the product repository. The filters that can be applied are:
```java
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }
    @Override
    public void deleteProduct(Long id) {}
    @Override
    public Product save(Product product) {return null;}
    @Override
    public Iterable<Product> getSneakerByName(String name) {
        return productRepository.findProductsByNameContainingIgnoreCase(name);
    }
    @Override
    public Iterable<Product> filterSneaker(Map<String,String> data) {
        String name = "";
        Integer price_min = 0, price_max = 1000000000;
        String brand = "", gender = "";
        String sort = "desc";
        if(data.get("sort") != null) {
            sort = data.get("sort");
        }
        if(data.get("price_min") != null) {
            price_min = Integer.parseInt(data.get("price_min"));
        }
        if(data.get("price_max") != null) {
            price_max = Integer.parseInt(data.get("price_max"));
        }
        if(data.get("brand") != null) {
            brand = data.get("brand");
        }
        if(data.get("gender") != null) {
            gender = data.get("gender");
        }
        if (data.get("name") != null) {
            name = data.get("name");
        }
        if(sort.equals("desc")) {
            if(gender.equals("") || gender.equals(null))
            {
                return productRepository.findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceDesc(price_min, price_max, brand, name);
            }
            return productRepository.findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceDesc(price_min, price_max, brand, gender, name);
        }
        else {
            if(gender.equals("") || gender.equals(null))
            {
                return productRepository.findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceAsc(price_min, price_max, brand, name);
            }
            return productRepository.findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceAsc(price_min, price_max, brand, gender, name);
        }
    }
}
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## How To Deploy?

1. Make sure you have Java Runtime 17+ installed on your machine. If not, download and install the latest version of Java from the official website.
2. Place the JAR file for your Spring Boot project in a convenient location on your machine.
3. Open a command prompt or PowerShell window and navigate to the directory where your JAR file is located.
4. Run the following command to start the Spring Boot application: Copy code 'java -jar your-app-name.jar'. Replace your-app-name.jar with the actual name of your JAR file.
5. Open XAMPP or any other program that supports MySQL database and create a database with a name of your choice, and make sure the port is set to 3306. You can onfigure this in the application.properties file in the source code folder of your project.
6. Import the data.sql file into your MySQL database. This file contains the initial data that will be used by your Spring Boot application.
7. Open the project in IntelliJ IDEA or any other Java IDE of your choice.
8. Open XAMPP or any other program that supports MySQL database and create another database with a name of your choice, and make sure the port is set to 3306. You can configure this in the application.properties file in the source code folder of your project.
9. Run the project through the main class in your Java IDE.
10. Finally, import the data.sql file into the newly created MySQL database for the project.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Website Screenshots
### 1. Homepage: contain header, nav, slider, filter bar, all sneaker. User can search, filter, get sneaker information, cart and history purchase.

![Homepage](https://user-images.githubusercontent.com/90997428/230706207-6b06b61c-e23a-462e-a836-8561bf9be248.png)

### 2. AllSneakersInfoPage: contain all sneakers information.

![AllSneakers](https://user-images.githubusercontent.com/90997428/230706266-53e82d1e-80e6-4d96-b09f-ab013b38de15.png)

### 3. Cart: contain sneaker which user choose to order.

![Cart](https://user-images.githubusercontent.com/90997428/230706306-1413f69d-183d-4e1c-8ccc-60c7417bddde.png)

### 4. PurchasePage: contain name, quantity, price, total of all sneakers which user order and button "Buy"

![Purchase](https://user-images.githubusercontent.com/90997428/230706354-3ab1bad3-f1ec-454e-ab2f-3cefcbdcb6f7.png)

### 5. History: contain all bill, click bill will show all sneakers were purchased by user.

![History_1](https://user-images.githubusercontent.com/90997428/230706429-f666b05c-8405-4f10-871b-ed151c7bf714.png)

![History_2](https://user-images.githubusercontent.com/90997428/230706462-6c1de8f3-c858-4ba7-8975-fd37a294a36c.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Postman Screenshots

### 1. addNewCart: add new sneaker to available cart. Return the message if success.

![AddNewCart](https://user-images.githubusercontent.com/90997428/230707895-d1ddb233-f3bf-42d9-8c6f-10ca46335ce4.png)

### 2. addOldCart: add sneaker to available cart. Return cart data if success.

![AddOldCart](https://user-images.githubusercontent.com/90997428/230708029-645834d2-3d84-4243-9d0d-8dab99ebf5e4.png)

### 3. getOrder: get sneaker list depending on status (status = 0 is cart) (status = 1 is history purchase). Return list of sneakers.

![GetOrder](https://user-images.githubusercontent.com/90997428/230708494-4f22a59e-0aef-4d99-b855-e46c75ee5fd1.png)

![GetHistory](https://user-images.githubusercontent.com/90997428/230710023-03bcd27f-1df6-48e6-b358-f058a35386d0.png)

### 4. getProduct: get sneaker by id. Return sneaker.

![GetProduct](https://user-images.githubusercontent.com/90997428/230708854-2bba8d7c-b4c7-4477-8b6f-c22ea566a576.png)

### 5. getSearch: search sneaker by input name. Return list sneakers.

![GetSearchSneaker](https://user-images.githubusercontent.com/90997428/230709156-be44c6eb-fc45-4bde-bba8-a64d74f58965.png)

### 6. filterSneaker: filter sneaker by price, brand, gender and sort price (desc & asc). Return sneaker list after filtering.

![FilterSneaker](https://user-images.githubusercontent.com/90997428/230709369-6a33d9aa-19ac-474d-a364-a12110a00b8d.png)

### 7. deleteSneaker: delete sneaker which user choose in cart. Return list sneaker stay in cart.

![DeleteSneaker](https://user-images.githubusercontent.com/90997428/230709545-1c92fe48-0c24-4c26-afa3-7f854b48d0b7.png)

### 8. purchaseSneaker: change status of cart to 1. Return success or error message

![PurchaseSneaker](https://user-images.githubusercontent.com/90997428/230709857-f554b38d-a3dd-4f3f-aa75-cd1c81074a2e.png)


<div align="center">
  <h1>THANKS FOR READING <3</1>
</div>
