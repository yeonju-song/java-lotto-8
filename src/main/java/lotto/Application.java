package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        int money = inputMoney();
        int count = money / 1000; //주어진금액으로 살 수 있는 로또 장수 계산


    }

    //구입금액 입력
    private static int inputMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        int money = Integer.parseInt(Console.readLine());
        if (money % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
        return money;
    }

    //각 로또의 번호를 랜덤으로 생성
    private static List<Lotto> makeLottoNumbers(int count) {
        List<Lotto> lottotickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6); //단순히 랜덤으로 뽑음
            lottotickets.add(new Lotto(numbers)); //모든 로또티켓의 숫자들의 정렬, 검증, 출력을 Lotto.java에서 담당
        }
        return  lottotickets;
    }

    //로또 장수와 번호를 출력
    private static void printLottoTickets(List<Lotto> lottotickets) {
        System.out.println(lottotickets.size() + "개를 구매했습니다.");
        for (Lotto lottoticket : lottotickets) {
            System.out.println(lottoticket.getNumbers());
        }
    }

    //당첨번호 입력
    private static List<Integer> inputWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        return Arrays.stream(Console.readLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    //보너스 번호 입력
    private static int inputBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        return Integer.parseInt(Console.readLine());
    }


}
