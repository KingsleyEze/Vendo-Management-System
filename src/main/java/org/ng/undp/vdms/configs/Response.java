package org.ng.undp.vdms.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by macbook on 6/27/17.
 */
public class Response<T> {

    public static final String STATUES_SUCCESS = "success";
    public static final String STATUES_FAILURE = "failure";

    private String status;
    private String message;
    private T data;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Response(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String toJson() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public void send(HttpServletResponse response, int code) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        String errorMessage;

        errorMessage = toJson();

        response.getWriter().println(errorMessage);
        response.getWriter().flush();
    }
}
