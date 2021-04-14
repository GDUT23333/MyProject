package com.lce.merchantmini.domain.dto.wallet;

/**
 * @Author: Ember
 * @Date: 2021/4/9 11:54
 * @Description: 钱包查询选择
 */
public enum WalletChooseEnums {
    /**
     * 查询类型
     * 4:全部
     * 3:只查询提现
     * 2:只查询充值
     * 1:只查询退票
     * 0:只查询售票
     */
    FIND_ALL("findAll","4"),
    ONLY_DRAW_CASH("draw","3"),
    ONLY_CHARGE("charge","2"),
    ONLY_REFUND("refund","1"),
    ONLY_SALE("sale","0"),

    /**
     * ticketStatus
     * 3:退票和售票
     * 2:退票
     * 1:售票
     */
    TICKET_ALL("ticketAll","3"),
    TICKET_REFUND("ticketRefund","2"),
    TICKET_SALE("ticketSale","3"),

    /**
     * status
     * 2:提现和充值
     * 1:充值
     * 0:提现
     */
    MONEY_ALL("moneyAll","2"),
    MONEY_CHARGE("charge","1"),
    MONEY_DRAW_CASH("drawCash","0")
    ;
    WalletChooseEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
