package th.co.azay.application.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum ScbTransactionStatus {

    PENDING(0,"PENDING"),
    PAID(1,"PAID"),
    CANCELLED(2,"CANCELLED"),
    INVALID(3,"INVALID"),
    PARTIAL(4,"PARTIAL"),
    EXPIRED(5,"EXPIRED");

    @Getter
    Integer code;
    @Getter
    String value;

    private static Map<Integer, ScbTransactionStatus> valueToTextMapping;

    public static ScbTransactionStatus getScbTransactionStatus(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(ScbTransactionStatus s : values()){
            valueToTextMapping.put(s.getCode(), s);
        }
    }
}
