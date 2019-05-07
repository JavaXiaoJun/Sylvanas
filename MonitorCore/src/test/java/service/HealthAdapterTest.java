package service;

import com.study.monitor.core.Application;
import com.study.monitor.core.adapter.MetricInfoAdapter;
import com.study.monitor.core.business.IActuatorDataCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lf52 on 2018/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HealthAdapterTest {

    @Autowired
    IActuatorDataCollection actuatorDataCollection;

    @Test
    public void testHealth(){
        try {
            System.err.println(MetricInfoAdapter.HealthAdapter(actuatorDataCollection.getHealthInfo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMetircs(){
        try {
            System.err.println(MetricInfoAdapter.MetricsAdapter(actuatorDataCollection.getMetricsInfo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
