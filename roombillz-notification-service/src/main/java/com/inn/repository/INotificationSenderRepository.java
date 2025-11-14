package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.NotificationDetail;

@Repository
public interface INotificationSenderRepository extends JpaRepository<NotificationDetail, Integer>{

}
