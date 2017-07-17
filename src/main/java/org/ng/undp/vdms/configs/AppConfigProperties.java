package org.ng.undp.vdms.configs;

/**
 * Created by macbook on 6/16/17.
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by emmanuel on 1/17/17.
 */
@Data
@Configuration
@PropertySource(value="classpath:application.properties")
public class AppConfigProperties {

    @Value("#{'${spring.profiles.active}'}")
    private String activeProfile;

    @Value("${aws_namecard_bucket}")
    private String awsBucketName;

}
