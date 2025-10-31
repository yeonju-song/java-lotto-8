package lotto;

public enum Rank {
    FIRST("6개 일치 (2,000,000,000원)", 2_000_000_000L),
    SECOND("5개 일치, 보너스 볼 일치 (30,000,000원)", 30_000_000),
    THIRD("5개 일치 (1,500,000원)", 1_500_000),
    FOURTH("4개 일치 (50,000원)", 50_000),
    FIFTH("3개 일치 (5,000원)", 5_000);

    private final String name;
    private final long value;
    Rank(String name, long value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public long getValue() {
        return value;
    }
}
