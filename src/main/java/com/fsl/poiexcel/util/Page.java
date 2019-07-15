package com.fsl.poiexcel.util;

import java.util.List;

public class Page<T> {
	/**
	 * 当前的索引
	 * 需要计算
	 */
	//private int index;
	
	/**
	 * 每页显示的条数,通过Servlet传过来
	 */
	private int pageSize;
	
	/**
	 * 当前的页码,通过Servlet传过来
	 */
	private int pageNumber;
	
	/**
	 * 总记录数，数据库查询
	 * select count(*) from bs_book
	 */
	private int totalRecord;
	
	/**
	 * 总页数  ，需要计算
	 */
	//private int totalPage;
	
	
	
	/**
	 * 要显示的数据 ，通过数据库查询获得
	 */
	private List<T> data;
	/**
	 * 保存请求的路径
	 */
	private String path;
	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public Page() {
	}
   
   
	public int getIndex() {
		//根据pageNumber计算而来
		return (getPageNumber()-1)*getPageSize();
	}



	public int getTotalPage() {
		//需要根据TotalRecord()计算而来
		if(getTotalRecord()%getPageSize()==0){
			return getTotalRecord()/getPageSize();
		}else{
			return getTotalRecord()/getPageSize()+1;
		}

	}


	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		/**
		 * 对pageNumber合法性判断
		 * 如果小于1 则返回1
		 * 如果超过最大页码 则 返回最大
		 * 否则正常返回
		 * 
		 * 上述所老师所叙述不严谨，因为当你符合条件的图书，getTotalPage 就为0，知识后，由于pageNumber经过了处理为1
		 * ，那么条件2就符合了，就会出现在查询语句中
		 * SELECT id , title , author ,price ,sales , stock , img_path imgPath FROM bs_book WHERE price>=? and price<=? LIMIT ? , ? Parameters: [0.0, 10.0, -3, 3]
		 * 这样的错误，所以
		 * 要在条件1中，修改为pageNumber<=1
		 */
		if(pageNumber<=1){
			return 1;
		}else if(pageNumber>getTotalPage()){
			return getTotalPage();
		}else{
			return pageNumber;
		}
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
