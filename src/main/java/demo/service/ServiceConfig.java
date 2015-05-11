package demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfig {
  @Bean
  public ProcessServiceHandler processServiceHandler() {
    return new ProcessServiceHandlerImpl();
  }

  @Bean
  public TaskServiceHandler taskServiceHandler() {
    return new TaskServiceHandlerImpl();
  }

  @Bean
  public HistoryServiceHandler historyServiceHandler() {
    return new HistoryServiceHandlerImpl();
  }
}
