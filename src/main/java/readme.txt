How to build the project:
gradle build

How to run the project:
The application consists of two parts and which must be run separately as two independent JVM processes:

Input processor:
java -classpath <path to payment.tracker.jar> cz.klimesova.payment.tracker.Input <path to payment.txt>

Output processor:
java -classpath <path to payment.tracker.jar> cz.klimesova.payment.tracker.Output <path to payment.txt>

Note that <path to payment.txt file> must be same for both parts.