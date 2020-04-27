package dsk.otus.softwarearchitect.task4.test.timertask;

import dsk.otus.softwarearchitect.task4.test.Task4AdapterRest;
import dsk.otus.softwarearchitect.task4.test.TestEnvironment;
import dsk.otus.softwarearchitect.task4.test.entity.UserEntity;

import java.util.TimerTask;

public class TimerTaskDeleteUser extends TimerTask {
    private TestEnvironment testEnvironment;
    private Task4AdapterRest adapterRest = new Task4AdapterRest();
    private String uri = "";
    private int rps = 1;

    public TimerTaskDeleteUser(TestEnvironment testEnvironment, String uri, int rps) {
        this.testEnvironment = testEnvironment;
        this.rps = rps;
        this.uri = uri;
    }

    @Override
    public void run() {
        if (!uri.equals("")) {
            for (int i = 0; i < rps; i++) {
                UserEntity user = testEnvironment.getUpdateUser();
                if (user != null) {
                    try {
                        adapterRest.deleteUser(uri, user.getId());
                    } catch (Exception e) {
//                    System.out.println("Не удалось создать пользователя");
                    }
                }
            }
        }

    }
}
