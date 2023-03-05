package com.radel.socialnetworkservice.api;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationResult<T> {

    private T result;
    private boolean success;
    private List<ErrorDetails> errorList;

    public OperationResult(T result) {
        this.result = result;
        this.success = true;
        this.errorList = Collections.emptyList();
    }

    public OperationResult(List<ErrorDetails> errorList) {
        this.success = false;
        this.errorList = errorList;
    }

    public <U> OperationResult<U> map(Function<? super T, ? extends U> mapper) {
        if (!this.success) {
            return new OperationResult<>(this.errorList);
        }
        return new OperationResult<>(mapper.apply(this.result));
    }
}
