package demo.config;

import demo.service.HistoryServiceHandler;
import demo.service.HistoryServiceHandlerImpl;
import demo.service.ProcessServiceHandler;
import demo.service.ProcessServiceHandlerImpl;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CoreConfig {
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
