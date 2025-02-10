package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

  private String code;
  private String title;
  private String detail;
}
