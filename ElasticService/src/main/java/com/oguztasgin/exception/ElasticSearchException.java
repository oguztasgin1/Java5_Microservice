package com.oguztasgin.exception;

import lombok.Getter;

@Getter
public class ElasticSearchException extends  RuntimeException{

    private final EErrorType errorType;

    public ElasticSearchException(EErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public ElasticSearchException(EErrorType errorType , String message){
        super(message);
        this.errorType=errorType;

    }

}
