package com.notifications.dto;

import java.util.List;

import lombok.Data;

@Data
public class WatiRequest {

	private String template_name;
	private String broadcast_name;
	private List<WatiParameters> parameters;

}
