package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Notification Detail Sender Controller",description = "APIs for managing and sending notifications (email, SMS, or in-app) in the RoomBillz application")
@RequestMapping(path = "/notificationSender", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface INotificationSenderController {

}
