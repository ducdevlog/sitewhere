package com.sitewhere.certificate.client;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//@FeignClient(url = "http://171.244.38.134:9030", name = "FileClient")
@FeignClient(url = "http://171.244.38.134:9030", name = "FileClient", configuration = FileClient.MultipartSupportConfig.class)
public interface FileClient {
    @RequestMapping(method = RequestMethod.POST, value = "/{bucketName}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@Headers("Content-Type: multipart/form-data")
    ResponseEntity<?> uploadFile(@PathVariable(value = "bucketName") String bucketName, @RequestParam("file") MultipartFile uploadFile);

    @Configuration
    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
}
