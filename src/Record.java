import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amalia on 9/20/2018.
 */
public class Record {
    private  String entity;
    //Buy or sell
    private  String operation;
    private  Double agreedFx;
    private  String currency;
    private  Date instructionDate;
    private  Date settlementDate;
    private  Integer units;
    private  Double pricePerUnit;
    private  Double USDAmount;
    private  Date fixedSettlementDate;

    public Record(String entity, String operation, Double agreedFx, String currency, Date instructionDate, Date settlementDate, Integer units, Double pricePerUnit) {
        this.entity = entity;
        this.operation = operation;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.USDAmount = calculateUSDAmount();
        //set settlement day at next working day
        this.fixedSettlementDate = fixSettlementDate();
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(Double agreedFx) {
        this.agreedFx = agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getUSDAmount(){
        return this.USDAmount;
    }

    public Double calculateUSDAmount(){
        Double amount = this.pricePerUnit*this.units*this.agreedFx;
        return  BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    public Date getFixedSettlementDate() {
        return fixedSettlementDate;
    }

    public Date fixSettlementDate (){

        Calendar c = Calendar.getInstance();
        c.setTime(this.settlementDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //if Day is friday and currency is AED or SAR set date to next Sunday
        if(dayOfWeek==6){
            if(getCurrency().equals("AED")||getCurrency().equals("SAR")){
                c.add(Calendar.DAY_OF_MONTH, 2);
            }
        }else if(dayOfWeek==7){
            //if Day is Saturday and currency is AED or SAR set date to next Sunday
            if(getCurrency().equals("AED")||getCurrency().equals("SAR")){
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
            //else set date to next Monday
            else{
                c.add(Calendar.DAY_OF_MONTH, 2);
            }

        }else if(dayOfWeek==1){
            //if Day is Sunday and currency is not AED and not SAR set next date to next Monday
            if(!getCurrency().equals("AED")&& !getCurrency().equals("SAR")){
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return c.getTime();
    }
}
