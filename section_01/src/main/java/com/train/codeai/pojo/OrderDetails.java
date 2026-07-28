package com.train.codeai.pojo;

public record OrderDetails (String orderId, String status, String eta){

    @Override
    public String toString(){
        return "orderId="+orderId+", status="+status+", eta="+eta;
    }
}
