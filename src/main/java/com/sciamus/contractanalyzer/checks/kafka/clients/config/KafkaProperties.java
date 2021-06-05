package com.sciamus.contractanalyzer.checks.kafka.clients.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    public Consum consum;
    public Produc produc;
    public Streams streams;
    public String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setConsum(Consum consum) {
        this.consum = consum;
    }

    public void setProduc(Produc produc) {
        this.produc = produc;
    }

    public Produc getProduc() {
        return produc;
    }

    public Consum getConsum() {
        return consum;
    }

    public Streams getStreams() {
        return streams;
    }

    public void setStreams(Streams streams) {
        this.streams = streams;
    }

    public static class Streams {
        public String defaultKeySerdeClassConfig;
        public String defaultValueSerdeClassConfig;


        public void setDefaultKeySerdeClassConfig(String defaultKeySerdeClassConfig) {
            this.defaultKeySerdeClassConfig = defaultKeySerdeClassConfig;
        }

        public void setDefaultValueSerdeClassConfig(String defaultValueSerdeClassConfig) {
            this.defaultValueSerdeClassConfig = defaultValueSerdeClassConfig;
        }
    }

    public static class Consum {

        public String keyDeserializer;
        public String valueDeserializer;

        public void setKeyDeserializer(String keyDeserializer) {
            this.keyDeserializer = keyDeserializer;
        }

        public String getKeyDeserializer() {
            return keyDeserializer;
        }

        public String getValueDeserializer() {
            return valueDeserializer;
        }

        public void setValueDeserializer(String valueDeserializer) {
            this.valueDeserializer = valueDeserializer;
        }
    }

    public  static  class Produc {

        public  String keySerializer;
        public  String valueSerializer;

        public String getKeySerializer() {
            return keySerializer;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }
    }


}
