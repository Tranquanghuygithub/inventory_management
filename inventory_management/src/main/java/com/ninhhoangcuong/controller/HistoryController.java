package com.ninhhoangcuong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ninhhoangcuong.model.History;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.service.HistoryService;
import com.ninhhoangcuong.utils.Constant;

@Controller
public class HistoryController {
	@Autowired
	private HistoryService historyService;
	private static final Logger log = Logger.getLogger(HistoryController.class);

	@RequestMapping(value = { "/history", "/history/" })
	public String redirectHistroyPage() {
		return "redirect:/history/1";
	}

	@RequestMapping(value = "/history/{page}")
	public String showHistory(Model model, @ModelAttribute("searchForm") History history, @PathVariable int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<History> histories = historyService.getAll(history, paging);
		Map<String, String> mapType = new HashMap<String, String>();
		mapType.put(String.valueOf(Constant.TYPE_ALL), "ALL");
		mapType.put(String.valueOf(Constant.TYPE_GOODS_RECEIPT), "Goods receipt");
		mapType.put(String.valueOf(Constant.TYPE_GOODS_ISSUES), "Goods issues");
		model.addAttribute("histories", histories);
		model.addAttribute("pageInfo", paging);
		model.addAttribute("mapType", mapType);
		model.addAttribute("titlePage", "History");
		return "history";
	}
}
