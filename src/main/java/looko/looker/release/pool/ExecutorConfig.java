package looko.looker.release.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class ExecutorConfig {
    @Bean
    public Executor executor(){
        return new ThreadPoolExecutor(128, 256, 1, TimeUnit.DAYS, new ArrayBlockingQueue<>(8192));
    }
}
