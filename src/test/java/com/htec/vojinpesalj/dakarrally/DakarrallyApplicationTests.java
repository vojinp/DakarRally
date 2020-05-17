package com.htec.vojinpesalj.dakarrally;

import com.htec.vojinpesalj.dakarrally.integration.AuthControllerTests;
import com.htec.vojinpesalj.dakarrally.integration.RaceControllerTests;
import com.htec.vojinpesalj.dakarrally.integration.VehicleControllerTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Suite.class)
@SuiteClasses({RaceControllerTests.class, VehicleControllerTests.class, AuthControllerTests.class})
@ActiveProfiles("test")
public class DakarrallyApplicationTests {}
