package com.dsfs.dsfshome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResult<T> {

    private String status;

    private String msg;

    private T data;
}
