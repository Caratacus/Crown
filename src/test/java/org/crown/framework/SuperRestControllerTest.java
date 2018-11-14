package org.crown.framework;

import org.crown.CrownApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * SuperRestController 测试类
 * </p>
 *
 * @author Caratacus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrownApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@Transactional
@Rollback
public class SuperRestControllerTest {

    /**
     * 获取MockMvc
     *
     * @param controllers
     * @return
     */
    public MockMvc getMockMvc(Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers).build();
    }

    @Test
    public void test() {
    }


}