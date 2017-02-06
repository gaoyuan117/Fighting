package service.api;


public class CommonReturn extends BaseEntity {
    private String card_no;
    private String true_name;

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
}
