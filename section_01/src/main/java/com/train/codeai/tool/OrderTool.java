package com.train.codeai.tool;

import com.train.codeai.pojo.OrderDetails;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class OrderTool {
    // NOTE - We need to tell the ChatClient about this orderTool
    // steps are mentione din the Readme.md
    /*
    return chatClient.prompt()
        .tools(orderTool)    <<<-------- there you can rovide your tool bean
        .user(message)
        .call()
        .content();

     */

    @Tool(description = "Returns the current status of a customer order.")
    public OrderDetails getOrder(@ToolParam(description = "The customer order id, for example 12345") String orderId){

        System.out.println("******** TOOL CALLED ********");
        System.out.println(orderId);
        /// for now hardcoding the order info, we can replace it by  hittign a db with a query
        OrderDetails  orderDetails = switch (orderId) {

            case "12345" ->
                    new OrderDetails(
                            orderId,
                            "OUT_FOR_DELIVERY",
                            "Tomorrow");

            case "99999" ->
                    new OrderDetails(
                            orderId,
                            "DELIVERED",
                            "Delivered Yesterday");

            default ->
                    new OrderDetails(
                            orderId,
                            "NOT_FOUND",
                            "N/A");
        };
        //System.out.println(orderDetails.toString());
        return orderDetails;
    }

}