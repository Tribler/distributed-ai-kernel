import static org.junit.jupiter.api.Assertions.*;


class MachineTest {

    Machine machine1;
    Machine machine2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        machine1 = new Machine(1,1);
        machine2 = new Machine(3,4);
    }


    @org.junit.jupiter.api.Test
    void updateMU() {
        machine2.updateMU(machine1);
        //should assert that the distance from the new line to the point is lower I guess ?
    }

    @org.junit.jupiter.api.Test
    void updateUM() {
    }


    @org.junit.jupiter.api.Test
    void getIntercept() {
        assertEquals(1, machine1.getIntercept(), "we have set the original line to have intercept of 1");
    }

    @org.junit.jupiter.api.Test
    void getBias() {
        assertEquals(0, machine1.getBias(), "and bias of 0");
    }
}