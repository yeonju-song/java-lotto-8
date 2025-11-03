package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 로또_번호_정렬_및_검증_테스트() {
        List<Integer> winningNumbers = List.of(6,2,3,4,5,1);
        Lotto lotto = new Lotto(winningNumbers);

        assertThat(lotto.getNumbers()).containsExactly(1,2,3,4,5,6);
    }

    @Test
    void 당첨_결과_계산_테스트() {
        List<Lotto> tickets = List.of(
                new Lotto(List.of(1,2,3,4,5,6)), //1등
                new Lotto(List.of(1,2,3,4,5,15)), //2등 (5개 + 보너스)
                new Lotto(List.of(1,2,3,4,5,16)), //3등
                new Lotto(List.of(1,2,3,4,7,8)), //4등
                new Lotto(List.of(1,2,3,20,25,26)) //5등
        );

        List<Integer> winningNumbers = List.of(1,2,3,4,5,6);
        int bonusNumber = 15;

        Map<Rank, Integer> results = Application.calculateResults(tickets, winningNumbers, bonusNumber);

        assertThat(results.get(Rank.FIRST)).isEqualTo(1);
        assertThat(results.get(Rank.SECOND)).isEqualTo(1);
        assertThat(results.get(Rank.THIRD)).isEqualTo(1);
        assertThat(results.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(results.get(Rank.FIFTH)).isEqualTo(1);
    }

    @Test
    void 수익률_계산_테스트() {
        Map<Rank, Integer> results = new HashMap<>();
        results.put(Rank.FIRST, 0);
        results.put(Rank.SECOND, 0);
        results.put(Rank.THIRD, 0);
        results.put(Rank.FOURTH, 0);
        results.put(Rank.FIFTH, 1);

        int money = 8000;

        long totalProfit = 0;
        for (Rank rank : Rank.values()) {
            totalProfit += results.get(rank) * rank.getValue();
        }
        double rate = ((double) totalProfit / money) * 100;
        rate = Math.round(rate * 100) / 100.0;

        // then
        assertThat(rate).isEqualTo(62.5);
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
