package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        int money = inputMoney();

    }

    //구입금액 입력
    private static int inputMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        int money = Integer.parseInt(Console.readLine());
        if (money % 1000 != 0) {
            throw new IllegalArgumentException("구입 금액은 1,000원 단위여야 합니다.");
        }
        return money;
    }
    //주어진금액으로 살 수 있는 로또 장수 계산

    //로또번호 만들어서 반환
    private static List<Integer> printLotto(List<Integer> numbers) {
        int[] randomNumbers = new int[6];
        for (int i = 0; i < randomNumbers.length(); i++) {
            Randoms.pickUniqueNumbersInRange(1, 45, 6);
        }
        Arrays.sort(randomNumbers);
        System.out.println(Arrays.toString(randomNumbers));
    }

    //여러장의 로또 생성 해서 출력

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
