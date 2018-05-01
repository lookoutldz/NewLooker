package looko.looker.release.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ExecutorForTasks {

    @Bean
    public AsyncTaskExecutor taskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Anno-Executor");
        executor.setCorePoolSize(32);
        executor.setMaxPoolSize(64);
        executor.setKeepAliveSeconds(36000);
        executor.setQueueCapacity(10000);
        executor.initialize();

        return executor;
    }
}