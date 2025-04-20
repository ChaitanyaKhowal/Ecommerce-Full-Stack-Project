package com.notifications.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.service.NotificationService;

@RestController
public class NotificationRestController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/demo")
	public String demo() {
		notificationService.sendDeliveryNotification();
		return "Success";
	}

}
