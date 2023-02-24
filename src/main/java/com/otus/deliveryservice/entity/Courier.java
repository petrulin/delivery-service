package com.otus.deliveryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "courier", schema = "delivery_service", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "phone", nullable = false)
    private String phone;

}
