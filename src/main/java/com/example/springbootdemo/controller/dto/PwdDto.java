package com.example.springbootdemo.controller.dto;

import lombok.Data;

@Data
public class PwdDto {
   private String oldPwd;
   private String newPwd;
}
