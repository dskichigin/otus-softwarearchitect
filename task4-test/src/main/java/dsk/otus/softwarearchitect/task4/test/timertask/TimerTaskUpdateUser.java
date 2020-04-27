package dsk.otus.softwarearchitect.task4.test.timertask;

import dsk.otus.softwarearchitect.task4.test.Task4AdapterRest;
import dsk.otus.softwarearchitect.task4.test.TestEnvironment;
import dsk.otus.softwarearchitect.task4.test.entity.UserEntity;

import java.util.TimerTask;

public class TimerTaskUpdateUser extends TimerTask {
    private TestEnvironment testEnvironment;
    private Task4AdapterRest adapterRest = new Task4AdapterRest();
    private String uri = "";
    private int rps = 1;

    public TimerTaskUpdateUser(TestEnvironment testEnvironment, String uri, int rps) {
        this.testEnvironment = testEnvironment;
        this.rps = rps;
        this.uri = uri;
    }

    @Override
    public void run() {
        if (!uri.equals("")) {
            for (int i = 0; i < rps; i++) {
                UserEntity user = testEnvironment.getReadUser();
                if (user != null) {
                    try {
                        user.setUsername("username");
                        user.setFirstname("firstname");
                        user.setLastname("lastname");
                        user.setEmail("email");
                        user.setPhone("phone");

                        user.setUsername(user.getUsername() + " update");
                        user.setFirstname(user.getFirstname() + " update");
                        user.setLastname(user.getLastname() + " update");
                        user.setEmail(user.getEmail() + " update");
                        user.setPhone(user.getPhone() + " update");
                        UserEntity res = adapterRest.updateUser(uri, user);
                        testEnvironment.addUpdateUser(res);
                    } catch (Exception e) {
//                    System.out.println("Не удалось создать пользователя");
                    }
                }
            }
        }
    }
}
