locust -f locustfile.py --headless -u 100000 -r 10000 --run-time 15m --host http://arch.homework/otusapp --csv product --step-load --step-users 40 --step-time 10s

locust -f locustfile.py --headless -u 1300 -r 130 --run-time 15m --host http://arch.homework/otusapp --csv product
