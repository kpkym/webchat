package com.ou.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kpkym
 * Date: 2018-10-12 13:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Object uid;
    private String msg;
    private Date date;
}
