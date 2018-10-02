package com.iToolsV2.form;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MachineReportSearch {
	private String companyCode;
	private String toolCode; /*CTIID*/
	private String machineCode;
	private String tray;
}
