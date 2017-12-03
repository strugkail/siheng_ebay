package com.oigbuy.jeesite.common.rabbitmq.receiver;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.mapper.JsonMapper;
import com.oigbuy.jeesite.modules.ebay.product.entity.MmsParam;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductMmsService;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * mms接口  开发sku消息接口
 * @author tony.liu
 *
 */
public class MmsSkuConsumer implements ChannelAwareMessageListener{

	private static final Logger logger = LoggerFactory.getLogger(MmsSkuConsumer.class);
	
	@Autowired
	private ProductMmsService productMessureService;
	
	@Resource(name="amqpTemplate")  
	private AmqpTemplate amqpTemplate;  
	
	@Override
	public void onMessage(Message msg, Channel channel) throws Exception {
		byte[] body = msg.getBody();
		try{
			MmsParam mmsParam = JSONObject.parseObject(new String(body), MmsParam.class);
			if(mmsParam != null){
				logger.info("mms子代码开发成功调用的接口参数："+new String(body));
				productMessureService.getMmsSku(mmsParam);
			}
		}catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			//basicNACK(msg,channel);
			//amqpTemplate.convertAndSend(Global.RABBIT_MMS_ERROR, msg); 
			channel.basicPublish(Global.RABBIT_MMS_EXCHANGE, Global.RABBIT_MMS_ERROR_ROUTINGKEY,MessageProperties.PERSISTENT_BASIC, body);
		}finally{
			basicACK(msg,channel);
		}
		basicNACK(msg,channel);
	}
	
    private void basicACK(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            logger.error("手动进行提交queue:" + e);
        }
    }

    private void basicNACK(Message message, Channel channel) {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
        } catch (IOException e) {
            logger.error("出现错误，进行queue回滚:" + e);
        }
    }

}
