package giuaki.application.repository;

import giuaki.application.entity.OrderSneaker;
import giuaki.application.entity.OrderSneakerDetail;
import giuaki.application.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderSneakerDetailRepository extends CrudRepository<OrderSneakerDetail, Long> {
    List<OrderSneakerDetail> findOrderDetailsByOrderSneaker(OrderSneaker orderSneaker);
    @Transactional
    OrderSneakerDetail save(OrderSneakerDetail orderSneakerDetail);

    OrderSneakerDetail findOrderDetailByOrderSneakerAndProduct(OrderSneaker orderSneaker, Product sneaker);
}
