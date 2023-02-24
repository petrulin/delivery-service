package com.otus.deliveryservice.repository;

import com.otus.deliveryservice.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findAllByIdNotIn(Iterable<Long> longs);
}
