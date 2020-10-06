package StateList;

import java.util.HashMap;
import java.util.Map;

public class StateList {
    public enum StateAbbrevEnum {
        AK("Alaska"),
        AL("Alabama"),
        AR("Arkansas"),
        AZ("Arizona"),
        CA("California"),
        CO("Colorado"),
        CT("Connecticut"),
        MI("Michigan"),
        OH("Ohio");

        private final String abbr;

        private static final Map<String, StateAbbrevEnum> lookup = new HashMap<String, StateAbbrevEnum>();

        static {
            for (StateAbbrevEnum sn : StateAbbrevEnum.values()) {
                lookup.put(sn.getAbbr(), sn);
            }
        }

        private StateAbbrevEnum(String abbr) {
            this.abbr = abbr;
        }

        public String getAbbr() {
            return  abbr;
        }

        public static StateAbbrevEnum getStateName (String stateName) {
            return lookup.get(stateName);
        }
    }
}
