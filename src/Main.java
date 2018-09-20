import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Report");
        //Initialize data
        List<Record> records = Utils.generateRecords();
        Utils.calculateIncomingUDSPerDay(records);
        Utils.calculateOutgoingUSDPerDay(records);
        Utils.rankEntitiesByOutgoing(records);
        Utils.rankEntitiesByIncoming(records);
    }
}
