package lotto;

public enum Rank {
    FIRST("6개 일치 (2,000,000,000원)", 2_000_000_000L, 5),
    SECOND("5개 일치, 보너스 볼 일치 (30,000,000원)", 30_000_000, 4),
    THIRD("5개 일치 (1,500,000원)", 1_500_000, 3),
    FOURTH("4개 일치 (50,000원)", 50_000, 2),
    FIFTH("3개 일치 (5,000원)", 5_000, 1);

    private final String name;
    private final long value;
    private final int order;

    Rank(String name, long value, int order) {
        this.name = name;
        this.value = value;
        this.order = order;
    }
    public String getName() {
        return name;
    }
    public long getValue() {
        return value;
    }
    public int getOrder() {
        return order;
    }
}
