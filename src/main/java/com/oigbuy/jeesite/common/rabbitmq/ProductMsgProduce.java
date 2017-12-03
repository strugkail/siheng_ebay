package com.oigbuy.jeesite.common.rabbitmq;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ProductMsgProduce {
	
    private Logger logger = LoggerFactory.getLogger(ProductMsgProduce.class);  

    @Resource(name="amqpTemplate")  
    private AmqpTemplate amqpTemplate;  

	public void sendMessage(ProductQueue message) throws IOException {
        logger.info("to send message:{}", message);  
        amqpTemplate.convertAndSend("exchangeTest",Global.RABBIT_BASE_PRODUCT_ROUTINGKEY, message);  
    }
	
}
