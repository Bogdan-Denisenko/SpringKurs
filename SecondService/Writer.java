package SecondService;

import FirstService.ScheduleGenerator;
import FirstService.Ship;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ThirdService.CommonStatistics;
import ThirdService.ServiceInfo;
import ThirdService.Statistics;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
@RestController
@RequestMapping("/writer")
public class Writer
{
    @GetMapping("/table")
    public static String writeTable()
    {
        Gson gsonOne = new Gson();
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        String schedule = restTemplate.getForObject("http://localhost:8001/generate/table", String.class);
        JsonArray object = JsonParser.parseString(schedule).getAsJsonArray();
        Type SHIP_TYPE = new TypeToken<ArrayList<Ship>>() {}.getType();
        ArrayList<Ship> parsedJson = gsonOne.fromJson(object, SHIP_TYPE);
        ScheduleGenerator generator = new ScheduleGenerator();
        generator.ships = parsedJson;


        Scanner in = new Scanner(System.in);
        while (true)
        {
            System.out.println("Хотите ли вы добавить еще один корабль в расписание?(д/н)");
            String consent = in.nextLine();
            while(!consent.equals("д") && !consent.equals("н"))
            {
                System.out.println("Хотите ли вы добавить еще один корабль в расписание?(д/н)");
                consent = in.nextLine();
            }
            if (consent.equals("д"))
            {
                try
                {
                    generator.addShipFromConsole();
                } catch (IllegalArgumentException exception){
                    System.out.println(exception.getMessage());
                }
            }
            else
            {
                break;
            }
        }
        try {
            JsonWriter  writer = new JsonWriter(new FileWriter(System.getProperty("user.dir") + "/table.json"));
            Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss").create();
            gson.toJson(generator.ships, SHIP_TYPE, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    @GetMapping("/tableTwo")
    public ArrayList<Ship> shipsTableTwo(@RequestParam("name") String name) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + name);
        if (!file.exists()) {
            throw new IllegalArgumentException("File with this name is not exist!!!");
        }
        JsonReader reader = new JsonReader(new FileReader(System.getProperty("user.dir") + "/" + name));
        Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss").create();
        Type SHIP_TYPE = new TypeToken<ArrayList<Ship>>() {}.getType();
        ArrayList<Ship> ships = gson.fromJson(reader, SHIP_TYPE);
        reader.close();
        return ships;
    }

    @PostMapping("/post-result")
    public static void writeResult(@RequestBody CommonStatistics commonStatistics)
    {
        Statistics statistics = commonStatistics.getStatistics();
        ArrayList<ServiceInfo> serviceInfo = commonStatistics.getServiceInfoArrayList();
        try {
            JsonWriter  writer = new JsonWriter(new FileWriter(System.getProperty("user.dir") + "/statistics.json"));
            writer.beginObject();
            writer.name("statistics");
            writer.beginObject();
            writer.name("unloaded ships count").value(statistics.unloadedShipsCount);
            writer.name("average queue length").value(statistics.averageQueueLength);
            writer.name("average waiting time").value(String.valueOf(statistics.averageWaitingTime));
            writer.name("average delay").value(String.valueOf(statistics.averageDelay));
            writer.name("max delay").value(String.valueOf(statistics.maxDelay));
            writer.name("final fine").value(statistics.finalFine);
            writer.name("liquid crane count").value(statistics.liquidCraneCount);
            writer.name("loose crane count").value(statistics.looseCraneCount);
            writer.name("container crane count").value(statistics.containerCraneCount);
            writer.endObject();
            writer.name("Ships");
            writer.beginArray();
            for (int i = 0; i < serviceInfo.size(); i++)
            {
                writer.beginObject();
                writer.name("name").value(serviceInfo.get(i).name);
                writer.name("coming time").value(String.valueOf(serviceInfo.get(i).comingTime));
                writer.name("waiting time").value(String.valueOf(serviceInfo.get(i).waitingTime));
                writer.name("beginning unloading time").value(String.valueOf(serviceInfo.get(i).beginningUnloadingTime));
                writer.name("unloading time").value(String.valueOf(serviceInfo.get(i).unloadingTime));
                writer.endObject();
            }
            writer.endArray();
            writer.endObject();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
