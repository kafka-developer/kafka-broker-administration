//##
//Author: xargs-pratix:Prateek Shukla
//##

package com.xargspratix;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducer {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.158:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100; i++) {
            String key = "Key" + i;
            String message = "Enjoy Kafka-Broker-Administration" + i;
            /* Asynchronously send a record to a topic and returns RecordMetadata */
            Future<RecordMetadata> out = producer.send(new ProducerRecord<String,
                    String>(topicName, key, message));
            String messageOut = " Topic: "+ out.get().topic() + " "+ " Partition: "+ out.get().partition() +
                    " "+ " Offset: "+out.get().offset() +  " Message: "+message;
            System.out.println(messageOut);
        }
        producer.close();
        System.out.println("Message sent successfully");

    }
}