package com.softdonating.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Take {
    private int t_ID;
    private String u_ID;
    private String d_ID;
    private String isbn;
    private Date donate_time;
    private Date take_time;

    public int getT_ID() {
        return t_ID;
    }

    public void setT_ID(int t_ID) {
        this.t_ID = t_ID;
    }

    public String getU_ID() {
        return u_ID;
    }

    public void setU_ID(String u_ID) {
        this.u_ID = u_ID;
    }

    public String getD_ID() {
        return d_ID;
    }

    public void setD_ID(String d_ID) {
        this.d_ID = d_ID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDonate_time() {
        return donate_time;
    }

    public void setDonate_time(Date donate_time) {
        this.donate_time = donate_time;
    }

    public Date getTake_time() {
        return take_time;
    }

    public void setTake_time(Date take_time) {
        this.take_time = take_time;
    }
}
