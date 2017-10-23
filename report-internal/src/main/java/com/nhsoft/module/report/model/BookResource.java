package com.nhsoft.module.report.model;



/**
 * BookResource entity. @author MyEclipse Persistence Tools
 */

public class BookResource  implements java.io.Serializable {

	private static final long serialVersionUID = 2215892114655491730L;
	private BookResourceId id;
    private String bookResourceParam;

    public BookResourceId getId() {
        return this.id;
    }
    
    public void setId(BookResourceId id) {
        this.id = id;
    }

    public String getBookResourceParam() {
        return this.bookResourceParam;
    }
    
    public void setBookResourceParam(String bookResourceParam) {
        this.bookResourceParam = bookResourceParam;
    }
   








}