package dsk.otus.softwarearchitect.task4.test.timertask;

import dsk.otus.softwarearchitect.task4.test.Task4AdapterRest;
import dsk.otus.softwarearchitect.task4.test.TestEnvironment;
import dsk.otus.softwarearchitect.task4.test.entity.UserEntity;

import java.util.TimerTask;

public class TimerTaskCreateUser extends TimerTask {
    private TestEnvironment testEnvironment;
    private Task4AdapterRest adapterRest = new Task4AdapterRest();
    private String uri = "";
    private int rps = 1;

    public TimerTaskCreateUser(TestEnvironment testEnvironment, String uri, int rps) {
        this.testEnvironment = testEnvironment;
        this.rps = rps;
        this.uri = uri;
    }

    @Override
    public void run() {
        if (!uri.equals("")) {
            for (int i = 0; i < rps; i++) {
                UserEntity user = new UserEntity();
                user.setUsername("username");
                user.setFirstname("firstname");
                user.setLastname("lastname");
                user.setEmail("email");
                user.setPhone("phone");
                try {
                    UserEntity res = adapterRest.createUser(uri, user);
                    testEnvironment.addCreateUser(res.getId());
                } catch (Exception e) {
                    System.out.println("Не удалось создать пользователя "+uri);
                }
            }
        }
    }
}
