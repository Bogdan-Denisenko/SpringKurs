package ThirdService;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class SpringAppThree {
    public static void main(String[] args) throws IOException {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        String schedule = restTemplate.getForObject("http://localhost:8002/writer/table", String.class);
        Port port = new Port();
        port.calculateOptimalNumberOfCranes(schedule);
        CommonStatistics commonStatistics = new CommonStatistics(port.statistics, port.serviceInfoArrayList);
        restTemplate.postForObject("http://localhost:8002/writer/post-result", commonStatistics, CommonStatistics.class);
    }
}
