package ThirdService;

import java.util.ArrayList;

public class CommonStatistics {
    private Statistics statistics;
    private ArrayList<ServiceInfo> serviceInfoArrayList;

    CommonStatistics(Statistics statistics, ArrayList<ServiceInfo> serviceInfoArrayList)
    {
        this.statistics = statistics;
        this.serviceInfoArrayList = serviceInfoArrayList;
    }

    public Statistics getStatistics()
    {
        return statistics;
    }

    public ArrayList<ServiceInfo> getServiceInfoArrayList()
    {
        return serviceInfoArrayList;
    }
}
