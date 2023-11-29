package org.ddd.shared.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class ErrorDto {
    private final String code;
    private final String message;
}
