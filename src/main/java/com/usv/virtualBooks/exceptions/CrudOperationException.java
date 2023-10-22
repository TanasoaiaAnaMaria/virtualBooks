package com.usv.virtualBooks.exceptions;

public class CrudOperationException extends RuntimeException{
    public CrudOperationException(String mesaj){
        super(mesaj);
    }
}
