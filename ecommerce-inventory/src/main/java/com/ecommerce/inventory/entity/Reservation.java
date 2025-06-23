package com.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String reservedBy; // e.g., userId, sessionId, etc.

    private LocalDateTime reservedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        RESERVED, CANCELLED
    }
}
