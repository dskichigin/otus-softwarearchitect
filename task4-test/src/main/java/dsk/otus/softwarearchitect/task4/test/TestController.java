package dsk.otus.softwarearchitect.task4.test;

import dsk.otus.softwarearchitect.task4.test.timertask.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Component
public class TestController {
    private static String TEST_URI = "TEST_URI";
    private static String TEST_RPS = "TEST_RPS";
    private static String TEST_THREAD = "TEST_THREAD";
    private static String TEST_THREAD_DELAY = "TEST_THREAD_DELAY";

//    private String uri = "";
//    private String uri = "127.0.0.1:8000";
    private String uri = "arch.homework/otusapp";
    private int period = 1000;
    private int rps = 10;
    private int thread = 1;
    private int thread_delay = 10;

    private TestEnvironment testEnvironment = new TestEnvironment();

    private List<Timer> timerCreateUser = new ArrayList();
    private List<Timer> timerGetUser = new ArrayList();
    private List<Timer> timerGetUsers = new ArrayList();
    private List<Timer> timerUpdateUser = new ArrayList();
    private List<Timer> timerDeleteUser = new ArrayList();

    public void start() {
        if (System.getenv(TEST_URI) != null)
            uri = System.getenv(TEST_URI);
        if (System.getenv(TEST_RPS) != null)
            rps = Integer.parseInt(System.getenv(TEST_RPS));
        if (System.getenv(TEST_THREAD) != null)
            thread = Integer.parseInt(System.getenv(TEST_THREAD));
        if (System.getenv(TEST_THREAD_DELAY) != null)
            thread_delay = Integer.parseInt(System.getenv(TEST_THREAD_DELAY));

        startTimers();
    }

    private void startTimers() {
        if (!uri.equals("")) {
            // create all timers for createUser
            for (int i = 0; i < thread; i++) {
                Timer timer = new Timer();
                timer.schedule(new TimerTaskCreateUser(testEnvironment, uri, rps), i * thread_delay, period);
            }
            // create all timers for getUser
            for (int i = 0; i < thread; i++) {
                Timer timer = new Timer();
                timer.schedule(new TimerTaskGetUser(testEnvironment, uri, rps), i * thread_delay, period);
            }
            // create all timers for updateUser
            for (int i = 0; i < thread; i++) {
                Timer timer = new Timer();
                timer.schedule(new TimerTaskUpdateUser(testEnvironment, uri, rps), i * thread_delay, period);
            }
            // create all timers for deleteUser
            for (int i = 0; i < thread; i++) {
                Timer timer = new Timer();
                timer.schedule(new TimerTaskDeleteUser(testEnvironment, uri, rps), i * thread_delay, period);
            }
            // create all timers for getUsers
            for (int i = 0; i < thread; i++) {
                Timer timer = new Timer();
                timer.schedule(new TimerTaskGetUsers(testEnvironment, uri, rps), i * thread_delay, period);
            }
        }
    }
}
