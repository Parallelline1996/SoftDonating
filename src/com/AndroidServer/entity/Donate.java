package com.AndroidServer.entity;

import java.util.Date;

public class Donate {
    private int d_ID;
    private String u_ID;
    private String isbn;
    private int status;
    private Date donate_time;

    public int getD_ID() {
        return d_ID;
    }

    public void setD_ID(int d_ID) {
        this.d_ID = d_ID;
    }

    public String getU_ID() {
        return u_ID;
    }

    public void setU_ID(String u_ID) {
        this.u_ID = u_ID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDonate_time() {
        return donate_time;
    }

    public void setDonate_time(Date donate_time) {
        this.donate_time = donate_time;
    }
}
