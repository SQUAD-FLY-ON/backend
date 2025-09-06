package com.choisong.flyon.global.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;

@Builder
public record ExampleHolder(Example holder, int code, String name) {

}
