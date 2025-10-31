package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        int money = inputMoney();
        int count = money / 1000; //주어진금액으로 살 수 있는 로또 장수 계산


    }

    //구입금액 입력
    private static int inputMoney() {
        while (true) {
            try {
                System.out.println("구입금액을 입력해 주세요.");
                int money = Integer.parseInt(Console.readLine());
                if (money <= 0 || money % 1000 != 0) {
                    throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
                }
                return money;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해야 합니다. 다시 입력 해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " 다시 입력 해주세요.");
            }
        }

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
        while (true) {
            try {
                System.out.println("당첨 번호를 입력해 주세요.");
                List<Integer> winningNumbers = Arrays.stream(Console.readLine().split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                validateWinningNumbers(winningNumbers);
                Collections.sort(winningNumbers);
                return winningNumbers;
            }catch (NumberFormatException e) {
                System.out.println("숫자를 입력해야 합니다. 다시 입력 해주세요.");
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " 다시 입력 해주세요.");
            }
        }
    }

    //당첨번호 검증하기
    private static void validateWinningNumbers(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("숫자 6개를 입력해주세요.");
        }
        Set<Integer> set = new HashSet<>(winningNumbers);
        if (set.size() != 6) {
            throw new IllegalArgumentException("숫자가 중복되었습니다. 다시 입력 해주세요.");
        }
        for(int number : set) {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("숫자는 1-45 사이여야 합니다.");
            }
        }
    }

    //보너스 번호 입력 --> 2등을 위해
    private static int inputBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        return Integer.parseInt(Console.readLine());
    }

    //당첨 통계 출력 --> enum개념 활용
    private static Map<Rank, Integer> calculateResults(List<Lotto> lottotickets, List<Integer> winningNumbers, int bonusNumber) {
        Map<Rank, Integer> results = new LinkedHashMap<>();
        for (Rank rank : Rank.values()) results.put(rank, 0);
        for (Lotto lottoticket : lottotickets) {
            long matchCount = lottoticket.getNumbers().stream().filter(winningNumbers::contains).count();
            if (matchCount == 6) {
                results.put(Rank.FIRST, results.get(Rank.FIRST) + 1);
            }
            if (matchCount ==5 && lottoticket.getNumbers().contains(bonusNumber)) {
                results.put(Rank.SECOND, results.get(Rank.SECOND) + 1);
            }
            if (matchCount ==5) {
                results.put(Rank.THIRD, results.get(Rank.THIRD) + 1);
            }
            if (matchCount == 4) {
                results.put(Rank.FOURTH, results.get(Rank.FOURTH) + 1);
            }
            if (matchCount == 3) {
                results.put(Rank.FIFTH, results.get(Rank.FIFTH) + 1);
            }
        }
        return results;
    }

    //수익률 계산
    private static void calculateProfit(Map<Rank, Integer> results, int money) {

    }
}
