package com.iToolsV2.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.iToolsV2.form.MachineReport;
import com.iToolsV2.form.MachineReportSearch;
import com.iToolsV2.form.TransactionReport;
import com.iToolsV2.form.TransactionSearch;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineReportService {
	
	@Value("${itool.sql.getMachineReport}")
	private String sqlGetMachineReport;
	
	@Autowired
	private DataSource dataSource;
	
	public List<MachineReport> searchMachineReport(MachineReportSearch search) throws Exception {
		List<MachineReport> lstResult = new ArrayList<>();
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			int sizeParam = (search.getCompanyCode().isEmpty()? 0 : 1) + (search.getToolCode().isEmpty()? 0 : 1) +					
					(search.getMachineCode().isEmpty()? 0 : 1) + (search.getTray().isEmpty()? 0 : 1);
			
			Object[] objectParams = new Object[sizeParam];
			
			String where = "";
			int indexParams = 0;
			if (!search.getCompanyCode().isEmpty()) {
				where += " AND c.CompanyCode = ?";
				objectParams[indexParams++] = search.getCompanyCode();
				log.info("c.CompanyCode = " + search.getCompanyCode());
				System.out.println("c.CompanyCode = " + search.getCompanyCode());
			}
			if (!search.getToolCode().isEmpty()) {
				where += " AND tmt.ToolCode = ?";
				objectParams[indexParams++] = search.getToolCode();
				log.info("tmt.ToolCode = " + search.getToolCode());
				System.out.println("tmt.ToolCode = " + search.getToolCode());
			}
			if (!search.getMachineCode().isEmpty()) {
				where += " AND tmt.MachineCode = ?";
				objectParams[indexParams++] = search.getMachineCode();
				log.info("tmt.MachineCode = " + search.getMachineCode());
				System.out.println("tmt.MachineCode = " + search.getMachineCode());
			}
			if (!search.getTray().isEmpty()) {
				where += " AND tmt.TrayIndex = ?";
				objectParams[indexParams++] = search.getTray();
				log.info("tmt.TrayIndex = " + search.getTray());
				System.out.println("tmt.TrayIndex = " + search.getTray());
			}
			log.info(sqlGetMachineReport + where);
			System.out.println(sqlGetMachineReport + where);
			
			lstResult = jdbcTemplate.query(sqlGetMachineReport + where, objectParams, new RowMapper<MachineReport>() {
				@Override
				public MachineReport mapRow(ResultSet rs, int rowNum) throws SQLException {
					MachineReport report = new MachineReport();
					report.setCompanyName(rs.getString("CompanyName"));
					report.setMachineName(rs.getString("MachineName"));
					report.setTray(rs.getString("TrayIndex"));
					report.setToolCode(rs.getString("ToolCode"));
					report.setQuantity(rs.getInt("Quantity"));
					report.setDescription(rs.getString("Description"));
					return report;
				}
			});
			return lstResult;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
