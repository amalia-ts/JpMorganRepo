import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Amalia on 9/20/2018.
 */
public final class Utils {

    public  static  List<Record> generateRecords(){
        List<Record> sampleRecords = new ArrayList<>();
        try {
            //generate some data
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sampleRecords.add(new Record("fooA","B",new Double("0.73"),"SGP", sdf.parse("01/01/2018"), sdf.parse("01/01/2018"), 200 ,new Double("100.25")));
            sampleRecords.add(new Record("fooA","S",new Double("0.73"),"SGP", sdf.parse("02/01/2018"), sdf.parse("02/01/2018"), 100 ,new Double("100.25")));
            sampleRecords.add(new Record("fooA","B",new Double("0.73"),"SGP", sdf.parse("03/01/2018"), sdf.parse("03/01/2018"), 200 ,new Double("100.25")));
            sampleRecords.add(new Record("fooB","B",new Double("1.17"),"EUR", sdf.parse("03/01/2018"), sdf.parse("03/01/2018"), 300 ,new Double("100.25")));
            sampleRecords.add(new Record("fooB","B",new Double("1.17"),"EUR", sdf.parse("03/01/2018"), sdf.parse("03/01/2018"), 200 ,new Double("100.25")));
            sampleRecords.add(new Record("fooB","B",new Double("0.72"),"AUD", sdf.parse("04/01/2018"), sdf.parse("04/01/2018"), 200 ,new Double("100.25")));
            sampleRecords.add(new Record("fooC","S",new Double("0.72"),"AUD", sdf.parse("04/01/2018"), sdf.parse("04/01/2018"), 400 ,new Double("100.25")));
            sampleRecords.add(new Record("fooC","B",new Double("0.72"),"AUD", sdf.parse("04/01/2018"), sdf.parse("04/01/2018"), 200 ,new Double("100.25")));
            sampleRecords.add(new Record("barA","S",new Double("0.27"),"AED", sdf.parse("05/01/2018"), sdf.parse("05/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barA","S",new Double("0.27"),"AED", sdf.parse("06/01/2018"), sdf.parse("06/01/2018"), 150 ,new Double("150.5")));
            sampleRecords.add(new Record("barA","S",new Double("0.26"),"SAR", sdf.parse("06/01/2018"), sdf.parse("06/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barB","B",new Double("0.26"),"SAR", sdf.parse("07/01/2018"), sdf.parse("07/01/2018"), 250 ,new Double("150.5")));
            sampleRecords.add(new Record("barB","B",new Double("0.26"),"SAR", sdf.parse("07/01/2018"), sdf.parse("07/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barB","S",new Double("0.27"),"AED", sdf.parse("08/01/2018"), sdf.parse("08/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barC","S",new Double("0.27"),"AED", sdf.parse("08/01/2018"), sdf.parse("08/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barC","B",new Double("0.27"),"AED", sdf.parse("09/01/2018"), sdf.parse("09/01/2018"), 650 ,new Double("150.5")));
            sampleRecords.add(new Record("barC","B",new Double("0.27"),"AED", sdf.parse("09/01/2018"), sdf.parse("09/01/2018"), 950 ,new Double("150.5")));
            sampleRecords.add(new Record("barC","S",new Double("0.27"),"AED", sdf.parse("10/01/2018"), sdf.parse("11/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barD","B",new Double("0.27"),"AED", sdf.parse("10/01/2018"), sdf.parse("11/01/2018"), 650 ,new Double("150.5")));
            sampleRecords.add(new Record("barD","B",new Double("0.27"),"AED", sdf.parse("12/01/2018"), sdf.parse("12/01/2018"), 950 ,new Double("150.5")));
            sampleRecords.add(new Record("barD","S",new Double("0.27"),"AED", sdf.parse("12/01/2018"), sdf.parse("12/01/2018"), 450 ,new Double("150.5")));
            sampleRecords.add(new Record("barD","B",new Double("0.27"),"AED", sdf.parse("13/01/2018"), sdf.parse("13/01/2018"), 650 ,new Double("150.5")));
            sampleRecords.add(new Record("barD","B",new Double("0.27"),"AED", sdf.parse("13/01/2018"), sdf.parse("14/01/2018"), 950 ,new Double("150.5")));

        } catch (ParseException e) {
            e.printStackTrace();
            sampleRecords = new ArrayList<>();
        }finally {
            return sampleRecords;
        }
    }

    public static void calculateIncomingUDSPerDay(List<Record> records){

        //Filter sell records
        Stream sellRecordsStream = records.stream().filter(x-> "S".equals(x.getOperation()));

        //Group by date and sum usd amounts
        Map<Date, DoubleSummaryStatistics> recordsPerSettlementDayMap  = (Map<Date, DoubleSummaryStatistics>) sellRecordsStream.collect(Collectors.groupingBy(Record::getFixedSettlementDate, Collectors.summarizingDouble(Record::getUSDAmount)));

        //Print results
        System.out.println("Amount in USD settled incoming everyday");
        recordsPerSettlementDayMap.forEach((k,v)->{System.out.println("Date "+ k+" - "+v.getSum()+" USD");});
    }
    public static void calculateOutgoingUSDPerDay(List<Record> records){

        //Filter buy records
        Stream buyRecordsStream = records.stream().filter(x-> "B".equals(x.getOperation()));

        //Group by date and sum usd amounts
        Map<Date, DoubleSummaryStatistics> recordsPerSettlementDayMap  = (Map<Date, DoubleSummaryStatistics>) buyRecordsStream.collect(Collectors.groupingBy(Record::getFixedSettlementDate, Collectors.summarizingDouble(Record::getUSDAmount)));

        //Print results
        System.out.println("Amount in USD settled outgoing everyday");
        recordsPerSettlementDayMap.forEach((k,v)->{System.out.println("Date "+ k+" -  "+v.getSum()+" USD");});
    }

    public static void rankEntitiesByOutgoing(List<Record> records){

        //Filter buy records
        Stream buyRecordsStream = records.stream().filter(x-> "B".equals(x.getOperation()));

        //Group by entity
        Map<String, Double> buyRecordsPerEntityMap  = (Map<String, Double>) buyRecordsStream.collect(Collectors.groupingBy(Record::getEntity, Collectors.summingDouble(Record::getUSDAmount)));

        Stream sortedRecords = buyRecordsPerEntityMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        //Print results
        System.out.println("Ranked entities based on outgoing amount");
        sortedRecords.forEach(System.out::println);
    }


    public static void rankEntitiesByIncoming(List<Record> records){

        //Filter sell records
        Stream sellRecordsStream = records.stream().filter(x-> "S".equals(x.getOperation()));

        //Group by entity
        Map<String, Double> sellRecordsPerEntityMap  = (Map<String, Double>) sellRecordsStream.collect(Collectors.groupingBy(Record::getEntity, Collectors.summingDouble(Record::getUSDAmount)));

        Stream sortedRecords = sellRecordsPerEntityMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        //Print results
        System.out.println("Ranked entities based on incoming amount");
        sortedRecords.forEach(System.out::println);
    }
}


















