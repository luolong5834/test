package jhh.reportCenter;

import com.jinhuhang.risk.common.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportCenterApplication {

    public static void main(String[] args) {
        new Constant().loadPorperties("risk_bus.properties");
        SpringApplication.run(ReportCenterApplication.class, args);
    }
}
