import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Logger {

    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ssZZ")
            .withZone(ZoneId.systemDefault());
    
    private static PriorityBlockingQueue<LogItem> itemsQueue = new PriorityBlockingQueue<>();
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    static {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        LogItem nextItem = itemsQueue.take();
                        System.out.println(nextItem.toString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        });
    }
    
    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public enum LogLevel {
        INFO, WARN, ERROR
    }

    public Logger(String name) {
        this.loggerName = name;
    }
    
    private String loggerName;
    
    public void info(String message, Object... parameters) {
        writeLog(LogLevel.INFO, message, parameters);
    }
    
    public void warn(String message, Object... parameters) {
        writeLog(LogLevel.WARN, message, parameters);
    }
    
    public void error(String message, Object... parameters) {
        writeLog(LogLevel.ERROR, message, parameters);
    }
    
    private void writeLog(LogLevel level, String message, Object... parameters) {
        String interpolatedMessage = String.format(message, parameters);
        LogItem item = new LogItem(loggerName, level, interpolatedMessage);
        itemsQueue.add(item);
    }
    
    private class LogItem implements Comparable<LogItem> {
        final Instant timestamp;
        final String loggerName;
        final LogLevel level;
        final String interpolatedMessage;
        
        public LogItem(String loggerName, LogLevel level, String interpolatedMessage) {
            this.timestamp = Instant.now();
            this.loggerName = loggerName;
            this.level = level;
            this.interpolatedMessage = interpolatedMessage;
        }
        
        public String toString() {
            String timeString = formatter.format(this.timestamp);
            return String.format("%s %s [%s]: %s", timeString, this.loggerName, this.level.name(), this.interpolatedMessage);
        }

        public int compareTo(LogItem o) {
            return this.timestamp.compareTo(o.timestamp);
        }
        
    }
}
