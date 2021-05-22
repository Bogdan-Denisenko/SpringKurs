package FirstService;
import ThirdService.Port;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ship
{
    public enum cargoType
    {
        LOOSE,
        LIQUID,
        CONTAINER
    }
    private final String name;
    private final Calendar date;
    private int weight;
    private final cargoType cargo;
    private int plannedTime;
    Ship(String name, Calendar date, int weight, cargoType cargo)
    {
        this.name = name;
        this.date = new GregorianCalendar();
        this.date.setTimeInMillis(date.getTimeInMillis());
        this.weight = weight;
        this.cargo = cargo;
        switch(cargo){
            case LOOSE:
                plannedTime = (int) Math.ceil(weight / Port.looseCraneSpeed);
                break;
            case LIQUID:
                plannedTime = (int) Math.ceil(weight / Port.liquidCraneSpeed);
                break;
            case CONTAINER:
                plannedTime = (int) Math.ceil(weight / Port.containerCraneSpeed);
                break;
        }
    }
    public Ship(Ship ship)
    {
        this.date = new GregorianCalendar();
        this.name = ship.name;
        this.date.setTimeInMillis(ship.date.getTimeInMillis());
        this.weight = ship.weight;
        this.cargo = ship.cargo;
        this.plannedTime = ship.plannedTime;
    }
    public String getName()
    {
        return name;
    }

    public Calendar getDate()
    {
        return date;
    }

    public int getWeight()
    {
        return weight;
    }

    public cargoType getCargoType()
    {
        return cargo;
    }

    public int getPlannedTime()
    {
        return plannedTime;
    }

    public void setDate(long time)
    {
        date.setTimeInMillis(time);
    }

    public void setPlannedTime(int plannedTime) // in minutes
    {
        this.plannedTime = plannedTime;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
