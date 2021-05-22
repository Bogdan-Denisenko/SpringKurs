package ThirdService;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
    public static Date addDays(Calendar date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
