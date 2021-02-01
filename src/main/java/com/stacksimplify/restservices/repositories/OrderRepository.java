package com.stacksimplify.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacksimplify.restservices.entities.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

}
