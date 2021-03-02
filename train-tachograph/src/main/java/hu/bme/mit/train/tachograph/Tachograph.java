package hu.bme.mit.train.tachograph;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Calendar;

public class Tachograph {
    Table<Calendar, Integer, Integer> data = HashBasedTable.create();
    TrainController contr;
    TrainUser usr;

    public Tachograph(TrainController c, TrainUser u)
    {
       contr =c;
       usr =u;
    }

    public void record() {
        Calendar a = Calendar.getInstance();
        int b = usr.getJoystickPosition();
        int c = contr.getReferenceSpeed();

        data.put(a, b, c);
    }

    public Table<Calendar, Integer, Integer> getTable() {
        return data;
    }
}
