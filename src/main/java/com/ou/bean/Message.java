package com.ou.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author kpkym
 * Date: 2018-10-12 13:48
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    private String id;
    private @NonNull String nickName;
    private @NonNull String msg;
    private @NonNull Date date;
}
