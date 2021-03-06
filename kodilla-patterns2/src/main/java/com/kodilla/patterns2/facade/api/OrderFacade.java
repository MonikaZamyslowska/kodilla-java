package com.kodilla.patterns2.facade.api;

import com.kodilla.patterns2.facade.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@EnableAspectJAutoProxy
public class OrderFacade {
    @Autowired
    private ShopService shopService;
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderDto.class);

    public void processOrder(final OrderDto orderDto, final Long userId) throws  OrderProcessingException {
        boolean wasError = false;
        long orderId = shopService.openOrder(userId);
        LOGGER.info("Registering new order, ID: " + orderId);
        if (orderId < 0) {
            LOGGER.error(OrderProcessingException.ERR_NOT_AUTHORIZED);
            wasError = true;
            throw new OrderProcessingException(OrderProcessingException.ERR_NOT_AUTHORIZED);
        }
        try {
            for (ItemDto orderItem: orderDto.getItems()) {
                LOGGER.info("Adding item" + orderItem.getProductId() + ", " + orderItem.getQuantity() + " pcs");
                shopService.addItem(orderId, orderItem.getProductId(), orderItem.getQuantity());
            }
            BigDecimal value = shopService.calculateValue(orderId);
            LOGGER.info("Order value is: " + value + "USD");
            if(!shopService.doPayment(orderId)) {
                LOGGER.error(OrderProcessingException.ERR_PAYMENT_REJECTED);
                wasError = true;
                throw new OrderProcessingException(OrderProcessingException.ERR_PAYMENT_REJECTED);
            }
            LOGGER.info("Payment for order was done");
            if(!shopService.verifyOrder(orderId)) {
                LOGGER.error(OrderProcessingException.ERR_VERIFICATION);
                wasError = true;
                throw new OrderProcessingException(OrderProcessingException.ERR_VERIFICATION);
            }
            LOGGER.info("Order is ready to submit");
            if(!shopService.submitOrder(orderId)) {
                LOGGER.error(OrderProcessingException.ERR_SUBMITTING);
                wasError = true;
                throw new OrderProcessingException(OrderProcessingException.ERR_SUBMITTING);
            }
            LOGGER.info("Order" + orderId + "submitted");
        } finally {
            if(wasError) {
                LOGGER.info("Canceling order " + orderId);
                shopService.cancelOrder(orderId);
            }
        }
    }
}
