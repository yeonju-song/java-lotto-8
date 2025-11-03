package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        int money = inputMoney();
        List<Lotto> lottotickets = makeLottoNumbers(money / 1000);
        printLottoTickets(lottotickets);

        List<Integer> winningNumbers = inputWinningNumbers();
        int bonusNumber = inputBonusNumber(winningNumbers);

        Map<Rank, Integer> ranks = calculateResults(lottotickets, winningNumbers, bonusNumber);

        printResults(ranks);
        calculateProfit(ranks, money);
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
        System.out.println();
        System.out.println(lottotickets.size() + "개를 구매했습니다.");
        for (Lotto lottoticket : lottotickets) {
            System.out.println(lottoticket.getNumbers());
        }
    }

    //당첨번호 입력
    private static List<Integer> inputWinningNumbers() {
        while (true) {
            try {
                System.out.println();
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
    private static int inputBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                System.out.println();
                System.out.println("보너스 번호를 입력해 주세요.");
                int bonusNumber = Integer.parseInt(Console.readLine().trim());

                if (bonusNumber < 1 || bonusNumber > 45) {
                    throw new IllegalArgumentException("숫자는 1-45 사이여야 합니다.");
                }
                if (winningNumbers.contains(bonusNumber)) {
                    throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복되면 안됩니다.");
                }

                return bonusNumber;
            }catch (NumberFormatException e) {
                System.out.println("숫자를 입력해야 합니다. 다시 입력 해주세요.");
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "다시 입력해주세요.");
            }
        }
    }

    //당첨 통계 로직 --> enum개념 활용
    private static Map<Rank, Integer> calculateResults(List<Lotto> lottotickets, List<Integer> winningNumbers, int bonusNumber) {
        Map<Rank, Integer> results = new LinkedHashMap<>();
        for (Rank rank : Rank.values()) {
            results.put(rank, 0);
        }
        for (Lotto lottoticket : lottotickets) {
            long matchCount = lottoticket.getNumbers().stream().filter(winningNumbers::contains).count();
            if (matchCount == 3) {
                results.put(Rank.FIFTH, results.get(Rank.FIFTH) + 1);
            }
            if (matchCount == 4) {
                results.put(Rank.FOURTH, results.get(Rank.FOURTH) + 1);
            }
            if (matchCount ==5 && !lottoticket.getNumbers().contains(bonusNumber)) {
                results.put(Rank.THIRD, results.get(Rank.THIRD) + 1);
            }
            if (matchCount ==5 && lottoticket.getNumbers().contains(bonusNumber)) {
                results.put(Rank.SECOND, results.get(Rank.SECOND) + 1);
            }
            if (matchCount == 6) {
                results.put(Rank.FIRST, results.get(Rank.FIRST) + 1);
            }
        }
        return results;
    }

    //당첨 통계 출력
    private static void printResults(Map<Rank, Integer> results) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");

        Arrays.stream(Rank.values())
                .sorted(Comparator.comparingInt(Rank::getOrder))
                .forEach(rank ->
                        System.out.println(rank.getName() + " - " + results.get(rank) + "개"));
    }

    //수익률 계산 & 출력
    private static void calculateProfit(Map<Rank, Integer> results, int money) {
        long totalProfit = 0;

        for (Rank rank : Rank.values() ) { //등수별 당첨금 누적
            totalProfit += results.get(rank) * rank.getValue();
        }

        double rate = ((double) totalProfit / money) * 100;
        rate = Math.round(rate * 100) / 100.0;
        System.out.println("총 수익률은 " + rate + "%입니다.");
    }
}
