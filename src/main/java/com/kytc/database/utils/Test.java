package com.kytc.database.utils;

import java.util.ArrayList;
import java.util.List;

import com.kytc.database.dto.ReportDTO;

public class Test {
	public static void main(String[] args) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		for(int i=0;i<10;i++){
			ReportDTO dto = new ReportDTO();
			dto.setCategory("2018-05-"+(i+1));
			dto.setTitle("制作");
			dto.setValue((100.0+i));
			list.add(dto);
		}
		for(int i=5;i<8;i++){
			ReportDTO dto = new ReportDTO();
			dto.setCategory("2018-05-"+(i+1));
			dto.setTitle("打开");
			dto.setValue((200.0+i));
			list.add(dto);
		}
		List<String> categoryList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(ReportDTO dto:list){
				if(!categoryList.contains(dto.getCategory())){
					categoryList.add(dto.getCategory());
				}
				if(!titleList.contains(dto.getTitle())){
					titleList.add(dto.getTitle());
				}
			}
			List<List<Double>> valueList = new ArrayList<List<Double>>();
			for(int i=0;i<titleList.size();i++){
				valueList.add(new ArrayList<Double>());
			}
			List<List<ReportDTO>> reportList = new ArrayList<List<ReportDTO>>();
			for(int i=0;i<titleList.size();i++){
				reportList.add(new ArrayList<ReportDTO>());
			}
			for(ReportDTO dto:list){
				reportList.get(titleList.indexOf(dto.getTitle())).add(dto);
			}
			int j=0;
			for(List<ReportDTO> list1:reportList){
				int i=0;
				for(ReportDTO dto:list1){
					for(;i<categoryList.size();){
						if(dto.getCategory().equals(categoryList.get(i))){
							valueList.get(j).add(dto.getValue());
							i=i+1;
							break;
						}else{
							valueList.get(j).add(0d);
						}
						i=i+1;
					}
				}
				j++;
			}
			for(List<Double> list1:valueList){
				for(int i=list1.size();i<categoryList.size();i++){
					list1.add(0d);
				}
			}
		}
	}
}
