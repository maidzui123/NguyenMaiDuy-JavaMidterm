package giuaki.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
