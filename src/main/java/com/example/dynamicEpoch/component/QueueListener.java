package com.example.dynamicEpoch.component;

import com.example.dynamicEpoch.DTO.DynamicEpochValueMessage;
import com.example.dynamicEpoch.services.DynamicEpochInputService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@Slf4j
public class QueueListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DynamicEpochInputService dynamicEpochInputService;

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String fileBody) {
        try {
            DynamicEpochValueMessage out = objectMapper.readValue(fileBody, DynamicEpochValueMessage.class);
            dynamicEpochInputService.inputDataDynamicEpoch(out);
        } catch (JsonProcessingException e) {
            // this exception in a real word application should send to a retry/DLQ topic to reprocess
            // or send to the producer to know that it's wrong
            e.printStackTrace();
        }
    }
}
