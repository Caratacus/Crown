package org.crown.service;

import org.crown.CrownApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * TODO class
 * </p>
 *
 * @author Caratacus
 * @date 2018/12/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrownApplication.class)
@Transactional
@Rollback
public class IResourceServiceTest {

    @Autowired
    private IResourceService resourceService;

    @Test
    public void getUserPerms() {
        resourceService.getUserPerms(1);
    }

    @Test
    public void getOpenPerms() {
        resourceService.getOpenPerms();
    }

    @Test
    public void getLoginPerms() {
        resourceService.getLoginPerms();
    }

    @Test
    public void getPerms() {
        resourceService.getPerms();
    }

    @Test
    public void getUserResourcePerms() {
        resourceService.getUserResourcePerms(1);
    }

}