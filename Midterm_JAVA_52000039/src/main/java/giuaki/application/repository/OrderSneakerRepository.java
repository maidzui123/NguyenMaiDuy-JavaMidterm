package giuaki.application.repository;

import giuaki.application.entity.OrderSneaker;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderSneakerRepository extends CrudRepository<OrderSneaker, Long> {

    OrderSneaker findOrderById(long cartId);

    List<OrderSneaker> findOrderSneakerByStatus(int i);
    @Transactional
    OrderSneaker save(OrderSneaker orderSneaker);
}
