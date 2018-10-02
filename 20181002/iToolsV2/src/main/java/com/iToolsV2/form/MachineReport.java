package com.iToolsV2.form;

import lombok.Data;

@Data
public class MachineReport {
	private String companyName;
	private String machineName;
	private String tray;
	private String toolCode;
	private int quantity;
	private String description;
}
