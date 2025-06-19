package com.example.bit.constant;

import java.util.Arrays;
import java.util.Optional;

public class CoinDeskConst {

    public enum CurrType{
        USD("USD","美金"),
        GBP("GBP","英鎊"),
        EUR("EUR","歐元");

        private final String curr;
        private final String name;

        CurrType(String curr, String name) {
            this.curr = curr;
            this.name=name;
        }

        public String getCurr() {
            return curr;
        }

        public String getName() {
            return name;
        }

        public static String findCurrToName(String currencyCode) {
            Optional<CurrType> type = Arrays.stream(CurrType.values())
                    .filter(currType -> currType.getCurr().equals(currencyCode))
                    .findFirst();

            if(type.isPresent()){
                return type.get().getName();
            } else {
                return "";
            }
        }
    }
}
