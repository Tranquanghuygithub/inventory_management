package com.ninhhoangcuong.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ninhhoangcuong.model.Invoice;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.utils.DateUtils;

public class InvoiceReport extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Invoice> invoices = (List<Invoice>) model.get(Constant.KEY_INVOICE_REPORT);
		if (invoices.get(0).getType() == Constant.TYPE_GOODS_ISSUES) {
			response.setHeader("Content-Disposition", "attachment;filename=\"goods-issues-export.xlsx\"");
		} else {
			response.setHeader("Content-Disposition", "attachment;filename=\"goods-receipt-export.xlsx\"");
		}
		Sheet sheet = workbook.createSheet("data");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("#");
		header.createCell(1).setCellValue("Code");
		header.createCell(2).setCellValue("Quantity");
		header.createCell(3).setCellValue("Price");
		header.createCell(4).setCellValue("Product Name");
		header.createCell(5).setCellValue("UpDate Date");
		int[] rowNum = { 1 };
		invoices.forEach(p -> {
			Row row = sheet.createRow(rowNum[0]++);
			row.createCell(0).setCellValue(rowNum[0] - 1);
			row.createCell(1).setCellValue(p.getCode());
			row.createCell(2).setCellValue(p.getQty());
			row.createCell(3).setCellValue(p.getPrice().toString());
			row.createCell(4).setCellValue(p.getProductInfo().getName());
			row.createCell(5).setCellValue(DateUtils.datetoString(p.getUpdateDate()));
		});
	}
}