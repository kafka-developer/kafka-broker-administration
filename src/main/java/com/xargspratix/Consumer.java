//##
//Author: xargs-pratix:Prateek Shukla
//##

package com.xargspratix;
import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Arrays;
public class Consumer {
    public static void main(String[] args) throws IOException {
        KafkaConsumer<String, String> consumer;
        try (InputStream props = Resources.getResource("consumer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            if (properties.getProperty("group.id") == null) {
                properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
            }
            consumer = new KafkaConsumer<String, String>(properties);
        }

        consumer.subscribe(Arrays.asList("test-topic"));
        int timeouts = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(200);
            if (records.count() == 0) {
                timeouts++;
            } else {
                System.out.printf("Got %d records after %d timeouts\n", records.count(), timeouts);
            }

            for (ConsumerRecord record: records) {
                switch (record.topic()) {
                    case "test-topic":
                        System.out.println(record.toString());
                }
            }
        }
    }
}
